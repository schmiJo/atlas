package com.atlas.atlas.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.atlas.atlas.R;
import com.atlas.atlas.general.Utils;


public class CaptureButton extends View {

    static final byte STATE_SHUTTER = 0;
    static final byte STATE_NORMAL = 2;

    static final byte CAPTURE_NONE = 1;
    static final byte CAPTURE_HOLDING = 2;
    static final byte CAPTURE_RECORDING = 3;
    static final byte CAPTURE_RECEDING = 4;

    static final int maxRecordingTime = 20;
    private static final int earthAnimSpeed = 16;
    private long pressDownTime, startRecedingTime;
    private byte captureState = CAPTURE_NONE;
    private Control control;
    private byte currentState = STATE_NORMAL;
    private float circleRadius;
    private float radiusAdd;
    private int heightDiv2 = 0, widthDiv2 = 0;
    private static boolean recording;
    private float maxRadiusAddSize;
    private boolean wasRecording;
    private float dp20, dp12, dp;
    private float sweepAngle;
    private Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint earthPaint = new Paint();
    private Paint contactPaint = new Paint();
    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CaptureButton(Context context) {
        super(context);
        init();
    }

    public CaptureButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CaptureButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        dp12 = Utils.dpToPixel(12, getContext());
        dp20 = Utils.dpToPixel(20, getContext());
        dp = Utils.dpToPixel(1, getContext());
        circleRadius = dp20;
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(Utils.dpToPixel(4, getContext()));

        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(Utils.dpToPixel(4, getContext()));
        redPaint.setStrokeCap(Paint.Cap.ROUND);
        redPaint.setStyle(Paint.Style.STROKE);
        earthPaint.setAlpha(0);
        contactPaint.setAlpha(0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (widthDiv2 == 0 || heightDiv2 == 0) {
            widthDiv2 = canvas.getWidth() / 2;
            heightDiv2 = canvas.getHeight() / 2;
        }

        switch (currentState) {

            case STATE_SHUTTER:

                switch (captureState) {
                    case CAPTURE_HOLDING: {

                        float x = System.currentTimeMillis() - pressDownTime;
                        double factor = Math.pow(Math.E, ((x / 50) - 3.5));
                        radiusAdd =  (float) ((4 * factor) / (4 + factor)) * dp * 1.5f;

                        if (x > 700) {
                            captureState = CAPTURE_RECORDING;
                            control.onVideoStart();
                            recording = true;
                        }

                        invalidate();
                        break;
                    }
                    case CAPTURE_RECEDING: {
                        long x = System.currentTimeMillis() - startRecedingTime;
                        double factor = Math.pow(Math.E, ((x / 50) - 3.5));
                        float v = (float) ((float) (-1 * ((factor) / (1 + factor)))) + 1.0f;
                        float y = v * maxRadiusAddSize;
                        radiusAdd = y;


                        if (wasRecording) {
                            float l = sweepAngle - (System.currentTimeMillis() - startRecedingTime) / 5;
                            float radius = circleRadius + radiusAdd;
                            canvas.drawCircle(widthDiv2, heightDiv2, circleRadius + radiusAdd, circlePaint);
                            if (l < 2) {
                                l = 0;
                            }
                            canvas.drawArc(widthDiv2 - radius, heightDiv2 - radius, widthDiv2 + radius, heightDiv2 + radius, 270f, l, false, redPaint);
                            invalidate();
                            if (l <= 5 && y <= 5) {
                                captureState = CAPTURE_NONE;
                            }
                            return;
                        } else {
                            if (y <= 5) {
                                captureState = CAPTURE_NONE;
                            }
                        }
                        invalidate();
                        break;
                    }
                    case CAPTURE_RECORDING: {

                        float x = (System.currentTimeMillis() - pressDownTime) - 700;

                        float radius = circleRadius + radiusAdd;
                        sweepAngle = ((x / 1000) / maxRecordingTime) * 360;
                        canvas.drawCircle(widthDiv2, heightDiv2, circleRadius + radiusAdd, circlePaint);
                        canvas.drawArc(widthDiv2 - radius, heightDiv2 - radius, widthDiv2 + radius, heightDiv2 + radius, 270f, sweepAngle, false, redPaint);

                        if(x/1000 >= maxRecordingTime){
                            control.onVideoStop();
                            recording = false;
                            wasRecording = true; captureState = CAPTURE_RECEDING;
                            maxRadiusAddSize = radiusAdd;
                            startRecedingTime = System.currentTimeMillis();
                        }
                        invalidate();
                        return;
                    }
                    case CAPTURE_NONE:
                        radiusAdd = 0;

                }

                break;


        }
        canvas.drawCircle(widthDiv2, heightDiv2, circleRadius + radiusAdd, circlePaint);





    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getX();
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_BUTTON_RELEASE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_HOVER_EXIT:
                if(currentState != STATE_SHUTTER){
                    return true;
                }

            case MotionEvent.ACTION_UP:

                switch (currentState) {
                    case STATE_SHUTTER:


                        if (inCircle(x, y, widthDiv2, heightDiv2, circleRadius)) {

                            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {

                                Toast.makeText(getContext(), R.string.noCameraPermissions, Toast.LENGTH_SHORT).show();
                            } else {


                                if (System.currentTimeMillis() - pressDownTime < 700) {
                                    control.onPhoto();
                                    wasRecording = false;
                                } else {
                                    control.onVideoStop();
                                    recording = false;
                                    wasRecording = true;
                                }
                                captureState = CAPTURE_RECEDING;
                                maxRadiusAddSize = radiusAdd;
                                startRecedingTime = System.currentTimeMillis();

                            }
                            return true;
                        }

                        break;

                    case STATE_NORMAL:

                        if (inCircle(x, y, widthDiv2, heightDiv2, circleRadius)) {
                            currentState = STATE_SHUTTER;
                            control.goToCam();
                        }

                        break;
                }

                break;


            case MotionEvent.ACTION_DOWN:
                recording = false;
                switch (currentState) {
                    case STATE_SHUTTER:
                        if (inCircle(x, y, widthDiv2, heightDiv2, circleRadius)) {
                            pressDownTime = System.currentTimeMillis();
                            captureState = CAPTURE_HOLDING;
                            break;
                        }

                }
                break;


        }


        invalidate();
        return true;
    }

    private boolean inCircle(float x, float y, float circleCenterX, float circleCenterY, float circleRadius) {
        double dx = Math.pow(x - circleCenterX, 2);
        double dy = Math.pow(y - circleCenterY, 2);

        return ((dx + dy) < Math.pow(circleRadius, 2));
    }

    public void setControl(Control control) {
        this.control = control;
    }

    void setState(byte newState) {


        currentState = newState;
        invalidate();
    }

    void setVerticalOffset(float offset) {
        offset = 1 - offset;
        circleRadius = dp20 + (dp12 * offset);
        invalidate();
    }

    static boolean isRecording() {
        return recording;
    }

    interface Control {

        void onPhoto();

        void onVideoStart();

        void onVideoStop();

        void onVideoCanceled();


        void goToCam();

    }

    public byte getCurrentState() {
        return currentState;
    }





}
