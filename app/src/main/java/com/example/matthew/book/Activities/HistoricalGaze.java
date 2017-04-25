package com.example.matthew.book.Activities;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.ReadingSession;
import com.example.matthew.book.Util.SaveData;
import com.example.matthew.book.customview.DrawPointTransparent;
import com.example.matthew.book.customview.HeatView;

public class HistoricalGaze extends AppCompatActivity {

    DrawPointTransparent drawPointTransparent;
    Button start, stop, restart;
    TextView textView;
    ProgressBar progressBar;
    boolean running = false;
    int position = 0;
    ReadingSession.PageInfo readingSessions;
    DrawGazeRepeat drawGazeRepeat;
    SeekBar seekBar;
    private CheckBox checkBox, checkBoxRead;
    private HeatView heatView;
    private boolean pre = true;
    int delay = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_gaze);
        SaveData saveData = new SaveData(getApplicationContext());
        drawPointTransparent = (DrawPointTransparent) findViewById(R.id.DrawPointEye);
        readingSessions = saveData.getReadingSessions(getApplicationContext()).get(HistoricalSessions.IndexClicked).getPageInfo().get(HistoricalPages.pageIndex);
        ImageView imageView = (ImageView) findViewById(R.id.showwhereeyeare);
        checkBox = (CheckBox) findViewById(R.id.gazeCheckBox);
        start = (Button) findViewById(R.id.gazeStart);
        stop = (Button) findViewById(R.id.gazeStop);
        restart = (Button) findViewById(R.id.gazeRestart);
        textView = (TextView) findViewById(R.id.gazeText);
        checkBox = (CheckBox) findViewById(R.id.gazeCheckBox);
        checkBoxRead = (CheckBox) findViewById(R.id.beforeOrAfterRead);
        heatView = (HeatView) findViewById(R.id.HeatMap);
        progressBar = (ProgressBar) findViewById(R.id.gazeProgress);
        progressBar.setMax(100);
        seekBar = (SeekBar) findViewById(R.id.seekBarEye);
        Handler handler = new Handler();
        drawGazeRepeat = new DrawGazeRepeat(handler);
        drawGazeRepeat.run();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = true;

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 0;
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                delay = (seekBar.getMax() - seekBar.getProgress()) + 5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( b ) {
                    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    if ( pre ) {
                        heatView.init(readingSessions.getEyePre(), width, height);
                    } else {
                        heatView.init(readingSessions.getEyePost(), width, height);
                    }
                    heatView.turnOn();
                } else {
                    heatView.turnOff();
                }
            }
        });
        checkBoxRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                if ( b ) {
                    pre = false;
                    position=0;
                    heatView.init(readingSessions.getEyePost(), width, height);
                } else {
                    pre = true;
                    position=0;

                    heatView.init(readingSessions.getEyePre(), width, height);

                }
            }
        });
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        switch (HistoricalPages.pageIndex) {
            case 0:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book1));
                break;
            case 1:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book2));
                break;
            case 2:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book3));
                break;
            case 3:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book4));
                break;
            case 4:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book5));
                break;
            case 5:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book6));
                break;
            case 6:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book7));
                break;
            case 7:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book8));
                break;
        }
    }

    public void drawPointTouch(float x, float y, boolean good) {

        drawPointTransparent.setPoint(x, y, good);
    }

    public void updateView() {
        ReadingSession.Touch touch;
        if ( pre ){
             touch = readingSessions.getEyePre().get(position);
        }else{
            touch = readingSessions.getEyePost().get(position);
        }
        drawPointTouch(touch.get_X(), touch.get_Y(), touch.is_Good());
        float progress = (float) position / (float) readingSessions.getEyePre().size();
        textView.setText("Coordinate:[" + touch.get_X() + "," + touch.get_Y() + "]");
        progressBar.setProgress((int) (progress * 100));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        heatView.init(readingSessions.getEyePre(), width, height);
    }

    class DrawGazeRepeat implements Runnable {
        private Handler handler;

        public DrawGazeRepeat(Handler handler) {
            this.handler = handler;
        }

        public void run() {
            if ( pre ) {
                if ( running ) {
                    if ( position + 1 == readingSessions.getEyePre().size() ) {

                    } else {
                        position++;
                    }
                    updateView();
                }

            } else {
                if ( running ) {
                    if ( position + 1 == readingSessions.getEyePost().size() ) {

                    } else {
                        position++;
                    }
                    updateView();
                }

            }
            handler.postDelayed(this, delay);
        }
    }
}