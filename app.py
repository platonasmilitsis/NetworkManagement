from operator import attrgetter
from ryu.controller import ofp_event
from ryu.controller.handler import MAIN_DISPATCHER, DEAD_DISPATCHER, CONFIG_DISPATCHER
from ryu.ofproto import ofproto_v1_3
from ryu.controller.handler import set_ev_cls
from ryu.base import app_manager
from ryu.controller import mac_to_port
from ryu.lib.packet import packet
from ryu.lib.packet import ethernet
from ryu.lib.packet import ether_types
from ryu.lib.packet import in_proto
from ryu.lib.packet import ipv4
from ryu.lib.packet import icmp
from ryu.lib.packet import tcp
from ryu.lib.packet import udp
from ryu.lib.packet import arp
from ryu.ofproto import ether
from ryu.topology import event, switches
from ryu.topology.api import get_switch, get_link
from ryu.lib import hub
import time
import networkx as nx

#time for the next topology checking
DISCOVERY_INERVAL = 15

class FailureDetection(app_manager.RyuApp):
    OFP_VERSIONS = [ofproto_v1_3.OFP_VERSION]
    
    def __init__(self, *args, **kwargs):
        super(FailureDetection, self).__init__(*args, **kwargs)
        self.mac_to_port = {}
        #create a graph with nodes the switches and aps and for edges the links
        self.net = nx.DiGraph()
        self.topology_api_app = self
        #thread for checking the topology 
        self.topodiscovery_thread = hub.spawn(self._tdiscovery)
        self.datapaths = {}
        #when a topology discovered flag=1
        self.topo_flag = 0
        
    
    #every 15s get the topology    
    def _tdiscovery(self):
        while True:
            hub.sleep(DISCOVERY_INERVAL)
            self.get_topology_data()
            self.topo_flag = 1
        
    def get_topology_data(self):
        #delete the previous graph
        self.net.clear()
        #get the switches
        switch_list = get_switch(self.topology_api_app, None)
        switches = [switch.dp.id for switch in switch_list]
        #for each switch or ap add a node to the graph
        self.net.add_nodes_from(switches)
        #get the links
        links_list = get_link(self.topology_api_app, None)
        links = [(link.src.dpid,link.dst.dpid,{'port':link.src.port_no}) for link in links_list]
        #for each link add an edge to the graph
        self.net.add_edges_from(links)
        print('****links***')
        print(links)
        print('***** switches****')
        print(switches)
        
    @set_ev_cls(ofp_event.EventOFPSwitchFeatures, CONFIG_DISPATCHER)
    def switch_features_handler(self, ev):
        datapath = ev.msg.datapath
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser
        #install the table-miss flow entry
        match = parser.OFPMatch()
        actions = [parser.OFPActionOutput(ofproto.OFPP_CONTROLLER, ofproto.OFPCML_NO_BUFFER)]
        self.add_flow(datapath, 0, match, actions)
        
    def add_flow(self, datapath, priority, match, actions):
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser
        #construct flow_mod message and send it
        inst = [parser.OFPInstructionActions(ofproto.OFPIT_APPLY_ACTIONS, actions)]
        mod = parser.OFPFlowMod(datapath=datapath, priority=priority, match=match, instructions=inst)
        datapath.send_msg(mod)
        
        
    def delete_flow(self, datapath):
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser
        for dst in self.mac_to_port[datapath.id].keys():
            match = parser.OFPMatch(eth_dst=dst)
            mod = parser.OFPFlowMod(datapath, command=ofproto.OFPFC_DELETE, out_port=ofproto.OFPP_ANY, out_group=ofproto.OFPG_ANY, priority=1, match=match)
            datapath.send_msg(mod)
            
    
    @set_ev_cls(ofp_event.EventOFPPortStatus, MAIN_DISPATCHER)
    def port_status_handler(self, ev):
        #when an event on ports detected
        msg = ev.msg
        datapath = msg.datapath
        ofproto = datapath.ofproto
        if ((msg.reason == ofproto.OFPPR_DELETE) or (msg.reason == ofproto.OFPPR_MODIFY)):
            print("**** Change detected ****")
            #delete flow for this port
            if datapath.id in self.mac_to_port:
                self.delete_flow(datapath)
                del self.mac_to_port[datapath.id]
        
         
    @set_ev_cls(ofp_event.EventOFPPacketIn, MAIN_DISPATCHER)
    def _packet_in_handler(self, ev):
        #sleep until the topology discovered for the first time 
        while self.topo_flag == 0:
            hub.sleep(5)
        msg = ev.msg
        datapath = msg.datapath
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser
        dpid = datapath.id
        self.mac_to_port.setdefault(dpid, {})
        pkt = packet.Packet(msg.data)
        eth_pkt = pkt.get_protocol(ethernet.ethernet)
        dst = eth_pkt.dst
        src = eth_pkt.src
        #get the received port number from packet_in message
        in_port = msg.match['in_port']
        #learn a mac address to avoid FLOOD next time
        self.mac_to_port[dpid][src] = in_port
        #if the destination mac address is already learned,
        #decide which port to output the packet, otherwise FLOOD
        if dst in self.mac_to_port:
            out_port = self.mac_to_port[dpid][dst]
        else:
            out_port = ofproto.OFPP_FLOOD
        #construct action list
        actions = [parser.OFPActionOutput(out_port)]
        #install a flow to avoid packet_in next time
        if out_port != ofproto.OFPP_FLOOD:
            match = parser.OFPMatch(in_port=in_port, eth_dst=dst)
            self.add_flow(datapath, 1, match, actions)
        out = parser.OFPPacketOut(datapath=datapath, buffer_id=ofproto.OFP_NO_BUFFER, in_port=in_port, actions=actions, data=msg.data)
        datapath.send_msg(out)
        
