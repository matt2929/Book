package com.example.matthew.book.Util;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by Matthew on 1/8/2017.
 */

public class Touch {
    private float _X, _Y;
    private boolean _Good;
    private Calendar _Time;

    public Touch(Calendar time, float x, float y, boolean good) {
        _Time=time;
        _X = x;
        _Y = y;
        _Good = good;
    }

    public boolean is_Good() {
        return _Good;
    }

    public float get_X() {
        return _X;
    }

    public float get_Y() {
        return _Y;
    }
}