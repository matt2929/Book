package com.example.matthew.book.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Matthew on 9/29/2016.
 */

public interface PageRule {
    public boolean doneTouching();

    public String getString();

    public void enabledisabletouch(boolean b);

    public void passMediaPlayer(Context context);


}
