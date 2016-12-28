package com.example.matthew.book.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.matthew.book.R;
import com.example.matthew.book.fragments.Page;
import com.example.matthew.book.fragments.PageEight;
import com.example.matthew.book.fragments.PageFive;
import com.example.matthew.book.fragments.PageFour;
import com.example.matthew.book.fragments.PageOne;
import com.example.matthew.book.fragments.PageSeven;
import com.example.matthew.book.fragments.PageSix;
import com.example.matthew.book.fragments.PageThree;
import com.example.matthew.book.fragments.PageTwo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PageTurner extends Activity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    TextView textView;
    Button nextPage, Repeat;
    RelativeLayout ll;
    FrameLayout fragCase;
    Clock clock;
    Clock2 clock2;
    MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<Integer> pageTextRecording = new ArrayList<>();
    ArrayList<Integer> touchDelayRecording = new ArrayList<>();
    Long startTime = System.currentTimeMillis();
    boolean justClick = false;
    Button leftView, rightView;
    int clickCount = 0;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction transaction;
    Page _CurrentPage = new PageEight();
    ArrayList<String> listOfWords = convertPageToList(_CurrentPage);
    //  TextToSpeech tts;
    boolean canClick = false;
    Handler handler,handler2;
    Typeface tf;
    private float x1, x2;
    static final int MIN_DISTANCE = 150;

    ArrayList<Page> allPages = new ArrayList<>();
    int currentPageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_turner);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        resetPages();

        pageTextRecording.add(R.raw.page1);
        pageTextRecording.add(R.raw.page2);
        pageTextRecording.add(R.raw.page3);
        pageTextRecording.add(R.raw.page4);
        pageTextRecording.add(R.raw.page5);
        pageTextRecording.add(R.raw.page6);
        pageTextRecording.add(R.raw.page7);
        pageTextRecording.add(R.raw.page8);

        touchDelayRecording.add(R.raw.slurp);
        touchDelayRecording.add(R.raw.slurp);
        touchDelayRecording.add(R.raw.slurp);
        touchDelayRecording.add(R.raw.slurp);
        touchDelayRecording.add(R.raw.slurp);
        touchDelayRecording.add(R.raw.slurp);
        touchDelayRecording.add(R.raw.slurp);
        touchDelayRecording.add(R.raw.slurp);

        handler = new Handler();
        handler2 = new Handler();

        clock = new Clock(handler);
        clock2= new Clock2(handler2);
        ll = (RelativeLayout) findViewById(R.id.activity_page_turner);
        textView = (TextView) findViewById(R.id.textonpage);
        textView.setText(_CurrentPage.getString());
        leftView = (Button) findViewById(R.id.left);
        rightView = (Button) findViewById(R.id.right);
        leftView.setVisibility(View.INVISIBLE);
        rightView.setVisibility(View.INVISIBLE);
        // tts = new TextToSpeech(this, this);
        mediaPlayer = MediaPlayer.create(this, R.raw.page1);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        clock2.run();
        //  tts.setOnUtteranceCompletedListener(this);
        tf = Typeface.createFromAsset(getAssets(), "fonts/calibri.otf");
        textView.setTypeface(tf);
        mediaPlayer = MediaPlayer.create(PageTurner.this, R.raw.page1);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        fragmentManager = getFragmentManager();
        _CurrentPage.passMediaPlayer(getApplicationContext());
        Repeat = (Button) findViewById(R.id.repeatspeaks);
        Repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                //         tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
                mediaPlayer.start();
            }
        });

//use this - textView.setText(Html.fromHtml("<font color='white'>" + textBeggining + "</font><font color='yellow'>" + theWord + "</font><font color='white'>" + textEnd + "</font>"), TextView.BufferType.NORMAL);

        fragCase = (FrameLayout) findViewById(R.id.fragmentcase);
        getFragmentManager().beginTransaction().replace(R.id.fragmentcase, _CurrentPage).commit();
    }

    public ArrayList<String> convertPageToList(Page page) {
        return new ArrayList<String>(Arrays.asList(page.getString().split(" ")));

    }

    public void resetPages() {
        allPages.clear();
        allPages.add(new PageEight());
        allPages.add(new PageTwo());
        allPages.add(new PageThree());
        allPages.add(new PageFour());
        allPages.add(new PageFive());
        allPages.add(new PageSix());
        allPages.add(new PageSeven());
        allPages.add(new PageOne());
    }

    @Override
    public void onUtteranceCompleted(String s) {
        Log.e("work", "work");
        canClick = true;

        _CurrentPage.enabledisabletouch(true);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        canClick = true;

        _CurrentPage.enabledisabletouch(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startTime = System.currentTimeMillis();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (deltaX < -MIN_DISTANCE) {
                    resetPages();
                    if (_CurrentPage.doneTouching()) {
                        resetPages();
                        transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.animator.fadein, R.animator.fadeout);
                        if (currentPageIndex == allPages.size() - 1) {
                            Intent i = new Intent(getApplicationContext(), Authors.class);
                            startActivity(i);
                        } else {
                            _CurrentPage = allPages.get(++currentPageIndex);
                            if (currentPageIndex == allPages.size() - 1) {
                                _CurrentPage = new PageOne();
                                ll.setBackground(getDrawable(R.drawable.pastellegreenback));
                                textView.setTextColor(Color.WHITE);
                                textView.setShadowLayer(10, 10, 10, Color.BLACK);

                            } else {
                                ll.setBackground(getDrawable(R.drawable.gre2));
                                textView.setTextColor(Color.BLACK);
                                //   textView.setShadowLayer(10, 10, 10, Color.WHITE);
                            }
                            transaction.replace(fragCase.getId(), _CurrentPage);
                            textView.setText(_CurrentPage.getString());
                            transaction.commit();
                            clickCount++;
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                            mediaPlayer = MediaPlayer.create(this, pageTextRecording.get(currentPageIndex));
                            mediaPlayer.setOnPreparedListener(this);
                            mediaPlayer.setOnCompletionListener(this);

                            //      tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
                            _CurrentPage.passMediaPlayer(getApplicationContext());
                            clock.reset();
                            clock.run();
                            _CurrentPage.enabledisabletouch(false);
                            canClick = false;

                        }
                    }
                } else if (deltaX > MIN_DISTANCE) {
                    resetPages();
                    if (_CurrentPage.doneTouching()) {
                        transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.animator.fadein2, R.animator.fadeout2);
                        if (currentPageIndex == 0) {
                        } else {
                            _CurrentPage = allPages.get(--currentPageIndex);
                            if (currentPageIndex == allPages.size() - 1) {
                                _CurrentPage = new PageOne();
                                ll.setBackground(getDrawable(R.drawable.pastellegreenback));
                                textView.setTextColor(Color.WHITE);
                                textView.setShadowLayer(10, 10, 10, Color.BLACK);

                            } else {
                                ll.setBackground(getDrawable(R.drawable.gre2));
                                textView.setTextColor(Color.BLACK);
                                //   textView.setShadowLayer(10, 10, 10, Color.WHITE);
                            }
                            transaction.replace(fragCase.getId(), _CurrentPage);
                            textView.setText(_CurrentPage.getString());
                            transaction.commit();
                            clickCount++;
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                            //   tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
                            mediaPlayer = MediaPlayer.create(this, pageTextRecording.get(currentPageIndex));
                            mediaPlayer.setOnCompletionListener(this);
                            mediaPlayer.setOnPreparedListener(this);
                            _CurrentPage.passMediaPlayer(getApplicationContext());
                            clock.reset();
                            clock.run();
                            _CurrentPage.enabledisabletouch(false);
                            canClick = false;
                        }
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mp.isPlaying()) {

        } else {
            mp.start();
            clock.run();
        }
    }

    @Override
    public void onInit(int status) {
      /*  // TODO Auto-generated method stub
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
        //    tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
            firstSpeak = !firstSpeak;
        }*/
    }


    class Clock implements Runnable {
        private Handler handler;
        private View view;
        private int count = 0;


        public Clock(Handler handler) {
            this.handler = handler;
        }

        public void run() {

            String whiteOne = "";
            String yellowOne = "";
            String whiteOne2 = "";
            if (count == 0) {
                yellowOne = listOfWords.get(0) + " ";
                for (int i = 1; i < listOfWords.size(); i++) {
                    whiteOne2 += listOfWords.get(i) + " ";
                }
                textView.setText(Html.fromHtml("<font color='yellow'>" + yellowOne + "</font><font color='white'>" + whiteOne + "</font>"), TextView.BufferType.NORMAL);
            } else if (count == listOfWords.size() - 1) {
                yellowOne = listOfWords.get(listOfWords.size() - 1) + " ";
                for (int i = 0; i < listOfWords.size() - 1; i++) {
                    whiteOne += listOfWords.get(i) + " ";
                }
                textView.setText(Html.fromHtml("<font color='white'>" + whiteOne + "</font><font color='yellow'>" + yellowOne + "</font>"), TextView.BufferType.NORMAL);
            } else if (count > listOfWords.size() - 1) {
                startTime=System.currentTimeMillis();
            } else {

                Log.i("data", "Array Size: " + listOfWords.size() + " count: " + count);
                for (int i = 0; i < count; i++) {
                    whiteOne += listOfWords.get(i) + " ";
                }
                for (int i = count + 1; i < listOfWords.size(); i++) {
                    whiteOne2 += listOfWords.get(i) + " ";
                }
                yellowOne = listOfWords.get(count) + " ";
                textView.setText(Html.fromHtml("<font color='white'>" + whiteOne + "</font><font color='yellow'>" + yellowOne + "</font><font color='white'>" + whiteOne2 + "</font>"), TextView.BufferType.NORMAL);
            }
            if (count >= listOfWords.size() - 1) {
            } else {
                String nextWord = listOfWords.get(count + 1);

                int delay = 0;
                delay = nextWord.length() * 150;
                if (nextWord.contains(",")) {
                    delay = delay * 2;
                }
                handler.postDelayed(this, delay);
            }
            count++;
        }

        public void reset() {
            count = 0;
            listOfWords = convertPageToList(_CurrentPage);
        }
    }

    class Clock2 implements Runnable {
        private Handler handler;
        private View view;
        private int count = 0;


        public Clock2(Handler handler) {
            this.handler = handler;
        }

        public void run() {
            if(_CurrentPage.doneTouching()) {
                if(justClick==false){
                  startTime=System.currentTimeMillis();
                    justClick=true;
                }else{

                }
                if (Math.abs(startTime - System.currentTimeMillis()) > 10000) {
                    Log.i("vis","vis");
                    leftView.setVisibility(View.VISIBLE);
                    rightView.setVisibility(View.VISIBLE);
                } else {


                    leftView.setVisibility(View.INVISIBLE);
                    rightView.setVisibility(View.INVISIBLE);

                }
            }else{
                justClick=false;
            }
            handler.postDelayed(this, 100);
        }
    }
}

