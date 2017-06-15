package com.atlas.atlas.main;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.atlas.atlas.general.Utils;


public class MainLayout extends CoordinatorLayout {
    private Action actionListener;
    private float pressDownY = 0, pressDownX = 0;
    private boolean handeled;


    public MainLayout(@NonNull Context context) {
        super(context);
    }

    public MainLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void setOnActionListener(Action actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                handeled = false;

                pressDownY = event.getRawY();
                pressDownX = event.getRawX();

                return  true;

            case MotionEvent.ACTION_UP:

                if ((MainSheetBehavior.mState == MainSheetBehavior.STATE_COLLAPSED || MainSheetBehavior.mState == MainSheetBehavior.STATE_HIDDEN)) {
                    float releaseY = event.getY();
                    float releaseX = event.getX();
                    if (!handeled && !CaptureButton.isRecording()) {
                        if ((pressDownY - releaseY > Utils.dpToPixel(90, getContext())) ) {
                            actionListener.onExpand();

                        }
                    }


                    break;
                }
        }
        return super.onTouchEvent(event);
    }


    interface Action {
        void onExpand();


    }


}
