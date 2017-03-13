package com.example.matthew.book.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.matthew.book.R;
import com.example.matthew.book.Util.ReadingSession;
import com.example.matthew.book.Util.SaveData;
import com.example.matthew.book.customview.DrawPointTransparent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HistoricalTouches extends AppCompatActivity {
    DrawPointTransparent drawPointTransparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_touches);
        ListView listView = (ListView) findViewById(R.id.listViewTouch);
        SaveData saveData = new SaveData(getApplicationContext());
        drawPointTransparent = (DrawPointTransparent) findViewById(R.id.DrawPoint);
        ReadingSession.PageInfo readingSessions = saveData.getReadingSessions(getApplicationContext()).get(HistoricalSessions.IndexClicked).getPageInfo().get(HistoricalPages.pageIndex);
        ArrayList<String> strings = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("h:mm:ss a");
        ImageView imageView = (ImageView) findViewById(R.id.showwheretouchare);
        for (int i = 0; i < readingSessions.getTouches().size(); i++) {
            String temp = "";
            String good = "";
            ReadingSession.Touch touch = readingSessions.getTouches().get(i);
            temp += "Touch " + (i + 1) + ":";
            temp += "X: [" + touch.get_X() + "] Y: [" + touch.get_Y() + "]\n";
            temp += "Time of Touch: " + format.format(touch.get_Time().getTime()) + "\n";
            if (touch.is_Good()) {
                good = "Good";
            } else {
                good = "Bad";
            }
            temp += "The Touch Was: " + good;
            strings.add(temp);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                strings);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaveData saveData = new SaveData(getApplicationContext());
                ReadingSession.Touch touch = saveData.getReadingSessions(getApplicationContext()).get(HistoricalSessions.IndexClicked).getPageInfo().get(HistoricalPages.pageIndex).getTouches().get(position);
                drawPointTouch(touch.get_X(), touch.get_Y(), touch.is_Good());
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
}
