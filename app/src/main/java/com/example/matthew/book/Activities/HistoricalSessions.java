package com.example.matthew.book.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class HistoricalSessions extends AppCompatActivity {
    public static int IndexClicked = 0;
    ArrayList<ReadingSession> readingSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_sessions);
        ListView listView = (ListView) findViewById(R.id.listViewSessions);
        SaveData saveData = new SaveData(getApplicationContext());
        readingSessions = saveData.getReadingSessions(getApplicationContext());
        ArrayList<String> strings = new ArrayList<>();
        int count = 0;
        for (int i = readingSessions.size() - 1; i >= 0; i--) {
            ReadingSession r = readingSessions.get(i);
            Calendar start = r.getStartTime();
            Calendar end = r.getEndTime();
            ArrayList<ReadingSession.PageInfo> pageInfos = r.getPageInfo();
            int good = 0;
            int bad = 0;
            int localGood = 0;
            int localBad = 0;
            double max = 0.0;
            int minPage = 0;
            for (int j = 0; j < pageInfos.size(); j++) {
                ReadingSession.PageInfo p = pageInfos.get(j);
                for (ReadingSession.Touch t : p.getTouches()) {
                    if (t.is_Good()) {
                        good++;
                        localGood++;
                    } else {
                        localBad++;
                        bad++;
                    }
                }
              if (((double) localBad / (double) (localGood + localBad))*100>=max){
                    max=((double) localBad / (double) (localGood + localBad))*100;
                    minPage=j;
                }
                localBad=0;
                localGood=0;
            }
            SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
            strings.add("User Name: "+FrontPage.name
                    + "Session Number: " + (++count)
                    + "\nStart Time: " + format.format(start.getTime())
                    + "\nEnd Time: " + format.format(end.getTime())
                    + "\nAccuracy: " + ((double) good / (double) (good + bad)) * 100.0 + "%"
                    + "\nWorse Page: " + (minPage+1));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                strings);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndexClicked = (readingSessions.size() - 1) - position;
                Intent i = new Intent(getApplicationContext(), HistoricalPages.class);
                startActivity(i);
            }
        });
    }
}
