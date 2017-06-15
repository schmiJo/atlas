package com.atlas.atlas.earth.Source.Core.Scene.Triangulation;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.TriangleIndices.TriangleIndicesShort;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.IndexedVector2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class EarClipping {

    public static List<TriangleIndicesShort> triangulate(List<Vector2D> polygonPositions){
        if (polygonPositions == null)
        {
            throw new IllegalArgumentException("positions");
        }

        LinkedList<IndexedVector2D> remainingPositions = new LinkedList<>();
        short index = 0;
        for (Vector2D position:polygonPositions) {
            remainingPositions.addLast(new IndexedVector2D(position, index++));
        }
        if (remainingPositions.size() < 3)
        {
            throw new IllegalArgumentException("At least three positions are required.");
        }

        List<TriangleIndicesShort> indices = new ArrayList<>(3*(remainingPositions.size()-2));

        // TODO: 3/18/2017

        return indices;
    }

    private static boolean isTipConvex(Vector2D p0, Vector2D p1, Vector2D p2){
        Vector2D u = p1.substract(p0);
        Vector2D v = p2.substract(p1);
        return ((u.x * v.y) - (u.y * v.x)) >= 0.0;
    }


}
