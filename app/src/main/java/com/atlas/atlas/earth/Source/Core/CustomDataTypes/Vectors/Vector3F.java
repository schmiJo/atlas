package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors;


import com.atlas.atlas.earth.Source.Core.MathTools;

import java.util.List;


public class Vector3F {

    public float x, y, z;

    public Vector3F(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector3F add(Vector3F value) {
        return new Vector3F(x + value.x, y + value.y, z + value.z);
    }
    public Vector3F substract(Vector3F value) {
        return new Vector3F(x - value.x, y - value.y, z - value.z);
    }
    public Vector3F multiplyComponents(float value) {
        return new Vector3F(x * value, y * value, z * value);
    }
    public Vector3F multiplyComponents(Vector3F scale) {
        return new Vector3F(this.x * scale.x, this.y * scale.y, this.z * scale.z);
    }
    public Vector3F divideComponents(float value) {
        return new Vector3F(x / value, y / value, z / value);
    }
    public Vector3F normalize() {
        if (getMagnitude() != 0) {
            return divideComponents(getMagnitude());
        } else {
            return new Vector3F(0, 0, 0);
        }

    }
    public Vector3F normalize(int length) {
        normalize();
        return multiplyComponents(length);
    }
    public Vector3F cross(Vector3F value) {
        return new Vector3F(y * value.z - z * value.y,
                z * value.x - x * value.z,
                x * value.y - y * value.x);
    }
    public float scalar(Vector3F value) {
        return (x * value.x + y * value.y + z * value.z);
    }

    public float getMagnitudeSquared() {
        return x * x + y * y + z * z;
    }
    public float getMagnitude() {
        return (float) Math.sqrt(getMagnitudeSquared());
    }
    public static float angleBetween(Vector3F value1, Vector3F value2) {
        if(value1.equals(value2)){
            return 0;
        }
        float magnitude1 = value1.getMagnitude();
        float magnitude2 = value2.getMagnitude();
        float scalar = value1.scalar(value2);
        float angleInRad = (float) Math.acos(scalar / (magnitude1 * magnitude2));
        return MathTools.radToDeg(angleInRad);
    }


    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector3F)) {
            return false;
        }


        return this.x == ((Vector3F) obj).x
                && this.y == ((Vector3F) obj).y
                && this.z == ((Vector3F) obj).z;
    }
    public static float[] toArray(List<Vector3F> positions) {
        float[] result = new float[positions.size() * 3];
        int arrayIndex = 0;
        for (int listIndex = 0; listIndex < positions.size(); listIndex++) {
            result[arrayIndex] = positions.get(listIndex).x;
            arrayIndex++;
            result[arrayIndex] = positions.get(listIndex).y;
            arrayIndex++;
            result[arrayIndex] = positions.get(listIndex).z;
            arrayIndex++;
        }
        return result;
    }
}
