package com.atlas.atlas.earth.Source.Core.BoundingVolumes;

import com.atlas.atlas.earth.Source.Core.CollisionDetection.IntersectionTests;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlas.earth.Source.Core.Geometry.geographicCS.Ellipsoid;

import java.util.ArrayList;
import java.util.List;


public class EllipsoidTangentPlane  {

    private Vector3D origin;
    private Vector3D normal;
    private double   d;
    private Vector3D xAxis;
    private Vector3D yAxis;

    public EllipsoidTangentPlane(Ellipsoid ellipsoid, List<Vector3D> positions)
    {
        if (ellipsoid == null)
        {
            throw new IllegalArgumentException("Ellipsoid can't be null");
        }

        if (positions == null)
        {
            throw new IllegalArgumentException("Positions can't be null");
        }


        AxisAlignedBoundingBox box = new AxisAlignedBoundingBox(positions);

        origin = ellipsoid.scaleToGeodeticSurface(box.getCenter());
        normal = ellipsoid.GeodeticSurfaceNormal(origin);
        d = -origin.dot(origin);
        yAxis = origin.cross(origin.mostOrthogonalAxis()).normalize();
        xAxis = yAxis.cross(origin).normalize();
    }

    public List<Vector2D> ComputePositionsOnPlane(List<Vector3D> positions)
    {
        if (positions == null)
        {
            throw new IllegalArgumentException("Positions can't be Null");
        }

        List<Vector2D> positionsOnPlane = new ArrayList<Vector2D>(positions.size());

        for (Vector3D position : positions)
        {
            Vector3D intersectionPoint = IntersectionTests.TryRayPlane(new Vector3D(0,0,0), position.normalize(), normal, d);

            if (intersectionPoint.x != Float.NaN)
            {
                Vector3D v = intersectionPoint.substract(origin);
                positionsOnPlane.add(new Vector2D(xAxis.dot(v), yAxis.dot(v)));
            }
            else
            {
                // Ray does not intersect plane
            }
        }

        return positionsOnPlane;
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public double getD() {
        return d;
    }

    public Vector3D getxAxis() {
        return xAxis;
    }

    public Vector3D getyAxis() {
        return yAxis;
    }
}
