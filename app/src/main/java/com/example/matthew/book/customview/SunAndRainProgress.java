package com.example.matthew.book.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Matthew on 11/9/2016.
 */

public class SunAndRainProgress extends RelativeLayout {
    private float A = 0f;
    private float B = 0f;
    private Paint paintBlack=new Paint(),paintGrey=new Paint(),paintRed=new Paint(),paintBlue=new Paint();
    public void setUp(){
        paintBlack.setColor(Color.DKGRAY);
        paintGrey.setColor(Color.GRAY);
        paintRed.setColor(Color.rgb(255,216,60));
        paintBlue.setColor(Color.rgb(141,196,200));
    }
    public SunAndRainProgress(Context context) {
        super(context);
        setUp();
    }

    public SunAndRainProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public SunAndRainProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    public SunAndRainProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,0,getWidth(),getHeight(),paintBlack);
        canvas.drawRect(getWidth()*(1f/5f),0,getWidth()*(2f/5f),getHeight(),paintGrey);
        canvas.drawRect(getWidth()*(1f/5f),getHeight()-(getHeight()*A),getWidth()*(2f/5f),getHeight(),paintRed);
        canvas.drawRect(getWidth()*(3f/5f),0,getWidth()*(4f/5f),getHeight(),paintGrey);
        canvas.drawRect(getWidth()*(3f/5f),getHeight()-(getHeight()*B),getWidth()*(4f/5f),getHeight(),paintBlue);

    }

    public void update(float a, float b) {
        A = b;
        B = a;
        invalidate();
    }
}
