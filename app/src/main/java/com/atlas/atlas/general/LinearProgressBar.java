package com.atlas.atlas.general;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.atlas.atlas.R;


public class LinearProgressBar extends View {

    private int currentColor = 0, width, height, heightDiv2;
    private Paint background = new Paint(Paint.ANTI_ALIAS_FLAG), foreGround = new Paint(Paint.ANTI_ALIAS_FLAG);
    private long currentStart;
    private double factor = 0;
    private int r = 255;
    private int g = 0;
    private int b = 0;
    private int animSpeed;
    private int colorSpeed;

    private float progress = 0.0f;

    private boolean anim = true;

    public LinearProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public LinearProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LinearProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();
        if (heightDiv2 == 0) {
            heightDiv2 = height / 2;
        }
        canvas.drawRect(heightDiv2, 0, width - heightDiv2, height, background);
        canvas.drawCircle(heightDiv2, heightDiv2, heightDiv2, background);
        canvas.drawCircle(width - heightDiv2, heightDiv2, heightDiv2, background);

        if (anim) {
            parabolaAnim(canvas);
        } else {
            float currentXRight = heightDiv2 + ((width - height) * (progress / 100));
            if (currentXRight >= (width - heightDiv2)) {
                currentXRight = (width - heightDiv2);
            }

            canvas.drawRect((float) heightDiv2, 0, currentXRight, height, foreGround);
            canvas.drawCircle((float) heightDiv2, heightDiv2, heightDiv2, foreGround);
            canvas.drawCircle(currentXRight, heightDiv2, heightDiv2, foreGround);
        }

        changePaint();
        invalidate();


    }

    private void parabolaAnim(Canvas canvas) {
        if (factor == 0) {
            factor = ((width - height) / 100);
        }
        //background

        //foregroundAnimation

        //index 1 is an integer from 1 -100 that represents where the animation is
        float indexRight = (float) ((System.currentTimeMillis() - currentStart) / animSpeed) + 15;
        float indexLeft = indexRight - 30;

        if (indexLeft < 0) {
            indexLeft = 0;
        }

        //index Result
        double indexResultRight = Math.pow(indexRight - 50, 3) * 0.0004 + 50;
        double indexResultLeft = Math.pow(indexLeft - 50, 3) * 0.0004 + 50;

        double currentXLeft = heightDiv2 + (indexResultLeft * factor);
        double currentXRight = heightDiv2 + (indexResultRight * factor);

        if (indexResultRight > 100) {
            currentXRight = width - heightDiv2;
        }


        canvas.drawRect((float) currentXLeft, 0, (float) currentXRight, height, foreGround);
        canvas.drawCircle((float) currentXLeft, heightDiv2, heightDiv2, foreGround);
        canvas.drawCircle((float) currentXRight, heightDiv2, heightDiv2, foreGround);

        if (indexResultLeft > 100) {
            currentStart = System.currentTimeMillis();

        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LinearProgressBar2, defStyle, 0);

        background.setStyle(Paint.Style.FILL);
        animSpeed = a.getInteger(R.styleable.LinearProgressBar2_animSpeed, 10);
        colorSpeed = a.getInteger(R.styleable.LinearProgressBar2_colorSpeed, 8);
        background.setColor(getResources().getColor(a.getResourceId(R.styleable.LinearProgressBar2_backgroundColor2, R.color.colorPrimaryDark1)));
        foreGround.setStyle(Paint.Style.FILL);
        currentStart = System.currentTimeMillis();
        a.recycle();

    }

    private void changePaint() {

        switch (currentColor) {
            case 0:
                g = g + colorSpeed;
                if (g >= 255) {
                    g = 255;
                    currentColor++;
                }

                break;
            case 1:
                r = r - colorSpeed;
                if (r <= 0) {
                    r = 0;
                    currentColor++;
                }

                break;
            case 2:
                b = b + colorSpeed;
                if (b >= 100) {
                    b = 100;
                    currentColor++;
                }

                break;
            case 3:
                g = g - colorSpeed;
                if (g <= 0) {
                    g = 0;
                    currentColor++;
                }

                break;
            case 4:
                r = r + colorSpeed;
                if (r >= 255) {
                    r = 255;
                    currentColor++;
                }
                break;
            case 5:
                b = b - colorSpeed;
                if (b <= 0) {
                    b = 0;
                    currentColor = 0;
                }
                break;

        }
        foreGround.setColor(Color.argb(255, r, g, b));
    }


    public void setProgress(float progress) {
        anim = false;
        this.progress = progress;
    }

    public void startAnim() {
        anim = true;
    }
}
