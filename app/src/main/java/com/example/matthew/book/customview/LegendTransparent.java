package com.example.matthew.book.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.matthew.book.Util.ReadingSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matth on 5/27/2017.
 */

public class LegendTransparent extends RelativeLayout {
    private Paint paintBack, paintText, paintRect;
    String text = "";
    Integer[] colors = new Integer[0];
    int[] scales = new int[0];

    public LegendTransparent(Context context) {
        super(context);
        setUp();
    }

    public LegendTransparent(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public LegendTransparent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paintBack = new Paint();
        paintText = new Paint();
        paintRect = new Paint();
        paintBack.setColor(Color.LTGRAY);
        paintText.setColor(Color.BLACK);
        paintBack.setAlpha(80);
        paintText.setAlpha(120);
        paintRect.setAlpha(129);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(55f);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paintBack);
        for (int i = 0; i < colors.length; i++) {
            float fraction1 = ((float) i / (float) (colors.length)) * (float) getHeight();
            float fraction2 = ((float) (i + 1) / (float) (colors.length)) * (float) getHeight();
            Log.e("legend", fraction1 + "<-->" + fraction2);
            if (i == 0) {
                canvas.drawText("0", getWidth() / 2, (fraction1 + fraction2) / 2, paintText);
            }else if(i==colors.length-1){
                canvas.drawText(scales[i]+"+", getWidth() / 2, (fraction1 + fraction2) / 2, paintText);
            } else {
                canvas.drawText(scales[i]+"", getWidth() / 2, (fraction1 + fraction2) / 2, paintText);
            }
            paintRect.setColor(colors[i]);
            canvas.drawRect(0, fraction1, 35, fraction2, paintRect);
        }
        canvas.drawText(text, 0, getHeight() / 2, paintText);
    }

    public void setParams(Integer[] colors, int[] scales) {
        this.colors = colors;
        this.scales = scales;
        invalidate();

    }
}
