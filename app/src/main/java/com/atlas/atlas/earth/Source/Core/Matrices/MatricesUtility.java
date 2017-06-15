package com.atlas.atlas.earth.Source.Core.Matrices;

import android.content.Context;
import android.renderscript.Matrix4f;

import com.atlas.atlas.earth.EarthView;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector4F;
import com.atlas.atlas.earth.Source.Core.Scene.Camera.Camera;


public class MatricesUtility {


    public static Matrix4f lookAtEarth(Vector3F eye, Vector3F up) {
        return lookAt(eye, new Vector3F(0, 0, 0), up);
    }

    public static Matrix4f lookAt(Vector3F eye, Vector3F target, Vector3F up) {

        Vector3F f = (target.substract(eye)).normalize();
        Vector3F s = (f.cross(up)).normalize();
        Vector3F u = s.cross(f).normalize();

        Matrix4f rotation = new Matrix4f(new float[]{
                s.x, s.y, s.z, 0.0f,
                u.x, u.y, u.z, 0.0f,
                -f.x, -f.y, -f.z, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f});

        Matrix4f translation = new Matrix4f(new float[]{
                1.0f, 0.0f, 0.0f, -eye.x,
                0.0f, 1.0f, 0.0f, -eye.y,
                0.0f, 0.0f, 1.0f, -eye.z,
                0.0f, 0.0f, 0.0f, 1.0f});
        rotation.multiply(translation);

        return rotation;
    }
   /* public static Matrix4f lookAt(Vector3F eye, Vector3F target, Vector3F up){
        float[] asdf = new float[]{1,2,3};
        Vector3F zaxis = (eye.substract(target)).normalize();    // The "forward" vector.
        Vector3F xaxis = (up.cross(zaxis)).normalize();// The "right" vector.
        Vector3F yaxis = zaxis.cross(xaxis);     // The "up" vector.

        // Create a 4x4 orientation matrix from the right, up, and forward vectors
        // This is transposed which is equivalent to performing an inverse
        // if the matrix is orthonormalized (in this case, it is).
        Matrix4f orientation = new Matrix4f(
                new float[]{
                        xaxis.x, yaxis.x, zaxis.x, 0,
                        xaxis.y, yaxis.y, zaxis.y, 0 ,
                        xaxis.z, yaxis.z, zaxis.z, 0 ,
                        0,       0,       0,     1
                }
        );

        // Create a 4x4 translation matrix.
        // The eye position is negated which is equivalent
        // to the inverse of the translation matrix.
        // T(v)^-1 == T(-v)
        Matrix4f translation = new Matrix4f(
                new float[]{
                        1, 0, 0, 0,
                        0, 1, 0, 0,
                        0, 0, 1, 0,
                        -eye.x, -eye.y, -eye.z, 1
                }
        );

        // Combine the orientation and translation to compute
        // the final view matrix
         Matrix4f result = orientation;
        orientation.multiply(translation);
        return result;
    }*/

    public static Matrix4f createTransformationMatrix(Vector3F transformation, float rx, float ry, float rz, float scale) {

        Matrix4f transformationMatrix = new Matrix4f();
        transformationMatrix.translate(transformation.x, transformation.y, transformation.z);
        transformationMatrix.rotate(rx, 1, 0, 0);//+90
        transformationMatrix.rotate(ry, 0, 1, 0);
        transformationMatrix.rotate(rz, 0, 0, 1);//-90
        transformationMatrix.scale(scale, scale, scale);

        return transformationMatrix;
    }

    public static Matrix4f createProjectionMatrix(Context context) {

        final float FOV = 50;
        final float NEAR_PLANE = 0.1f;
        final float FAR_PLANE = 150;

        float aspectRatio = EarthView.getEarthViewWidth(context) / EarthView.getEarthViewHeight(context);
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        Matrix4f projectionMatrix = new Matrix4f();

        projectionMatrix.set(0, 0, x_scale);
        projectionMatrix.set(1, 1, y_scale);
        projectionMatrix.set(2, 2, -((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.set(2, 3, -1);
        projectionMatrix.set(3, 2, -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.set(3, 3, 0);

        return projectionMatrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.loadIdentity();
        viewMatrix.rotate(camera.getPitch(), 1, 0, 0);
        viewMatrix.rotate(camera.getYaw(), 0, 1, 0);
        //viewMatrix.rotate((float)Math.toRadians(camera.getRoll()), 0, 0, 1);
        Vector3F negativeCameraPos = new Vector3F(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);

        viewMatrix.translate(negativeCameraPos.x, negativeCameraPos.y, negativeCameraPos.z);
        return viewMatrix;
    }

    public static Vector4F transformVector(Vector4F vector, Matrix4f transformMatrix) {
        float x = transformMatrix.get(0, 0) * vector.x + transformMatrix.get(0, 1) * vector.y + transformMatrix.get(0, 2) * vector.z + transformMatrix.get(0, 3) * vector.w;
        float y = transformMatrix.get(1, 0) * vector.x + transformMatrix.get(1, 1) * vector.y + transformMatrix.get(1, 2) * vector.z + transformMatrix.get(1, 3) * vector.w;
        float z = transformMatrix.get(2, 0) * vector.x + transformMatrix.get(2, 1) * vector.y + transformMatrix.get(2, 2) * vector.z + transformMatrix.get(2, 3) * vector.w;
        float w = transformMatrix.get(3, 0) * vector.x + transformMatrix.get(3, 1) * vector.y + transformMatrix.get(3, 2) * vector.z + transformMatrix.get(3, 3) * vector.w;
        return new Vector4F(x, y, z, w);
    }

}
