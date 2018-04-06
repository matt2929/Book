package com.example.matthew.book.Activities.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Matthew on 5/4/2017.
 */

public class MineralProgress extends RelativeLayout{
    private float A = 0f;
    private Paint paintBlack=new Paint(), paintDKGrey =new Paint(),paintRed=new Paint(),paintBlue=new Paint(),paintGrey=new Paint(),paintLtGrey=new Paint(),paintWhite=new Paint();
    public void setUp(){
        paintBlack.setColor(Color.BLACK);
        paintDKGrey.setColor(Color.DKGRAY);
        paintRed.setColor(Color.BLACK);
        paintBlue.setColor(Color.rgb(141,196,200));
        paintGrey.setColor(Color.GRAY);
        paintLtGrey.setColor(Color.LTGRAY);
        paintWhite.setColor(Color.WHITE);
    }
    public MineralProgress(Context context) {
        super(context);
        setUp();
    }

    public MineralProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public MineralProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    public MineralProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(getWidth()*(1f/4f),0,getWidth()*(3f/4f),getHeight(),paintDKGrey);

        canvas.drawRect(getWidth()*(1f/3f),0,getWidth()*(2f/3f),getHeight(),paintLtGrey);

        for(int i=0;i<255;i++){
            paintBlack.setColor(Color.rgb(i,i,i));
            canvas.drawRect(getWidth()*((float)(i+256)/768f),getHeight()-(getHeight()*A),getWidth()*((float)(i+257)/768f),getHeight(),paintBlack);
        }
       //
        /* canvas.drawRect(getWidth()*(5f/15f),getHeight()-(getHeight()*A),getWidth()*(6f/15f),getHeight(),paintBlack);
        canvas.drawRect(getWidth()*(6f/15f),getHeight()-(getHeight()*A),getWidth()*(7f/15f),getHeight(),paintDKGrey);
        canvas.drawRect(getWidth()*(7f/15f),getHeight()-(getHeight()*A),getWidth()*(8f/15f),getHeight(),paintGrey);
        canvas.drawRect(getWidth()*(8f/15f),getHeight()-(getHeight()*A),getWidth()*(9f/15f),getHeight(),paintLtGrey);
        canvas.drawRect(getWidth()*(9f/15f),getHeight()-(getHeight()*A),getWidth()*(10f/15f),getHeight(),paintWhite);
    */}


    public void update(float a) {
        A = a;
        invalidate();
    }
}
