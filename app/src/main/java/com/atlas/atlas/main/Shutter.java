package com.atlas.atlas.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.atlas.atlas.R;


public class Shutter extends View {


    private Paint paint = new Paint();

    private int width = 0, heigth = 0;

    public Shutter(Context context) {
        super(context);
        init();
    }

    public Shutter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Shutter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        paint.setAlpha(255);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (heigth == 0 || width == 0) {
            heigth = canvas.getHeight();
            width = canvas.getWidth();
        }
        canvas.drawRect(0, 0, width, heigth, paint);


    }

    public void setShutter(float visibility) {
        if (visibility < 0) {

            visibility = 0;
        }
        invalidate();

        paint.setAlpha(Math.round(visibility * 255));
    }

}
