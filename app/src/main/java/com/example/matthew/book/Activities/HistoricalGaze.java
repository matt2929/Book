package com.example.matthew.book.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.ReadingSession;
import com.example.matthew.book.Util.SaveData;
import com.example.matthew.book.customview.DrawPointTransparent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoricalGaze extends AppCompatActivity {

    DrawPointTransparent drawPointTransparent;

    Button start, stop, restart;
    TextView textView;
    CheckBox checkBox;
    ProgressBar progressBar;
    boolean running = false;
    int position = 0;
    ReadingSession.PageInfo readingSessions;
    DrawGazeRepeat drawGazeRepeat;
    SeekBar seekBar;
    int delay = 55;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_gaze);
        SaveData saveData = new SaveData(getApplicationContext());
        drawPointTransparent = (DrawPointTransparent) findViewById(R.id.DrawPointEye);
        readingSessions = saveData.getReadingSessions(getApplicationContext()).get(HistoricalSessions.IndexClicked).getPageInfo().get(HistoricalPages.pageIndex);
        ImageView imageView = (ImageView) findViewById(R.id.showwhereeyeare);
        start = (Button) findViewById(R.id.gazeStart);
        stop = (Button) findViewById(R.id.gazeStop);
        restart = (Button) findViewById(R.id.gazeRestart);
        textView = (TextView) findViewById(R.id.gazeText);
        checkBox = (CheckBox) findViewById(R.id.gazeCheckBox);
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

seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        delay = (seekBar.getMax()-seekBar.getProgress())+5;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
        Log.e("data: ", " " + position);
        ReadingSession.Touch touch = readingSessions.getEye().get(position);
        drawPointTouch(touch.get_X(), touch.get_Y(), touch.is_Good());
        float progress = (float) position / (float) readingSessions.getEye().size();

        progressBar.setProgress((int) (progress * 100));

    }

    class DrawGazeRepeat implements Runnable {
        private Handler handler;

        public DrawGazeRepeat(Handler handler) {
            this.handler = handler;
        }

        public void run() {
            if ( running ) {
                if ( position+1 == readingSessions.getEye().size() ) {

                } else {
                    position++;
                }
                updateView();
            }
            handler.postDelayed(this, delay);
        }
    }
}