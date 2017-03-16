package com.example.matthew.book.Util;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.matthew.book.EyeTracking.Calibration9Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Matthew on 1/8/2017.
 */

public class GoodBadTouch implements Serializable {
    private ArrayList<Button> _ValidTouchableAreas;
    private ArrayList<ReadingSession.Touch> _Touches = new ArrayList<>();
    private ArrayList<ReadingSession.Touch> _EyeCoordinates = new ArrayList<>();
    private SaveCSV _saveCSVTouch;
    private SaveCSV _saveCSVEye;
    private int early = 0;

    public GoodBadTouch(Context context) {
        _saveCSVTouch = new SaveCSV("touch", context);
        Log.e("touch",_saveCSVTouch.get_fileName());
        _saveCSVEye = new SaveCSV("eye", context);
        Log.e("touch",_saveCSVTouch.get_fileName());
    }

    public boolean checkTouchValidity(int page, ArrayList<Button> allViews, int x, int y) {
        _ValidTouchableAreas = allViews;
        Calendar calendar = java.util.Calendar.getInstance();
        for ( View v : _ValidTouchableAreas ) {
            int[] location = new int[2];
            if ( v != null ) {
                v.getLocationInWindow(location);
                int vx = location[0];
                int vy = location[1];
                if ( x >= vx
                        && x <= vx + v.getWidth()
                        && y >= vy
                        && y <= vy + v.getHeight() ) {
                    _Touches.add(new ReadingSession.Touch(calendar, x, y, true));
                    _saveCSVTouch.saveData(page, x, y, true, v.getResources().getResourceName(v.getId()));
                    Log.e("Touch", "Good");
                    return true;
                }
            }
        }
        Log.e("Touch", "Bad");
        _Touches.add(new ReadingSession.Touch(calendar, x, y, false));
        _saveCSVTouch.saveData(page, x, y, false," ");
        return false;
    }

    public void checkEyeValidity(int page, ArrayList<View> allViews, float x, float y) {
        Calendar calendar = java.util.Calendar.getInstance();
        for ( View v : allViews ) {
            int[] location = new int[2];
            if ( v != null ) {
                v.getLocationInWindow(location);
                int vx = location[0];
                int vy = location[1];
                if ( x >= vx
                        && x <= vx + v.getWidth()
                        && y >= vy
                        && y <= vy + v.getHeight() ) {

                    _EyeCoordinates.add(new ReadingSession.Touch(calendar, x, y, true));
                    _saveCSVEye.saveData(page, x, y, true, v.getResources().getResourceName(v.getId()));
                }
            }
        }
        _EyeCoordinates.add(new ReadingSession.Touch(calendar, x, y, false));
        _saveCSVEye.saveData(page, x, y, false," ");


    }
/*

    public void touchedAheadOfTime(int x, int y) {
        Log.e("Touch", "Bad");
        early++;
        Calendar calendar = java.util.Calendar.getInstance();
        _Touches.add(new ReadingSession.Touch(calendar, x, y, false));
    }
*/

    public void lastTouchWasAGoodSwipe() {
        Log.e("Touch", "Undo");
        _Touches.remove(_Touches.size() - 1);
    }

    public void reset() {
        _Touches.clear();
        early = 0;
    }

    public ArrayList<ReadingSession.Touch> get_Touches() {
        return _Touches;
    }

    public ArrayList<ReadingSession.Touch> get_EyeCoordinates() {
        return _EyeCoordinates;
    }

    public int getEarly() {
        return early;
    }
}
