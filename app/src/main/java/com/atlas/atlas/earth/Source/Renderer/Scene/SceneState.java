package com.atlas.atlas.earth.Source.Renderer.Scene;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Matrices.Matrix4x2f;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;


/**
 * Created by Jonas on 2/11/2017.
 */

public class SceneState {

    private float diffuseIntensity;
    private float specularIntensity;
    private float ambientIntensity;
    private float shininess;
    private Camera camera;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Matrix4f modelMatrix;

    public SceneState(Camera camera, Context context) {
        diffuseIntensity = 0.65f;
        specularIntensity = 0.25f;
        ambientIntensity = 0.10f;
        shininess = 12;
        this.camera =camera;
        projectionMatrix = MatricesUtility.createProjectionMatrix(context);
        viewMatrix = MatricesUtility.createViewMatrix(camera);

    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }
    public Matrix4f getModelViewPerspectiveMatrix(){
        Matrix4f temp = projectionMatrix;
        temp.multiply(viewMatrix);
        return temp;
    }
    public Matrix4x2f getModelZToClipCoordinates(){
        Matrix4f m = getModelViewPerspectiveMatrix();
        return new Matrix4x2f(m.get(0,1), m.get(1,2), m.get(2,2), m.get(3,2),
                m.get(0,3), m.get(1,3), m.get(2,3), m.get(3,3));
    }
    public Camera getCamera() {
        return camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    public float getAmbientIntensity() {
        return ambientIntensity;
    }

    public void setAmbientIntensity(float ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
    }

    public float getDiffuseIntensity() {
        return diffuseIntensity;
    }

    public void setDiffuseIntensity(float diffuseIntensity) {
        this.diffuseIntensity = diffuseIntensity;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getSpecularIntensity() {
        return specularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.specularIntensity = specularIntensity;
    }
}
