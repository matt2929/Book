package com.example.matthew.book.Activities.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.example.matthew.book.R;

public class Authors extends Activity {
    Button button1, button2, button3, button4;
    Handler handler;
    Clock clock;
    int rotateNum = 0;
    int rotateMax = 25;
    boolean increase = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);
        button1 = (Button) findViewById(R.id.dance1);
        button2 = (Button) findViewById(R.id.dance2);
        button3 = (Button) findViewById(R.id.dance3);
        button4 = (Button) findViewById(R.id.dance4);
        handler = new Handler();
        clock = new Clock(handler);
        clock.run();
    }

    class Clock implements Runnable {
        private Handler handler;
        public Clock(Handler handler) {
            this.handler = handler;

        }
        public void run() {
            if (increase) {
                if (rotateNum == rotateMax) {
                    increase = !increase;
                } else {
                    rotateNum++;
                }
            } else {
                if (rotateNum == -rotateMax) {
                    increase = !increase;
                } else {
                    rotateNum--;
                }
            }
            button1.setRotation(rotateNum);
            button2.setRotation(rotateNum);
            button3.setRotation(rotateNum);
            button4.setRotation(rotateNum);
            handler.postDelayed(this, 45);
        }
    }
}
