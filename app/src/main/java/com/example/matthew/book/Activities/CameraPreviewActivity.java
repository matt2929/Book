/*
 * ======================================================================
 * Copyright � 2014 Qualcomm Technologies, Inc. All Rights Reserved.
 * QTI Proprietary and Confidential.
 * =====================================================================
 * @file: CameraPreviewActivity.java
 */

package com.example.matthew.book.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matthew.book.EyeTracking.Calibration9Point;
import com.example.matthew.book.EyeTracking.CameraSurfacePreview;
import com.example.matthew.book.EyeTracking.DrawView;
import com.example.matthew.book.EyeTracking.MovingAverage;
import com.example.matthew.book.EyeTracking.NinePointCalibrationView;
import com.example.matthew.book.R;
import com.example.matthew.book.Util.SaveCSV;
import com.qualcomm.snapdragon.sdk.face.FaceData;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing.FP_MODES;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing.PREVIEW_ROTATION_ANGLE;

import java.util.EnumSet;

@SuppressLint("NewApi")
public class CameraPreviewActivity extends Activity implements Camera.PreviewCallback {

    // Global Variables Required

    Camera cameraObj;
    FrameLayout preview;

    FacialProcessing faceProc;
    FaceData[] faceArray = null;// Array in which all the face data values will be returned for each face detected.
    View myView;
    //calibration////
    static Calibration9Point calibration9Point = new Calibration9Point();
    NinePointCalibrationView ninePointCalibration;
    boolean calibrationAvailable = false;
    ////////////////
    ImageView imageView;
    boolean canRecord = false;
    Canvas canvas = new Canvas();
    private Clock2 clock2;
    private Handler handler;
    private CameraSurfacePreview mPreview;
    private DrawView drawView;
    private final int FRONT_CAMERA_INDEX = 1;
    private final int BACK_CAMERA_INDEX = 0;
    CheckBox checkBox;
    boolean recordingMovement = false;
    // boolean clicked = false;
    ProgressBar progressBar;
    boolean fpFeatureSupported = false;
    boolean cameraPause = false;        // Boolean to check if the "pause" button is pressed or no.
    static boolean cameraSwitch = false;    // Boolean to check if the camera is switched to back camera or no.
    boolean landScapeMode = false;      // Boolean to check if the phone orientation is in landscape mode or portrait mode.
    Button record, restartCalibration;

    int cameraIndex;// Integer to keep track of which camera is open.
    int smileValue = 0;
    int leftEyeBlink = 0;
    int rightEyeBlink = 0;
    int faceRollValue = 0;

    int pitch = 0;
    int yaw = 0;
    int horizontalGaze = 0;
    int verticalGaze = 0;
    PointF gazePointValue = null;
    private final String TAG = "CameraPreviewActivity";
    // TextView Variables
    int surfaceWidth = 0;
    int surfaceHeight = 0;
    static MovingAverage movingAverageX, movingAverageY;
    OrientationEventListener orientationEventListener;
    int deviceOrientation;
    int presentOrientation;
    float rounded;
    Display display;
    int displayAngle;
    int progressMax = 100;
    int progressCurent = 0;

    private TextView coordinateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        myView = new View(CameraPreviewActivity.this);
        record = (Button) findViewById(R.id.Record);
        restartCalibration = (Button) findViewById(R.id.restartcalibration);
        record.setTextColor(Color.GREEN);

        preview = (FrameLayout) findViewById(R.id.camera_preview);
        coordinateText = (TextView) findViewById(R.id.coordinateText);
        imageView = (ImageView) findViewById(R.id.imageView);
        ninePointCalibration = (NinePointCalibrationView) findViewById(R.id.pointcalibration);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(progressMax);
        //save data
        //save data
        //clocks
        handler = new Handler();
        clock2 = new Clock2(handler);
        clock2.run();
        ///smoothing algorithm
        movingAverageX = new MovingAverage(20);
        movingAverageY = new MovingAverage(20);
        // Check to see if the FacialProc feature is supported in the device or no.
        fpFeatureSupported = FacialProcessing
                .isFeatureSupported(FacialProcessing.FEATURE_LIST.FEATURE_FACIAL_PROCESSING);

        if (fpFeatureSupported && faceProc == null) {
            Log.e("TAG", "Feature is supported");
            faceProc = FacialProcessing.getInstance();  // Calling the Facial Processing Constructor.
            faceProc.setProcessingMode(FP_MODES.FP_MODE_VIDEO);
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
        mPreview = new CameraSurfacePreview(CameraPreviewActivity.this, cameraObj, faceProc);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.removeView(mPreview);
         preview.addView(mPreview);
        cameraObj.setPreviewCallback(CameraPreviewActivity.this);

        orientationListener();
        display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        record.setOnClickListener(new OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if (canRecord) {
                                              if (progressCurent != progressMax) {

                                              } else {
                                                  calibratingView();
                                              }
                                          } else {
                                              Toast.makeText(CameraPreviewActivity.this, "Adjust Face", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                  }
        );
        restartCalibration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PageTurner.class);
                startActivity(i);     }
        });

    }

    FaceDetectionListener faceDetectionListener = new FaceDetectionListener() {

        @Override
        public void onFaceDetection(Face[] faces, Camera camera) {
            Log.e(TAG, "Faces Detected through FaceDetectionListener = " + faces.length);
        }
    };

    public void calibratingView() {
        progressBar.setVisibility(View.GONE);
        ninePointCalibration.start();
        recordingMovement = true;
        record.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        record.setTextColor(Color.YELLOW);
        coordinateText.setVisibility(View.INVISIBLE);
        restartCalibration.setVisibility(View.GONE);
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


    /*
     * Function for pause button action listener to pause and resume the preview.
     */


    /*
     * This function will update the TextViews with the new values that come in.
     */

    public void setUI(int numFaces, int smileValue, int leftEyeBlink, int rightEyeBlink, int faceRollValue,
                      int faceYawValue, int facePitchValue, PointF gazePointValue, int horizontalGazeAngle, int verticalGazeAngle) {

        if (numFaces > 0) {
            canRecord = true;
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_video_online));
            if (progressCurent + 1 <= progressMax)
                progressCurent++;
            progressBar.setProgress(progressCurent);

        } else {
            progressCurent--;
            progressBar.setProgress(progressCurent);
            canRecord = false;
            imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_video_busy));

        }
        if (gazePointValue != null && canRecord) {
            double x = Math.round(gazePointValue.x * 100.0) / 100.0;// Rounding the gaze point value.
            double y = Math.round(gazePointValue.y * 100.0) / 100.0;
            movingAverageX.update(x);
            movingAverageY.update(y);
            if (recordingMovement) {
                if (ninePointCalibration.getCurrentDot() != -1) {
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            stopCamera();
        }

        if (!cameraSwitch)
            startCamera(FRONT_CAMERA_INDEX);
        else
            startCamera(BACK_CAMERA_INDEX);
    }

    /*
     * This is a function to stop the camera preview. Release the appropriate objects for later use.
     */
    public void stopCamera() {
        if (cameraObj != null) {
            cameraObj.stopPreview();
            cameraObj.setPreviewCallback(null);
            preview.removeView(mPreview);
            cameraObj.release();
            faceProc.release();
            faceProc = null;
        }

        cameraObj = null;
    }

    /*
     * This is a function to start the camera preview. Call the appropriate constructors and objects.
     * @param-cameraIndex: Will specify which camera (front/back) to start.
     */
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

        mPreview = new CameraSurfacePreview(CameraPreviewActivity.this, cameraObj, faceProc);
        preview.removeView(mPreview);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        cameraObj.setPreviewCallback(CameraPreviewActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    /*
     * Detecting the face according to the new Snapdragon SDK. Face detection will now take place in this function.
     * 1) Set the Frame
     * 2) Detect the Number of faces.
     * 3) If(numFaces > 0) then do the necessary processing.
     */
    @Override
    public void onPreviewFrame(byte[] data, Camera arg1) {

        presentOrientation = (90 * Math.round(deviceOrientation / 90)) % 360;
        int dRotation = display.getRotation();
        PREVIEW_ROTATION_ANGLE angleEnum = PREVIEW_ROTATION_ANGLE.ROT_0;

        switch (dRotation) {
            case 0:
                displayAngle = 90;
                angleEnum = PREVIEW_ROTATION_ANGLE.ROT_90;
                break;

            case 1:
                displayAngle = 0;
                angleEnum = PREVIEW_ROTATION_ANGLE.ROT_0;
                break;

            case 2:
                // This case is never reached.
                break;

            case 3:
                displayAngle = 180;
                angleEnum = PREVIEW_ROTATION_ANGLE.ROT_180;
                break;
        }

        if (faceProc == null) {
            faceProc = FacialProcessing.getInstance();
        }

        Parameters params = cameraObj.getParameters();
        Size previewSize = params.getPreviewSize();
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
            canvas.drawColor(0, Mode.CLEAR);
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

    class Clock2 implements Runnable {
        private Handler handler;
        private int counter = 0;

        public Clock2(Handler handler) {
            this.handler = handler;
        }

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();

        public void run() {
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            counter++;
            ninePointCalibration.invalidate();
            if (recordingMovement) {
                if (progressCurent < (progressMax * .75)) {
                    //Calibration is running but face detection has become poor in quality
                    ninePointCalibration.forgetTime();
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    //Calibration is running correctly
                    progressBar.setVisibility(View.INVISIBLE);
                    calibration9Point.recordCalibration(movingAverageX.getCurrentNeg(), movingAverageY.getCurrentNeg(), ninePointCalibration.getCurrentDot());
                }
                if (!ninePointCalibration.isRunningCalibration()) {
                    //calibration finished
                    finishedCalibratingView();
                }
            } else {
                if (calibrationAvailable) {
                    //done calibrating currently drawing where user is looking
                    double[] coordinates = calibration9Point.getXYPoportional(movingAverageX.getCurrentNeg(), movingAverageY.getCurrentNeg(), width, height);
                    ninePointCalibration.setBallPosition(coordinates[0], coordinates[1]);
                }
            }
            handler.postDelayed(this, 60);
        }
    }

    public void finishedCalibratingView() {
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        recordingMovement = !recordingMovement;
        calibrationAvailable = true;
        coordinateText.setVisibility(View.VISIBLE);
        restartCalibration.setVisibility(View.VISIBLE);
        coordinateText.setText("Look around, is the yellow dot accurate?");
        restartCalibration.setText("No, Inaccurate, Start Calibration Again");
        record.setText("Yes, Accurate, Begin Reading");
        record.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PageTurner.class);
                startActivity(i);

            }
        });
        restartCalibration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        record.setVisibility(View.VISIBLE);
    }

}

