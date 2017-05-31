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

import com.example.matthew.book.Activities.PageTurner;
import com.example.matthew.book.R;
import com.example.matthew.book.Util.DrawMultipleStates2Input;
import com.example.matthew.book.Util.DrawableMutlipleStates;
import com.example.matthew.book.customview.SunAndRainProgress;

import java.util.ArrayList;
import java.util.Arrays;


public class PageFour extends Page implements View.OnTouchListener {
    Button seedbutt1, seedbutt2, seedbutt3, cloud, sun;
    DrawMultipleStates2Input seedstattes1, seedstates2, seedstates3;
    View masterView;
    int updateLimiter = 0;
    Float lastX = 0f, lastY = 0f;
    boolean bool = true;
    //    boolean bool = false;
    public Handler handler;
    int indexRain = 0;
    int indexSun = 0;
    MediaPlayer mp;
    Context _context;
    SunAndRainProgress sr1, sr2, sr3;
    boolean cloudIsClicked = false;
    boolean sunIsClicked = false;
    DrawableMutlipleStates[] seeds;
    ArrayList<Drawable> myDrawablesSprout = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesRain = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesSun = new ArrayList<Drawable>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_four, container, false);
        handler = new Handler();

        sun = (Button) viewHierarchy.findViewById(R.id.Page4Sun);
        cloud = (Button) viewHierarchy.findViewById(R.id.Page5Minerals);
        seedbutt1 = (Button) viewHierarchy.findViewById(R.id.Page4SeedLeft);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.Page4SeedCenter);
        seedbutt3 = (Button) viewHierarchy.findViewById(R.id.Page4SeedRight);
        sr1 = (SunAndRainProgress) viewHierarchy.findViewById(R.id.rainsun1);
        sr2 = (SunAndRainProgress) viewHierarchy.findViewById(R.id.rainsun2);
        sr3 = (SunAndRainProgress) viewHierarchy.findViewById(R.id.rainsunmineral3);
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout0));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout1));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout2));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout3));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout4));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout5));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout6));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout7));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.sprout8));

        myDrawablesSun.add(getResources().getDrawable(R.drawable.page5sun1));
        myDrawablesSun.add(getResources().getDrawable(R.drawable.page5sun2));

        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain1));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain2));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain3));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain4));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain5));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain6));
        seeds = new DrawableMutlipleStates[3];
        seedstattes1 = new DrawMultipleStates2Input(myDrawablesSprout, 50);
        seedstates2 = new DrawMultipleStates2Input(myDrawablesSprout, 50);
        seedstates3 = new DrawMultipleStates2Input(myDrawablesSprout, 50);
        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt1, seedbutt2, seedbutt3, cloud, sun}));
        PageTurner.allButtons = new ArrayList<>(butttemp);

        cloud.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_BUTTON_PRESS){
                    PageTurner.dragginSomething = true;
                }else if(event.getAction()==MotionEvent.ACTION_BUTTON_RELEASE){
                    PageTurner.dragginSomething = false;
                }
                ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt1, seedbutt2, seedbutt3, cloud, sun}));
                PageTurner.allButtons = new ArrayList<>(butttemp);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cloudIsClicked = true;
                    mp = MediaPlayer.create(_context, R.raw.thunder);
                    mp.setLooping(true);
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

                    if ((v.getX() - xdiff) >= 0 && (v.getX() - xdiff) + v.getWidth() < masterView.getWidth()) {
                        v.setX((v.getX() - xdiff));
                    }
                    if (((v.getY() - ydiff) >= 0 && (v.getY() - ydiff) + v.getHeight() < masterView.getHeight())) {
                        v.setY((v.getY() - ydiff));
                    }
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
        });
        sun.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    PageTurner.dragginSomething = true;
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                }
                ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt1, seedbutt2, seedbutt3, cloud, sun}));
                PageTurner.allButtons = new ArrayList<>(butttemp);

                if (bool) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mp = MediaPlayer.create(_context, R.raw.sunny);
                        mp.setLooping(true);
                        sunIsClicked = true;
                        mp.start();
                        Log.e("click", "down");
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        mp.pause();
                        indexRain = 0;
                        sun.setBackground(getResources().getDrawable(R.drawable.page5sun1));
                        sunIsClicked = false;
                        Log.e("click", "up");
                        //    cloud.setBackground(getResources().getDrawable(R.drawable.page4rain0));
                    }
                    if (lastY == 0) {
                        lastX = event.getRawX();
                        lastY = event.getRawY();

                    } else {
                        float xdiff = lastX - event.getRawX();
                        float ydiff = lastY - event.getRawY();
                        if ((v.getX() - xdiff) >= 0 && (v.getX() - xdiff) + v.getWidth() < masterView.getWidth()) {
                            v.setX((v.getX() - xdiff));
                        }
                        if (((v.getY() - ydiff) >= 0 && (v.getY() - ydiff) + v.getHeight() < masterView.getHeight())) {
                            v.setY((v.getY() - ydiff));
                        }

                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        Button imageView = (Button) v;
                        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                            lastY = 0f;
                            lastX = 0f;
                        }
                    }


                }
                return true;
            }
        });
        masterView = viewHierarchy;
        final Clock clock = new Clock(handler, cloud);
        clock.run();
        return viewHierarchy;
    }

    private void isRainOverlapping() {
        if (cloud.getX() + (cloud.getWidth() / 2) < (masterView.getWidth() / 3)) {
            seedbutt1.setBackground(seedstattes1.update(true));
        } else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth() * (2f / 3f)) {
            seedbutt2.setBackground(seedstates2.update(true));
        } else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth()) {
            seedbutt3.setBackground(seedstates3.update(true));
        }

    }

    private void isSunOverlapping() {
        if (sun.getX() + (sun.getWidth() / 2) < (masterView.getWidth() / 3)) {
            seedbutt1.setBackground(seedstattes1.update(false));

        } else if (sun.getX() + sun.getWidth() / 2 < masterView.getWidth() * (2f / 3f)) {
            seedbutt2.setBackground(seedstates2.update(false));
        } else if (sun.getX() + sun.getWidth() / 2 < masterView.getWidth()) {
            seedbutt3.setBackground(seedstates3.update(false));
        }

    }

    public boolean checkButtOverlap(Button butt1, Button butt2) {
        return ((butt1.getX() > butt2.getX() && butt1.getX() < butt2.getX() + butt2.getWidth())
                || (butt1.getX() + butt1.getWidth() > butt2.getX() && butt1.getX() + butt1.getWidth() < butt2.getX() + butt2.getWidth()));

    }

    @Override
    public void passMediaPlayer(Context context) {
        mp = MediaPlayer.create(context, R.raw.thunder);
        _context = context;

        mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "To grow my stem, leaves, and roots to become a sapling, I need water and sunlight.  Could you help me?";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
    }

    @Override
    public boolean doneTouching() {
        if (seedstattes1 == null) {
            return true;
        } else {
            return (seedstattes1.allComplete() && seedstates2.allComplete() && seedstates3.allComplete());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    class Clock implements Runnable {
        private Handler handler;
        private View view;

        public Clock(Handler handler, View v) {

            this.handler = handler;
            view = v;
        }

        public void run() {
            sr1.update(seedstattes1.getFirstFraction(), seedstattes1.getSecondFraction());
            sr2.update(seedstates2.getFirstFraction(), seedstates2.getSecondFraction());
            sr3.update(seedstates3.getFirstFraction(), seedstates3.getSecondFraction());

            if (cloudIsClicked) {

                isRainOverlapping();
                if (indexRain + 1 == myDrawablesRain.size()) {
                    indexRain = 0;
                } else {
                    indexRain++;
                }
                if (indexRain == 0) {

                }
                cloud.setBackground(myDrawablesRain.get(indexRain));
            } else if (sunIsClicked) {
                isSunOverlapping();
                indexSun++;
                sun.setBackground(myDrawablesSun.get(indexSun));
                if (indexSun + 1 == (myDrawablesSun.size())) {
                    indexSun = myDrawablesSun.size() - 3;
                }
            }
            handler.postDelayed(this, 70);
        }
    }
}
