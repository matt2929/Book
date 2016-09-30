package com.example.matthew.book;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.matthew.book.fragments.Page;
import com.example.matthew.book.fragments.PageRule;
import com.example.matthew.book.fragments.PageOne;
import com.example.matthew.book.fragments.PageTwo;

public class FrontPage extends Activity {
    Button button;
    RelativeLayout ll;
    FrameLayout fragCase;
    int clickCount = 0;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction transaction;
    Page _CurrentPage = new PageOne();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        fragmentManager = getFragmentManager();
        button = (Button) findViewById(R.id.changefragment);
        button.setBackground(new BitmapDrawable(getResources(), Bitmap.createScaledBitmap((((BitmapDrawable) this.getResources().getDrawable(R.drawable.greenwhitebuttback))).getBitmap(), 75, 75, false)));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_CurrentPage.doneTouching()) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.fadein, R.animator.fadeout);
                    if (clickCount == 0) {
                        _CurrentPage = new PageTwo();
                        transaction.replace(fragCase.getId(), _CurrentPage);
                    } else if (clickCount == 1) {
                        _CurrentPage = new PageTwo();
                        transaction.replace(fragCase.getId(), _CurrentPage);
                    } else {
                        _CurrentPage = new PageTwo();
                        transaction.replace(fragCase.getId(), _CurrentPage);
                    }
                    transaction.commit();
                    clickCount++;
                }
            }
        });
        fragCase = (FrameLayout) findViewById(R.id.fragmentcase);
        getFragmentManager().beginTransaction().add(R.id.fragmentcase, _CurrentPage).commit();
    }
}