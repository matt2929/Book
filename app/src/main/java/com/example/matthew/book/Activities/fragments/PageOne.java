package com.example.matthew.book.Activities.fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matthew.book.Activities.Activities.PageTurner;
import com.example.matthew.book.R;

import java.util.ArrayList;

public class PageOne extends Page {
    Button seedbutt1, seedbutt2, seedbutt3;
    View masterView;
    boolean bool = false;
    public Handler handler;
    int index = 0;
    Context _context;
    MediaPlayer mp;
    ArrayList<Drawable> myDrawablesSprout = new ArrayList<Drawable>();
    int seedCount[] = new int[3];
    View viewHierarchy;
    ArrayList<Button> butttemp = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         viewHierarchy = inflater.inflate(R.layout.fragment_page_one, container, false);
        handler = new Handler();
        seedbutt1 = (Button) viewHierarchy.findViewById(R.id.treeLeft);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.treeCenter);
        seedbutt3 = (Button) viewHierarchy.findViewById(R.id.treeRight);
        butttemp.add(seedbutt1);
        butttemp.add(seedbutt2);
        butttemp.add(seedbutt3);
        PageTurner.allButtons =  butttemp;
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page77));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page81));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page82));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page83));
        seedbutt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bool) {
                    seedCount[0] = seedCount[0] + 1;
                    if (seedCount[0] >= myDrawablesSprout.size()) {

                    } else {
                        v.setBackground(myDrawablesSprout.get(seedCount[0]));
                    }
                }
            }
        });
        seedbutt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bool) {
                    seedCount[1] = seedCount[1] + 1;
                    if (seedCount[1] >= myDrawablesSprout.size()) {
                    } else {
                        v.setBackground(myDrawablesSprout.get(seedCount[1]));
                    }
                }
            }
        });

        seedbutt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bool) {
                    seedCount[2] = seedCount[2] + 1;
                    if (seedCount[2] >= myDrawablesSprout.size()) {
                    } else {
                        v.setBackground(myDrawablesSprout.get(seedCount[2]));
                    }
                }
            }
        });
        masterView = viewHierarchy;
        return viewHierarchy;
    }





    @Override
    public void passMediaPlayer(Context context) {
        mp = MediaPlayer.create(context, R.raw.slurp);
        _context = context;
        mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "All living things have a life cycle. This is the story of my life cycle, which starts with me as a tiny seed. I am nestled safely inside of an apple. Can you find me?";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        bool = b;
        Log.e("touch","enabled");
    }

    @Override
    public boolean doneTouching() {
        return (seedCount[0] + seedCount[1] + seedCount[2] >= 9);
    }
}
