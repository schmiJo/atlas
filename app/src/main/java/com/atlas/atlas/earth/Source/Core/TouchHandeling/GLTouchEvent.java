package com.atlas.atlas.earth.Source.Core.TouchHandeling;

import android.content.Context;
import android.renderscript.Matrix4f;
import android.util.Log;

import com.atlas.atlas.earth.EarthView;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2F;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;


public class GLTouchEvent {


    private Vector2F normalizedCoords = new Vector2F(0,0);
    private Vector4F clipCoords = new Vector4F(0,0,0,0);
    private Vector4F eyeCoords = new Vector4F(0,0,0,0);
    private Vector3F worldCoords = new Vector3F(0,0,0);


    private Matrix4f inversedProjectionMatrix;
    private Matrix4f inversedViewMatrix;
    private Context context;

    public GLTouchEvent(Matrix4f inversedProjectionMatrix, Matrix4f inversedViewMatrix, Context context) {
        this.inversedProjectionMatrix = inversedProjectionMatrix;
        this.inversedViewMatrix = inversedViewMatrix;
        this.context = context;
    }

    public void updateTouchEvent(Matrix4f inversedViewMatrix, float touchX, float touchY) {
        this.inversedViewMatrix = inversedViewMatrix;
        this.normalizedCoords = toNormalizedDeviceCoords(touchX, touchY);
        this.clipCoords = new Vector4F(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
        this.eyeCoords = toEyeCoords(clipCoords);
        this.worldCoords = toWorldCoords(eyeCoords);

        logCoordinates(touchX, touchY);
    }



    private Vector2F toNormalizedDeviceCoords(float touchX, float touchY) {
        return new Vector2F((2.0f * touchX) / EarthView.getEarthViewWidth(context) - 1.0f, -((2.0f * touchY) / EarthView.getEarthViewHeight(context) - 1.0f));
    }
    private Vector4F toEyeCoords(Vector4F clipCoords) {
        Vector4F eyeCoords = MatricesUtility.transformVector(clipCoords, inversedProjectionMatrix);
        //We only care about the x and y coordinates (from touch) but the Vector should point into the scene (z = -1)
        return new Vector4F(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }
    private Vector3F toWorldCoords(Vector4F eyeCoords) {
        Vector4F rayWorld = MatricesUtility.transformVector(eyeCoords, inversedViewMatrix);
        Vector3F mouseRay = new Vector3F(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalize();
        return mouseRay;
    }

    private void logCoordinates(float touchX, float touchY) {
        Log.d("EarthTouch", "DeviceCoords: \t\t" + touchX + ", " + touchY);
        Log.d("EarthTouch", "NormalizedCoords: \t" + normalizedCoords.x + ", " + normalizedCoords.y);
        Log.d("EarthTouch", "ClipCoords: \t\t\t" + clipCoords.x + ", " + clipCoords.y + ", " + clipCoords.z + ", " + clipCoords.w);
        Log.d("EarthTouch", "EyeCoords: \t\t\t" + eyeCoords.x + ", " + eyeCoords.y + ", " + eyeCoords.z + ", " + eyeCoords.w);
        Log.d("EarthTouch", "WorldCoords: \t\t\t" + worldCoords.x + ", " + worldCoords.y + ", " + worldCoords.z + "\n");
        Log.d("EarthTouch", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }


    public static GLTouchEvent copyEvent(Context context, GLTouchEvent glTouchEvent){
        GLTouchEvent copy = new GLTouchEvent(glTouchEvent.getInversedProjectionMatrix(), glTouchEvent.getInversedViewMatrix(),context);
        copy.setNormalizedCoords(glTouchEvent.getNormalizedCoords());
        copy.setClipCoords(glTouchEvent.getClipCoords());
        copy.setEyeCoords(glTouchEvent.getEyeCoords());
        copy.setWorldCoords(glTouchEvent.getWorldCoords());
        return copy;
    }

    public Vector2F getNormalizedCoords() {
        return normalizedCoords;
    }
    public Vector4F getClipCoords() {
        return clipCoords;
    }
    public Vector4F getEyeCoords() {
        return eyeCoords;
    }
    public Vector3F getWorldCoords() {
        return worldCoords;
    }
    public Matrix4f getInversedProjectionMatrix() {
        return inversedProjectionMatrix;
    }
    public Matrix4f getInversedViewMatrix() {
        return inversedViewMatrix;
    }

    public void setNormalizedCoords(Vector2F normalizedCoords) {
        this.normalizedCoords = normalizedCoords;
    }
    public void setClipCoords(Vector4F clipCoords) {
        this.clipCoords = clipCoords;
    }
    public void setEyeCoords(Vector4F eyeCoords) {
        this.eyeCoords = eyeCoords;
    }
    public void setWorldCoords(Vector3F worldCoords) {
        this.worldCoords = worldCoords;
    }
}
