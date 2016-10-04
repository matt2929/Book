package com.example.matthew.book.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matthew.book.R;
import com.example.matthew.book.fragments.Page;
import com.example.matthew.book.fragments.PageOne;
import com.example.matthew.book.fragments.PageTwo;

import java.util.HashMap;
import java.util.Locale;

public class PageTurner extends AppCompatActivity implements TextToSpeech.OnInitListener ,TextToSpeech.OnUtteranceCompletedListener{
    TextView textView;
    Button button;
    RelativeLayout ll;
    FrameLayout fragCase;
    int clickCount = 0;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction transaction;
    Page _CurrentPage = new PageOne();
    TextToSpeech tts;
    boolean firstSpeak = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_turner);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        textView =(TextView) findViewById(R.id.textonpage);
        tts = new TextToSpeech(this, this);
        tts.setOnUtteranceCompletedListener(this);
        fragmentManager = getFragmentManager();
        button = (Button) findViewById(R.id.changefragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_CurrentPage.doneTouching()) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.fadein, R.animator.fadeout);
                    if (clickCount == 0) {
                        _CurrentPage = new PageTwo();
                        transaction.replace(fragCase.getId(), _CurrentPage);
                    } else if (clickCount == 1) {
                        _CurrentPage = new PageTwo();
                        transaction.replace(fragCase.getId(), _CurrentPage);
                    } else {
                        _CurrentPage = new PageTwo();
                        transaction.replace(fragCase.getId(), _CurrentPage);
                    }
                    textView.setText(_CurrentPage.getString());
                    transaction.commit();
                    clickCount++;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                    tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
                    _CurrentPage.enabledisabletouch(false);
                }
            }
        });
        fragCase = (FrameLayout) findViewById(R.id.fragmentcase);
        getFragmentManager().beginTransaction().replace(R.id.fragmentcase, _CurrentPage).commit();
      }

    @Override
    public void onUtteranceCompleted(String s) {
        Log.e("work","work");
        _CurrentPage.enabledisabletouch(true);
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        // TTS is successfully initialized
        if (status == TextToSpeech.SUCCESS) {
            // Setting speech language
            int result = tts.setLanguage(Locale.CANADA);
            tts.setSpeechRate(.8f);
            tts.setPitch(1f);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Cook simple toast message with message
                Toast.makeText(getApplicationContext(), "Language not supported",
                        Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
            // Enable the button - It was disabled in main.xml (Go back and
            // Check it)
            else {

            }

            // TTS is not initialized properly
        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
                    .show();
            Log.e("TTS", "Initilization Failed");
        }
        if (!firstSpeak) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
            firstSpeak=!firstSpeak;
        }
    }
}
