package com.atlas.atlas.earth.Source.Core.CustomDataTypes;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;


public class Rectangle2D {

    private Vector2D lowerLeft;
    private Vector2D upperRight;

    public Rectangle2D(Vector2D lowerLeft, Vector2D upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public Vector2D getLowerLeft() {
        return lowerLeft;
    }
    public Vector2D getUpperRight() {
        return upperRight;
    }

    @Override
    public String toString() {
        return "Lower Left: " +lowerLeft.toString() + "Upper Right: "+ upperRight.toString();
    }





}
