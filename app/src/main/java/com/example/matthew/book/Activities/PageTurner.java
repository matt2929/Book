package com.example.matthew.book.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matthew.book.EyeTracking.Calibration9Point;
import com.example.matthew.book.EyeTracking.DrawView;
import com.example.matthew.book.EyeTracking.MovingAverage;
import com.example.matthew.book.EyeTracking.NinePointCalibrationView;
import com.example.matthew.book.R;
import com.example.matthew.book.Util.GoodBadTouch;
import com.example.matthew.book.Util.SaveData;
import com.example.matthew.book.customview.PleaseSwipe;
import com.example.matthew.book.fragments.Page;
import com.example.matthew.book.fragments.PageOne;
import com.example.matthew.book.fragments.PageFive;
import com.example.matthew.book.fragments.PageFour;
import com.example.matthew.book.fragments.PageEight;
import com.example.matthew.book.fragments.PageSeven;
import com.example.matthew.book.fragments.PageSix;
import com.example.matthew.book.fragments.PageThree;
import com.example.matthew.book.fragments.PageTwo;
import com.qualcomm.snapdragon.sdk.face.FaceData;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;

public class PageTurner extends Activity implements TextToSpeech.OnInitListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, Camera.PreviewCallback {
    TextView textView;
    Button nextPage, Repeat, test;
    RelativeLayout ll;
    FrameLayout fragCase;
    Clock clock;
    Clock2 clock2;
    Calendar calendar;
    public static boolean dragginSomething = false;
    Long startTimeTouchable = System.currentTimeMillis();
    boolean goodTouch = false;
    PleaseSwipe pleaseSwipe;
    public static ArrayList<Button> allButtons;
    GoodBadTouch goodBadTouch;
    static MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<Integer> pageTextRecording = new ArrayList<>();
    ArrayList<Integer> touchDelayRecording = new ArrayList<>();
    Long startTime = System.currentTimeMillis();
    boolean justClick = false;
    int clickCount = 0;
    android.app.FragmentManager fragmentManager;
    android.app.FragmentTransaction transaction;
    Page _CurrentPage = new PageOne();
    ArrayList<String> listOfWords = convertPageToList(_CurrentPage);
    TextToSpeech tts;
    boolean canClick = false;
    Handler handler2;
    boolean canRecord = false;
    Typeface tf;
    private float x1, x2;
    static final int MIN_DISTANCE = 375;
    ArrayList<Page> allPages = new ArrayList<>();
    int currentPageIndex = 0;
    SaveData saveData;
    private float width = 0, height = 0;
    /////////////////////////////////////////camera
    ///////////////////
    Camera cameraObj;
    FrameLayout preview;
    FacialProcessing faceProc;
    FaceData[] faceArray = null;// Array in which all the face data values will be returned for each face detected.
    //calibration////
    static Calibration9Point calibration9Point = new Calibration9Point();
    NinePointCalibrationView ninePointCalibration;
    boolean calibrationAvailable = false;
    ////////////////
    ImageView imageView;
    Canvas canvas = new Canvas();
    private Handler handler;
    private CameraSurfacePreview mPreview;
    private DrawView drawView;
    private final int FRONT_CAMERA_INDEX = 1;
    private final int BACK_CAMERA_INDEX = 0;
    // boolean clicked = false;
    boolean fpFeatureSupported = false;
    static boolean cameraSwitch = false;    // Boolean to check if the camera is switched to back camera or no.
    boolean landScapeMode = false;      // Boolean to check if the phone orientation is in landscape mode or portrait mode.
///test button

    int cameraIndex;// Integer to keep track of which camera is open.
    int smileValue = 0;
    int leftEyeBlink = 0;
    int rightEyeBlink = 0;
    int faceRollValue = 0;
    View myView;
    int pitch = 0;
    int yaw = 0;
    int horizontalGaze = 0;
    int verticalGaze = 0;
    PointF gazePointValue = null;
    private final String TAG = "CameraPreviewActivity";
    // TextView Variables
    int surfaceWidth = 0;
    int surfaceHeight = 0;
    MovingAverage movingAverageX, movingAverageY;
    OrientationEventListener orientationEventListener;
    int deviceOrientation;
    int presentOrientation;
    float rounded;
    Display display;
    int displayAngle;
    int progressMax = 100;
    int progressCurent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_turner);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        saveData = new SaveData(getApplicationContext());
        resetPages();
        goodBadTouch = new GoodBadTouch(getApplicationContext());
        calendar = Calendar.getInstance();
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
        clock2 = new Clock2(handler2);

        Button testButt = (Button) findViewById(R.id.testsave);
        testButt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Thread thread = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    saveData.savePage(goodBadTouch.get_Touches(), goodBadTouch.get_ReadEyeCoordinates(), goodBadTouch.get_PostReadEyeCoordinates(), goodBadTouch.getEarly(), Math.abs(startTimeTouchable - System.currentTimeMillis()), currentPageIndex + 1);
                                                    saveData.saveSession(getApplicationContext(), calendar, Calendar.getInstance());
                                                }
                                            });
                                            thread.start();
                                        }
                                    }
        );
        ll = (RelativeLayout) findViewById(R.id.activity_page_turner);
        textView = (TextView) findViewById(R.id.textonpage);
        textView.setText(_CurrentPage.getString());
        pleaseSwipe = (PleaseSwipe) findViewById(R.id.swipeAnimation);
        preview = (FrameLayout) findViewById(R.id.previewviewturn);
        myView = new View(PageTurner.this);
        pleaseSwipe.setVisibility(View.GONE);
            Log.e("Initialize", "Initialize");
            tts = new TextToSpeech(this, this);

        if (savedInstanceState == null) {

            mediaPlayer = MediaPlayer.create(this, R.raw.page1);
            mediaPlayer.setVolume(10, 10);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setVolume(10, 10);
        }
        clock2.run();
        tf = Typeface.createFromAsset(getAssets(), "fonts/calibri.otf");
        textView.setTypeface(tf);
        fragmentManager = getFragmentManager();
        _CurrentPage.passMediaPlayer(getApplicationContext());
        Repeat = (Button) findViewById(R.id.repeatspeaks);
        Repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                mediaPlayer.start();
            }
        });

        fragCase = (FrameLayout) findViewById(R.id.fragmentcase);
        if (FrontPage.EYETRACK) {
            calibration9Point = CameraPreviewActivity.calibration9Point;
            movingAverageX = CameraPreviewActivity.movingAverageX;
            movingAverageY = CameraPreviewActivity.movingAverageY;
            fpFeatureSupported = FacialProcessing
                    .isFeatureSupported(FacialProcessing.FEATURE_LIST.FEATURE_FACIAL_PROCESSING);

            if (fpFeatureSupported && faceProc == null) {
                Log.e("TAG", "Feature is supported");
                faceProc = FacialProcessing.getInstance();  // Calling the Facial Processing Constructor.
                faceProc.setProcessingMode(FacialProcessing.FP_MODES.FP_MODE_VIDEO);
            } else {
                Log.e("TAG", "Feature is NOT supported");
                return;
            }

            cameraIndex = Camera.getNumberOfCameras() - 1;// Start with front Camera
            try {
                cameraObj = Camera.open(cameraIndex); // attempt to get a Camera instance
            } catch (Exception e) {
                Log.d("TAG", "Camera Does Not exist");// Camera is not available (in use or does not exist)
            }
            // Change the sizes according to phone's compatibility.
            mPreview = new CameraSurfacePreview(PageTurner.this, cameraObj, faceProc);
            preview.removeView(mPreview);
            preview.addView(mPreview);
            cameraObj.setPreviewCallback(PageTurner.this);

            orientationListener();
        }
        display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        getFragmentManager().beginTransaction().replace(R.id.fragmentcase, _CurrentPage).commit();
    }


    private void orientationListener() {
        orientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                deviceOrientation = orientation;
            }
        };

        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable();
        }

        presentOrientation = 90 * (deviceOrientation / 360) % 360;
    }


    public void setUI(int numFaces, int smileValue, int leftEyeBlink, int rightEyeBlink, int faceRollValue,
                      int faceYawValue, int facePitchValue, PointF gazePointValue, int horizontalGazeAngle, int verticalGazeAngle) {
        if (FrontPage.EYETRACK) {
            if (numFaces > 0) {
                canRecord = true;
            } else {
                canRecord = false;
            }
            if (gazePointValue != null && canRecord) {
                double x = Math.round(gazePointValue.x * 100.0) / 100.0;// Rounding the gaze point value.
                double y = Math.round(gazePointValue.y * 100.0) / 100.0;
                movingAverageX.update(x);
                movingAverageY.update(y);
                ArrayList<View> allButtonsT = new ArrayList<View>(allButtons);
                allButtonsT.add(textView);
                double[] xy = calibration9Point.getXYPoportional(movingAverageX.getCurrentNeg(), movingAverageY.getCurrentNeg(), width, height);
                if (canClick) {
                    goodBadTouch.checkEyePostReadValidity(currentPageIndex, allButtonsT, (int) xy[0], (int) xy[1]);
                } else {
                    goodBadTouch.checkEyeDuringReadValidity(currentPageIndex, allButtonsT, (int) xy[0], (int) xy[1]);

                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (FrontPage.EYETRACK)
            stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cameraObj != null) {
            if (FrontPage.EYETRACK)
                stopCamera();
        }

        if (!cameraSwitch)
            if (FrontPage.EYETRACK)
                startCamera(FRONT_CAMERA_INDEX);
            else if (FrontPage.EYETRACK)
                startCamera(BACK_CAMERA_INDEX);
    }

    public void stopCamera() {

        if (cameraObj != null && FrontPage.EYETRACK) {
            cameraObj.stopPreview();
            cameraObj.setPreviewCallback(null);
            preview.removeView(mPreview);
            cameraObj.release();
            faceProc.release();
            faceProc = null;
        }

        cameraObj = null;
    }

    public void startCamera(int cameraIndex) {

        if (fpFeatureSupported && faceProc == null) {

            Log.e("TAG", "Feature is supported");
            faceProc = FacialProcessing.getInstance();// Calling the Facial Processing Constructor.
        }

        try {
            cameraObj = Camera.open(cameraIndex);// attempt to get a Camera instance
        } catch (Exception e) {
            Log.d("TAG", "Camera Does Not exist");// Camera is not available (in use or does not exist)
        }

        mPreview = new CameraSurfacePreview(PageTurner.this, cameraObj, faceProc);
        preview.removeView(mPreview);
        preview.addView(mPreview);
        cameraObj.setPreviewCallback(PageTurner.this);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (!canClick) {
                goodTouch = false;
            } else {

                if (goodBadTouch.checkTouchValidity(currentPageIndex + 1, allButtons, (int) event.getRawX(), (int) event.getRawY())) {
                    goodTouch = true;
                } else {
                    goodTouch = false;
                }
            }

        }

        startTime = System.currentTimeMillis();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (!canClick) {
                    goodBadTouch.checkTouchValidity(currentPageIndex, allButtons, (int) event.getRawX(), (int) event.getRawY());
                    //  fragCase.setBackground(getResources().getDrawable(R.drawable.listen));
                }
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:

                x2 = event.getX();
                float deltaX = x2 - x1;
                fragCase.setBackground(null);
                mediaPlayer.stop();
                if(!dragginSomething) {
                    if (deltaX < -MIN_DISTANCE) {

                        resetPages();

                        //if (_CurrentPage.doneTouching()) {
                        resetPages();
                        transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.animator.fadein, R.animator.fadeout);
                        goodBadTouch.lastTouchWasAGoodSwipe();
                        saveData.savePage(goodBadTouch.get_Touches(), goodBadTouch.get_ReadEyeCoordinates(), goodBadTouch.get_PostReadEyeCoordinates(), goodBadTouch.getEarly(), Math.abs(startTimeTouchable - System.currentTimeMillis()), currentPageIndex + 1);
                        goodBadTouch.reset(currentPageIndex);

                        if (currentPageIndex == allPages.size() - 1) {
                            new Thread(new Runnable() {
                                public void run() {
                                    saveData.saveSession(getApplicationContext(), calendar, Calendar.getInstance());
                                }
                            }).start();

                            Intent i = new Intent(getApplicationContext(), Authors.class);
                            startActivity(i);
                        } else {
                            _CurrentPage = allPages.get(++currentPageIndex);

                            if (currentPageIndex == allPages.size() - 1) {
                                _CurrentPage = new PageEight();
                                ll.setBackground(getDrawable(R.drawable.pastellegreenback));
                                textView.setTextColor(Color.WHITE);
                                textView.setShadowLayer(10, 10, 10, Color.BLACK);

                            } else {
                                ll.setBackground(getDrawable(R.drawable.gre2));
                                textView.setTextColor(Color.BLACK);
                            }
                            transaction.replace(fragCase.getId(), _CurrentPage);
                            textView.setText(_CurrentPage.getString());
                            transaction.commit();
                            clickCount++;
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = MediaPlayer.create(this, pageTextRecording.get(currentPageIndex));
                            mediaPlayer.setOnPreparedListener(this);
                            mediaPlayer.setOnCompletionListener(this);

                            //      tts.speak(_CurrentPage.getString(), TextToSpeech.QUEUE_FLUSH, map);
                            _CurrentPage.passMediaPlayer(getApplicationContext());
                            clock.reset();
                            clock.run();
                            _CurrentPage.enabledisabletouch(true);

//                        _CurrentPage.enabledisabletouch(false);
                            canClick = false;

                        }

                        // } else if (canClick) {
                        //}

                    } else if (deltaX > 10000000) {
                        resetPages();
                        if (_CurrentPage.doneTouching()) {

                            transaction = fragmentManager.beginTransaction();
                            transaction.setCustomAnimations(R.animator.fadein2, R.animator.fadeout2);
                            if (currentPageIndex == 0) {
                            } else {
                                goodBadTouch.lastTouchWasAGoodSwipe();
                                saveData.savePage(goodBadTouch.get_Touches(), goodBadTouch.get_ReadEyeCoordinates(), goodBadTouch.get_PostReadEyeCoordinates(), goodBadTouch.getEarly(), Math.abs(startTimeTouchable - System.currentTimeMillis()), currentPageIndex + 1);
                                goodBadTouch.reset(currentPageIndex);
                                _CurrentPage = allPages.get(--currentPageIndex);
                                if (currentPageIndex == allPages.size() - 1) {
                                    _CurrentPage = new PageEight();
                                    ll.setBackground(getDrawable(R.drawable.pastellegreenback));
                                    textView.setTextColor(Color.WHITE);
                                    textView.setShadowLayer(10, 10, 10, Color.BLACK);

                                } else {
                                    ll.setBackground(getDrawable(R.drawable.gre2));
                                    textView.setTextColor(Color.BLACK);
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

                                _CurrentPage.enabledisabletouch(true);
                                //                           _CurrentPage.enabledisabletouch(false);
                                canClick = false;
                            }
                        } else if (canClick) {

                        }
                    }
                }
                dragginSomething=false;
                break;
        }
        boolean ret = super.dispatchTouchEvent(event);
        return ret;

    }

    public ArrayList<String> convertPageToList(Page page) {
        return new ArrayList<String>(Arrays.asList(page.getString().split(" ")));

    }

    public void resetPages() {
        allPages.clear();
        allPages.add(new PageOne());
        allPages.add(new PageTwo());
        allPages.add(new PageThree());
        allPages.add(new PageFour());
        allPages.add(new PageFive());
        allPages.add(new PageSix());
        allPages.add(new PageSeven());
        allPages.add(new PageEight());
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        canClick = true;
        _CurrentPage.enabledisabletouch(true);
        startTimeTouchable = System.currentTimeMillis();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mediaPlayer.isPlaying()) {

        } else {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                clock.run();
            }
        }
    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        // TTS is successfully initialized
        tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {

                                                @Override
                                                public void onUtteranceCompleted(String utteranceId) {
                                                    runOnUiThread(new Runnable() {

                                                        @Override
                                                        public void run() {
                                                            //   mediaPlayer.start();
                                                            clock.setPause(false);
                                                        }
                                                    });
                                                }
                                            }
        );
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
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera arg1) {
        if (FrontPage.EYETRACK) {
            presentOrientation = (90 * Math.round(deviceOrientation / 90)) % 360 - 90;
            int dRotation = display.getRotation() - 90;
            FacialProcessing.PREVIEW_ROTATION_ANGLE angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_0;

            switch (dRotation) {
                case 0:
                    displayAngle = 90;
                    angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_90;
                    break;

                case 1:
                    displayAngle = 0;
                    angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_0;
                    break;

                case 2:
                    // This case is never reached.
                    break;

                case 3:
                    displayAngle = 180;
                    angleEnum = FacialProcessing.PREVIEW_ROTATION_ANGLE.ROT_180;
                    break;
            }

            if (faceProc == null) {
                faceProc = FacialProcessing.getInstance();
            }

            Camera.Parameters params = cameraObj.getParameters();
            Camera.Size previewSize = params.getPreviewSize();
            surfaceWidth = mPreview.getWidth();
            surfaceHeight = mPreview.getHeight();

            // Landscape mode - front camera
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !cameraSwitch) {
                faceProc.setFrame(data, previewSize.width, previewSize.height, true, angleEnum);
                cameraObj.setDisplayOrientation(displayAngle);
                landScapeMode = true;
            }
            // landscape mode - back camera
            else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                    && cameraSwitch) {
                faceProc.setFrame(data, previewSize.width, previewSize.height, false, angleEnum);
                cameraObj.setDisplayOrientation(displayAngle);
                landScapeMode = true;
            }
            // Portrait mode - front camera
            else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                    && !cameraSwitch) {
                faceProc.setFrame(data, previewSize.width, previewSize.height, true, angleEnum);
                cameraObj.setDisplayOrientation(displayAngle);
                landScapeMode = false;
            }
            // Portrait mode - back camera
            else {
                faceProc.setFrame(data, previewSize.width, previewSize.height, false, angleEnum);
                cameraObj.setDisplayOrientation(displayAngle);
                landScapeMode = false;
            }

            int numFaces = faceProc.getNumFaces();

            if (numFaces == 0) {
                Log.d("TAG", "No Face Detected");
                if (drawView != null) {
                    preview.removeView(drawView);

                    drawView = new DrawView(this, null, false, 0, 0, null, landScapeMode);
                    preview.addView(drawView);
                }
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                setUI(0, 0, 0, 0, 0, 0, 0, null, 0, 0);
            } else {

                Log.d("TAG", "Face Detected");
                faceArray = faceProc.getFaceData(EnumSet.of(FacialProcessing.FP_DATA.FACE_RECT,
                        FacialProcessing.FP_DATA.FACE_COORDINATES, FacialProcessing.FP_DATA.FACE_CONTOUR,
                        FacialProcessing.FP_DATA.FACE_SMILE, FacialProcessing.FP_DATA.FACE_ORIENTATION,
                        FacialProcessing.FP_DATA.FACE_BLINK, FacialProcessing.FP_DATA.FACE_GAZE));
                // faceArray = faceProc.getFaceData(); // Calling getFaceData() alone will give you all facial data except the
                // face
                // contour. Face Contour might be a heavy operation, it is recommended that you use it only when you need it.
                if (faceArray == null) {
                    Log.e("TAG", "Face array is null");
                } else {
                    if (faceArray[0].leftEyeObj == null) {
                        Log.e(TAG, "Eye Object NULL");
                    } else {
                        Log.e(TAG, "Eye Object not NULL");
                    }

                    faceProc.normalizeCoordinates(surfaceWidth, surfaceHeight);
                    preview.removeView(drawView);// Remove the previously created view to avoid unnecessary stacking of Views.
                    drawView = new DrawView(this, faceArray, true, surfaceWidth, surfaceHeight, cameraObj, landScapeMode);
                    preview.addView(drawView);

                    for (int j = 0; j < numFaces; j++) {
                        smileValue = faceArray[j].getSmileValue();
                        leftEyeBlink = faceArray[j].getLeftEyeBlink();
                        rightEyeBlink = faceArray[j].getRightEyeBlink();
                        faceRollValue = faceArray[j].getRoll();
                        gazePointValue = faceArray[j].getEyeGazePoint();
                        pitch = faceArray[j].getPitch();
                        yaw = faceArray[j].getYaw();
                        horizontalGaze = faceArray[j].getEyeHorizontalGazeAngle();
                        verticalGaze = faceArray[j].getEyeVerticalGazeAngle();
                    }
                    setUI(numFaces, smileValue, leftEyeBlink, rightEyeBlink, faceRollValue, yaw, pitch, gazePointValue,
                            horizontalGaze, verticalGaze);
                }
            }
        }
    }

    class Clock implements Runnable {
        private Handler handler;
        private View view;
        private int count = 0;
        boolean clockPause = false;

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
                startTime = System.currentTimeMillis();
            } else {

                for (int i = 0; i < count; i++) {
                    whiteOne += listOfWords.get(i) + " ";
                }
                for (int i = count + 1; i < listOfWords.size(); i++) {
                    whiteOne2 += listOfWords.get(i) + " ";
                }
                yellowOne = listOfWords.get(count) + " ";
                textView.setText(Html.fromHtml("<font color='white'>" + whiteOne + "</font><font color='yellow'>" + yellowOne + "</font><font color='white'>" + whiteOne2 + "</font>"), TextView.BufferType.NORMAL);
            }
            if (count == listOfWords.size() - 1) {
                handler.postDelayed(this, 750);
            } else if (count > listOfWords.size() - 1) {
                textView.setText(_CurrentPage.getString());

            } else {
                String nextWord = listOfWords.get(count + 1);
                int delay = 0;
                if (currentPageIndex == 0) {
                    delay = nextWord.length() * 60;
                } else if (currentPageIndex == allPages.size() - 1) {
                    delay = nextWord.length() * 140;

                } else if (currentPageIndex == 3) {
                    delay = nextWord.length() * 140;
                    Log.e("page index", "" + 3);
                } else {
                    delay = nextWord.length() * 150;
                }
                if (nextWord.contains(",")) {
                    delay = delay * 2;
                }
                handler.postDelayed(this, delay);
            }
            if (!clockPause) {
                count++;
            }
        }

        public void reset() {
            count = 0;
            listOfWords = convertPageToList(_CurrentPage);
        }

        public void setPause(boolean isPause) {
            clockPause = isPause;
        }
    }

    class Clock2 implements Runnable {
        private Handler handler;
        private View view;
        private int count = 0;


        DisplayMetrics metrics;

        public Clock2(Handler handler) {
            this.handler = handler;
            metrics = getApplicationContext().getResources().getDisplayMetrics();
        }

        public void run() {
            width = metrics.widthPixels;
            height = metrics.heightPixels;

            if (_CurrentPage.doneTouching()) {
                if (justClick == false) {
                    startTime = System.currentTimeMillis();
                    justClick = true;
                } else {
                }
                if (Math.abs(startTime - System.currentTimeMillis()) > 5000) {
                    pleaseSwipe.update(false);
                    pleaseSwipe.setVisibility(View.VISIBLE);
                } else {
                    pleaseSwipe.setVisibility(View.GONE);
                }
            } else {
                justClick = false;
                pleaseSwipe.setVisibility(View.GONE);
            }
            handler.postDelayed(this, 140);
        }
    }
}

