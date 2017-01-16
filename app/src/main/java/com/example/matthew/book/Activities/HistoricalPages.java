package com.example.matthew.book.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.ReadingSession;
import com.example.matthew.book.Util.SaveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoricalPages extends AppCompatActivity {
    public static int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_pages);
        ListView listView = (ListView) findViewById(R.id.listViewPages);
        SaveData saveData = new SaveData(getApplicationContext());
        ReadingSession readingSessions = saveData.getReadingSessions(getApplicationContext()).get(HistoricalSessions.IndexClicked);
        ArrayList<String> strings = new ArrayList<>();
        int count = 0;
        for (ReadingSession.PageInfo p : readingSessions.getPageInfo()) {
            long duration = 0;
            if (p.getDuration() != null) {
                duration = p.getDuration();
            }
            int pageNum = p.getPageNum();
            int[] goodbad = getGoodBadTouches(p.getTouches());
            int good = goodbad[0];
            int bad = goodbad[1];
            long h = duration / (60 * 60 * 1000);
            long x = duration - h * (60 * 60 * 1000);
            long m = duration / (60 * 1000);
            duration = duration - m * (60 * 1000);
            long s = duration / 1000;
            float percentage = 0f;
            if (bad != 0&& p.getNumEarly()!=0) {
                percentage = ((float) p.getNumEarly() / (float) bad)*100;
            }
            duration = duration - s * 1000;
            String temp = "";
            temp += "Page #: " + pageNum + "\n";
            temp += "Duration Spent Completing Page: " + h + " Hour(s) " + m + " Minute(s) " + s + " Second(s) " + duration + " Millisecond(s) " + "\n";
            temp += "Good Touches: " + good + " Bad Touches: " + bad + "\nPercentage of Bad Touches that where early: " + percentage + "%\n";
            strings.add(temp);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    strings);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pageIndex=position;
                    Intent i = new Intent(getApplicationContext(), HistoricalTouches.class);
                    startActivity(i);
                }
            });
        }
    }

    public int[] getGoodBadTouches(ArrayList<ReadingSession.Touch> touches) {
        int good = 0;
        int bad = 0;
        for (int i = 0; i < touches.size(); i++) {
            if (touches.get(i).is_Good()) {
                good++;
            } else {
                bad++;
            }
        }
        return new int[]{good, bad};
    }

}
