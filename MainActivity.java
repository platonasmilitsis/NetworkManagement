package com.dji.smart_evacuation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Escape_route_layout escape_route_layout;
    String loc="";
    String[] location_list = new String[]{"Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Room 6", "Room 7", "Room 8", "Conference Room", "Reception","Open Workstation"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //using my layout with android canvas and NO with xml file
        escape_route_layout = new Escape_route_layout(this);
        setContentView(escape_route_layout);

        //call function alert() for choosing the location
        alert();

    }

    // alert box for choosing the user's location
    public void alert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Select Your Location");

        alertDialog.setSingleChoiceItems(location_list, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                loc = Arrays.asList(location_list).get(i);
                if (loc == "Room 1") {
                    escape_route_layout.state = 1;

                }
                if (loc == "Room 2") {
                    escape_route_layout.state = 2;

                }
                if (loc == "Room 3") {
                    escape_route_layout.state = 3;

                }
                if (loc == "Room 4") {
                    escape_route_layout.state = 4;

                }
                if (loc == "Room 5") {
                    escape_route_layout.state = 5;

                }
                if (loc == "Room 6") {
                    escape_route_layout.state = 6;

                }
                if (loc == "Room 7") {
                    escape_route_layout.state = 7;

                }
                if (loc == "Room 8") {
                    escape_route_layout.state = 8;

                }
                if (loc == "Reception") {
                    escape_route_layout.state = 9;

                }
                if (loc == "Conference Room") {
                    escape_route_layout.state = 10;

                }
                if (loc == "Open Workstation") {
                    escape_route_layout.state = 11;

                }

            }
        });

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if user doesn't choose
                if (loc==""){
                    Toast.makeText(getApplicationContext(),"Please Select Your Location",Toast.LENGTH_LONG).show();

                }
                else{
                    //call the class for drawing on the canva
                    escape_route_layout.invalidate();
                    //timer for the fire alert
                    new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {

                            //call the function firealert for the notification of fire
                            firealert();
                        }

                    }.start();

                    dialog.dismiss();

                }
            }
        });
    }

    //alert box for the fire
    public void firealert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Fire Alert");
        alertDialog.setMessage("Follow the route to evacuate the building");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                escape_route_layout.path = true;
                escape_route_layout.flag = false;
                escape_route_layout.invalidate();

            }
        });
        AlertDialog dialogf = alertDialog.create();
        dialogf.show();
        dialogf.setCanceledOnTouchOutside(false);
    }
}