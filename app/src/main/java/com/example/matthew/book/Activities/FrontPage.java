package com.example.matthew.book.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.matthew.book.R;
import com.example.matthew.book.fragments.Page;
import com.example.matthew.book.fragments.PageRule;
import com.example.matthew.book.fragments.PageOne;
import com.example.matthew.book.fragments.PageTwo;

public class FrontPage extends Activity {
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        button =(Button) findViewById(R.id.titlereadtome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PageTurner.class);
                startActivity(i);

            }
        });
    }
}