package com.atlas.atlas.earth.Source.Core.Scene.Shapefile;

import android.graphics.Color;


public class ShapefileAppearance {

    public  int polylineColor;
    public  int polylineOutlineColor;
    public double polylineWidth;
    public double polylineOutlineWidth;


    public ShapefileAppearance() {
        polylineColor = Color.YELLOW;
        polylineOutlineColor = Color.BLACK;
        polylineWidth = 3.0;
        polylineOutlineWidth = 2.0;
    }

    public int getPolylineColor() {
        return polylineColor;
    }

    public void setPolylineColor(int polylineColor) {
        this.polylineColor = polylineColor;
    }

    public int getPolylineOutlineColor() {
        return polylineOutlineColor;
    }

    public void setPolylineOutlineColor(int polylineOutlineColor) {
        this.polylineOutlineColor = polylineOutlineColor;
    }

    public double getPolylineWidth() {
        return polylineWidth;
    }

    public void setPolylineWidth(double polylineWidth) {
        this.polylineWidth = polylineWidth;
    }

    public double getPolylineOutlineWidth() {
        return polylineOutlineWidth;
    }

    public void setPolylineOutlineWidth(double polylineOutlineWidth) {
        this.polylineOutlineWidth = polylineOutlineWidth;
    }
}

