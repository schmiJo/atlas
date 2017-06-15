package com.atlas.atlas.earth.Source.Core.Rendables.Shapefiles;

import com.atlas.atlas.earth.Source.Core.ByteFlags;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;

import java.util.ArrayList;
import java.util.List;


public class SimplePolygonAlgorithms  {


    public static List<Vector3D> cleanUp(List<Vector3D> positions){

        if(positions.size()<3){
            throw new IllegalArgumentException("At least three positions are required!");
        }
        List<Vector3D> cleanedPositions = new ArrayList<>(positions.size());

        for (int i0 = positions.size() - 1, i1 = 0; i1 < positions.size(); i0 = i1++)
        {
            Vector3D v0 = positions.get(i0);
            Vector3D v1 = positions.get(i1);



            if (!(v0.x == v1.x && v0.y == v1.y && v0.z == v1.z))
                {
                cleanedPositions.add(v1);
            }
        }
        return cleanedPositions;
    }

    public static double ComputeArea(List<Vector2D> positions)
    {


        if (positions.size() < 3)
        {
            throw new IllegalArgumentException("At least three positions are required.");
        }

        double area = 0.0;

        //
        // Compute the area of the polygon.  The sign of the area determines the winding order.
        //
        for (int i0 = positions.size() - 1, i1 = 0; i1 < positions.size(); i0 = i1++)
        {
            Vector2D v0 = positions.get(i0);
            Vector2D v1 = positions.get(i1);

            area += (v0.x * v1.y) - (v1.x * v0.y);
        }

        return area * 0.5;
    }

    public static int ComputeWindingOrder(List<Vector2D> positions)
    {
        return (ComputeArea(positions) >= 0.0) ? ByteFlags.COUNTERCLOCKWISE : ByteFlags.CLOCKWISE;
    }

}
