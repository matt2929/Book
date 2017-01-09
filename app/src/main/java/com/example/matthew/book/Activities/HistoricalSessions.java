package com.example.matthew.book.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.SaveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoricalSessions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_sessions);
        ListView listView = (ListView) findViewById(R.id.listViewSessions);
        SaveData saveData = new SaveData(getApplicationContext());
        ArrayList<SaveData.ReadingSession> readingSessions = saveData.getReadingSessions(getApplicationContext());
        ArrayList<String> strings = new ArrayList<>();
        int count = 0;
        for (SaveData.ReadingSession r : readingSessions) {
            Calendar start = r.getStartTime();
            Calendar end = r.getEndTime();
            SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
            strings.add("Session Number: " + (++count)
                    + "\nStart Time: " + format.format(start)
                    + "\nEnd Time: " + format.format(end));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                strings);

        listView.setAdapter(arrayAdapter);
    }
}
