package com.example.matthew.book.Activities.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matthew.book.Activities.Activities.PageTurner;
import com.example.matthew.book.Activities.Util.DrawableMutlipleStates;
import com.example.matthew.book.R;

import java.util.ArrayList;
import java.util.Arrays;


public class PageSeven extends Page implements View.OnTouchListener {
    Button seedbutt1, seedbutt2, seedbutt3, cloud;
    DrawableMutlipleStates seedstattes1, seedstates2, seedstates3;
    View masterView;
    int updateLimiter = 0;
    Float lastX = 0f, lastY = 0f;
    boolean offsetIncrease = true;
    Float offset = 0f, offsetMax = 10f, offsetMin = -10f;
    boolean bool = false;
    public Handler handler;
    Context _context;
    MediaPlayer mp;
    boolean cloudIsClicked = false;
    DrawableMutlipleStates[] seeds;
    ArrayList<Drawable> myDrawablesSprout = new ArrayList<Drawable>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_seven, container, false);
        handler = new Handler();
        cloud = (Button) viewHierarchy.findViewById(R.id.Page7Bee);
        seedbutt1 = (Button) viewHierarchy.findViewById(R.id.Page7SeedLeft);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.Page7SeedCenter);
        seedbutt3 = (Button) viewHierarchy.findViewById(R.id.Page7SeedRight);
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page71));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page72));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page73));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page74));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page75));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page76));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page77));
        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt1, seedbutt2, seedbutt3, cloud}));
        PageTurner.allButtons = new ArrayList<>(butttemp);
        seeds = new DrawableMutlipleStates[3];
        seedstattes1 = new DrawableMutlipleStates(myDrawablesSprout, 45);
        seedstates2 = new DrawableMutlipleStates(myDrawablesSprout, 45);
        seedstates3 = new DrawableMutlipleStates(myDrawablesSprout, 45);
        cloud.setOnTouchListener(this);
        masterView = viewHierarchy;
        final ClockSeven clock = new ClockSeven(handler);

        clock.run();
        return viewHierarchy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(new Button[]{seedbutt1, seedbutt2, seedbutt3, cloud}));
        PageTurner.allButtons = new ArrayList<>(butttemp);
        if (bool) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mp.start();
                cloudIsClicked = true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                cloudIsClicked = false;
                mp.pause();
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
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    lastY = 0f;
                    lastX = 0f;
                }
            }


        }
        return true;
    }


    private void isViewOverlapping() {
        if (cloud.getY() > masterView.getHeight() * (1f / 4f) && cloud.getY() < masterView.getHeight() * (3f / 4f))
            if (cloud.getX() + (cloud.getWidth() / 2) < (masterView.getWidth() / 3)) {
                seedbutt1.setBackground(seedstattes1.update());

            } else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth() * (2f / 3f)) {
                seedbutt2.setBackground(seedstates2.update());

            } else if (cloud.getX() + cloud.getWidth() / 2 < masterView.getWidth()) {
                seedbutt3.setBackground(seedstates3.update());
            } else {
                mp.pause();
            }
    }

    public boolean checkButtOverlap(Button butt1, Button butt2) {
        return ((butt1.getX() > butt2.getX() && butt1.getX() < butt2.getX() + butt2.getWidth())
                || (butt1.getX() + butt1.getWidth() > butt2.getX() && butt1.getX() + butt1.getWidth() < butt2.getX() + butt2.getWidth()));


    }

    @Override
    public void passMediaPlayer(Context context) {
        // mp.release();
        mp = MediaPlayer.create(context, R.raw.beenoise);
        if (mp.isPlaying()) {
            mp.pause();
        }
        _context = context;
        mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "The bees come to help pollinate my blossoms so that they grow into apples. Can you help them?";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
    }

    @Override
    public boolean doneTouching() {
        if (seedstattes1 != null) {
            return (seedstattes1.allComplete() && seedstates2.allComplete() && seedstates3.allComplete());
        } else {
            return true;
        }
    }

    class ClockSeven implements Runnable {
        private Handler handler;

        public ClockSeven(Handler handler) {
            this.handler = handler;

        }

        public void run() {
            if (cloudIsClicked) {
            }
            isViewOverlapping();
            //bees do stuff here
            if (offsetIncrease) {
                if (offset > offsetMax) {
                    offsetIncrease = !offsetIncrease;
                } else {
                    offset += 3;
                }
            } else {
                if (offset < offsetMin) {
                    offsetIncrease = !offsetIncrease;
                } else {
                    offset -= 3;
                }

            }

            cloud.setX(cloud.getX() + offset);
            handler.postDelayed(this, 100);

        }

    }

}