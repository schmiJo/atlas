package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors;


import java.util.List;

public class Vector2F {

    public float x, y;

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public static float[] toArray(List<Vector2F> positions) {
        float[] result = new float[positions.size() * 2];
        int a = 0;
        for (int i = 0; i < positions.size(); i++) {
            result[a] = positions.get(i).x;
            a++;
            result[a] = positions.get(i).y;
            a++;
        }


        return result;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
