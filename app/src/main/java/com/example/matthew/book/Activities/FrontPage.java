package com.example.matthew.book.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.matthew.book.R;

public class FrontPage extends Activity {
    Button start, author, history,confirmName;
    EditText editText;
    public static String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        start = (Button) findViewById(R.id.titlereadtome);
        author = (Button) findViewById(R.id.author);
        history = (Button) findViewById(R.id.ADMIN);
        confirmName = (Button) findViewById(R.id.savename);
        editText=(EditText) findViewById(R.id.editText);
        mainButtons();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterNameView();

            }
        });
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Authors.class);
                startActivity(i);

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistoricalSessions.class);
                startActivity(i);

            }
        });
        confirmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PageTurner.class);
                startActivity(i);
                name=confirmName.getText().toString();
            }
        });
    }
    public void mainButtons(){
        start.setVisibility(View.VISIBLE);
        author.setVisibility(View.VISIBLE);
        history.setVisibility(View.VISIBLE);
        confirmName.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

    }
    public void enterNameView(){
        start.setVisibility(View.GONE);
        author.setVisibility(View.GONE);
        history.setVisibility(View.GONE);
        confirmName.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
    }
}