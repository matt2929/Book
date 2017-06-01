package com.example.matthew.book.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matthew.book.Activities.PageTurner;
import com.example.matthew.book.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew on 9/29/2016.
 */

public class PageTwo extends Page implements View.OnTouchListener {
    Button Seeds1, Seeds2, Seeds3, Dirt1, Dirt2, Dirt3;
    Button[] seeds;
    Button[] dirt;
    Boolean[] dirtFertilized = new Boolean[]{false, false, false};
    View masterView;

    int updateLimiter = 0;
    Float lastX = 0f, lastY = 0f;
    boolean bool = false;
    MediaPlayer mp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy =
                inflater.inflate(R.layout.fragmentpage2, container, false);
        Seeds1 = (Button) viewHierarchy.findViewById(R.id.MainDirt2);
        Seeds2 = (Button) viewHierarchy.findViewById(R.id.Page2AppleCenter);
        Seeds3 = (Button) viewHierarchy.findViewById(R.id.Page2AppleLeft);

        seeds = new Button[]{Seeds1, Seeds2, Seeds3};
        Dirt1 = (Button) viewHierarchy.findViewById(R.id.Page2DirtLeft);
        Dirt2 = (Button) viewHierarchy.findViewById(R.id.Page2DirtCenter);
        Dirt3 = (Button) viewHierarchy.findViewById(R.id.Page2DirtRight);
        dirt = new Button[]{Dirt1, Dirt2, Dirt3};

        for (int i = 0; i < seeds.length; i++) {
            seeds[i].setOnTouchListener(this);
        }
        masterView = viewHierarchy;
        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(dirt));
        butttemp.addAll(Arrays.asList(seeds));
        PageTurner.allButtons =  new ArrayList<>(butttemp);

        return viewHierarchy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ArrayList<Button> butttemp = new ArrayList<>(Arrays.asList(dirt));
        butttemp.addAll(Arrays.asList(seeds));
        PageTurner.allButtons =  new ArrayList<>(butttemp);

        if (bool) {

            v.setBackground(getResources().getDrawable(R.drawable.seeds_for_dirt));
            if (lastY == 0) {
                lastX = event.getRawX();
                lastY = event.getRawY();

            } else {


                float xdiff = lastX - event.getRawX();
                float ydiff = lastY - event.getRawY();

                if ((v.getX() - xdiff) + v.getWidth() > masterView.getWidth()
                        || (v.getX() - xdiff) < 0
                        || (v.getY() - ydiff) + v.getHeight() > masterView.getHeight()
                        || (v.getY() - ydiff) < 0
                        ) {
                } else {
                    v.setX((v.getX() - xdiff));
                    v.setY((v.getY() - ydiff));
                    lastX = event.getRawX();
                    lastY = event.getRawY();
                }
                Button imageView = (Button) v;

                if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    isOverLap(v);
                    lastY = 0f;
                    lastX = 0f;
                }

            }

            return true;
        } else {
            return false;
        }
    }

    private boolean isOverLap(View v) {
        for (int i = 0; i < dirt.length; i++) {
            if (!dirtFertilized[i] && isViewOverlapping(dirt[i], v)) {
                dirtFertilized[i] = true;
                mp.start();
                return true;
            } else {

            }
        }
        return false;
    }

    private boolean isViewOverlapping(View v1, View v2) {
        Rect rect1 = new Rect((int) v1.getX(), (int) v1.getY(), (int) v1.getX() + v1.getWidth(), (int) v1.getY() + v1.getHeight());
        Rect rect2 = new Rect((int) v2.getX(), (int) v2.getY(), (int) v2.getX() + v1.getWidth(), (int) v2.getY() + v2.getHeight());
        Log.e("rect2", "" + rect2.toString());
        if (rect1.intersect(rect2)) {
            v1.setBackground(getResources().getDrawable(R.drawable.dirt_with_seeds));
            v2.setVisibility(View.GONE);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void passMediaPlayer(Context context) {
        mp = MediaPlayer.create(context, R.raw.dirtmove);
    }

    @Override
    public String getString() {
        return "If I fall to the ground I might grow into a big apple tree, just like my mother. Place me in the ground and plant me so I can grow big and tall!";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
    }

    @Override
    public boolean doneTouching() {
        for (int i = 0; i < dirtFertilized.length; i++) {
            if (!dirtFertilized[i]) {
                return false;
            }
        }
        return true;
    }
}
