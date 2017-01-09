package com.example.matthew.book.Util;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matthew on 1/8/2017.
 */

public class GoodBadTouch {
    private ArrayList<Button> _ValidTouchableAreas;
    private ArrayList<Touch> _Touches = new ArrayList<>();

    public void checkTouchValidity(ArrayList<Button> allViews, int x, int y) {
        _ValidTouchableAreas = allViews;
        Calendar calendar = java.util.Calendar.getInstance();
        for (View v : _ValidTouchableAreas) {
            int[] location = new int[2];
            if (v != null) {
                v.getLocationInWindow(location);
                int vx = location[0];
                int vy = location[1];
                Log.e("Touch Coordinate", "vx{"+vx+"} "+" vy{"+vy+"}");
                if (x >= vx
                        && x <= vx + v.getWidth()
                        && y >= vy
                        && y <= vy + v.getHeight()) {
                    _Touches.add(new Touch(calendar, x, y, true));

                    Log.e("Touch", "Good");
                    return;
                }
            }
        }
        Log.e("Touch", "Bad");
        _Touches.add(new Touch(calendar, x, y, false));
    }

    public void touchedAheadOfTime(int x, int y) {
        Calendar calendar = java.util.Calendar.getInstance();
        _Touches.add(new Touch(calendar, x, y, false));


    }
}
