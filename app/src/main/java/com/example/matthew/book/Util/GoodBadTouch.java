package com.example.matthew.book.Util;


import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.matthew.book.Util.SaveData.*;

/**
 * Created by Matthew on 1/8/2017.
 */

public class GoodBadTouch  implements Serializable{
    private ArrayList<Button> _ValidTouchableAreas;
    private ArrayList<ReadingSession.Touch> _Touches = new ArrayList<>();
    private int early=0;
    public boolean checkTouchValidity(ArrayList<Button> allViews, int x, int y) {
        _ValidTouchableAreas = allViews;
        Calendar calendar = java.util.Calendar.getInstance();
        for (View v : _ValidTouchableAreas) {
            int[] location = new int[2];
            if (v != null) {
                v.getLocationInWindow(location);
                int vx = location[0];
                int vy = location[1];
                 if (x >= vx
                        && x <= vx + v.getWidth()
                        && y >= vy
                        && y <= vy + v.getHeight()) {
                    _Touches.add(new ReadingSession.Touch(calendar, x, y, true));

                    Log.e("Touch", "Good");
                    return true;
                }
            }
        }
        Log.e("Touch", "Bad");
        _Touches.add(new ReadingSession.Touch(calendar, x, y, false));
        return false;
    }

    public void touchedAheadOfTime(int x, int y) {
        Log.e("Touch", "Bad");
        early++;
        Calendar calendar = java.util.Calendar.getInstance();
        _Touches.add(new ReadingSession.Touch(calendar, x, y, false));
    }
    public void lastTouchWasAGoodSwipe(){
        Log.e("Touch", "Undo");
        _Touches.remove(_Touches.size()-1);
    }

    public void reset(){
        _Touches.clear();
        early=0;
    }
    public ArrayList<ReadingSession.Touch> get_Touches() {
        return _Touches;
    }


    public int getEarly() {
        return early;
    }
}
