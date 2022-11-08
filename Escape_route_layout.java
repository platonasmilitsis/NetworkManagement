package com.dji.smart_evacuation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.Random;


public class Escape_route_layout extends View {
    Paint green_brush; //green line for exit route
    Bitmap location,fire; //icons for location & fire
    public boolean flag= true; //true: show user's location, false: show there is a fire
    public boolean path=false;
    final int random = new Random().nextInt(7); //random number for fire's location
    Path route = new Path();
    public int state = 0; // user's room
    public Escape_route_layout(Context context) {
        super(context);
        setBackground(ContextCompat.getDrawable(context, R.drawable.katopsi)); //background

    }

    //------convert density pixels for all screen's sizes

    private int px(int dps){

        return (int)(dps*getResources().getDisplayMetrics().density);
    }

    private float dpFromPixels(float px)
    {
        return px / this.getContext().getResources().getDisplayMetrics().density;
    }

    private float pixelsFromDp(float dp)
    {
        return dp * this.getContext().getResources().getDisplayMetrics().density;
    }
//------

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        green_brush = new Paint();
        green_brush.setColor(Color.GREEN);
        green_brush.setStyle(Paint.Style.STROKE);
        green_brush.setStrokeWidth(20);


    if (flag==true) {
        //room 1
         if (state == 1) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 300, 550, null);
         }
         //room 2
        if (state == 2) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 150, 550, null);
        }
        if (state == 3) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 150, 400, null);
         }
        if (state == 4) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 150, 100, null);
        }
        if (state == 5) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 320, 100, null);
            }
        if (state == 6) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 500, 100, null);
        }
        if (state == 7) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 680, 100, null);
        }
        if (state == 8) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 1050, 400, null);
        }
        //reception
        if (state == 9) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 850, 400, null);
        }
        //conference room
        if (state == 10) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 400, 400, null);
        }
        //workstation
        if (state == 11) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 1050, 100, null);
        }
    }
    if(flag==false){

        //room1
        if (random == 0) {
            fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
            canvas.drawBitmap(fire, 300, 550, null);

        }
        //room 4
        if (random == 1) {
            fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
            canvas.drawBitmap(fire, 150, 100, null);

        }
        //room 8
        if (random == 2) {
            fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
            canvas.drawBitmap(fire, 1050, 400, null);

        }
        //reception
        if (random == 3) {
            fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
            canvas.drawBitmap(fire, 1000, 400, null);

        }
        //workstation
        if (random == 4) {
            fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
            canvas.drawBitmap(fire, 1050, 80, null);

        }
        //conference room
        if (random == 5) {
            fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
            canvas.drawBitmap(fire, 1200, 600, null);

        }
        //corridor
        if (random == 6) {
            fire = BitmapFactory.decodeResource(getResources(), R.drawable.flame);
            canvas.drawBitmap(fire, 1070, 380, null);

        }


    }
    //route appears after clicking the 'ok' on the fire alert
    if(path==true) {
        if (state == 1) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 300, 550, null);

            //drawing the route on the canva

            route.moveTo(400, 620);
            route.lineTo(1650, 620);
            route.moveTo(1650, 620);
            route.lineTo(1650, 730);

            route.moveTo(1650, 725);
            route.lineTo(1500,725);
            canvas.drawPath(route, green_brush);

        }
        if (state == 2 ) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 150, 550, null);

            //fire in room 1 or in corridor
            if (random==0 || random==5) {
                route.moveTo(300, 620);
                route.lineTo(380, 620);

                route.moveTo(380, 620);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(1410, 330);

                route.moveTo(1410, 330);
                route.lineTo(1410, 620);

                route.moveTo(1410, 620);
                route.lineTo(1650, 620);

                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);

            }
            //classic route
            else{
                route.moveTo(300, 620);
                route.lineTo(1650, 620);
                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500,725);

            }


            canvas.drawPath(route, green_brush);


        }

        if (state == 3) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 150, 400, null);

            //fire in room 1 or in corridor
           if (random==0 || random==5) {
                route.moveTo(300, 550);
                route.lineTo(380, 550);

                route.moveTo(380, 550);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(1410, 330);

                route.moveTo(1410, 330);
                route.lineTo(1410, 620);

                route.moveTo(1410, 620);
                route.lineTo(1650, 620);

                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);

           }
           else{
               route.moveTo(330, 550);
               route.lineTo(380, 550);

               route.moveTo(380, 550);
               route.lineTo(380, 620);

               route.moveTo(380, 620);
               route.lineTo(1650, 620);
               route.moveTo(1650, 620);
               route.lineTo(1650, 730);

               route.moveTo(1650, 725);
               route.lineTo(1500,725);

           }
            canvas.drawPath(route, green_brush);

        }
        if (state == 4) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 150, 100, null);

            //fire in room 1, corridor or conference
            if(random == 0 || random ==5 || random ==6 ) {
                route.moveTo(310, 250);
                route.lineTo(310, 330);

                route.moveTo(310, 330);
                route.lineTo(1410, 330);

                route.moveTo(1410, 330);
                route.lineTo(1410, 620);

                route.moveTo(1410, 620);
                route.lineTo(1650, 620);

                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);

            }
            else {
                route.moveTo(310, 250);
                route.lineTo(310, 330);

                route.moveTo(310, 330);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(380, 620);

                route.moveTo(380, 620);
                route.lineTo(1650, 620);
                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500,725);

            }

            canvas.drawPath(route, green_brush);
        }

        if (state == 5) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 320, 100, null);

            //fire in room 1, corridor or conference
           if(random == 0 || random ==5 || random ==6 ) {
                route.moveTo(380, 250);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(1410, 330);

                route.moveTo(1410, 330);
                route.lineTo(1410, 620);

                route.moveTo(1410, 620);
                route.lineTo(1650, 620);

                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);

           }
           else{

               route.moveTo(380, 250);
               route.lineTo(380, 620);

               route.moveTo(380, 620);
               route.lineTo(1650, 620);
               route.moveTo(1650, 620);
               route.lineTo(1650, 730);

               route.moveTo(1650, 725);
               route.lineTo(1500,725);

           }

            canvas.drawPath(route, green_brush);
        }

        if (state == 6) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 500, 100, null);

            //fire in room 2, reception or room 8
            if(random == 2 || random ==3 || random ==4) {

                route.moveTo(650, 290);
                route.lineTo(650, 330);


                route.moveTo(650, 330);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(380, 620);

                route.moveTo(380, 620);
                route.lineTo(1650, 620);
                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);
            }
            else
                {
                    route.moveTo(650,290);
                    route.lineTo(650,330);


                    route.moveTo(650, 330);
                    route.lineTo(1430, 330);
                    route.moveTo(1430, 330);
                    route.lineTo(1430, 620);


                    route.lineTo(1650, 620);
                    route.moveTo(1650,620);
                    route.lineTo(1650,725);

                    route.moveTo(1650, 725);
                    route.lineTo(1500,725);

                }

            canvas.drawPath(route, green_brush);

        }
        if (state == 7) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 680, 100,null);

            //fire in room 2, reception or room 8
            if(random == 2 || random ==3 || random ==4) {

                route.moveTo(700, 290);
                route.lineTo(700, 330);


                route.moveTo(700, 330);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(380, 620);

                route.moveTo(380, 620);
                route.lineTo(1650, 620);
                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);
            }
            else{
                route.moveTo(700,290);
                route.lineTo(700,330);


                route.moveTo(700, 330);
                route.lineTo(1430, 330);
                route.moveTo(1430, 330);
                route.lineTo(1430, 620);


                route.lineTo(1650, 620);
                route.moveTo(1650,620);
                route.lineTo(1650,725);

                route.moveTo(1650, 725);
                route.lineTo(1500,725);
            }


            canvas.drawPath(route, green_brush);

        }

        if (state == 8) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 1050, 400,null);

            route.moveTo(1150, 380);
            route.lineTo(1150, 330);


            route.moveTo(1150, 330);
            route.lineTo(1430, 330);
            route.moveTo(1430, 330);
            route.lineTo(1430, 620);


            route.lineTo(1650, 620);
            route.moveTo(1650,620);
            route.lineTo(1650,725);

            route.moveTo(1650, 725);
            route.lineTo(1500,725);

            canvas.drawPath(route, green_brush);

        }
        //reception
        if (state == 9) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 850, 400,null);

            //fire in room 8 or workstation
            if(random==2 || random == 4) {

                route.moveTo(900, 380);
                route.lineTo(900, 330);


                route.moveTo(900, 330);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(380, 620);

                route.moveTo(380, 620);
                route.lineTo(1650, 620);
                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);
            }
            else
                {
                    route.moveTo(900,380);
                    route.lineTo(900,330);


                    route.moveTo(900, 330);
                    route.lineTo(1430, 330);
                    route.moveTo(1430, 330);
                    route.lineTo(1430, 620);


                    route.lineTo(1650, 620);
                    route.moveTo(1650,620);
                    route.lineTo(1650,725);

                    route.moveTo(1650, 725);
                    route.lineTo(1500,725);

                }


            canvas.drawPath(route, green_brush);

        }
        //conference room
        if (state == 10) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 400, 400,null);

            if(random==0 ||random==6) {

                route.moveTo(420, 550);
                route.lineTo(380, 550);

                route.moveTo(380, 550);
                route.lineTo(380, 330);

                route.moveTo(380, 330);
                route.lineTo(1410, 330);

                route.moveTo(1410, 330);
                route.lineTo(1410, 620);

                route.moveTo(1410, 620);
                route.lineTo(1650, 620);

                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500, 725);
            }
            else {
                route.moveTo(420, 550);
                route.lineTo(380, 550);

                route.moveTo(380, 550);
                route.lineTo(380, 620);

                route.moveTo(380, 620);
                route.lineTo(1650, 620);
                route.moveTo(1650, 620);
                route.lineTo(1650, 730);

                route.moveTo(1650, 725);
                route.lineTo(1500,725);
            }

            canvas.drawPath(route, green_brush);


        }
        //workstation
        if (state == 11) {
            location = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);
            canvas.drawBitmap(location, 1050, 100,null);

            route.moveTo(1150, 300);
            route.lineTo(1150, 330);


            route.moveTo(1150, 330);
            route.lineTo(1430, 330);
            route.moveTo(1430, 330);
            route.lineTo(1430, 620);


            route.lineTo(1650, 620);
            route.moveTo(1650,620);
            route.lineTo(1650,725);

            route.moveTo(1650, 725);
            route.lineTo(1500,725);

            canvas.drawPath(route, green_brush);


        }

    }

    }
}
