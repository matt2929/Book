package com.example.matthew.book.Activities.Util;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matthew on 11/10/2016.
 */

public class DrawMultipleStates2Input {
    ArrayList<Drawable> myDrawables;
    ArrayList<Integer> checkpoint = new ArrayList<Integer>();
    int total1 = 0;
    int total2 = 0;
    int Max1 = 0;
    int Max2 = 0;

    public DrawMultipleStates2Input(ArrayList<Drawable> drawables, int MaxNum) {
        myDrawables = drawables;
        for (int i = 0; i < myDrawables.size(); i++) {
            checkpoint.add((int) ((float) (MaxNum) * ((float) i / (float) myDrawables.size())));
            Log.e("taint", "" + checkpoint.get(i));

        }
        checkpoint.add(MaxNum);
        Max1 = (MaxNum / 2);
        Max2 = Max1;
    }

    public Drawable update(boolean isFirst) {
        if (isFirst && total1 < Max1) {
            total1++;
        } else if(!isFirst&&total2 < Max2){
                total2++;
            }
        for (int i = checkpoint.size() - 1; i > 0; i--) {

            if (total1 + total2 < checkpoint.get(i) && total1 + total2 > checkpoint.get(i - 1)) {
                return myDrawables.get(i - 1);
            }
            if (total1 + total2 >= Max1 + Max2) {
                return myDrawables.get(myDrawables.size() - 1);
            }
        }
        return myDrawables.get(0);
    }

    public boolean allComplete() {
        return total1 + total2 >= checkpoint.get(checkpoint.size() - 2);
    }

    public float getFirstFraction() {
        return ((float) total1 / (float) Max1);
    }

    public float getSecondFraction() {
        return ((float) total2 / (float) Max2);
    }
}
