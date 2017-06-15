package com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Rectangle2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.ShapeType;


public class PolylineShape extends Shape {


    private Rectangle2D extend;
    private  ShapePart[] parts;

    public PolylineShape(int recordNumber, Rectangle2D extent, int[] parts, Vector2D[] positions) {
        super(recordNumber, ShapeType.Polyline);
        this.extend = extent;
        this.parts = new ShapePart[parts.length];

        for (int i = 0; i < parts.length; ++i)
        {
            int count = ((i == parts.length - 1) ?
                    positions.length : parts[i + 1]) - parts[i];

            this.parts[i] = new ShapePart(positions, parts[i], count);
        }
    }

    public Rectangle2D getExtend() {
        return extend;
    }

    public ShapePart getPart(int index) {
        return parts[index];
    }

    public int count(){
        return parts.length;
    }
}
