package com.atlas.atlas.earth.Source.Core.BoundingVolumes;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;


public class ContainmentTests {

    public static boolean PointInsideTriangle(Vector2D point, Vector2D A, Vector2D B, Vector2D C)
    {
        //
        // Implementation based on http://www.blackpawn.com/texts/pointinpoly/default.html.
        //
        Vector2D v0 = (B.substract(A));
        Vector2D v1 = (C.substract(A));
        Vector2D v2 = (point.substract(A));

        double dot00 = v0.dot(v0);
        double dot01 = v0.dot(v1);
        double dot02 = v0.dot(v2);
        double dot11 = v1.dot(v1);
        double dot12 = v1.dot(v2);

        double q = 1.0 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * q;
        double v = (dot00 * dot12 - dot01 * dot02) * q;

        return (u > 0) && (v > 0) && (u + v < 1);
    }

    public static boolean PointInsideThreeSidedInfinitePyramid(
            Vector3D point,
            Vector3D pyramidApex,
            Vector3D pyramidBase0,
            Vector3D pyramidBase1,
            Vector3D pyramidBase2)
    {
        Vector3D v0 = pyramidBase0.substract(pyramidApex);
        Vector3D v1 = pyramidBase1.substract(pyramidApex);
        Vector3D v2 = pyramidBase2.substract(pyramidApex);

        //
        // FaceCulling normals
        //
        Vector3D n0 = v1.cross(v0);
        Vector3D n1 = v2.cross(v1);
        Vector3D n2 = v0.cross(v2);

        Vector3D planeToPoint = point.substract(pyramidApex);

        return ((planeToPoint.dot(n0) < 0) && (planeToPoint.dot(n1) < 0) && (planeToPoint.dot(n2) < 0));
    }
}
