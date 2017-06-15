package com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices;

import java.util.List;


public class TriangleIndicesInt {
    private int t0;
    private int t1;
    private int t2;


    public TriangleIndicesInt(int t0, int t1, int t2) {
        if (t0 < 0 || t1 < 0 || t2 < 0)
            throw new IllegalArgumentException("Indices can't be negative");
        this.t0 = t0;
        this.t1 = t1;
        this.t2 = t2;
    }

    public int getT0() {
        return t0;
    }

    public int getT1() {
        return t1;
    }

    public int getT2() {
        return t2;
    }

    public static int[] convertToIntArray(List<TriangleIndicesInt> data) {
        int[] result = new int[data.size() * 3];
        int a = 0;
        for (int i = 0; i < data.size(); i++) {
            result[a] = data.get(i).getT0();
            a++;
            result[a] = data.get(i).getT1();
            a++;
            result[a] = data.get(i).getT2();
            a++;

        }
        return result;
    }

    @Override
    public String toString() {
        return t0 + ", " + t1 + ", " + t2+ " ";
    }
}
