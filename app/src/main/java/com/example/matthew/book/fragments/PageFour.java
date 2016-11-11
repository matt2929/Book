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
import android.widget.RelativeLayout;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.DrawMultipleStates2Input;
import com.example.matthew.book.Util.DrawableMutlipleStates;
import com.example.matthew.book.customview.SunAndRain;

import java.util.ArrayList;


public class PageFour extends Page implements View.OnTouchListener {
    Button seedbutt1, seedbutt2, seedbutt3, cloud, sun;
    DrawMultipleStates2Input seedstattes1, seedstates2, seedstates3;
    View masterView;
    int updateLimiter = 0;
    Float lastX = 0f, lastY = 0f;
    boolean bool = false;
    public Handler handler;
    int indexRain = 0;
    int indexSun = 0;
    MediaPlayer mp;
    Context _context;
    SunAndRain sr1, sr2, sr3;
    boolean cloudIsClicked = false;
    boolean sunIsClicked = false;
    DrawableMutlipleStates[] seeds;
    ArrayList<Drawable> myDrawablesSprout = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesRain = new ArrayList<Drawable>();
    ArrayList<Drawable> myDrawablesSun = new ArrayList<Drawable>();
    Button[] seedButts;
    boolean[] completeGrowth = new boolean[]{false, false, false};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_four, container, false);
        handler = new Handler();
        sun = (Button) viewHierarchy.findViewById(R.id.Page4Sun);
        cloud = (Button) viewHierarchy.findViewById(R.id.Page4Cloud);
        seedbutt1 = (Button) viewHierarchy.findViewById(R.id.Page4Seed1);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.Page4Seed2);
        seedbutt3 = (Button) viewHierarchy.findViewById(R.id.Page4Seed3);
        sr1 = (SunAndRain) viewHierarchy.findViewById(R.id.rainsun1);
        sr2 = (SunAndRain) viewHierarchy.findViewById(R.id.rainsun2);
        sr3 = (SunAndRain) viewHierarchy.findViewById(R.id.rainsun3);
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
        seedstattes1 = new DrawMultipleStates2Input(myDrawablesSprout, 100);
        seedstates2 = new DrawMultipleStates2Input(myDrawablesSprout, 100);
        seedstates3 = new DrawMultipleStates2Input(myDrawablesSprout, 100);
        cloud.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                if (bool) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mp= MediaPlayer.create(_context,R.raw.sunny);
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
                        v.setX((v.getX() - xdiff));
                        v.setY((v.getY() - ydiff));
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
        }
        else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth() * (2f/3f)) {
            seedbutt2.setBackground(seedstates2.update(true));
        }
        else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth()) {
            seedbutt3.setBackground(seedstates3.update(true));
        }

    }

    private void isSunOverlapping() {
        if (sun.getX() + (sun.getWidth() / 2) < (masterView.getWidth() / 3)) {
            seedbutt1.setBackground(seedstattes1.update(false));

        }
        else if (sun.getX() + sun.getWidth() / 2 < masterView.getWidth() * (2f/3f)) {
            seedbutt2.setBackground(seedstates2.update(false));
        }
        else if (sun.getX() + sun.getWidth() / 2 < masterView.getWidth()) {
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
        _context=context;

        mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "I am thirsty and need water to grow.  Could you help me?\n";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
    }

    @Override
    public boolean doneTouching() {
        return (seedstattes1.allComplete() && seedstates2.allComplete() && seedstates3.allComplete());
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
            sr1.update(seedstattes1.getFirstFraction(),seedstattes1.getSecondFraction());
            sr2.update(seedstates2.getFirstFraction(),seedstates2.getSecondFraction());
            sr3.update(seedstates3.getFirstFraction(),seedstates3.getSecondFraction());

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
