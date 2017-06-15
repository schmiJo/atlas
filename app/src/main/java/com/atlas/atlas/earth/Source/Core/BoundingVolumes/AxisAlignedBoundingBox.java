package com.atlas.atlas.earth.Source.Core.BoundingVolumes;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;

import java.util.List;


public class AxisAlignedBoundingBox {

    private Vector3D minimum;
    private Vector3D maximum;

    public AxisAlignedBoundingBox(List<Vector3D> positions) {
        if (positions == null) {
            throw new IllegalArgumentException("Positions can't be null");
        }

        double minimumX = Double.MAX_VALUE;
        double minimumY = Double.MAX_VALUE;
        double minimumZ = Double.MAX_VALUE;

        double maximumX = -Double.MAX_VALUE;
        double maximumY = -Double.MAX_VALUE;
        double maximumZ = -Double.MAX_VALUE;

        for (Vector3D position : positions) {
            if (position.x < minimumX) {
                minimumX = position.x;
            }

            if (position.x > maximumX) {
                maximumX = position.x;
            }

            if (position.y < minimumY) {
                minimumY = position.y;
            }

            if (position.y > maximumY) {
                maximumY = position.y;
            }

            if (position.z < minimumZ) {
                minimumZ = position.z;
            }

            if (position.z > maximumZ) {
                maximumZ = position.z;
            }
        }

        Vector3D minimum = new Vector3D(minimumX, minimumY, minimumZ);
        Vector3D maximum = new Vector3D(maximumX, maximumY, maximumZ);

        if (minimum.isBigger(maximum)) {
            Vector3D temp = maximum;
            minimum = maximum;
            maximum = temp;
        }

        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Vector3D getMinimum() {
        return minimum;
    }

    public Vector3D getMaximum() {
        return maximum;
    }
    public Vector3D getCenter(){
        return (minimum.add(maximum)).multiplyComponents(0.5);
    }
}
