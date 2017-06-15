package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Matrices;


public class Matrix4x2f {


    private float[] mMat;

    public Matrix4x2f() {
        mMat = new float[8];
        loadIdentity();
    }

    public Matrix4x2f(float[] value) {
        if (value.length != 8) {
            throw new IllegalArgumentException("For a 3x3 Matrix are 9 values required!");
        }
        this.mMat = value;
    }
    public Matrix4x2f(float v0x0, float v0x1, float v1x0, float v1x1,
                      float v2x0, float v2x1, float v3x0, float v3x1) {

        mMat = new float[]{
                v0x0, v0x1,
                v1x0, v1x1,
                v2x0, v2x1,
                v3x0, v3x1
        };
    }


    public void loadIdentity() {

        mMat[0] = 1f;
        mMat[1] = 0f;
        mMat[2] = 0f;
        mMat[3] = 0f;
        mMat[4] = 0f;
        mMat[5] = 1f;
        mMat[6] = 0f;
        mMat[7] = 0f;
    }


    public float[] getArray() {
        return this.mMat;
    }

    public float get(int x, int y) {
        return this.mMat[x * 4 + y];
    }
}
