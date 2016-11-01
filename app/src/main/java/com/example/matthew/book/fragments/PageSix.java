package com.example.matthew.book.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.DrawableMutlipleStates;

import java.util.ArrayList;

public class PageSix extends Page implements View.OnTouchListener {
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
        View viewHierarchy = inflater.inflate(R.layout.fragment_page_six, container, false);
        handler = new Handler();
        cloud = (Button) viewHierarchy.findViewById(R.id.Page4Cloud);
        seedbutt1 = (Button) viewHierarchy.findViewById(R.id.page6seed1);
        seedbutt2 = (Button) viewHierarchy.findViewById(R.id.page6seed2);
        seedbutt3 = (Button) viewHierarchy.findViewById(R.id.page6seed3);
        seedbutt1.setBackground(getResources().getDrawable(R.drawable.page6sprout1));
        seedbutt2.setBackground(getResources().getDrawable(R.drawable.page6sprout1));
        seedbutt3.setBackground(getResources().getDrawable(R.drawable.page6sprout1));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6sprout1));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6sprout2));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6sprout3));
        myDrawablesSprout.add(getResources().getDrawable(R.drawable.page6sprout4));
        seeds = new DrawableMutlipleStates[3];
        seedstattes1 = new DrawableMutlipleStates(myDrawablesSprout, 100);
        seedstates2 = new DrawableMutlipleStates(myDrawablesSprout, 100);
        seedstates3 = new DrawableMutlipleStates(myDrawablesSprout, 100);


        masterView = viewHierarchy;
        return viewHierarchy;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction()== MotionEvent.ACTION_DOWN) {
            Log.e("Click","Click: "+v.getBackground().toString()+", "+getResources().getDrawable(R.drawable.page6sprout1).getConstantState().toString());

            if(v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.page6sprout1).getConstantState())){
                Log.e("Click1","Click1");

                v.setBackground(getResources().getDrawable(R.drawable.page6sprout2));
            }else if(v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.page6sprout2).getConstantState())){
                v.setBackground(getResources().getDrawable(R.drawable.page6sprout3));
            }else if(v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.page6sprout3).getConstantState())){
                v.setBackground(getResources().getDrawable(R.drawable.page6sprout4));
            }
            else if(v.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.page6sprout4).getConstantState())){
                v.setBackground(getResources().getDrawable(R.drawable.page6sprout4));
            }
        }
        return true;
    }


    @Override
    public void passMediaPlayer(Context context) {
        mp = MediaPlayer.create(context, R.raw.thunder);
        mp.setLooping(true);
    }

    @Override
    public String getString() {
        return "I can feel my roots beginning to grow downward into the ground.  My roots help keep me anchored to the ground. Press my roots to see how they grow.\n";
    }

    @Override
    public void enabledisabletouch(boolean b) {
        seedbutt1.setOnTouchListener(this);
        seedbutt2.setOnTouchListener(this);
        seedbutt3.setOnTouchListener(this);
    }

    @Override
    public boolean doneTouching() {
        return (seedbutt1.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.page6sprout4).getConstantState())
        &&seedbutt2.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.page6sprout4).getConstantState())
        &&seedbutt3.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.page6sprout4).getConstantState()));

    }
}
