package com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices;

import java.util.List;


public class TriangleIndicesShort {
    private short t0;
    private short t1;
    private short t2;


    public TriangleIndicesShort(short t0, short t1, short t2) {
        if (t0 < 0 || t1 < 0 || t2 < 0)
            throw new IllegalArgumentException("Indices can't be negative");
        this.t0 = t0;
        this.t1 = t1;
        this.t2 = t2;
    }

    public short getT0() {
        return t0;
    }

    public short getT1() {
        return t1;
    }

    public short getT2() {
        return t2;
    }

    public static short[] convertToShortArray(List<TriangleIndicesShort> data) {
        short[] result = new short[data.size() * 3];
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
