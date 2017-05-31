package com.example.matthew.book.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matthew.book.Activities.PageTurner;
import com.example.matthew.book.R;
import com.example.matthew.book.Util.DrawableMutlipleStates;
import com.example.matthew.book.customview.MineralProgress;

import java.util.ArrayList;
import java.util.Arrays;


public class PageFive extends Page implements View.OnTouchListener {
    Button seedbutt1, seedbutt2, seedbutt3, cloud;
    DrawableMutlipleStates seedstates1, seedstates2, seedstates3;
    MineralProgress mineralProgress1, mineralProgress2, mineralProgress3;
    View masterView;
    int updateLimiter = 0;
    Float lastX = 0f, lastY = 0f;
    boolean bool = true;
    //    boolean bool = false;
    public Handler handler;
    int index = 0;
    // Context _context;
    //   MediaPlayer mp;
    boolean cloudIsClicked = false;
    DrawableMutlipleStates[] seeds;
    ArrayList<Drawable> myDrawablesSprout = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesCloud = new ArrayList<Drawable>();
    Button[] seedButts;
    boolean[] completeGrowth = new boolean[]{ false, false, false };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_five, container, false);
        handler = new Handler();
        cloud = (Button) viewHierarchy.findViewById(R.id.Page5Minerals);
        seedbutt1 = (Button) viewHierarchy.findViewById(R.id.Page5SeedLeft);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.Page5SeedCenter);
        seedbutt3 = (Button) viewHierarchy.findViewById(R.id.Page5seedRight);
        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{ seedbutt1, seedbutt2, seedbutt3, cloud }));
        PageTurner.allButtons = new ArrayList<>(butttemp);
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout9));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout10));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout11));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout12));

        myDrawablesCloud.add(getResources().getDrawable(R.drawable.minerals1));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.minerals2));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.minerals3));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.minerals4));
        seeds = new DrawableMutlipleStates[3];
        seedstates1 = new DrawableMutlipleStates(myDrawablesSprout, 40);
        seedstates2 = new DrawableMutlipleStates(myDrawablesSprout, 40);
        seedstates3 = new DrawableMutlipleStates(myDrawablesSprout, 40);
        mineralProgress1 = (MineralProgress) viewHierarchy.findViewById(R.id.minProg1);
        mineralProgress2 = (MineralProgress) viewHierarchy.findViewById(R.id.minProg2);
        mineralProgress3 = (MineralProgress) viewHierarchy.findViewById(R.id.minProg3);
        cloud.setOnTouchListener(this);
        masterView = viewHierarchy;
        final Clock clock = new Clock(handler, cloud);
        clock.run();
        return viewHierarchy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            PageTurner.dragginSomething = true;
        }
        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{ seedbutt1, seedbutt2, seedbutt3, cloud }));
        PageTurner.allButtons = new ArrayList<>(butttemp);

        if ( bool ) {
            if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
                cloudIsClicked = true;

                Log.e("click", "down");
            } else if ( event.getAction() == MotionEvent.ACTION_UP ) {

                index = 0;
                cloud.setBackground(getResources().getDrawable(R.drawable.minerals1));
                cloudIsClicked = false;

                //    cloud.setBackground(getResources().getDrawable(R.drawable.page4rain0));
            }
            if ( lastY == 0 ) {
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
                if ( event.getAction() == android.view.MotionEvent.ACTION_UP ) {
                    lastY = 0f;
                    lastX = 0f;
                }
            }


        }
        return true;
    }


    private void isViewOverlapping() {

        if ( cloud.getY() + cloud.getHeight() > masterView.getHeight() * (4f / 5f) ) {
            if ( cloud.getX() + (cloud.getWidth() / 2) < (masterView.getWidth() / 3) ) {
                Log.e("sound", "start");
                seedbutt1.setBackground(seedstates1.update());
                seedbutt1.setScaleY(1f + (seedstates1.getProgress()/10f));
                mineralProgress1.update(seedstates1.getProgress());
            } else if ( cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth() * (2f / 3f) ) {
                Log.e("sound", "start");
                seedbutt2.setBackground(seedstates2.update());
                seedbutt2.setScaleY(1f + (seedstates2.getProgress()/10f));
                mineralProgress2.update(seedstates2.getProgress());

            } else if ( cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth() ) {
                Log.e("sound", "start");
                seedbutt3.setBackground(seedstates3.update());
                seedbutt3.setScaleY(1f + (seedstates3.getProgress()/10f));
                mineralProgress3.update(seedstates3.getProgress());
            }
        } else {
            Log.e("sound", "pause");

        }
    }

    public boolean checkButtOverlap(Button butt1, Button butt2) {
        return ((butt1.getX() > butt2.getX() && butt1.getX() < butt2.getX() + butt2.getWidth())
                || (butt1.getX() + butt1.getWidth() > butt2.getX() && butt1.getX() + butt1.getWidth() < butt2.getX() + butt2.getWidth()));


    }

    @Override
    public void passMediaPlayer(Context context) {
        //  mp = MediaPlayer.create(context, R.raw.slurp);
//_context=context;
        // mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "My roots take in nutrients from the soil that help me grow. Can you help my roots get the nutrients?";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
    }

    @Override
    public boolean doneTouching() {
        if ( seedstates1 == null ) {
            return true;
        } else {
            return (seedstates1.allComplete() && seedstates2.allComplete() && seedstates3.allComplete());
        }
    }

    class Clock implements Runnable {
        private Handler handler;
        private View view;

        public Clock(Handler handler, View v) {
            this.handler = handler;
            view = v;
        }

        public void run() {
            if ( cloudIsClicked ) {
                isViewOverlapping();
                index++;
                cloud.setBackground(myDrawablesCloud.get(index));
                if ( index + 1 == (myDrawablesCloud.size()) ) {
                    index = 0;
                }
            }
            handler.postDelayed(this, 70);
        }
    }
}
