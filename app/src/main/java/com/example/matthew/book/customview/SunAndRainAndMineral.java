package com.example.matthew.book.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Matthew on 11/14/2016.
 */

public class SunAndRainAndMineral extends RelativeLayout {
    private float A = 0f;
    private float B = 0f;
    private float C =0f;
    private Paint paintBlack=new Paint(), paintDKGrey =new Paint(),paintRed=new Paint(),paintBlue=new Paint(),paintGrey=new Paint(),paintLtGrey=new Paint(),paintWhite=new Paint();
    public void setUp(){
        paintBlack.setColor(Color.DKGRAY);
        paintDKGrey.setColor(Color.GRAY);
        paintRed.setColor(Color.rgb(255,216,60));
        paintBlue.setColor(Color.rgb(141,196,200));
        paintGrey.setColor(Color.GRAY);
        paintLtGrey.setColor(Color.LTGRAY);
        paintWhite.setColor(Color.WHITE);
    }
    public SunAndRainAndMineral(Context context) {
        super(context);
        setUp();
    }

    public SunAndRainAndMineral(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public SunAndRainAndMineral(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    public SunAndRainAndMineral(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,getWidth(),getHeight(),paintBlack);
        canvas.drawRect(0,0,getWidth(),getHeight(),paintBlack);

        canvas.drawRect(getWidth()*(1f/7f),0,getWidth()*(2f/7f),getHeight(), paintDKGrey);
        canvas.drawRect(getWidth()*(1f/7f),getHeight()-(getHeight()*A),getWidth()*(2f/7f),getHeight(),paintRed);

        canvas.drawRect(getWidth()*(3f/7f),0,getWidth()*(4f/7f),getHeight(), paintDKGrey);
        canvas.drawRect(getWidth()*(3f/7f),getHeight()-(getHeight()*B),getWidth()*(4f/7f),getHeight(),paintBlue);

        canvas.drawRect(getWidth()*(5f/7f),0,getWidth()*(6f/7f),getHeight(), paintDKGrey);
        canvas.drawRect(getWidth()*(25f/35f),getHeight()-(getHeight()*C),getWidth()*(26f/35f),getHeight(),paintBlack);
        canvas.drawRect(getWidth()*(26f/35f),getHeight()-(getHeight()*C),getWidth()*(27f/35f),getHeight(),paintDKGrey);
        canvas.drawRect(getWidth()*(27f/35f),getHeight()-(getHeight()*C),getWidth()*(28f/35f),getHeight(),paintGrey);
        canvas.drawRect(getWidth()*(28f/35f),getHeight()-(getHeight()*C),getWidth()*(29f/35f),getHeight(),paintLtGrey);
        canvas.drawRect(getWidth()*(29f/35f),getHeight()-(getHeight()*C),getWidth()*(30f/35f),getHeight(),paintWhite);
    }

    public void update(float a, float b,float c) {
        A = b;
        B = a;
        C = c;
        invalidate();
    }
}
