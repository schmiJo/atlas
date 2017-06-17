package com.atlas.atlas.main;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.atlas.atlas.R;
import com.atlas.atlas.camera.CameraFragment;
import com.atlas.atlas.general.Utils;
import com.atlas.atlas.general.modeBar.CameraModeFragment;
import com.atlas.atlas.general.modeBar.ModeFragment;

public class MainActivity extends AppCompatActivity implements CameraModeFragment.CloseListener, CameraModeFragment.ModeChangeListener {

    static public final int MY_PERMISSION_REQUEST_CAMERA = 0;
    static public int previousState = MainSheetBehavior.STATE_EXPANDED;

    private CameraFragment cameraFragment;
    private MainSheetBehavior bottomSheet;
    private CaptureButton captureButton;
    private Shutter shutter;
    private ModeFragment earthModes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        cameraFragment = new CameraFragment();
        earthModes = ModeFragment.getInstance(ModeFragment.TYPE_EARTH);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.camContainer, cameraFragment)
                    .commit();
        }

        getFragmentManager().beginTransaction().replace(R.id.earthModesContainer, earthModes).commit();


        shutter = (Shutter) findViewById(R.id.shutter);
        captureButton = (CaptureButton) findViewById(R.id.captureButton);
        Utils.setMargins(captureButton,0,0,0,Utils.getSoftButtonsBarHeight(this));
        View mainSheet = findViewById(R.id.mainSheet);
        MainLayout mainLayout = (MainLayout) findViewById(R.id.mainLayout);
        cameraFragment.setErrorListener(new CameraFragment.ErrorListener() {
            @Override
            public void onError() {
                Snackbar.make(captureButton, R.string.camError, Snackbar.LENGTH_SHORT).show();
            }
        });

        captureButton.setControl(new CaptureButton.Control() {
            @Override
            public void onPhoto() {

            }

            @Override
            public void onVideoStart() {

            }

            @Override
            public void onVideoStop() {

            }

            @Override
            public void onVideoCanceled() {

            }

            @Override
            public void goToCam() {
                openCamera();
            }
        });
        mainLayout.setOnActionListener(new MainLayout.Action() {
            @Override
            public void onExpand() {
                closeCamera();
                if (cameraFragment.getModeLayer() != 0) {

                    captureButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.capture_button_slide_in));
                    captureButton.setVisibility(View.VISIBLE);
                    cameraFragment.hideModesNoAnim();


                }
            }
        });
        bottomSheet = MainSheetBehavior.from(mainSheet);
        bottomSheet.setState(MainSheetBehavior.STATE_EXPANDED);
        MainSheetBehavior.BottomSheetCallback sheetCallback = new MainSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @MainSheetBehavior.State int newState) {

                if ((newState == MainSheetBehavior.STATE_SETTLING || newState == MainSheetBehavior.STATE_HIDDEN) && previousState == MainSheetBehavior.STATE_EXPANDED) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


                } else if ((newState == MainSheetBehavior.STATE_SETTLING || newState == MainSheetBehavior.STATE_EXPANDED) && previousState == MainSheetBehavior.STATE_HIDDEN) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                }
                previousState = newState;
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                captureButton.setVerticalOffset(slideOffset);
                shutter.setShutter(slideOffset);
            }
        };
        bottomSheet.setBottomSheetCallback(sheetCallback);
    }


    private void openCamera() {

        cameraFragment.mainStartCam();
        bottomSheet.setState(MainSheetBehavior.STATE_HIDDEN);
    }

    private void closeCamera() {
        captureButton.setState(CaptureButton.STATE_NORMAL);
        bottomSheet.setState(MainSheetBehavior.STATE_EXPANDED);
        cameraFragment.mainStopCam();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // camera-related task you need to do.
                    if (cameraFragment.isOnErrorFragment()) {
                        cameraFragment.removeErrorResult();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    if (!cameraFragment.isOnErrorFragment()) {
                        cameraFragment.showErrorResult();
                    }
                }

        }
    }

    @Override
    public void onBackPressed() {
        if (cameraFragment.getModeLayer() == 1) {
            captureButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.camera_slide_in_buttons));
            captureButton.setVisibility(View.VISIBLE);
            cameraFragment.startRemoveAnim();
            cameraFragment.slideOutModes();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    cameraFragment.removeCamModes();
                }
            }, 250);

        }else if(cameraFragment.getModeLayer() == 2){
            cameraFragment.modeGoDownLayer();

        }else
         if (bottomSheet.getState() != MainSheetBehavior.STATE_EXPANDED) {
            bottomSheet.setState(MainSheetBehavior.STATE_EXPANDED);
        } else if (earthModes.layer != 0) {
            earthModes.goDownLayer();

        } else {
            super.onBackPressed();
        }
    }

    public void openCamModes(View view) {
        cameraFragment.showModes();
        captureButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.camera_slide_out_buttons));
        captureButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClosed() {
        cameraFragment.removeCamModes();
    }

    @Override
    public void onClose() {
        captureButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.camera_slide_in_buttons));
        captureButton.setVisibility(View.VISIBLE);
        cameraFragment.hideModesNoAnim();
        cameraFragment.startRemoveAnim();
    }

    @Override
    public void camModeChanged(int position) {

    }

    @Override
    public void filterModeChanged(int position) {

    }

    @Override
    public void timeModeChanged(int position) {

    }

    @Override
    public void textModeChanged(int position) {

    }
}
