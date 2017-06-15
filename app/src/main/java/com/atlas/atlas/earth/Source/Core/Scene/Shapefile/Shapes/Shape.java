package com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes;


public abstract class Shape {
    private int recordNumber;
    private byte shapeType;

    protected Shape(int recordNumber, byte shapeType)
    {
        this.recordNumber = recordNumber;
        this.shapeType = shapeType;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public byte getShapeType() {
        return shapeType;
    }
}
