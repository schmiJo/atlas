package com.atlas.atlas.earth.Source.Core.TouchHandeling;


import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;

public class TouchHandler {

    private Vector3F currentRay;
    private Matrix4f inversedProjectionMatrix;
    private Matrix4f inversedViewMatrix;
    private Camera camera;
    private Context context;
    private GLTouchEvent staticTouchPoint;
    private GLTouchEvent dynamicTouchPoint;



    public TouchHandler(Matrix4f projectionMatrix, Camera camera, Context context) {
        this.inversedProjectionMatrix = projectionMatrix;
        this.inversedProjectionMatrix.inverse();
        inversedViewMatrix = MatricesUtility.createViewMatrix(camera);
        inversedViewMatrix.inverse();
        this.camera = camera;
        this.context = context;
        staticTouchPoint = new GLTouchEvent(inversedProjectionMatrix, inversedViewMatrix, context);
    }

    public void setUp(float touchX, float touchY, Camera camera){
        inversedViewMatrix = MatricesUtility.createViewMatrix(camera);
        inversedViewMatrix.inverse();
        staticTouchPoint.updateTouchEvent(inversedViewMatrix, touchX, touchY);
        dynamicTouchPoint = GLTouchEvent.copyEvent(context, staticTouchPoint);
    }
    public void update(float touchX, float touchY, Camera camera) {
        inversedViewMatrix = MatricesUtility.createViewMatrix(camera);
        inversedViewMatrix.inverse();
        dynamicTouchPoint.updateTouchEvent(inversedViewMatrix, touchX, touchY);
        calculateCameraRotation();
    }
    public void reset(){

    }

    private void calculateCameraRotation(){
        float rotX = (float)Math.atan(calculateDeltaXInEyeCoords()/camera.getDistanceFromEarth());
        float rotY = (float)Math.atan(calculateDeltaYInEyeCoords()/camera.getDistanceFromEarth());
        camera.increaseViewAngle(rotX*4);
        camera.increasePitch(rotY*2);
    }



    private float calculateDeltaXInEyeCoords(){
        return staticTouchPoint.getEyeCoords().x - dynamicTouchPoint.getEyeCoords().x;
    }
    private float calculateDeltaYInEyeCoords(){
        return staticTouchPoint.getEyeCoords().y - dynamicTouchPoint.getEyeCoords().y;
    }

}
