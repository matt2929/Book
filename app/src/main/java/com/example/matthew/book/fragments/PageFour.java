package com.example.matthew.book.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.DrawableMutlipleStates;

import java.util.ArrayList;


public class PageFour extends Page implements View.OnTouchListener {
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
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_four, container, false);
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
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page4rain1));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page4rain2));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page4rain3));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page4rain4));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page4rain5));
        myDrawablesCloud.add(getResources().getDrawable(R.drawable.page4rain6));
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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cloudIsClicked = true;
            mp.start();
            Log.e("click", "down");
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mp.pause();
            cloudIsClicked = false;
            Log.e("click", "up");
            cloud.setBackground(getResources().getDrawable(R.drawable.page4rain0));
        }
        if (lastY == 0) {
            lastX = event.getRawX();
            lastY = event.getRawY();

        } else {


            float xdiff = lastX - event.getRawX();
            float ydiff = lastY - event.getRawY();

            //  if ((v.getX() - xdiff) + v.getWidth() > masterView.getWidth()
            //|| (v.getX() - xdiff) < 0
            //  || (v.getY() - ydiff) + v.getHeight() > masterView.getHeight()
            //    || (v.getY() - ydiff) < 0
            //      ) {
            //} else {
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

        return true;

    }


    private void isViewOverlapping() {
        if(cloud.getX()>seedbutt1.getX()-50&&cloud.getX()<seedbutt1.getX()+seedbutt1.getWidth()+100){
            seedbutt1.setBackground(seedstattes1.update());
        Log.e("IS","1");
        }if(cloud.getX()>seedbutt2.getX()-50&&cloud.getX()<seedbutt2.getX()+seedbutt2.getWidth()+100){
            seedbutt2.setBackground(seedstates2.update());
            Log.e("IS","2");
        } if(cloud.getX()>seedbutt3.getX()-50&&cloud.getX()<seedbutt3.getX()+seedbutt3.getWidth()+100){
            seedbutt3.setBackground(seedstates3.update());
            Log.e("IS","3");
        }

    }

    @Override
    public void passMediaPlayer(Context context) {
        mp = MediaPlayer.create(context, R.raw.thunder);
        mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "The dirt feels soft and cool as it covers my little seed body. Can you help cover me with dirt so that I can start to grow beneath the ground?\n";
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
                if (index + 1 == myDrawablesCloud.size()) {
                    index = 0;
                } else {
                    index++;
                }
                cloud.setBackground(myDrawablesCloud.get(index));
            }
            handler.postDelayed(this, 70);
        }
    }
}
