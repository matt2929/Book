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
import com.example.matthew.book.Util.DrawMultipleStates3Input;
import com.example.matthew.book.Util.DrawableMutlipleStates;
import com.example.matthew.book.customview.SunAndRain;
import com.example.matthew.book.customview.SunAndRainAndMineral;

import java.util.ArrayList;
import java.util.Arrays;


public class PageSix extends Page implements View.OnTouchListener {
    Button seedbutt2, cloud, sun, mineral;
    DrawMultipleStates3Input seedstates2;
    View masterView;
    int updateLimiter = 0;
    Float lastX = 0f, lastY = 0f;
    boolean bool = false;
    public Handler handler;
    int indexRain = 0;
    int indexSun = 0;
    int indexMineral = 0;
    MediaPlayer mp;
    Context _context;
    SunAndRainAndMineral sr2;
    boolean cloudIsClicked = false;
    boolean sunIsClicked = false;
    boolean mineralIsClicked = false;
    DrawableMutlipleStates[] seeds;
    ArrayList<Drawable> myDrawablesSprout = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesRain = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesSun = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesMineral = new ArrayList<Drawable>();

    Button[] seedButts;
    boolean[] completeGrowth = new boolean[]{false, false, false};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_six, container, false);
        handler = new Handler();
        sun = (Button) viewHierarchy.findViewById(R.id.page6sun);
        cloud = (Button) viewHierarchy.findViewById(R.id.Page6Cloud);
        mineral = (Button) viewHierarchy.findViewById(R.id.Page6mineral);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.Page6Seed2);

        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt2, cloud, sun, mineral}));
        PageTurner.allButtons = new ArrayList<>(butttemp);

        sr2 = (SunAndRainAndMineral) viewHierarchy.findViewById(R.id.rainsunmineral2);
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6tree1));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6tree2));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6tree3));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6tree4));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6tree5));
       myDrawablesSprout.add(getResources().getDrawable(R.drawable.page71));

        myDrawablesSun.add(getResources().getDrawable(R.drawable.page5sun1));
        myDrawablesSun.add(getResources().getDrawable(R.drawable.page5sun2));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain1));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain2));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain3));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain4));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain5));
        myDrawablesRain.add(getResources().getDrawable(R.drawable.page4rain6));
        myDrawablesMineral.add(getResources().getDrawable(R.drawable.minerals1));
        myDrawablesMineral.add(getResources().getDrawable(R.drawable.minerals2));
        myDrawablesMineral.add(getResources().getDrawable(R.drawable.minerals3));
        myDrawablesMineral.add(getResources().getDrawable(R.drawable.minerals4));
        seeds = new DrawableMutlipleStates[3];
        seedstates2 = new DrawMultipleStates3Input(myDrawablesSprout, 250);
        cloud.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt2, cloud, sun, mineral}));
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
                ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt2, cloud, sun, mineral}));
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
        mineral.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt2, cloud, sun, mineral}));
                PageTurner.allButtons = new ArrayList<>(butttemp);
                if (bool) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        //mp = MediaPlayer.create(_context, R.raw.sunny);
                        //mp.setLooping(true);
                        mineralIsClicked = true;
                        //mp.start();
                        Log.e("click", "down");
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        mp.pause();
                        indexMineral = 0;
                        mineral.setBackground(getResources().getDrawable(R.drawable.minerals1));
                        mineralIsClicked = false;
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
        } else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth() * (2f / 3f)) {
            seedbutt2.setBackground(seedstates2.update(0));
        } else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth()) {
        }

    }

    private void isSunOverlapping() {
        if (sun.getX() + (sun.getWidth() / 2) < (masterView.getWidth() / 3)) {

        } else if (sun.getX() + sun.getWidth() / 2 < masterView.getWidth() * (2f / 3f)) {
            seedbutt2.setBackground(seedstates2.update(1));
        } else if (sun.getX() + sun.getWidth() / 2 < masterView.getWidth()) {
        }

    }


    private void isMineralOverlapping() {
        if (mineral.getY() > masterView.getHeight() * (2f / 5f)) {
            if (mineral.getX() + (mineral.getWidth() / 2) < (masterView.getWidth() / 3)) {

            } else if (mineral.getX() + mineral.getWidth() / 2 < masterView.getWidth() * (2f / 3f)) {
                seedbutt2.setBackground(seedstates2.update(2));
            } else if (mineral.getX() + mineral.getWidth() / 2 < masterView.getWidth()) {
            }
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
        return "When I grow bigger into a tree, I will develop apple blossoms that will grow into apples. Can you help me grow bigger?";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
    }

    @Override
    public boolean doneTouching() {
        if (seedstates2==null) {
            return true;
        }else{
            return (seedstates2.allComplete());
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
            sr2.update(seedstates2.getFirstFraction(), seedstates2.getSecondFraction(), seedstates2.getThirdFraction());

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
            } else if (mineralIsClicked) {
                isMineralOverlapping();
                indexMineral++;
                mineral.setBackground(myDrawablesMineral.get(indexMineral));
                if (indexMineral + 1 == (myDrawablesMineral.size())) {
                    indexMineral = 0;
                }
            }
            handler.postDelayed(this, 70);
        }
    }
}
