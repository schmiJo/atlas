package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors;


public class IndexedVector2D extends Vector2D {

    private short index;

    public IndexedVector2D(double x, double y, short index) {
        super(x, y);
        this.index = index;
    }
    public IndexedVector2D(Vector2D vector2D, short index) {
        super(vector2D.x, vector2D.y);
        this.index = index;
    }

    public short getIndex() {
        return index;
    }
}
