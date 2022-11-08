#!/usr/bin/python

from mininet.node import Controller, OVSKernelSwitch, Host, RemoteController
from mininet.log import setLogLevel, info
from mn_wifi.net import Mininet_wifi
from mn_wifi.node import Station, OVSKernelAP
from mn_wifi.cli import CLI
from mininet.link import TCLink
from functools import partial


def Topology():

    OVSKernelSwitch13 = partial(OVSKernelSwitch, protocols='OpenFlow13')
    OVSKernelAP13 = partial(OVSKernelAP, protocols='OpenFlow13')

    net = Mininet_wifi(controller=RemoteController, switch=OVSKernelSwitch13, accessPoint=OVSKernelAP13, link=TCLink)

    info("*** Adding controller\n")
    c0 = net.addController(name='c0', controller=RemoteController, ip='127.0.0.1', protocol='tcp', port=6653)

    info("*** Add switches/APs\n")
    ap1 = net.addAccessPoint('ap1', cls=OVSKernelAP13, ssid='ssid_1', channel='1', mode='g', position='703.0,737.0,0')
    ap2 = net.addAccessPoint('ap2', cls=OVSKernelAP13, ssid='ssid_2', channel='1', mode='g', position='715.0,225.0,0')
    ap3 = net.addAccessPoint('ap3', cls=OVSKernelAP13, ssid='ssid_3', channel='1', mode='g', position='1192.0,227.0,0')
    ap4 = net.addAccessPoint('ap4', cls=OVSKernelAP13, ssid='ssid_4', channel='1', mode='g', position='1204.0,746.0,0')
    s1 = net.addSwitch('s1', cls=OVSKernelSwitch13)
    s2 = net.addSwitch('s2', cls=OVSKernelSwitch13)
    s3 = net.addSwitch('s3', cls=OVSKernelSwitch13)

    info("*** Add hosts/stations\n")
    h1 = net.addHost('h1', cls=Host, ip='10.0.0.1', defaultRoute=None, mac='00:00:00:00:00:01')
    sta1 = net.addStation('sta1', ip='10.0.0.1', position='949.0,1256.0,0')

    info("*** Configuring Propagation Model\n")
    net.setPropagationModel(model="logDistance", exp=3)

    info("*** Configuring wifi nodes\n")
    net.configureWifiNodes()

    info("*** Add links\n")
    net.addLink(h1, s1)
    net.addLink(s1, s2)
    net.addLink(s1, s3)
    net.addLink(s2, ap2)
    net.addLink(s3, ap3)
    net.addLink(ap2, ap1)
    net.addLink(ap3, ap4)
    net.addLink(ap1, sta1)
    net.addLink(sta1, ap4)

    net.plotGraph(max_x=2000, max_y=2000)

    info("*** Starting network\n")
    net.build()
    info("*** Starting controllers\n")
    for controller in net.controllers:
        controller.start()

    info("*** Starting switches/APs\n")
    net.get('ap1').start([c0])
    net.get('ap2').start([c0])
    net.get('ap3').start([c0])
    net.get('ap4').start([c0])
    net.get('s1').start([c0])

    info("*** Post configure nodes\n")

    CLI(net)
    net.stop()


if __name__ == '__main__':
    setLogLevel( 'info' )
    Topology()

