package com.example.matthew.book.Activities.fragments;

import android.content.Context;

/**
 * Created by Matthew on 9/29/2016.
 */

public interface PageRule {
    public boolean doneTouching();

    public String getString();

    public void enabledisabletouch(boolean b);

    public void passMediaPlayer(Context context);


}
