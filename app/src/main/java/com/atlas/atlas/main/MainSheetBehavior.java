/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.atlas.atlas.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;


public class MainSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

   public static final int STATE_DRAGGING = 1;
   public static final int STATE_SETTLING = 2;
   public static final int STATE_EXPANDED = 3;
   public static final int STATE_COLLAPSED = 4;
   public static final int STATE_HIDDEN = 5;
    private static final int PEEK_HEIGHT_AUTO = -1;
    private static final float HIDE_THRESHOLD = 0.5f;
    private static final float HIDE_FRICTION = 0.1f;
    @State
    static int mState = STATE_COLLAPSED;
    ViewDragHelper mViewDragHelper;
    int mParentHeight;
    WeakReference<V> mViewRef;
    WeakReference<View> mNestedScrollingChildRef;
    int mActivePointerId;
    boolean mTouchingScrollingChild;
    private int mMinOffset;
    private int mMaxOffset;
    private boolean mHideable;
    private float mMaximumVelocity;
    private int mPeekHeight;
    private boolean mPeekHeightAuto;
    private int mPeekHeightMin;
    private boolean mSkipCollapsed;
    private BottomSheetCallback mCallback;
    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (mState == STATE_DRAGGING) {
                return false;
            }
            if (mTouchingScrollingChild) {
                return false;
            }
            if (mState == STATE_EXPANDED && mActivePointerId == pointerId) {
                View scroll = mNestedScrollingChildRef.get();
                if (scroll != null && ViewCompat.canScrollVertically(scroll, -1)) {
                    // Let the content scroll up
                    return false;
                }
            }
            return mViewRef != null && mViewRef.get() == child;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            dispatchOnSlide(top);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_DRAGGING) {
                setStateInternal(STATE_DRAGGING);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top;
            @State int targetState;
            if (yvel < 0) { // Moving up
                top = mMinOffset;
                targetState = STATE_EXPANDED;
            } else if (mHideable && shouldHide(releasedChild, yvel)) {
                top = mParentHeight;
                targetState = STATE_HIDDEN;
            } else if (yvel == 0.f) {
                int currentTop = releasedChild.getTop();
                if (Math.abs(currentTop - mMinOffset) < Math.abs(currentTop - mMaxOffset)) {
                    top = mMinOffset;
                    targetState = STATE_EXPANDED;
                } else {
                    top = mMaxOffset;
                    targetState = STATE_COLLAPSED;
                }
            } else {
                top = mMaxOffset;
                targetState = STATE_COLLAPSED;
            }
            if (mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                setStateInternal(STATE_SETTLING);
                ViewCompat.postOnAnimation(releasedChild,
                        new SettleRunnable(releasedChild, targetState));
            } else {
                setStateInternal(targetState);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return constrain(top, mMinOffset, mHideable ? mParentHeight : mMaxOffset);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return child.getLeft();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            if (mHideable) {
                return mParentHeight - mMinOffset;
            } else {
                return mMaxOffset - mMinOffset;
            }
        }
    };
    private VelocityTracker mVelocityTracker;
    private int mInitialY;
    private float dp52 = 0;

    public MainSheetBehavior() {
    }

    public MainSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                android.support.design.R.styleable.BottomSheetBehavior_Layout);
        TypedValue value = a.peekValue(android.support.design.R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (value != null && value.data == PEEK_HEIGHT_AUTO) {
            setPeekHeight(value.data);
        } else {
            setPeekHeight(a.getDimensionPixelSize(
                    android.support.design.R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, PEEK_HEIGHT_AUTO));
        }
        setHideable(a.getBoolean(android.support.design.R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setSkipCollapsed(a.getBoolean(android.support.design.R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed,
                false));
        a.recycle();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    @SuppressWarnings("unchecked")
    public static <V extends View> MainSheetBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof MainSheetBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with BottomSheetBehavior");
        }
        return (MainSheetBehavior<V>) behavior;
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), mState);
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        // Intermediate states are restored as collapsed state
        if (ss.state == STATE_DRAGGING || ss.state == STATE_SETTLING) {
            mState = STATE_COLLAPSED;
        } else {
            mState = ss.state;
        }
    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            ViewCompat.setFitsSystemWindows(child, true);
        }
        int savedTop = child.getTop();
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);
        // Offset the bottom sheet
        mParentHeight = parent.getHeight();
        int peekHeight;
        if (mPeekHeightAuto) {
            if (mPeekHeightMin == 0) {
                mPeekHeightMin = parent.getResources().getDimensionPixelSize(
                        android.support.design.R.dimen.design_bottom_sheet_peek_height_min);
            }
            peekHeight = Math.max(mPeekHeightMin, mParentHeight - parent.getWidth() * 9 / 16);
        } else {
            peekHeight = mPeekHeight;
        }
        mMinOffset = Math.max(0, mParentHeight - child.getHeight());
        mMaxOffset = Math.max(mParentHeight - peekHeight, mMinOffset);
        if (mState == STATE_EXPANDED) {
            ViewCompat.offsetTopAndBottom(child, mMinOffset);
        } else if (mHideable && mState == STATE_HIDDEN) {
            ViewCompat.offsetTopAndBottom(child, mParentHeight);
        } else if (mState == STATE_COLLAPSED) {
            ViewCompat.offsetTopAndBottom(child, mMaxOffset);
        } else if (mState == STATE_DRAGGING || mState == STATE_SETTLING) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        if (mViewDragHelper == null) {
            mViewDragHelper = ViewDragHelper.create(parent, mDragCallback);
        }
        mViewRef = new WeakReference<>(child);
        mNestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        return true;
    }


    /*

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {


        super.onInterceptTouchEvent(parent,child,event);

        // if (!child.isShown() || getState() == STATE_HIDDEN || getState() == STATE_COLLAPSED ) {
        //     return  false;
        // }
        // if (event.getY() < 200) {
        //     if (!child.isShown()) {
        //         mIgnoreEvents = true;
        //         return false;
        //     }
        //     int action = MotionEventCompat.getActionMasked(event);
        //     // Record the velocity
        //     if (action == MotionEvent.ACTION_DOWN) {
        //         reset();
        //     }
        //     if (mVelocityTracker == null) {
        //         mVelocityTracker = VelocityTracker.obtain();
        //     }
        //     mVelocityTracker.addMovement(event);
        //     switch (action) {
        //         case MotionEvent.ACTION_UP:
        //         case MotionEvent.ACTION_CANCEL:
        //             mTouchingScrollingChild = false;
        //             mActivePointerId = MotionEvent.INVALID_POINTER_ID;
        //             // Reset the ignore flag
        //             if (mIgnoreEvents) {
        //                 mIgnoreEvents = false;
        //                 return false;
        //             }
        //             break;
        //         case MotionEvent.ACTION_DOWN:
        //             int initialX = (int) event.getX();
        //             mInitialY = (int) event.getY();
        //             View scroll = mNestedScrollingChildRef.get();
        //             if (scroll != null && parent.isPointInChildBounds(scroll, initialX, mInitialY)) {
        //                 mActivePointerId = event.getPointerId(event.getActionIndex());
        //                 mTouchingScrollingChild = true;
        //             }
        //             mIgnoreEvents = mActivePointerId == MotionEvent.INVALID_POINTER_ID &&
        //                     !parent.isPointInChildBounds(child, initialX, mInitialY);
        //             break;
        //     }
        //     if (!mIgnoreEvents && mViewDragHelper.shouldInterceptTouchEvent(event)) {
        //         return true;
        //     }
        //     // We have to handle cases that the ViewDragHelper does not capture the bottom sheet because
        //     // it is not the top most view of its parent. This is not necessary when the touch event is
        //     // happening over the scrolling content as nested scrolling logic handles that case.
        //     View scroll = mNestedScrollingChildRef.get();
        //     return action == MotionEvent.ACTION_MOVE && scroll != null &&
        //             !mIgnoreEvents && mState != STATE_DRAGGING &&
        //             !parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) &&
        //             Math.abs(mInitialY - event.getY()) > mViewDragHelper.getTouchSlop();
        // }

        return false;
    }
    */

    /*
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        //  if(dp52 == 0){
        //      dp52 = Utils.dpToPixel(52,parent.getContext());
        //  }
//
        //  if (!child.isShown() || getState() == STATE_HIDDEN || getState() == STATE_COLLAPSED ) {
        //      return  false;
        //  }
        //  int action = MotionEventCompat.getActionMasked(event);
        //  if (mState == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
        //      return true;
        //  }
        //  mViewDragHelper.processTouchEvent(event);
        //  // Record the velocity
        //  if (action == MotionEvent.ACTION_DOWN) {
        //      reset();
        //  }
        //  if (mVelocityTracker == null) {
        //      mVelocityTracker = VelocityTracker.obtain();
        //  }
        //  mVelocityTracker.addMovement(event);
        //  // The ViewDragHelper tries to capture only the top-most View. We have to explicitly tell it
        //  // to capture the bottom sheet in case it is not captured and the touch slop is passed.
        //  if (action == MotionEvent.ACTION_MOVE && !mIgnoreEvents) {
        //      if (Math.abs(mInitialY - event.getY()) > mViewDragHelper.getTouchSlop()) {
        //          mViewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
        //      }
        //  }

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        mLastNestedScrollDy = 0;
        mNestedScrolled = false;
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx,
                                  int dy, int[] consumed) {
        View scrollingChild = mNestedScrollingChildRef.get();
        if (target != scrollingChild) {
            return;
        }
        int currentTop = child.getTop();
        int newTop = currentTop - dy;
        if (dy > 0) { // Upward
            if (newTop < mMinOffset) {
                consumed[1] = currentTop - mMinOffset;
                ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                setStateInternal(STATE_EXPANDED);
            } else {
                consumed[1] = dy;
                ViewCompat.offsetTopAndBottom(child, -dy);
                setStateInternal(STATE_DRAGGING);
            }
        } else if (dy < 0) { // Downward
            if (!ViewCompat.canScrollVertically(target, -1)) {
                if (newTop <= mMaxOffset || mHideable) {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(STATE_DRAGGING);
                } else {
                    consumed[1] = currentTop - mMaxOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    setStateInternal(STATE_COLLAPSED);
                }
            }
        }
        dispatchOnSlide(child.getTop());
        mLastNestedScrollDy = dy;
        mNestedScrolled = true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        if (child.getTop() == mMinOffset) {
            setStateInternal(STATE_EXPANDED);
            return;
        }
        if (target != mNestedScrollingChildRef.get() || !mNestedScrolled) {
            return;
        }
        int top;
        int targetState;
        if (mLastNestedScrollDy > 0) {
            top = mMinOffset;
            targetState = STATE_EXPANDED;
        } else if (mHideable && shouldHide(child, getYVelocity())) {
            top = mParentHeight;
            targetState = STATE_HIDDEN;
        } else if (mLastNestedScrollDy == 0) {
            int currentTop = child.getTop();
            if (Math.abs(currentTop - mMinOffset) < Math.abs(currentTop - mMaxOffset)) {
                top = mMinOffset;
                targetState = STATE_EXPANDED;
            } else {
                top = mMaxOffset;
                targetState = STATE_COLLAPSED;
            }
        } else {
            top = mMaxOffset;
            targetState = STATE_COLLAPSED;
        }
        if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            setStateInternal(STATE_SETTLING);
            ViewCompat.postOnAnimation(child, new SettleRunnable(child, targetState));
        } else {
            setStateInternal(targetState);
        }
        mNestedScrolled = false;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target,
                                    float velocityX, float velocityY) {
        return target == mNestedScrollingChildRef.get() &&
                (mState != STATE_EXPANDED ||
                        super.onNestedPreFling(coordinatorLayout, child, target,
                                velocityX, velocityY));
    }

    */

    public final int getPeekHeight() {
        return mPeekHeightAuto ? PEEK_HEIGHT_AUTO : mPeekHeight;
    }

    public final void setPeekHeight(int peekHeight) {
        boolean layout = false;
        if (peekHeight == PEEK_HEIGHT_AUTO) {
            if (!mPeekHeightAuto) {
                mPeekHeightAuto = true;
                layout = true;
            }
        } else if (mPeekHeightAuto || mPeekHeight != peekHeight) {
            mPeekHeightAuto = false;
            mPeekHeight = Math.max(0, peekHeight);
            mMaxOffset = mParentHeight - peekHeight;
            layout = true;
        }
        if (layout && mState == STATE_COLLAPSED && mViewRef != null) {
            V view = mViewRef.get();
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    public boolean isHideable() {
        return mHideable;
    }

    public void setHideable(boolean hideable) {
        mHideable = hideable;
    }


    public boolean getSkipCollapsed() {
        return mSkipCollapsed;
    }

    public void setSkipCollapsed(boolean skipCollapsed) {
        mSkipCollapsed = skipCollapsed;
    }


    public void setBottomSheetCallback(BottomSheetCallback callback) {
        mCallback = callback;
    }


    @State
    public final int getState() {
        return mState;
    }


    public final void setState(final @State int state) {
        if (state == mState) {
            return;
        }
        if (mViewRef == null) {
            // The view is not laid out yet; modify mState and let onLayoutChild handle it later
            if (state == STATE_COLLAPSED || state == STATE_EXPANDED ||
                    (mHideable && state == STATE_HIDDEN)) {
                mState = state;
            }
            return;
        }
        final V child = mViewRef.get();
        if (child == null) {
            return;
        }
        // Start the animation; wait until a pending layout if there is one.
        ViewParent parent = child.getParent();
        if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(child)) {
            child.post(new Runnable() {
                @Override
                public void run() {
                    startSettlingAnimation(child, state);
                }
            });
        } else {
            startSettlingAnimation(child, state);
        }
    }

    void setStateInternal(@State int state) {
        if (mState == state) {
            return;
        }
        mState = state;
        View bottomSheet = mViewRef.get();
        if (bottomSheet != null && mCallback != null) {
            mCallback.onStateChanged(bottomSheet, state);
        }
    }

    private void reset() {
        mActivePointerId = ViewDragHelper.INVALID_POINTER;
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    boolean shouldHide(View child, float yvel) {
        if (mSkipCollapsed) {
            return true;
        }
        if (child.getTop() < mMaxOffset) {
            // It should not hide, but collapse.
            return false;
        }
        final float newTop = child.getTop() + yvel * HIDE_FRICTION;
        return Math.abs(newTop - mMaxOffset) / (float) mPeekHeight > HIDE_THRESHOLD;
    }

    private View findScrollingChild(View view) {
        if (view instanceof NestedScrollingChild) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    private float getYVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
            return VelocityTrackerCompat.getYVelocity(mVelocityTracker, mActivePointerId);
        }
        return 0.0f;
    }

    void startSettlingAnimation(View child, int state) {
        int top;
        if (state == STATE_COLLAPSED) {
            top = mMaxOffset;
        } else if (state == STATE_EXPANDED) {
            top = mMinOffset;
        } else if (mHideable && state == STATE_HIDDEN) {
            top = mParentHeight;
        } else {
            throw new IllegalArgumentException("Illegal state argument: " + state);
        }
        setStateInternal(STATE_SETTLING);
        if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            ViewCompat.postOnAnimation(child, new SettleRunnable(child, state));
        }
    }

    void dispatchOnSlide(int top) {
        View bottomSheet = mViewRef.get();
        if (bottomSheet != null && mCallback != null) {
            if (top > mMaxOffset) {
                mCallback.onSlide(bottomSheet, (float) (mMaxOffset - top) /
                        (mParentHeight - mMaxOffset));
            } else {
                mCallback.onSlide(bottomSheet,
                        (float) (mMaxOffset - top) / ((mMaxOffset - mMinOffset)));
            }
        }
    }

    @VisibleForTesting
    int getPeekHeightMin() {
        return mPeekHeightMin;
    }

    @RestrictTo(LIBRARY_GROUP)
    @IntDef({STATE_EXPANDED, STATE_COLLAPSED, STATE_DRAGGING, STATE_SETTLING, STATE_HIDDEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public abstract static class BottomSheetCallback {


        public abstract void onStateChanged(@NonNull View bottomSheet, @State int newState);

        public abstract void onSlide(@NonNull View bottomSheet, float slideOffset);
    }

    protected static class SavedState extends AbsSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(
                new ParcelableCompatCreatorCallbacks<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                        return new SavedState(in, loader);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                });
        @State
        final int state;

        public SavedState(Parcel source) {
            this(source, null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            //noinspection ResourceType
            state = source.readInt();
        }

        public SavedState(Parcelable superState, @State int state) {
            super(superState);
            this.state = state;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }
    }

    private class SettleRunnable implements Runnable {

        private final View mView;

        @State
        private final int mTargetState;

        SettleRunnable(View view, @State int targetState) {
            mView = view;
            mTargetState = targetState;
        }

        @Override
        public void run() {
            if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(mView, this);
            } else {
                setStateInternal(mTargetState);
            }
        }
    }

}
