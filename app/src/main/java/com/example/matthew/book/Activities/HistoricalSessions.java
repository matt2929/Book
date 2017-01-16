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
import java.util.List;

public class HistoricalSessions extends AppCompatActivity {
public static int  IndexClicked = 0;
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
        for (int i = readingSessions.size()-1;i>=0;i--){
            ReadingSession r = readingSessions.get(i);
            Calendar start = r.getStartTime();
            Calendar end = r.getEndTime();
            SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
            strings.add("Session Number: " + (++count)
                    + "\nStart Time: " + format.format(start.getTime())
                    + "\nEnd Time: " + format.format(end.getTime()));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                strings);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndexClicked=(readingSessions.size()-1)-position;
                Intent i = new Intent(getApplicationContext(), HistoricalPages.class);
                startActivity(i);
            }
        });
    }
}
