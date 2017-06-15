package com.atlas.atlas.camera;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.atlas.atlas.R;
import com.atlas.atlas.main.MainActivity;


public class CameraAllowError extends Fragment {


    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.camera_error_fragment, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        this.view = view;
        view.findViewById(R.id.retryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MainActivity.MY_PERMISSION_REQUEST_CAMERA);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    void closeFragmentAnim(final AnimationListener animationListener) {

        final View myView = view.findViewById(R.id.my_view);

        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
                animationListener.animEnd();
            }
        });

// start the animation
        anim.start();

    }

    interface AnimationListener {
        void animEnd();
    }


}
