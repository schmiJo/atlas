package com.atlas.atlas.earth.Source.Core.CustomDataTypes;

/**
 * Created by Jonas on 3/27/2017.
 */

public class Edge {

    private int index0;
    private int index1;

    public Edge(int index0, int index1) {
        this.index0 = index0;
        this.index1 = index1;
    }

    public int getIndex0() {
        return index0;
    }

    public int getIndex1() {
        return index1;
    }

    @Override
    public int hashCode() {
        return index0 ^ index1;
    }

    @Override
    public boolean equals(Object obj) {
        return ((index0==((Edge)obj).getIndex0())&&(index1==((Edge)obj).getIndex1()));
    }


}
