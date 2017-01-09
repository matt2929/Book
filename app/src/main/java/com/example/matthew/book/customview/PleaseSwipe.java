package com.example.matthew.book.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.matthew.book.R;

import static android.R.attr.width;

/**
 * Created by Matthew on 1/7/2017.
 */

public class PleaseSwipe extends RelativeLayout {
    private float _Acc = 1.25f, _Velocity = 2;
    private float _Margin = 15f;
    private float _CurrentX = 0;
    private boolean _LeftAndRight = false;
    private Paint paintBlack = new Paint(), paintGrey = new Paint(), paintYellow = new Paint();
    private Bitmap finger;

    public void setUp() {
        paintBlack.setColor(Color.DKGRAY);
        paintGrey.setColor(Color.GRAY);
        paintYellow.setColor(Color.RED);
        Bitmap immutableBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.finger);
        finger = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true);


    }

    public PleaseSwipe(Context context) {
        super(context);
        setUp();
    }

    public PleaseSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public PleaseSwipe(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateBall();
        canvas = drawBoxAndSlider(canvas);

    }

    private void updateBall() {
      //  Log.e("X","<"+_CurrentX+">");
        if (!_LeftAndRight) {
            if (_CurrentX == _Margin) {
                _CurrentX = this.getWidth() - _Margin;
            } else {
                _Velocity = _Velocity * Math.abs(_Acc);
                _CurrentX -= Math.abs(_Velocity);
                if (_CurrentX <= _Margin) {
                    _CurrentX = _Margin;
                    _Velocity = 1;
                }
            }
        } else {
            if (_CurrentX <= _Margin || _CurrentX >= this.getWidth() - _Margin) {
                _Velocity = 1;
                _Acc = -_Acc;
            }
            _Velocity = _Velocity * _Acc;
            _CurrentX -= _Velocity;
        }
    }

   private Canvas drawBoxAndSlider(Canvas canvas) {
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paintBlack);
      //  canvas.drawRect(_Margin, (this.getHeight() / 2) - 10, this.getWidth() - _Margin, (this.getHeight() / 2) + 10, paintGrey);
      //  canvas.drawCircle(_CurrentX, this.getHeight() / 2, this.getHeight() / 20, paintYellow);
       canvas.drawBitmap(finger,_CurrentX-(finger.getWidth()/2),this.getHeight()/2 - (finger.getHeight()/2),paintYellow);
        return canvas;

    }

    public void update(boolean leftAndRight) {
        invalidate();
        _LeftAndRight = leftAndRight;
    }
}
