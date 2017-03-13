package com.example.matthew.book.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.matthew.book.R;

public class FrontPage extends Activity {
    Button start, author, history, confirmName;
    EditText editText;
    CheckBox checkBox;
    public static boolean EYETRACK = true;
    public static String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_front_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if ( ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ) {

            // Should we show an explanation?
            if ( ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS) ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                int n = 0;
                ActivityCompat.requestPermissions(this,
                        new String[]{ Manifest.permission.CAMERA, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },
                        n);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        start = (Button) findViewById(R.id.titlereadtome);
        author = (Button) findViewById(R.id.author);
        history = (Button) findViewById(R.id.ADMIN);
        confirmName = (Button) findViewById(R.id.savename);
        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
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
                name = editText.getText().toString();
                Log.e("NAME", name);
                Intent i;
                if ( EYETRACK ) {
                    i = new Intent(getApplicationContext(), CameraPreviewActivity.class);
                } else {
                    i = new Intent(getApplicationContext(), PageTurner.class);
                }
                startActivity(i);

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                EYETRACK = b;
            }
        });
    }

    public void mainButtons() {
        start.setVisibility(View.VISIBLE);
        author.setVisibility(View.VISIBLE);
        history.setVisibility(View.VISIBLE);
        confirmName.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

    }

    public void enterNameView() {
        start.setVisibility(View.GONE);
        author.setVisibility(View.GONE);
        history.setVisibility(View.GONE);
        confirmName.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
    }
}