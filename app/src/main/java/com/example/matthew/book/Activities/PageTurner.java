package com.example.matthew.book.Activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matthew.book.R;
import com.example.matthew.book.fragments.Page;
import com.example.matthew.book.fragments.PageFive;
import com.example.matthew.book.fragments.PageFour;
import com.example.matthew.book.fragments.PageOne;
import com.example.matthew.book.fragments.PageThree;
import com.example.matthew.book.fragments.PageTwo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class PageTurner extends Activity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    TextView textView;
    Button nextPage, Repeat;
    RelativeLayout ll;
    FrameLayout fragCase;
    MediaPlayer mediaPlayer;
    int clickCount = 0;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction transaction;
    Page _CurrentPage = new PageOne();
    TextToSpeech tts;
    boolean firstSpeak = false;
    boolean canClick = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_turner);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        textView = (TextView) findViewById(R.id.textonpage);
        tts = new TextToSpeech(this, this);
        tts.setOnUtteranceCompletedListener(this);
        mediaPlayer = MediaPlayer.create(PageTurner.this, R.raw.dirtmove);
        fragmentManager = getFragmentManager();
        _CurrentPage.passMediaPlayer(getApplicationContext());
        Repeat = (Button) findViewById(R.id.repeatspeaks);
        Repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
            }
        });
        nextPage = (Button) findViewById(R.id.changefragment);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("turn", "" + clickCount);
                if (_CurrentPage.doneTouching()) {
                    transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.animator.fadein, R.animator.fadeout);
                    if (clickCount == 0) {
                        _CurrentPage = new PageTwo();
                        transaction.replace(fragCase.getId(), _CurrentPage);

                    } else if (clickCount == 1) {
                        _CurrentPage = new PageThree();
                        transaction.replace(fragCase.getId(), _CurrentPage);

                    } else if (clickCount == 2) {
                        _CurrentPage = new PageFour();
                        transaction.replace(fragCase.getId(), _CurrentPage);
                    } else if (clickCount == 3) {
                        _CurrentPage = new PageFive();
                        transaction.replace(fragCase.getId(), _CurrentPage);

                    } else if (clickCount == 4) {
                        _CurrentPage = new PageFive();
                        transaction.replace(fragCase.getId(), _CurrentPage);

                    }
                    textView.setText(_CurrentPage.getString());
                    transaction.commit();
                    clickCount++;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                    tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
                    _CurrentPage.passMediaPlayer(getApplicationContext());
                    _CurrentPage.enabledisabletouch(false);
                    canClick=false;
                }
            }
        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (canClick) {
                    int off = textView.getOffsetForPosition(event.getX(), event.getY());
                    if (off < _CurrentPage.getString().length()) {
                        Log.e("Val", "" + off);
                        String myWord = "";
                        for (int i = 0; i < _CurrentPage.getString().length(); i++) {
                            if (_CurrentPage.getString().charAt(i) == ' ' && i < off) {
                                myWord = "";
                            } else {
                                if (_CurrentPage.getString().charAt(i) == ' ') {
                                    break;
                                }
                            }
                            myWord = myWord + _CurrentPage.getString().charAt(i);
                        }
                        textView.getText();
                        tts.speak(myWord, TextToSpeech.QUEUE_FLUSH, null);
                    }


                }
                return false;
            }
            });
        fragCase = (FrameLayout) findViewById(R.id.fragmentcase);
        getFragmentManager().beginTransaction().replace(R.id.fragmentcase, _CurrentPage).commit();
    }

    @Override
    public void onUtteranceCompleted(String s) {
        Log.e("work", "work");
        canClick=true;
        _CurrentPage.enabledisabletouch(true);
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        // TTS is successfully initialized
        if (status == TextToSpeech.SUCCESS) {
            // Setting speech language
            int result = tts.setLanguage(Locale.US);
            tts.setSpeechRate(.8f);
            tts.setPitch(1f);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Cook simple toast message with message
                Toast.makeText(getApplicationContext(), "Language not supported",
                        Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
            // Enable the nextPage - It was disabled in main.xml (Go back and
            // Check it)
            else {
            }

        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG)
                    .show();
            Log.e("TTS", "Initilization Failed");
        }
        if (!firstSpeak) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
            firstSpeak = !firstSpeak;
        }
    }
}
