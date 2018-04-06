package com.example.matthew.book.Activities.Util;

import android.widget.Button;

/**
 * Created by Matthew on 1/16/2017.
 */

public class RotationalMovement {
    float startX = 0, startY = 0, radius = 0;
    double angleStart = 0;
    double angleRotate = 0;
    double angleRate = 0;
    Button[] buttons;

    public RotationalMovement(float x, float y, float radius, Button[] b) {
        this.radius = radius;
        buttons = b;
        angleRate = 360d / (double) b.length;
        startX = x;
        startY = y;
    }

    public void nextPos() {
        angleRotate = 0;
        for (Button b : buttons) {
            b.setX(startX + (float) (Math.cos(Math.toRadians(angleStart + angleRotate)) * radius) - (b.getWidth() / 2));
            b.setY(startY + (float) (Math.sin(Math.toRadians(angleStart + angleRotate)) * radius) - (b.getHeight() / 2));
            angleRotate += angleRate;
        }
    }

    public void rotate() {
        angleStart++;
        if (angleStart == 361) {
            angleStart = 0;
        }
    }
}
