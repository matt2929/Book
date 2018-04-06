package com.example.matthew.book.Activities.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by Matthew on 1/14/2017.
 */

public class DrawPointTransparent extends RelativeLayout {
    private Paint paintYellow,paintRed;
    private float _X = 0f, _Y = 0f;
    private boolean _Good = false;

    public DrawPointTransparent(Context context) {
        super(context);
        setUp();
    }

    public DrawPointTransparent(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public DrawPointTransparent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paintYellow = new Paint();
        paintRed = new Paint();
        paintYellow.setColor(Color.YELLOW);
        paintYellow.setAlpha(172);
        paintRed.setColor(Color.RED);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    public void setPoint(float x, float y, boolean good) {
        _X = x;
        _Y = y;
        _Good = good;
        if(good){
            paintYellow.setColor(Color.GREEN);
        }else{
            paintYellow.setColor(Color.RED);
        }
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Float porpX = (_X / dm.widthPixels) * this.getWidth();
        Float porpY = (_Y / dm.heightPixels) * this.getHeight();
        Log.e("x:", "" + porpX);
        Log.e("y:", "" + porpY);
        canvas.drawCircle(porpX, porpY, 15, paintYellow);
    }
}
