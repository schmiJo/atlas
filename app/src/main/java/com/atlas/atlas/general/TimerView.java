package com.atlas.atlas.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class TimerView extends View {

    private int percentageLeft = 100;
    private int tempPercentageLeft;
    private Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean animation;
    private boolean increment;


    public TimerView(Context context) {
        super(context);
        init();
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPercentageLeft(int percentageLeft) {
        if (this.percentageLeft != percentageLeft) {
            tempPercentageLeft = this.percentageLeft;
            this.percentageLeft = percentageLeft;
            animation = true;
            increment = (tempPercentageLeft < this.percentageLeft);


        }


        invalidate();
    }

    private void init() {

        stroke.setStyle(Paint.Style.FILL);
        stroke.setStrokeWidth(Utils.dpToPixel(2, getContext()));
        stroke.setColor(Color.WHITE);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float dp2 = Utils.dpToPixel(1, getContext());

        if (animation) {
            if (increment) {
                tempPercentageLeft = tempPercentageLeft + 2;
                if (tempPercentageLeft >= percentageLeft) {
                    animation = false;
                }
            } else {
                tempPercentageLeft = tempPercentageLeft -2;
                if (tempPercentageLeft <= percentageLeft) {
                    animation = false;

                }
            }
            canvas.drawArc(0 + dp2, 0 + dp2, canvas.getWidth() - dp2, canvas.getHeight() - dp2, -90, -(((float) tempPercentageLeft / (float) 100) * 360), true, stroke);
            invalidate();
        } else {
            canvas.drawArc(0 + dp2, 0 + dp2, canvas.getWidth() - dp2, canvas.getHeight() - dp2, -90, -(((float) percentageLeft / (float) 100) * 360), true, stroke);
        }

    }
}
