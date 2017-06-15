package com.atlas.atlas.general;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TimeDecline extends View {

    private boolean time = false;
    private int millSeconds;
    private int startMillSeconds;
    private int width = 0;
    private int height = 0;
    private Paint paint = new Paint();
    private TimerFinishListener timerFinishListener;

    public TimeDecline(Context context) {
        super(context);
        init();
    }

    public TimeDecline(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeDecline(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private void init() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
    }

    public void setTime(int millSeconds, TimerFinishListener timerFinishListener ) {
        time = true;
        startMillSeconds = millSeconds;
        this.millSeconds = millSeconds;
        startTicker();
        this.timerFinishListener = timerFinishListener;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (height == 0) {
            height = canvas.getHeight();
        }
        if (width == 0) {
            width = canvas.getWidth();
        }
        if (time) {
            float st =((float) millSeconds)  / (float) startMillSeconds;
            float widths = width * st;
            canvas.drawRect(0, 0,widths , height, paint);
            invalidate();
        }
    }


    private void startTicker() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (time) {
                    if (millSeconds > 16) {

                        try {
                            Thread.sleep(16);
                            millSeconds = millSeconds - 16;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        time = false;
                        if(timerFinishListener != null) {
                            timerFinishListener.onTimerFinished();
                        }
                    }
                }
            }
        }).start();

    }

    public interface TimerFinishListener {
        void onTimerFinished();
    }

}
