package com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;


public class ShapePart {

    private Vector2D[] positions;

    public ShapePart(Vector2D[] positions, int offset, int count) {
        this.positions = new Vector2D[count];

        for (int i = 0; i < count; ++i) {
            this.positions[i] = positions[offset + i];
        }
    }

    public Vector2D getPosition(int index) {
        return positions[index];
    }
    public int size(){
        return positions.length;
    }
}
