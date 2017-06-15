package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Matrices;

/**
 * Created by Jonas on 5/2/2017.
 */

public class Matrix3x3d {

    private double[] mMat;

    public Matrix3x3d() {
        mMat = new double[9];
        loadIdentity();
    }

    public Matrix3x3d(double[] values) {
        if (values.length != 9) {
            throw new IllegalArgumentException("For a 3x3 Matrix are 9 values required!");
        }
        mMat = values;
    }

    public Matrix3x3d(double v0x0, double v0x1, double v0x2,
                      double v1x0, double v1x1, double v1x2,
                      double v2x0, double v2x1, double v2x2) {

        mMat = new double[]{
                v0x0, v0x1, v0x2,
                v1x0, v1x1, v1x2,
                v2x0, v2x1, v2x2
        };

    }

    public double[] getArray() {
        return mMat;
    }
    public void loadIdentity() {
        mMat[0] = 1;
        mMat[1] = 0;
        mMat[2] = 0;
        mMat[3] = 0;
        mMat[4] = 1;
        mMat[5] = 0;
        mMat[6] = 0;
        mMat[7] = 0;
        mMat[8] = 1;
    }
}
