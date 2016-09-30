package com.example.matthew.book.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.matthew.book.R;

/**
 * Created by Matthew on 9/29/2016.
 */

public class PageOne extends Page {
    String thisPagesText="All living things have a life cycle. This is the story of my life cycle, which started with me as a tiny seed. Press the stages of my life cycle to learn about them.\"";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewHierarchy=
                inflater.inflate(R.layout.fragmentpage1,container,false);
        return viewHierarchy;

    }

    @Override
    public boolean doneTouching() {
        return true;
    }
}
