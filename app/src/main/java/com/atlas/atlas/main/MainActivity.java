package com.atlas.atlas.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.atlas.atlas.R;
import com.atlas.atlas.camera.CameraFragment;
import com.atlas.atlas.general.modeBar.ModeFragment;

public class MainActivity extends AppCompatActivity{

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
            }
        });
        bottomSheet = MainSheetBehavior.from(mainSheet);
        bottomSheet.setState(MainSheetBehavior.STATE_EXPANDED);
        MainSheetBehavior.BottomSheetCallback sheetCallback = new MainSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @MainSheetBehavior.State int newState) {


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







}
