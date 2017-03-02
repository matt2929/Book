    /*
     * ======================================================================
     * Copyright � 2014 Qualcomm Technologies, Inc. All Rights Reserved.
     * QTI Proprietary and Confidential.
     * =====================================================================
     * @file: DrawView.java
     */

package com.example.matthew.book.EyeTracking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.hardware.Camera;
import android.view.SurfaceView;

import com.qualcomm.snapdragon.sdk.face.FaceData;

    public class DrawView extends SurfaceView {

        private final Paint leftEyeBrush = new Paint();
        private final Paint rightEyeBrush = new Paint();
        private final Paint mouthBrush = new Paint();
        private final Paint rectBrush = new Paint();
        public Point leftEye, rightEye, mouth;
        Rect mFaceRect;
        public FaceData[] mFaceArray;
        boolean _inFrame;            // Boolean to see if there is any faces in the frame
        int mSurfaceWidth;
        int mSurfaceHeight;
        int cameraPreviewWidth;
        int cameraPreviewHeight;
        boolean mLandScapeMode;
        float scaleX = 1.0f;
        float scaleY = 1.0f;

        public DrawView(Context context, FaceData[] faceArray, boolean inFrame, int surfaceWidth, int surfaceHeight,
                        Camera cameraObj, boolean landScapeMode) {
            super(context);

            setWillNotDraw(false);                    // This call is necessary, or else the draw method will not be called.
            mFaceArray = faceArray;
            _inFrame = inFrame;
            mSurfaceWidth = surfaceWidth;
            mSurfaceHeight = surfaceHeight;
            mLandScapeMode = landScapeMode;
            if (cameraObj != null) {
                cameraPreviewWidth = cameraObj.getParameters().getPreviewSize().width;
                cameraPreviewHeight = cameraObj.getParameters().getPreviewSize().height;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {

            if (_inFrame)
            {
                for (int i = 0; i < mFaceArray.length; i++) {
                    if (mFaceArray[i].leftEye != null) {
                        leftEyeBrush.setColor(Color.RED);
                    //    canvas.drawCircle(mFaceArray[i].leftEye.x * scaleX, mFaceArray[i].leftEye.y * scaleY, 5f,
                    //
                        //            leftEyeBrush);

                        rightEyeBrush.setColor(Color.GREEN);
                      //  canvas.drawCircle(mFaceArray[i].rightEye.x * scaleX, mFaceArray[i].rightEye.y * scaleY, 5f,
                      //          rightEyeBrush);

                        mouthBrush.setColor(Color.WHITE);
                        //canvas.drawCircle(mFaceArray[i].mouth.x * scaleX, mFaceArray[i].mouth.y * scaleY, 5f, mouthBrush);
                    }
                    if (mFaceArray[i].leftEyeObj != null) {
                        mouthBrush.setColor(Color.CYAN);
                    }
                }
            } else {
                canvas.drawColor(0, Mode.CLEAR);
            }
        }
        public void drawEye(double eyeX, double eyeY){

        }

    }
