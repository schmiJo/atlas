package com.atlas.atlas.earth.Source.Core.Scene.Shapefile;

import java.io.InvalidObjectException;

public class ShapeType {

    public byte currentShapeType;
    public static final byte NullShape = 0;
    public static final byte Point = 1;
    public static final byte Polyline = 3;
    public static final byte Polygon = 5;
    public static final byte MultiPoint = 8;
    public static final byte PointZ = 11;
    public static final byte PolylineZ = 13;
    public static final byte PolygonZ = 15;
    public static final byte MultiPointZ = 18;
    public static final byte PointM = 21;
    public static final byte PolylineM = 23;
    public static final byte PolygonM = 25;
    public static final byte MultiPointM = 28;
    public static final byte MultiPatch = 31;

    public void setShapeType(byte shapeType) {
        if(shapeType<0||shapeType>31){
            throw new IllegalArgumentException("Invalid Shapetype");
        }
        this.currentShapeType = shapeType;
    }

    public byte getShapeType() {
        return currentShapeType;
    }

    static int getShapeType(int shapeType) throws InvalidObjectException {
        switch (shapeType) {
            case 0:
                return ShapeType.NullShape;
            case 1:
                return ShapeType.Point;
            case 3:
                return ShapeType.Polyline;
            case 5:
                return ShapeType.Polygon;
            case 8:
                return ShapeType.MultiPoint;
            case 11:
                return ShapeType.PointZ;
            case 13:
                return ShapeType.PolylineZ;
            case 15:
                return ShapeType.PolygonZ;
            case 18:
                return ShapeType.MultiPointZ;
            case 21:
                return ShapeType.PointM;
            case 23:
                return ShapeType.PolylineM;
            case 25:
                return ShapeType.PolygonM;
            case 28:
                return ShapeType.MultiPointM;
            case 31:
                return ShapeType.MultiPatch;
            default:
                throw new InvalidObjectException("Can not resolve Shape Type");
        }
    }

    @Override
    public String toString() {
        switch (currentShapeType){
            case 0: return "NullShape";
            case 1: return "Point";
            case 3: return "Polyline";
            case 5: return "Polygon";
            case 8: return "MultiPoint";
            case 11: return "PointZ";
            case 13: return "PolylineZ";
            case 15: return "PolygonZ";
            case 18: return "MultiPointZ";
            case 21 : return "PointM";
            case 23: return "PolylineM";
            case 25: return "PolygonM";
            case 28: return "MultiPointM";
            case 31: return "MultiPatch";
            default: return "NOT FOUND!";
        }
    }
}
