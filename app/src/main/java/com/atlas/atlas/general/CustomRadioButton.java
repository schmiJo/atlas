package com.atlas.atlas.general;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.atlas.atlas.R;


public class CustomRadioButton extends View {


    private static final byte STATE_SELECTED = 1;
    private static final byte STATE_UNSELECTED = 2;
    private static final byte STATE_SELECT = 3;
    private static final byte STATE_UNSELECT = 4;
    @ColorInt
    private int color = Color.parseColor("#b8bee4");
    private byte state = STATE_UNSELECTED;
    private boolean selected = false;
    private boolean clickable = true;
    private int height = 0, width = 0, radius, dp2, timeDelta, radiusFilled;
    private long timeStart;
    private OnSelectListener onSelectListener;
    private float factor;
    private Paint buttonStroke = new Paint(Paint.ANTI_ALIAS_FLAG), buttonFilled = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomRadioButton(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomRadioButton, defStyle, 0);

        color = a.getColor(
                R.styleable.CustomRadioButton_radioColor,
                color);

        clickable = a.getBoolean(R.styleable.CustomRadioButton_clickable, true);
        a.recycle();
        buttonStroke.setStyle(Paint.Style.STROKE);
        dp2 = (int) Utils.dpToPixel(2, getContext());
        buttonStroke.setStrokeWidth(dp2);
        buttonStroke.setColor(color);

        timeDelta = 150;
        buttonFilled.setStyle(Paint.Style.FILL);
        buttonFilled.setColor(color);
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (height == 0 || width == 0) {
            height = (canvas.getHeight() / 2);
            width = canvas.getWidth() / 2;
            if (width < height) {
                radius = width - dp2 * 2;
                radiusFilled = radius - dp2 * 3;
            } else {
                radius = height - dp2 * 2;
                radiusFilled = radius - dp2 * 3;
            }

            factor = ((float) radiusFilled / (float) timeDelta) * -1;
        }

        switch (state) {

            case STATE_UNSELECTED:
                canvas.drawCircle(width, height, radius - dp2, buttonStroke);
                break;
            case STATE_SELECTED:
                canvas.drawCircle(width, height, radiusFilled, buttonFilled);
                canvas.drawCircle(width, height, radius - dp2, buttonStroke);
                break;

            case STATE_UNSELECT: {

                int animIndex = (int) (System.currentTimeMillis() - timeStart);
                //at animIndex 0 -> anim Radius == radius
                //at animIndex == timeDelta -> anim Radius == 0

                int animRadius = (int) (((factor) * (float) animIndex) + radiusFilled);

                canvas.drawCircle(width, height, animRadius, buttonFilled);
                if (animRadius <= 0) {
                    state = STATE_UNSELECTED;
                }
                //the circle that always remains
                canvas.drawCircle(width, height, radius - dp2, buttonStroke);
                invalidate();
                break;
            }
            case STATE_SELECT: {

                int animIndex = (int) (System.currentTimeMillis() - timeStart);
                //at animIndex 0 -> anim Radius == 0
                //at animIndex == timeDelta -> anim Radius == radius

                int animRadius = (int) ((-1 * factor) * animIndex);

                canvas.drawCircle(width, height, animRadius, buttonFilled);
                if (animRadius >= radiusFilled) {
                    state = STATE_SELECTED;
                }
                //the circle that always remains
                canvas.drawCircle(width, height, radius - dp2, buttonStroke);
                invalidate();
                break;
            }


        }

        super.onDraw(canvas);
    }

    public void forceClick(){
        switch (state) {
            case STATE_SELECTED:
                state = STATE_UNSELECT;
                selected = false;
                timeStart = System.currentTimeMillis();

                if (onSelectListener != null) {
                    onSelectListener.onUnselect();
                }
                break;
            case STATE_UNSELECTED:
                state = STATE_SELECT;
                timeStart = System.currentTimeMillis();
                selected = true;

                if (onSelectListener != null) {
                    onSelectListener.onSelect();
                }
                break;

        }
        invalidate();
    }
    public void click() {
        if(!clickable){
            return;
        }
        forceClick();

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            state = STATE_SELECTED;
        } else {
            state = STATE_UNSELECTED;
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    interface OnSelectListener {
        void onSelect();

        void onUnselect();
    }
}
