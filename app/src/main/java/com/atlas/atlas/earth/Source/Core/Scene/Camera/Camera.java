package com.atlas.atlas.earth.Source.Core.Scene.Camera;

import android.util.Log;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.Rendables.Renderable;


public class Camera {

    private Vector3F position = new Vector3F(0, 0, 5);
    private float pitch = 0;
    private float yaw = 0;
    private float distanceFromEarth = 5;
    private float angleAroundEarth = 0;
    private Renderable earthRenderable;


    public Camera(Renderable earthRenderable) {
        this.earthRenderable = earthRenderable;
    }
    public Camera() {
    }
    /**
     * Zooming
     */
    public void increaseZoom(float time) {
        Log.d("debug", "Time delta: " + time);
        distanceFromEarth += calculateZoomFactor(distanceFromEarth) * time / 1000;
        checkZoomLevelForValidity();
        Log.i("WorldSpaceInfo", "Camera:\t Zoom: " + distanceFromEarth);
    }
    public void decreaseZoom(float time) {
        Log.d("debug", "Time delta: " + time);
        distanceFromEarth -= calculateZoomFactor(distanceFromEarth) * time / 1000;
        checkZoomLevelForValidity();
        Log.i("WorldSpaceInfo", "Camera:\t Zoom: " + distanceFromEarth);
    }
    public void increaseDistanceToEarth(float x){
        distanceFromEarth += x;
    }

    private float calculateZoomFactor(float x) {
        Log.d("debug", "Current Camera Position: " + position.x);
        Log.d("debug", "CurrentFactor: " + ((float) (Math.pow(x - 1, Math.E) / 2)));
        return (float) (Math.pow(x - 1, Math.E) / 2);
    }
    private void checkZoomLevelForValidity() {
        if (distanceFromEarth > 7) {
            distanceFromEarth = 7;
        } else if (distanceFromEarth < 1.0) {
            distanceFromEarth = 1.2f;
        }
    }




    /**
     * Camera position Calculation (Satellite view)
     */
    public void increaseViewAngle(float value) {
        angleAroundEarth += value;
        Log.i("WorldSpaceInfo", "Camera:\t ViewAngle: " + angleAroundEarth);
    }
    public void increasePitch(float value) {
        value /= 2;
//        if ((pitch += value) > 0) {
 //           pitch = 0;
 //       } else if ((pitch += value) < -180) {
 //           pitch = -180;
//        } else {
            pitch += value;
 //       }
        Log.i("WorldSpaceInfo", "Camera:\t Pitch: " + pitch);
     
    }

    float x = -5;
    public void calculateCameraPosition() {
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        position.y = earthRenderable.getPosition().y + verticalDistance;

        float fullrotationAngle = earthRenderable.getRotZ() + angleAroundEarth;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(fullrotationAngle)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(fullrotationAngle)));
        position.x = earthRenderable.getPosition().x - offsetX;
        position.z = earthRenderable.getPosition().z - offsetZ;


        yaw = 180 - (earthRenderable.getRotZ() + angleAroundEarth);

    }
    private float calculateHorizontalDistance() {
        return (float) (distanceFromEarth * Math.cos(Math.toRadians(pitch)));
    }
    private float calculateVerticalDistance() {
        return (float) (distanceFromEarth * Math.sin(Math.toRadians(pitch)));
    }


    /**
     * Getter & Setter
     */
    public Vector3F getPosition() {
        return position;
    }
    public void setPosition(Vector3F position) {
        this.position = position;
    }
    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    public float getDistanceFromEarth(){
        return distanceFromEarth;
    }
    public float getAngleAroundEarth() {
        return angleAroundEarth;
    }
}
