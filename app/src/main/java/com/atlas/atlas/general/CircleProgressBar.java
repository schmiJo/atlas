package com.atlas.atlas.general;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.atlas.atlas.R;


public class CircleProgressBar extends View {

    private int circleColor = Color.WHITE;

    private Paint circlePaint;
    private RectF oval;
    private Path path;


    private int xCircle1 = 0;
    private int xCircle2 = 0;
    private int xCircle3 = 0;
    private int xCircle4 = 0;

    private int yShiftFactor1 = 0;
    private int yShiftFactor2 = 0;
    private int yShiftFactor3 = 0;
    private int yShiftFactor4 = 0;
    private boolean run = true;

    private boolean contract1 = false;
    private boolean contract2 = false;
    private boolean contract3 = false;
    private boolean contract4 = false;


    private int whaitincrement = 0;

    private byte expandObject = 0;

    public CircleProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleProgressBar, defStyle, 0);
        circleColor = a.getColor(
                R.styleable.CircleProgressBar_circleColor,
                circleColor);
        a.recycle();

        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(circleColor);


        path = new Path();
        oval = new RectF();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (run) {
            int width = canvas.getWidth();
            int radius = width / 11;
            int height = canvas.getHeight();

            xCircle1 = radius;
            xCircle2 = radius * 4;
            xCircle3 = radius * 7;
            xCircle4 = radius * 10;


            switch (expandObject) {
                case 0:
                    if (yShiftFactor1 <= radius * 2) {
                        yShiftFactor1 += radius / 5;
                    } else {
                        expandObject = 1;
                        contract1 = true;
                    }
                    break;
                case 1:
                    if (yShiftFactor2 <= radius * 2) {
                        yShiftFactor2 += radius / 5;
                    } else {
                        expandObject = 2;
                        contract2 = true;
                    }
                    break;
                case 2:
                    if (yShiftFactor3 <= radius * 2) {
                        yShiftFactor3 += radius / 5;
                    } else {
                        expandObject = 3;
                        contract3 = true;
                    }
                    break;
                case 3:
                    if (yShiftFactor4 <= radius * 2) {
                        yShiftFactor4 += radius / 5;
                    } else {
                        expandObject = 4;
                        contract4 = true;
                    }
                    break;
                case 4:
                    break;
            }

            if (contract1) {
                if (yShiftFactor1 > 0) {
                    yShiftFactor1 -= radius / 6;
                } else {
                    yShiftFactor1 = 0;
                    contract1 = false;
                }

            }
            if (contract2) {
                if (yShiftFactor2 > 0) {
                    yShiftFactor2 -= radius / 6;
                } else {
                    yShiftFactor2 = 0;
                    contract2 = false;
                }
            }
            if (contract3) {
                if (yShiftFactor3 > 0) {
                    yShiftFactor3 -= radius / 6;
                } else {
                    yShiftFactor3 = 0;
                    contract3 = false;
                }
            }
            if (contract4) {
                if (yShiftFactor4 > 0) {
                    yShiftFactor4 -= radius / 6;
                } else {
                    yShiftFactor4 = 0;

                    if (whaitincrement <= 35) {
                        whaitincrement++;
                    } else {
                        expandObject = 0;
                        whaitincrement = 0;
                        contract4 = false;
                    }
                }

            }


            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle1 - radius,
                    height / 2 - radius + yShiftFactor1,
                    xCircle1 + radius,
                    height / 2 + radius + yShiftFactor1);
            canvas.drawArc(oval, 0, 180, false, circlePaint);

            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle2 - radius,
                    height / 2 - radius + yShiftFactor2,
                    xCircle2 + radius,
                    height / 2 + radius + yShiftFactor2);
            canvas.drawArc(oval, 0, 180, false, circlePaint);

            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle3 - radius,
                    height / 2 - radius + yShiftFactor3,
                    xCircle3 + radius,
                    height / 2 + radius + yShiftFactor3);
            canvas.drawArc(oval, 0, 180, false, circlePaint);

            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle4 - radius,
                    height / 2 - radius + yShiftFactor4,
                    xCircle4 + radius,
                    height / 2 + radius + yShiftFactor4);
            canvas.drawArc(oval, 0, 180, false, circlePaint);

            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle1 - radius,
                    height / 2 - radius - yShiftFactor1,
                    xCircle1 + radius,
                    height / 2 + radius - yShiftFactor1);
            canvas.drawArc(oval, 180, 180, false, circlePaint);

            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle2 - radius,
                    height / 2 - radius - yShiftFactor2,
                    xCircle2 + radius,
                    height / 2 + radius - yShiftFactor2);
            canvas.drawArc(oval, 180, 180, false, circlePaint);

            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle3 - radius,
                    height / 2 - radius - yShiftFactor3,
                    xCircle3 + radius,
                    height / 2 + radius - yShiftFactor3);
            canvas.drawArc(oval, 180, 180, false, circlePaint);

            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);


            oval.set(xCircle4 - radius,
                    height / 2 - radius - yShiftFactor4,
                    xCircle4 + radius,
                    height / 2 + radius - yShiftFactor4);
            canvas.drawArc(oval, 180, 180, false, circlePaint);


            canvas.drawRect(xCircle1 - radius, height / 2 - yShiftFactor1, xCircle1 + radius, height / 2 + yShiftFactor1, circlePaint);
            canvas.drawRect(xCircle2 - radius, height / 2 - yShiftFactor2, xCircle2 + radius, height / 2 + yShiftFactor2, circlePaint);
            canvas.drawRect(xCircle3 - radius, height / 2 - yShiftFactor3, xCircle3 + radius, height / 2 + yShiftFactor3, circlePaint);
            canvas.drawRect(xCircle4 - radius, height / 2 - yShiftFactor4, xCircle4 + radius, height / 2 + yShiftFactor4, circlePaint);


            invalidate();
        }
    }

    public void startAnimation() {
        run = true;


        yShiftFactor1 = 0;
        yShiftFactor2 = 0;
        yShiftFactor3 = 0;
        yShiftFactor4 = 0;

        invalidate();

    }

    public void stopAnimation() {
        run = false;
        invalidate();

    }


}
