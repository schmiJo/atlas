package com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes;


import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Rectangle2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.ShapeType;

public class PolygonShape extends Shape {

    private Rectangle2D extent;
    private ShapePart[] parts;


    public PolygonShape(int recordNumber, Rectangle2D extent, int[] parts, Vector2D[] positions) {
        super(recordNumber, ShapeType.Polygon);
        this.extent = extent;
        this.parts = new ShapePart[parts.length];


        for (int i = 0; i < parts.length; ++i) {
            int count = ((i == parts.length - 1) ? positions.length : parts[i + 1]) - parts[i];

            this.parts[i] = new ShapePart(positions, parts[i], count);
        }

    }

    public Rectangle2D getExtent() {
        return extent;
    }

    public ShapePart getPart(int index) {
        return parts[index];
    }

    public int size(){
        return parts.length;
    }


}
