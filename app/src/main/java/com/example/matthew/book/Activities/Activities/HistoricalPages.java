package com.example.matthew.book.Activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matthew.book.Activities.Util.ReadingSession;
import com.example.matthew.book.Activities.Util.SaveData;
import com.example.matthew.book.R;

import java.util.ArrayList;

public class HistoricalPages extends AppCompatActivity {
    public static int pageIndex = -1;
    private Button eyeButt, touchButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_pages);
        final ListView listView = (ListView) findViewById(R.id.listViewPages);
        SaveData saveData = new SaveData(getApplicationContext());
        ReadingSession readingSessions = saveData.getReadingSessions(getApplicationContext()).get(HistoricalSessions.IndexClicked);
        ArrayList<String> strings = new ArrayList<>();
        eyeButt = (Button) findViewById(R.id.eye_data);
        touchButt = (Button) findViewById(R.id.touch_data);
        eyeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( pageIndex == -1 ) {
                    Toast.makeText(getApplicationContext(), "Please Select a Chapter", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), HistoricalGaze.class);
                    startActivity(i);
                }
            }
        });
        touchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( pageIndex == -1 ) {
                    Toast.makeText(getApplicationContext(), "Please Select a Chapter", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), HistoricalTouches.class);
                    startActivity(i);
                }
            }
        });
        int count = 0;
        for ( ReadingSession.PageInfo p : readingSessions.getPageInfo() ) {
            long duration = 0;
            if ( p.getDuration() != null ) {
                duration = p.getDuration();
            }
            int pageNum = p.getPageNum();
            int[] goodbadTouch = getGoodBadTouches(p.getTouches());
            int[] goodbadEye = getGoodBadTouches(p.getEyePre());
            int goodEye = goodbadEye[0];
            int badEye = goodbadEye[1];
            float eyeAccuracy= (float)goodEye/(float)badEye;
            int goodTouch = goodbadTouch[0];
            int badTouch = goodbadTouch[1];
            long h = duration / (60 * 60 * 1000);
            long x = duration - h * (60 * 60 * 1000);
            long m = duration / (60 * 1000);
            duration = duration - m * (60 * 1000);
            long s = duration / 1000;
            float percentage = 0f;
            if ( badTouch != 0 && p.getNumEarly() != 0 ) {
                percentage = ((float) p.getNumEarly() / (float) badTouch) * 100;
            }
            duration = duration - s * 1000;
            String temp = "";
            temp += "Page #: " + pageNum + "\n";
            temp += "Duration Spent Completing Page: " + h + " Hour(s) " + m + " Minute(s) " + s + " Second(s) " + duration + " Millisecond(s) " + "\n";
            temp += "Good Touches: " + goodTouch + " Bad Touches: " + badTouch + "\nPercentage of Bad Touches that where early: " + percentage + "%\n";
            temp += "Eye Accuracy: " + p.getEyePre().size()+"%\n";
            strings.add(temp);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    strings);
            listView.setAdapter(arrayAdapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setSelector(android.R.color.holo_green_light);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pageIndex = position;

                }
            });
        }
    }

    public int[] getGoodBadTouches(ArrayList<ReadingSession.Touch> touches) {
        int good = 0;
        int bad = 0;
        for ( int i = 0; i < touches.size(); i++ ) {
            if ( touches.get(i).is_Good() ) {
                good++;
            } else {
                bad++;
            }
        }
        return new int[]{ good, bad };
    }

}
