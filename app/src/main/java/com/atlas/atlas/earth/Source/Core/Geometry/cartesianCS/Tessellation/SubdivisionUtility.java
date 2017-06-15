package com.atlas.atlas.earth.Source.Core.Geometry.cartesianCS.Tessellation;

/**
 * Created by Jonas on 2/20/2017.
 */

public class SubdivisionUtility {

    public static int NumberOfTriangles(int numberOfSubdivisions)
    {
        int numberOfTriangles = 0;
        for (int i = 0; i <= numberOfSubdivisions; ++i)
        {
            numberOfTriangles += Math.pow(4, i);
        }
        numberOfTriangles *= 4;

        return numberOfTriangles;
    }

    public static int NumberOfVertices(int numberOfSubdivisions)
    {
        int numberOfVertices = 0;
        for (int i = 0; i < numberOfSubdivisions; ++i)
        {
            numberOfVertices += Math.pow(4, i);
        }
        numberOfVertices = 4 + (12 * numberOfVertices);

        return numberOfVertices;
    }

}
