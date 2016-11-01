package com.example.matthew.book.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.DrawableMutlipleStates;

import java.util.ArrayList;


public class PageFive extends Page implements View.OnTouchListener {
    Button seedbutt1, seedbutt2, seedbutt3, cloud;
    DrawableMutlipleStates seedstattes1, seedstates2, seedstates3;
    View masterView;
    int updateLimiter = 0;
    Float lastX = 0f, lastY = 0f;
    boolean bool = false;
    public Handler handler;
    int index = 0;
    MediaPlayer mp;
    boolean cloudIsClicked = false;
    DrawableMutlipleStates[] seeds;
    ArrayList<Drawable> myDrawablesSprout = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesCloud = new ArrayList<Drawable>();
    Button[] seedButts;
    boolean[] completeGrowth = new boolean[]{false, false, false};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_five, container, false);
        handler = new Handler();
        cloud = (Button) viewHierarchy.findViewById(R.id.Page4Cloud);
        seedbutt1 = (Button) viewHierarchy.findViewById(R.id.Page4Seed1);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.Page4Seed2);
        seedbutt3 = (Button) viewHierarchy.findViewById(R.id.Page4Seed3);
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page4sprout2));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page4sprout3));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page4sprout4));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page4sprout5));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page4sprout6));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.cloud9));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.cloud8));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.cloud7));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.cloud5));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.cloud4));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.cloud3));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.cloud2));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page5sun2));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page5sun1));
        seeds = new DrawableMutlipleStates[3];
        seedstattes1      = new DrawableMutlipleStates(myDrawablesSprout, 100);
        seedstates2 = new DrawableMutlipleStates(myDrawablesSprout, 100);
        seedstates3 = new DrawableMutlipleStates(myDrawablesSprout, 100);
        cloud.setOnTouchListener(this);
        masterView = viewHierarchy;
        final Clock clock = new Clock(handler, cloud);
        clock.run();
        return viewHierarchy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(bool) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                cloudIsClicked = true;
                mp.start();
                Log.e("click", "down");
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mp.pause();
                index = 0;
                cloud.setBackground(getResources().getDrawable(R.drawable.cloud9));
                cloudIsClicked = false;
                Log.e("click", "up");
                //    cloud.setBackground(getResources().getDrawable(R.drawable.page4rain0));
            }
            if (lastY == 0) {
                lastX = event.getRawX();
                lastY = event.getRawY();

            } else {


                float xdiff = lastX - event.getRawX();
                float ydiff = lastY - event.getRawY();
                v.setX((v.getX() - xdiff));
                v.setY((v.getY() - ydiff));
                lastX = event.getRawX();
                lastY = event.getRawY();
//                }
                Button imageView = (Button) v;
                if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    lastY = 0f;
                    lastX = 0f;
                }
            }


        }
        return true;
    }


    private void isViewOverlapping() {
        if(checkButtOverlap(seedbutt1,cloud)){
            seedbutt1.setBackground(seedstattes1.update());
            Log.e("IS","1");
        }if(checkButtOverlap(seedbutt2,cloud)){
            seedbutt2.setBackground(seedstates2.update());
            Log.e("IS","2");
        } if(checkButtOverlap(seedbutt3,cloud)){
            seedbutt3.setBackground(seedstates3.update());
            Log.e("IS","3");
        }

    }

    public boolean checkButtOverlap(Button butt1,Button butt2){
        return((butt1.getX()>butt2.getX()&&butt1.getX()<butt2.getX()+butt2.getWidth())
                ||(butt1.getX()+butt1.getWidth()>butt2.getX()&&butt1.getX()+butt1.getWidth()<butt2.getX()+butt2.getWidth()));


    }
    @Override
    public void passMediaPlayer(Context context) {
        mp = MediaPlayer.create(context, R.raw.thunder);
        mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "It is dark and I need sunlight to grow. Could you help me?\n";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
    }

    @Override
    public boolean doneTouching() {
        return (seedstattes1.allComplete()&&seedstates2.allComplete()&&seedstates3.allComplete());
    }

    class Clock implements Runnable {
        private Handler handler;
        private View view;

        public Clock(Handler handler, View v) {

            this.handler = handler;
            view = v;
        }

        public void run() {
            if (cloudIsClicked) {
                isViewOverlapping();
                index++;
                cloud.setBackground(myDrawablesCloud.get(index));
                if(index+1==(myDrawablesCloud.size())){
                    index=myDrawablesCloud.size()-3;
                }
            }
            handler.postDelayed(this, 70);
        }
    }
}
