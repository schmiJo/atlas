package com.atlas.atlas.general;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.atlas.atlas.R;

import java.util.ArrayList;


public class FloatingActionWheel extends View {

    Paint tempPaint = new Paint();
    Context context;
    Resources resources;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    private int fabFocusDistance;
    private float maxPCircleRadius;
    private float maxSCircleRadius;
    private float maxWobbleRadius;
    private float primaryCircleIncrement;
    private float secondaryCircleIncrement;
    private float primaryCircleWobbleIncrement;
    private float secondaryCircleRadius;
    private float circleRadius = 0;
    private float iconsMargin = 0;
    private int mBackgroundColor = Color.RED;
    private int secondaryCircleAlpha = 128;
    private int maxItemNumber = 0;
    private boolean extend = false;
    private boolean invalidateOnDraw = false;
    private boolean makeOnMoreExecution = true;
    private boolean primaryCircleIsReady = false;
    private boolean secondaryCircleIsReady = false;
    private boolean startWobbleAnimation = false;
    private boolean extendPrimaryCircle = true;
    private boolean primaryCircleIsCompleatelyReady = false;
    private Paint mCirclePaint;
    private Paint mSecondaryCirclePaint;
    private Paint mButtonPaint;
    private Paint textPaint;
    //Variables for Faw Items
    private int itemsPerSector = 0;
    private int itemsPerSectorXML = 0;
    private double scrollFactor = 0;
    private int xItem1 = 0;
    private int yItem1 = 0;
    private int xItem2 = 0;
    private int yItem2 = 0;
    private int xItem3 = 0;
    private int yItem3 = 0;
    private int xItem4 = 0;
    private int yItem4 = 0;
    private int xItem5 = 0;
    private int yItem5 = 0;
    private int xItem6 = 0;
    private int yItem6 = 0;
    private int xItem7 = 0;
    private int yItem7 = 0;
    private int xItem8 = 0;
    private int yItem8 = 0;
    private float xActionDown = 0;
    private float yActionDown = 0;
    private int bitmapDimensions = 0;
    private Bitmap item1 = null;
    private Bitmap item2 = null;
    private Bitmap item3 = null;
    private Bitmap item4 = null;
    private Bitmap item5 = null;
    private Bitmap item6 = null;
    private Bitmap item7 = null;
    private Bitmap item8 = null;
    private boolean availabilityItem1 = false;
    private boolean availabilityItem2 = false;
    private boolean availabilityItem3 = false;
    private boolean availabilityItem4 = false;
    private boolean availabilityItem5 = false;
    private boolean availabilityItem6 = false;
    private boolean availabilityItem7 = false;
    private boolean availabilityItem8 = false;
    private OnFAWItemClickListner onFAWItemClickListener;
    private float textLengthFactor = Utils.dpToPixel(7.2f,getContext());
    private String[] names;
    private float dp24 = Utils.dpToPixel(2,getContext());
    public FloatingActionWheel(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public FloatingActionWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public FloatingActionWheel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d("debug", "onAttachedToWindow");
        super.onAttachedToWindow();
        try {
            Activity activity = (Activity) context;
            onFAWItemClickListener = (OnFAWItemClickListner) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        Log.d("debug", "init");

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(Utils.dpToPixel(12,getContext()));
        textPaint.setElegantTextHeight(true);


        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.FloatingActionWheel, defStyle, 0);

        mBackgroundColor = a.getColor(
                R.styleable.FloatingActionWheel_backgroundColor,
                mBackgroundColor);

        extend = a.getBoolean(R.styleable.FloatingActionWheel_extentionState, false);


        itemsPerSectorXML = a.getInt(R.styleable.FloatingActionWheel_itemsPerSector, 4);


        a.recycle();


        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondaryCirclePaint = new Paint();
        mButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        Log.d("debug", "initiale");
        resources = getResources();
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(mBackgroundColor);


        mSecondaryCirclePaint.setStyle(Paint.Style.FILL);
        mSecondaryCirclePaint.setColor(mBackgroundColor);
        mSecondaryCirclePaint.setAlpha(secondaryCircleAlpha);


        mButtonPaint.setColor(Color.BLACK);
        mButtonPaint.setStyle(Paint.Style.FILL);

        tempPaint.setStyle(Paint.Style.FILL);


        fabFocusDistance = (int) Utils.dpToPixel(44, context);
        maxPCircleRadius = Utils.dpToPixel(130, context);
        maxWobbleRadius = Utils.dpToPixel(135, context);
        maxSCircleRadius = Utils.dpToPixel(140, context);
        iconsMargin = Utils.dpToPixel(18, context);
        primaryCircleIncrement = Utils.dpToPixel(10, context);
        secondaryCircleIncrement = Utils.dpToPixel(3, context);
        primaryCircleWobbleIncrement = Utils.dpToPixel(2 , context);


        secondaryCircleRadius = maxPCircleRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("debug", "onDraw");
        //Log.d("debug", "extend=  " + extend);

        if (extend) {

            if (!primaryCircleIsCompleatelyReady) {
                if (circleRadius <= maxPCircleRadius && extendPrimaryCircle) {
                    //As Long as the primary circle radius is less than 200dp increase its radius
                    circleRadius = circleRadius + primaryCircleIncrement;
                } else {
                    //If the primary circle radius is equal than 200dp make wobble effect
                    startWobbleAnimation = true;
                }


                if (startWobbleAnimation) {
                    //Make sure that the if statement, which expand the primary circle wont be called
                    extendPrimaryCircle = false;
                    //If the circle radius is less than the wobble effect radius increase its radius
                    if (circleRadius <= maxWobbleRadius && !primaryCircleIsReady) {
                        circleRadius = circleRadius + primaryCircleWobbleIncrement;
                    } else {
                        primaryCircleIsReady = true;
                        circleRadius = circleRadius - primaryCircleWobbleIncrement;
                    }
                    if (circleRadius < maxPCircleRadius) {
                        startWobbleAnimation = false;
                        primaryCircleIsCompleatelyReady = true;
                    }

                }
            }
            //draw Primary Circle
            canvas.drawCircle(canvas.getWidth() - fabFocusDistance, canvas.getHeight() - fabFocusDistance, circleRadius, mCirclePaint);

            if (primaryCircleIsReady) {

                //increase the radius of the secondary circle
                secondaryCircleRadius = secondaryCircleRadius + secondaryCircleIncrement;
                if (secondaryCircleAlpha - 16 >= 0) {

                    //If the alpha wont got negative reduce the Alpha Value
                    secondaryCircleAlpha = secondaryCircleAlpha - 16;
                }
                mSecondaryCirclePaint.setAlpha(secondaryCircleAlpha);

                //Log.d("debug", "secondaryCircleRadius:  " + secondaryCircleRadius + ", secondaryCirclemax: " + maxSCircleRadius + "alpha: " + secondaryCircleAlpha);
                if (!secondaryCircleIsReady) {
                    //if it is not the last execution of onDraw draw the secondary circle
                    canvas.drawCircle(canvas.getWidth() - fabFocusDistance, canvas.getHeight() - fabFocusDistance, secondaryCircleRadius, mSecondaryCirclePaint);
                }
            }


            if (secondaryCircleRadius > maxSCircleRadius && primaryCircleIsCompleatelyReady) {

                //If the secondary circle is as big as wished, invalidate onDraw one more Time to remove the secondary circle
                if (makeOnMoreExecution) {
                    makeOnMoreExecution = false;
                    secondaryCircleIsReady = true;
                    invalidate();
                }
                //If the secondary circle is removed stop the invalidation
                invalidateOnDraw = false;
            }

        } else {

            //If the faw should retracts
            circleRadius = circleRadius - primaryCircleIncrement;
            canvas.drawCircle(canvas.getWidth() - fabFocusDistance, canvas.getHeight() - fabFocusDistance, circleRadius, mCirclePaint);

            if (circleRadius <= 0) {
                //If the primary circle is retracted completely reset variables
                invalidateOnDraw = false;
                secondaryCircleRadius = maxPCircleRadius;
                secondaryCircleAlpha = 128;
                circleRadius = 0;

                makeOnMoreExecution = true;
                primaryCircleIsReady = false;
                secondaryCircleIsReady = false;
                extendPrimaryCircle = true;
                primaryCircleIsCompleatelyReady = false;

            }
        }

        if (circleRadius > fabFocusDistance) {

            boolean textIncluded = names != null;
            if (availabilityItem1) {
                xItem1 = canvas.getWidth() + calculateCoordinates(true, 1) - fabFocusDistance;
                yItem1 = canvas.getHeight() - calculateCoordinates(false, 1) - fabFocusDistance;
                canvas.drawBitmap(item1, xItem1, yItem1, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[0], xItem1+ (bitmapDimensions-(names[0].length()*textLengthFactor))/2 , yItem1 +bitmapDimensions+ dp24, textPaint);
                }
            }

            if (availabilityItem2) {
                xItem2 = canvas.getWidth() + calculateCoordinates(true, 2) - fabFocusDistance;
                yItem2 = canvas.getHeight() - calculateCoordinates(false, 2) - fabFocusDistance;
                canvas.drawBitmap(item2, xItem2, yItem2, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[1], xItem2+  (bitmapDimensions-(names[1].length()*textLengthFactor))/2, yItem2 +bitmapDimensions+ dp24, textPaint);
                }
            }

            if (availabilityItem3) {
                xItem3 = canvas.getWidth() + calculateCoordinates(true, 3) - fabFocusDistance;
                yItem3 = canvas.getHeight() - calculateCoordinates(false, 3) - fabFocusDistance;
                canvas.drawBitmap(item3, xItem3, yItem3, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[2], xItem3 +  (bitmapDimensions-(names[2].length()*textLengthFactor))/2 , yItem3 +bitmapDimensions+ dp24, textPaint);
                }
            }

            if (availabilityItem4) {
                xItem4 = canvas.getWidth() + calculateCoordinates(true, 4) - fabFocusDistance;
                yItem4 = canvas.getHeight() - calculateCoordinates(false, 4) - fabFocusDistance;
                canvas.drawBitmap(item4, xItem4, yItem4, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[3], xItem4 +  (bitmapDimensions-(names[3].length()*textLengthFactor))/2, yItem4 +bitmapDimensions+ dp24, textPaint);
                }
            }
            if (availabilityItem5) {
                xItem5 = canvas.getWidth() + calculateCoordinates(true, 5) - fabFocusDistance;
                yItem5 = canvas.getHeight() - calculateCoordinates(false, 5) - fabFocusDistance;
                canvas.drawBitmap(item5, xItem5, yItem5, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[4], xItem5 +  (bitmapDimensions-(names[4].length()*textLengthFactor))/2, yItem5 +bitmapDimensions+ dp24, textPaint);
                }
            }
            if (availabilityItem6) {
                xItem6 = canvas.getWidth() + calculateCoordinates(true, 6) - fabFocusDistance;
                yItem6 = canvas.getHeight() - calculateCoordinates(false, 6) - fabFocusDistance;
                canvas.drawBitmap(item6, xItem6, yItem6, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[5], xItem6 +  (bitmapDimensions-(names[5].length()*textLengthFactor))/2, yItem6 +bitmapDimensions+ dp24, textPaint);
                }
            }
            if (availabilityItem7) {
                xItem7 = canvas.getWidth() + calculateCoordinates(true, 7) - fabFocusDistance;
                yItem7 = canvas.getHeight() - calculateCoordinates(false, 7) - fabFocusDistance;
                canvas.drawBitmap(item7, xItem7, yItem7, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[6], xItem7 +  (bitmapDimensions-(names[6].length()*textLengthFactor))/2, yItem7 +bitmapDimensions+ dp24, textPaint);
                }
            }
            if (availabilityItem8) {
                xItem8 = canvas.getWidth() + calculateCoordinates(true, 8) - fabFocusDistance;
                yItem8 = canvas.getHeight() - calculateCoordinates(false, 8) - fabFocusDistance;
                canvas.drawBitmap(item8, xItem8, yItem8, mButtonPaint);
                if(textIncluded){
                    canvas.drawText(names[7], xItem8+  (bitmapDimensions-(names[7].length()*textLengthFactor))/2 , yItem8 +bitmapDimensions+ dp24, textPaint);
                }
            }
        }

        //Keep onDraw alive
        if (invalidateOnDraw) {
            invalidate();
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (extend) {
            int index = event.getActionIndex();
            int action = event.getActionMasked();
            int pointerId = event.getPointerId(index);


            switch (action) {

                case MotionEvent.ACTION_DOWN:
                    xActionDown = event.getX();
                    yActionDown = event.getY();
                    mVelocityTracker.clear();
                    // Add a user's movement to the tracker.
                    mVelocityTracker.addMovement(event);
                    break;

                case MotionEvent.ACTION_UP:
                    //Log.d("debug", "xActionDown: " + xActionDown);
                    //Log.d("debug", "yActionDown: " + yActionDown);
                    //Log.d("debug", "xActionUp:   " + event.getX());
                    //Log.d("debug", "yActionUp:   " + event.getY());
                    //Log.d("debug", "deltaX:      " + (xActionDown - event.getX()));
                    //Log.d("debug", "deltaY:      " + (yActionDown - event.getY()));

                    if ((Math.abs(xActionDown - event.getX()) < 6 && Math.abs(yActionDown - event.getY()) < 6)) {

                        if (availabilityItem1) {
                            if (xActionDown > ((xItem1 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem1 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem1 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem1 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(0);
                            }
                        }
                        if (availabilityItem2) {
                            if (xActionDown > ((xItem2 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem2 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem2 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem2 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(1);
                            }
                        }
                        if (availabilityItem3) {
                            if (xActionDown > ((xItem3 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem3 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem3 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem3 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(2);
                            }
                        }
                        if (availabilityItem4) {
                            if (xActionDown > ((xItem4 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem4 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem4 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem4 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(3);
                            }
                        }
                        if (availabilityItem5) {
                            if (xActionDown > ((xItem5 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem5 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem5 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem5 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(4);
                            }
                        }
                        if (availabilityItem6) {
                            if (xActionDown > ((xItem6 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem6 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem6 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem6 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(5);
                            }
                        }
                        if (availabilityItem7) {
                            if (xActionDown > ((xItem7 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem7 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem7 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem7 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(6);
                            }
                        }
                        if (availabilityItem8) {
                            if (xActionDown > ((xItem8 + bitmapDimensions / 2) - 30) && yActionDown > ((yItem8 + bitmapDimensions / 2) - 30) && xActionDown < ((xItem8 + bitmapDimensions / 2) + 30) && yActionDown < ((yItem8 + bitmapDimensions / 2) + 30)) {
                                onFAWItemClickListener.onFAWItemClick(7);
                            }
                        }


                    }

                    //Reset variables
                    xActionDown = 0;
                    yActionDown = 0;
                    break;

                case MotionEvent.ACTION_MOVE:
                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(1000);

                    // velocity of pixels per second
                    float xVelocity = VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId);
                    float yVelocity = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId);


                    if (Math.abs(xVelocity) > Math.abs(yVelocity)) {

                        setScrollFactor(-xVelocity);

                    } else {

                        setScrollFactor(yVelocity);
                    }
                    break;

            }
            return true;
        } else {
            return false;
        }
    }

    @NonNull
    private Boolean setRessources(ArrayList<Integer> resouceIds) {
        int ressourcesCounter = resouceIds.size();
        Log.d("debug", "ressources: " + ressourcesCounter);
        if (ressourcesCounter > 8) {
            return false;
        }
        if (ressourcesCounter > 0) {
            item1 = Utils.drawableToBitmap(context, resouceIds.get(0));
            Log.d("debug", "Sets Item1 : " + resouceIds.get(0));
            ressourcesCounter--;
            availabilityItem1 = true;
            maxItemNumber++;
            bitmapDimensions = item1.getWidth();

        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            item2 = Utils.drawableToBitmap(context, resouceIds.get(1));
            Log.d("debug", "Sets Item2");
            ressourcesCounter--;
            availabilityItem2 = true;
            maxItemNumber++;
        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            item3 = Utils.drawableToBitmap(context, resouceIds.get(2));
            Log.d("debug", "Sets Item3");
            ressourcesCounter--;
            availabilityItem3 = true;
            maxItemNumber++;
        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            item4 = Utils.drawableToBitmap(context, resouceIds.get(3));
            Log.d("debug", "Sets Item4");
            ressourcesCounter--;
            availabilityItem4 = true;
            maxItemNumber++;
        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            item5 = Utils.drawableToBitmap(context, resouceIds.get(4));
            ressourcesCounter--;
            availabilityItem5 = true;
            maxItemNumber++;
        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            item6 = Utils.drawableToBitmap(context, resouceIds.get(5));
            ressourcesCounter--;
            availabilityItem6 = true;
            maxItemNumber++;
        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            item7 = Utils.drawableToBitmap(context, resouceIds.get(6));
            ressourcesCounter--;
            availabilityItem7 = true;
            maxItemNumber++;
        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            item8 = Utils.drawableToBitmap(context, resouceIds.get(7));
            ressourcesCounter--;
            availabilityItem8 = true;
            maxItemNumber++;
        } else {
            return true;
        }
        if (ressourcesCounter > 0) {
            return false;
        }


        return false;
    }



    @NonNull
    private Boolean setRessources(int[] resouceIds) {
        maxItemNumber = resouceIds.length;

        if (resouceIds.length > 0) {
            if (resouceIds[0] != 0) {
                item1 = Utils.drawableToBitmap(context, resouceIds[0]);
                availabilityItem1 = true;
                bitmapDimensions = item1.getWidth()+(int) Utils.dpToPixel(8,getContext());
            }
        }else{
            setItemsPerSelector();
            return false ;
        }
        if (resouceIds.length > 1) {
            if (resouceIds[1] != 0) {
                item2 = Utils.drawableToBitmap(context, resouceIds[1]);
                availabilityItem2 = true;
            }
        }else{
            setItemsPerSelector();
            return true ;
        }
        if (resouceIds.length > 2) {
            if (resouceIds[2] != 0) {
                item3 = Utils.drawableToBitmap(context, resouceIds[2]);
                availabilityItem3 = true;
            }
        }else{
            setItemsPerSelector();
            return true ;
        }
        if (resouceIds.length > 3) {
            if (resouceIds[3] != 0) {
                item4 = Utils.drawableToBitmap(context, resouceIds[3]);
                availabilityItem4 = true;
            }
        }else{
            setItemsPerSelector();
            return true ;
        }
        if (resouceIds.length > 4) {
            if (resouceIds[4] != 0) {
                item5 = Utils.drawableToBitmap(context, resouceIds[0]);
                availabilityItem5 = true;
            }
        }else{
            setItemsPerSelector();
            return true ;
        }
        if (resouceIds.length > 5) {
            if (resouceIds[5] != 0) {
                item6 = Utils.drawableToBitmap(context, resouceIds[1]);
                availabilityItem6 = true;
            }
        }else{
            setItemsPerSelector();
            return true ;
        }
        if (resouceIds.length > 6) {
            if (resouceIds[6] != 0) {
                item7 = Utils.drawableToBitmap(context, resouceIds[2]);
                availabilityItem7 = true;
            }
        }else{
            setItemsPerSelector();
            return true ;
        }
        if (resouceIds.length > 7) {
            if (resouceIds[7] != 0) {
                item8 = Utils.drawableToBitmap(context, resouceIds[3]);
                availabilityItem8 = true;
                maxItemNumber++;
            }
        }else{
            setItemsPerSelector();
            return true ;
        }

        setItemsPerSelector();
        return true;
    }

    private void setScrollFactor(float scrollVelocity) {

        //reduce the scrollVelocity relatively to admit the Values
        this.scrollFactor = this.scrollFactor + scrollVelocity / 15000;
        if (this.scrollFactor < 0) {
            //Handle the Scrolling Stop clockwise
            this.scrollFactor = 0;
        }

        if (this.scrollFactor > (Math.PI * (itemsPerSector + maxItemNumber) / (2 * itemsPerSector)) - Math.PI) {
            //Handle the Scrolling Stop counterclockwise
            this.scrollFactor = (Math.PI * (itemsPerSector + maxItemNumber) / (2 * itemsPerSector)) - Math.PI;
        }

        //Draw the canvas again to submit changes
        invalidateOnDraw = true;
        invalidate();
    }

    private void setItemsPerSelector() {

        if (maxItemNumber <= itemsPerSectorXML) {
            /*If there are less icons available, than were defining over the array,
            set the ItemPerSelector to the number of items in the Array,
            so they are placed fine
            */
            itemsPerSector = maxItemNumber;
            scrollFactor = 0;
        } else {
            //If there are enough items take the default value, defined over XML
            itemsPerSector = itemsPerSectorXML;
        }
    }

    private int calculateCoordinates(Boolean xValue, int index) {
        if (xValue) {
            //Formula for calculating the x Value of the icons, depending on the radius, the scrollFactor and the items that should be in one Sector
            Double x = (circleRadius - iconsMargin) * Math.cos(scrollFactor + Math.PI - Math.PI * (index - 0.5) / (2 * itemsPerSector));
            return x.intValue();
        } else {
            //Formula for calculating the y Value of the icons, depending on the radius, the scrollFactor and the items that should be in one Sector
            Double y = (circleRadius - iconsMargin) * Math.sin(scrollFactor + Math.PI - Math.PI * (index - 0.5) / (2 * itemsPerSector));
            return y.intValue();
        }


    }

    //**************************Methods for Overriding Behaviour and Layout of the FAW in RUNTIME****************************************//
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidateTextPaintAndMeasurements();
    }

    public boolean isExpanded() {
        return extend;
    }

    public void setExpantionState(Boolean expantionState) {
        //if (!invalidateOnDraw) {
        extend = expantionState;
        invalidateOnDraw = true;
        invalidate();
        //}

    }

    public void changeExpantionState() {
        if (!invalidateOnDraw) {
            extend = !extend;
            invalidateOnDraw = true;
            invalidate();
        }
    }

    //static Array
    public void setItems(int[] resourceIds,String[] names) {
        setRessources(resourceIds);
        this.names = names;
    }
    public void setItems(int[] resourceIds) {
        setRessources(resourceIds);

    }

    //Dynamic Array
    public void setItems(ArrayList<Integer> resourceIds) {
        maxItemNumber = 0;

        item1 = null;
        item2 = null;
        item3 = null;
        item4 = null;
        item5 = null;
        item6 = null;
        item7 = null;
        item8 = null;

        availabilityItem1 = false;
        availabilityItem2 = false;
        availabilityItem3 = false;
        availabilityItem4 = false;
        availabilityItem5 = false;
        availabilityItem6 = false;
        availabilityItem7 = false;
        availabilityItem8 = false;


        setRessources(resourceIds);

        setItemsPerSelector();
        invalidate();
    }

    public interface OnFAWItemClickListner {
        void onFAWItemClick(int itemId);

    }


}


