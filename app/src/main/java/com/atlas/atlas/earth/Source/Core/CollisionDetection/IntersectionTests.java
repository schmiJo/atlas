package com.atlas.atlas.earth.Source.Core.CollisionDetection;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;


public class IntersectionTests {

    public static Vector3D TryRayPlane(
            Vector3D rayOrigin,
            Vector3D rayDirection,
            Vector3D planeNormal,
            double planeD) {
        Vector3D intersectionPoint;
        double denominator = planeNormal.dot(rayDirection);

        if (Math.abs(denominator) < 0.00000000000000000001) {
            //
            // Ray is parallel to plane.  The ray may be in the polygon's plane.
            //
            return new Vector3D(Float.NaN, Float.NaN, Float.NaN);
        }
        double t = (-planeD - planeNormal.dot(rayOrigin)) / denominator;

        if (t < 0) {
            return new Vector3D(Float.NaN, Float.NaN, Float.NaN);
        }

        intersectionPoint = rayOrigin.add(rayDirection.multiplyComponents(t));
        return intersectionPoint;
    }
}
