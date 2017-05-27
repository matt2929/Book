package com.example.matthew.book.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.matthew.book.Util.ReadingSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 3/19/2017.
 */

public class HeatView extends RelativeLayout {
    private Paint paint;
    private int widthTile = 15, heightTile = 15;
    private HashMap<String, Integer> trackHeat = new HashMap<>();
    private HashMap<String, Integer> trackHeatColor = new HashMap<>();
    Integer[] colors = new Integer[]{ Color.TRANSPARENT, Color.argb(200,205,237,26),Color.argb(200,237,181,26),Color.argb(200,255,130,26), Color.argb(200,255,49,26) };
    int max=0,min=0;
    public boolean ON = false;
    float width,height;
    public HeatView(Context context) {
        super(context);
        setUp();
    }

    public HeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public HeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAlpha(30);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ( ON ) {
            for ( int i = 0; i < heightTile; i++ ) {
                for ( int j = 0; j < widthTile; j++ ) {
                    int index=trackHeatColor.get("" + i + "" + j);
                    paint.setColor(colors[index]);
                    float left = (getWidth() * ((float) i / (float) widthTile));
                    float top = (getHeight() * ((float) j / (float) heightTile));
                    float right = left + (getWidth()/(float)widthTile);
                    float bottom = top + (getHeight()/ heightTile);
                    canvas.drawRect(left, top, right, bottom, paint);
                }
            }
        }
    }

    public void turnOff() {
        ON = false;
        invalidate();
    }

    public void turnOn() {
        ON = true;
        invalidate();
    }

    public void init(ArrayList<ReadingSession.Touch> touches,float width,float height) {
        this.width=width;
        this.height=height;

        for ( int i = 0; i < heightTile; i++ ) {
            for ( int j = 0; j < widthTile; j++ ) {
                trackHeat.put(("" + i + "" + j), 0);
                trackHeatColor.put(("" + i + "" + j), 0);
            }
            this.invalidate();
        }

        int x = 0, y = 0;
        for ( ReadingSession.Touch t : touches ) {
            if ( t.get_X() >= 0 && t.get_X() < this.width && t.get_Y() >= 0 && t.get_Y() < this.height ) {
                x = (int) (((t.get_X()) *(widthTile)) /(((float) this.width)));
                y = (int) (((t.get_Y()* (heightTile)) / (((float) this.height) )));
                trackHeat.put(("" + x + "" + y), trackHeat.get(("" + x + "" + y))+1);
            }
        }
        int maxEntry = -1;
        int minEntry = Integer.MAX_VALUE;
        for ( Map.Entry<String, Integer> entry : trackHeat.entrySet()) {
            int entryVal = entry.getValue();
            if ( maxEntry == -1 || Integer.compare(entryVal, maxEntry) > 0 ) {
                maxEntry = entryVal;
            }
            if ( Integer.compare(entryVal, maxEntry) < 0 ) {
                minEntry = entryVal;
            }
        }
        max=maxEntry;
        min=minEntry;
        int diff = Math.abs(maxEntry - minEntry);
        int[] heatCheck = new int[5];
        if ( diff >= 4 ) {
            for ( int i = 0; i < 5; i++ ) {
                heatCheck[i] = (int) ((float) i / (float) maxEntry);
            }
        }
        heatCheck[0]=0;
        heatCheck[1]=1;

        for ( Map.Entry<String, Integer> entry : trackHeat.entrySet() ) {
            int value=entry.getValue();

            int a =(int) (((((float)value/ ((float) maxEntry)) * 5f))+.5);

            if(a==5){
                a=4;
            }
            if(value!=0&&a==0){
                a=1;
            }
            trackHeatColor.put(entry.getKey(),a);
        }
    }
    public int[] getLegendValue(){
        int[] legend=new int[6];
        for ( int i = 0; i <5 ; i++ ) {
            legend[i+1] = (int) ((float) i / (float) max);
        }
        legend[0]=0;
        return legend;
    }
    public Integer[] getLegendColor(){
        return colors;
    }
}
