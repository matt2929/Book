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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.matthew.book.Activities.PageTurner;
import com.example.matthew.book.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthew on 9/29/2016.
 */

public class PageThree extends Page implements View.OnTouchListener {
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
                inflater.inflate(R.layout.fragmentpage3, container, false);
        Seeds1 = (Button) viewHierarchy.findViewById(R.id.Page3MainPile);
        Seeds2 = (Button) viewHierarchy.findViewById(R.id.small1);
        Seeds3 = (Button) viewHierarchy.findViewById(R.id.small2);


        seeds = new Button[]{Seeds1, Seeds2, Seeds3};
        Dirt1 = (Button) viewHierarchy.findViewById(R.id.Page3Dirt1);
        Dirt2 = (Button) viewHierarchy.findViewById(R.id.Page3Dirt2);
        Dirt3 = (Button) viewHierarchy.findViewById(R.id.Page3Dirt3);
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
            v.setBackground(getResources().getDrawable(R.drawable.page3dirt1));
            if (!v.getBackground().equals(getResources().getDrawable(R.drawable.page3dirt1))&&event.getAction()==(MotionEvent.ACTION_DOWN)) {
                for (int i = 0; i < seeds.length; i++) {
                    if (!seeds[i].equals(v)) {
                        if (updateLimiter == 0) {
                            seeds[i].setBackground(getResources().getDrawable(R.drawable.page3dirt2));
                        } else {
                            seeds[i].setBackground(getResources().getDrawable(R.drawable.page3dirt1));
                        }
                    }
                }
                updateLimiter++;

            }
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
            v1.setBackground(getResources().getDrawable(R.drawable.page3dirt2));
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
        return "The dirt feels soft and cool as it covers my little seed body. Can you help cover me with dirt so that I can start to grow beneath the ground?\n";
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
