package com.example.matthew.book.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.matthew.book.Activities.PageTurner;
import com.example.matthew.book.R;
import com.example.matthew.book.Util.RotationalMovement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Matthew on 9/29/2016.
 */

public class PageEight extends Page implements View.OnTouchListener {
    String thisPagesText = "So, my life is a cycle: from seed, to sapling, to full-grown tree, to blossom, to apple, and back to seed again.";
    Button seed, seedling, tree, treeFlower, treeApple, arrows;
    Button[] buttons;
    RotationalMovement rotationalMovement;
    HashMap<Button, Boolean> didITouch = new HashMap<Button, Boolean>();
    int count = 0;
    float rotate = 0;
    Drawable drawable;
    String strings[] = { "Seed", "Sapling", "Young Tree", "Tree With Flowers", "Tree With Apples" };
    Boolean currentBool = false;
    public Handler handler;
    RelativeLayout relativeLayout;
    Context _context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewHierarchy =
                inflater.inflate(R.layout.fragmentpageeight, container, false);
        handler = new Handler();
        arrows = (Button) viewHierarchy.findViewById(R.id.arrows);
        seed = (Button) viewHierarchy.findViewById(R.id.seed);
        seed.setBackgroundResource(R.drawable.seedling);
        seedling = (Button) viewHierarchy.findViewById(R.id.sprout);
        seedling.setBackgroundResource(R.drawable.sapling);
        tree = (Button) viewHierarchy.findViewById(R.id.tree);
        tree.setBackgroundResource(R.drawable.atree);
        treeFlower = (Button) viewHierarchy.findViewById(R.id.blossom);
        treeFlower.setBackgroundResource(R.drawable.treewithflowers);
        treeApple = (Button) viewHierarchy.findViewById(R.id.apple);
        treeApple.setBackgroundResource(R.drawable.treewithapples);
        relativeLayout = (RelativeLayout) viewHierarchy.findViewById(R.id.nospinzone);
        buttons = new Button[]{ seed, seedling, tree, treeFlower, treeApple };
        PageTurner.allButtons = new ArrayList<>(Arrays.asList(buttons));

        for ( int i = 0; i < buttons.length; i++ ) {
            didITouch.put(buttons[i], false);
            buttons[i].setOnTouchListener(this);
            buttons[i].setTextColor(Color.WHITE);
            buttons[i].setShadowLayer(10, 10, 10, Color.BLACK);
        }
        viewHierarchy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if ( count <= 1 ) {
                    float degreevariation = 360f / 5f;
                    float degreeCount = 0f;
                    for ( int i = 0; i < buttons.length; i++ ) {
                        buttons[i].setX((relativeLayout.getWidth() / 2) + (float) (Math.cos(Math.toRadians(degreeCount)) * 200f) - (buttons[i].getWidth() / 2));
                        buttons[i].setY((relativeLayout.getHeight() / 2) + (float) (Math.sin(Math.toRadians(degreeCount)) * 140f) - (buttons[i].getHeight() / 2));
                        degreeCount += degreevariation;
                    }
                    rotationalMovement = new RotationalMovement(relativeLayout.getWidth() / 2, relativeLayout.getHeight() / 2, 75f, buttons);
                    final ClockOne clock = new ClockOne(handler, relativeLayout.getWidth() / 2, relativeLayout.getHeight() / 2);
                    clock.run();
                }
                count++;
            }

        });

        enabledisabletouch(false);

        return viewHierarchy;


    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        Button imageView = (Button) view;
        didITouch.put(imageView, true);
        if ( event.getAction() == android.view.MotionEvent.ACTION_DOWN ) {
            buttons = new Button[]{ seed, seedling, tree, treeFlower, treeApple };
            PageTurner.allButtons = new ArrayList<>(Arrays.asList(buttons));
            drawable = imageView.getBackground();
            view.setBackgroundResource(R.drawable.emptybox);
            if ( imageView.equals(seed) ) {

                MediaPlayer mp = MediaPlayer.create(_context, R.raw.seed);
                mp.start();
                imageView.setText(strings[0]);
            } else if ( imageView.equals(seedling) ) {
                MediaPlayer mp = MediaPlayer.create(_context, R.raw.sappling);
                mp.start();
                imageView.setText(strings[1]);

            } else if ( imageView.equals(tree) ) {
                MediaPlayer mp = MediaPlayer.create(_context, R.raw.full_grown_tree);
                mp.start();
                imageView.setText(strings[2]);

            } else if ( imageView.equals(treeFlower) ) {
                MediaPlayer mp = MediaPlayer.create(_context, R.raw.blossom);
                mp.start();
                imageView.setText(strings[3]);

            } else if ( imageView.equals(treeApple) ) {
                MediaPlayer mp = MediaPlayer.create(_context, R.raw.apple);
                mp.start();
                imageView.setText(strings[4]);
            }

        } else if ( event.getAction() == android.view.MotionEvent.ACTION_UP ) {
            imageView.setText("");
            view.setBackground(drawable);
            view.setAlpha(.5f);
        }
        return true;
    }

    @Override
    public String getString() {
        return thisPagesText;
    }

    @Override
    public void getSound(int id) {
        super.getSound(id);
    }

    @Override
    public void passMediaPlayer(Context context) {
        _context = context;
    }

    @Override
    public boolean doneTouching() {
        for ( Boolean b : didITouch.values() ) {
            if ( b == false ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void enabledisabletouch(boolean b) {
        currentBool = b;


    }

    class ClockOne implements Runnable {
        private Handler handler;

        public ClockOne(Handler handler, float x, float y) {
            this.handler = handler;

        }

        public void run() {
            rotate += 2;
            arrows.setRotation(rotate);
            handler.postDelayed(this, 50);

        }

    }
}
