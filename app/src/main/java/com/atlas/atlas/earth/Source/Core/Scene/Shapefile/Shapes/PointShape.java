package com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.ShapeType;


public class PointShape extends Shape {

    private Vector2D position;

    public PointShape(int recordNumber, Vector2D position) {
        super(recordNumber, ShapeType.Point);
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }
}
