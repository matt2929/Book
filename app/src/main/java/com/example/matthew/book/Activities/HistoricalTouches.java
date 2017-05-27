package com.example.matthew.book.Activities;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.ReadingSession;
import com.example.matthew.book.Util.SaveData;
import com.example.matthew.book.customview.DrawPointTransparent;
import com.example.matthew.book.customview.HeatView;
import com.example.matthew.book.customview.LegendTransparent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoricalTouches extends AppCompatActivity {

    DrawPointTransparent drawPointTransparent;
    Button start, stop, restart, stepFwd, stepBack;
    TextView textView;
    ProgressBar progressBar;
    boolean running = false;
    int position = 0;
    int switcher=0;

    ReadingSession.PageInfo readingSessions;
    DrawGazeRepeat drawGazeRepeat;
    SeekBar seekBar;
    private CheckBox checkBox;
    private HeatView heatView;
    private LegendTransparent legendTransparent;
    int delay = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_touches);
        SaveData saveData = new SaveData(getApplicationContext());
        drawPointTransparent = (DrawPointTransparent) findViewById(R.id.DrawPointEye);
        legendTransparent = (LegendTransparent) findViewById(R.id.Legend);
        readingSessions = saveData.getReadingSessions(getApplicationContext()).get(HistoricalSessions.IndexClicked).getPageInfo().get(HistoricalPages.pageIndex);
        ImageView imageView = (ImageView) findViewById(R.id.showwhereeyeare);
        checkBox = (CheckBox) findViewById(R.id.gazeCheckBox);
        start = (Button) findViewById(R.id.gazeStart);
        stop = (Button) findViewById(R.id.gazeStop);
        stepFwd = (Button) findViewById(R.id.stepFoward);
        stepBack = (Button) findViewById(R.id.stepBack);
        restart = (Button) findViewById(R.id.gazeRestart);
        textView = (TextView) findViewById(R.id.gazeText);
        checkBox = (CheckBox) findViewById(R.id.gazeCheckBox);
        heatView = (HeatView) findViewById(R.id.HeatMap);

        legendTransparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(view==legendTransparent) {
                    if (legendTransparent.getVisibility() == View.VISIBLE) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                            float x = motionEvent.getRawX();
                            float y = motionEvent.getRawY();
                            int[] coor=new int[2];
                            heatView.getLocationOnScreen(coor);
                           if (x+legendTransparent.getWidth() < coor[0] + heatView.getWidth()) {
                                Log.e("coor", "1");
                                if (x >= 0) {
                                    Log.e("coor", "2");
                                    if (y+legendTransparent.getHeight() <= coor[1] + heatView.getHeight()) {
                                        Log.e("coor", "3");
                                        if (y >= 0) {
                                            Log.e("coor", "4");
                                            if (switcher == 1) {
                                                x= x-coor[0];
                                                y= y-coor[1];
                                                legendTransparent.setX(x);
                                                legendTransparent.setY(y);
                                                switcher = 0;
                                                return true;

                                            } else {
                                                switcher++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return true;
            }
        });

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
        stepBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                position--;
                if (position < 0)
                    position = 0;
                ReadingSession.Touch touch = readingSessions.getTouches().get(position);
                drawPointTouch(touch.get_X(), touch.get_Y(), touch.is_Good());
                float progress = (float) position / (float) readingSessions.getTouches().size() - 1;
                textView.setText("Coordinate:[" + touch.get_X() + "," + touch.get_Y() + "]");
                progressBar.setProgress((int) (progress * 100));

            }
        });
        stepFwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                position++;
                if (position == readingSessions.getTouches().size())
                    position--;
                ReadingSession.Touch touch = readingSessions.getTouches().get(position);
                drawPointTouch(touch.get_X(), touch.get_Y(), touch.is_Good());
                float progress = (float) position / (float) readingSessions.getTouches().size() - 1;
                textView.setText("Coordinate:[" + touch.get_X() + "," + touch.get_Y() + "]");
                progressBar.setProgress((int) (progress * 100));


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
                if (b) {
                    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;
                    heatView.init(readingSessions.getTouches(), width, height);
                    heatView.turnOn();
                    legendTransparent.setVisibility(View.VISIBLE);
                    legendTransparent.setParams(heatView.getLegendColor(), heatView.getLegendValue());

                } else {
                    heatView.turnOff();
                    legendTransparent.setVisibility(View.INVISIBLE);
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

        touch = readingSessions.getTouches().get(position);
        drawPointTouch(touch.get_X(), touch.get_Y(), touch.is_Good());
        float progress = (float) position / (float) readingSessions.getTouches().size() - 1;
        textView.setText("Coordinate:[" + touch.get_X() + "," + touch.get_Y() + "]");
        progressBar.setProgress((int) (progress * 100));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        heatView.init(readingSessions.getTouches(), width, height);
    }

    class DrawGazeRepeat implements Runnable {
        private Handler handler;

        public DrawGazeRepeat(Handler handler) {
            this.handler = handler;
        }

        public void run() {
            if (running) {
                if (position + 1 == readingSessions.getTouches().size()) {

                } else {
                    position++;
                }
                updateView();
            }
            handler.postDelayed(this, delay);
        }
    }
}