package com.example.matthew.book.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.matthew.book.R;

import java.util.HashMap;

/**
 * Created by Matthew on 9/29/2016.
 */

public class PageOne extends Page implements View.OnTouchListener {
    String thisPagesText = "All living things have a life cycle. This is the story of my life cycle, which started with me as a tiny seed. Press the stages of my life cycle to learn about them.";
   Button seed, seedling, sappling, tree, treeFlower, treeApple;
    Button[] buttons;

    HashMap<Button,Boolean> didITouch= new HashMap<Button,Boolean>();
    Drawable drawable;
    String strings[]= {"Seed","Seedling","Sappling","Young Tree","Tree With Flowers", "Tree With Apples"};
    Boolean currentBool=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewHierarchy =
                inflater.inflate(R.layout.fragmentpage1, container, false);

        seed = (Button) viewHierarchy.findViewById(R.id.cycleseed);
        seed.setBackgroundResource(R.drawable.seed);
        seedling = (Button) viewHierarchy.findViewById(R.id.cycleseedling);
        seedling.setBackgroundResource(R.drawable.seedling);
        sappling = (Button) viewHierarchy.findViewById(R.id.cyclesapling);
        sappling.setBackgroundResource(R.drawable.sapling);
        tree = (Button) viewHierarchy.findViewById(R.id.cycletree);
        tree.setBackgroundResource(R.drawable.tree);
        treeFlower = (Button) viewHierarchy.findViewById(R.id.cycletreeflower);
        treeFlower.setBackgroundResource(R.drawable.treewithflowers);
        treeApple = (Button) viewHierarchy.findViewById(R.id.cycletreeapple);
        treeApple.setBackgroundResource(R.drawable.treewithapples);
        buttons = new Button[]{seed, seedling, sappling, tree, treeFlower, treeApple};
        for(int i=0;i<buttons.length;i++){
            didITouch.put(buttons[i],false);
            buttons[i].setOnTouchListener(this);
            buttons[i].setTextColor(Color.WHITE);
            buttons[i].setShadowLayer(10,10,10,Color.BLACK);
        }
        enabledisabletouch(false);
        return viewHierarchy;


    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        Button imageView = (Button) view;
        didITouch.put(imageView,true);
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            drawable = imageView.getBackground();
            view.setBackgroundResource(R.drawable.emptybox);
            //imageView.setBackground(getResources().getDrawable(R.drawable.emptybox));
            if(imageView.equals(seed)){
                imageView.setText(strings[0]);
            }else if(imageView.equals(seedling)){
                imageView.setText(strings[1]);

            }else if(imageView.equals(sappling)){
                imageView.setText(strings[2]);

            }else if(imageView.equals(tree)){
                imageView.setText(strings[3]);

            }else if(imageView.equals(treeFlower)){
                imageView.setText(strings[4]);

            }else if(imageView.equals(treeApple)){
                imageView.setText(strings[5]);

            }

        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            imageView.setText("");
            view.setBackground(drawable);
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.twiststretch);
            view.startAnimation(hyperspaceJumpAnimation);
            view.setAlpha(.5f);
        }
        return true;
    }

    @Override
    public String getString() {
        return thisPagesText;
    }

    @Override
    public boolean doneTouching() {
        for(Boolean b: didITouch.values()){
            if(b==false){
                return false;
            }
        }
        return true;
    }

    @Override
    public void enabledisabletouch(boolean b) {
        currentBool=b;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("enabledisable",""+
                        currentBool);
                for(int i =0;i<buttons.length;i++){
                    buttons[i].setEnabled(currentBool);
                }
            }
        });

    }
}
