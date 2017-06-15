package com.atlas.atlas.earth.Source.Core.Scene.Camera;


import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.Geometry.CSConverter;
import com.atlas.atlas.earth.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlas.earth.Source.Core.Geometry.geographicCS.Geodetic2D;


public class CameraLookAtUtility {


    public static void lookAtGeodeticCoordinate(Camera camera, Ellipsoid globeShape, Geodetic2D coordinate, int height){

        Vector3F pointInWorldSpace = globeShape.ToVector3D(CSConverter.toRadians(coordinate)).toVector3F();
        Vector3F cameraPos = pointInWorldSpace.normalize(height);
        lookFromTo(camera,cameraPos ,new Vector3F(0,0,0));

    }
    public static void lookAt(Camera camera, Vector3F target) {
        lookFromTo(camera, camera.getPosition(), target);
    }
    public static void lookFromTargetVectorToOrigin(Camera camera, Vector3F target, int distanceToOrigin){

        Vector3F cameraPos = target.normalize(distanceToOrigin);


    }
    public static void lookFromTo(Camera camera, Vector3F cameraPosition, Vector3F target) {
        camera.setPosition(cameraPosition);

        /**
         * Development\GeoGebra Functions\Pitch and Yaw Calculation.obb
         */


        Vector3F EyeToTarget = target.substract(camera.getPosition());
        Vector3F EyeToTargetInxzPlane = new Vector3F(EyeToTarget.x, 0, EyeToTarget.z);

        float pitch = Vector3F.angleBetween(EyeToTarget, EyeToTargetInxzPlane);
        float yaw = -Vector3F.angleBetween(EyeToTargetInxzPlane, new Vector3F(0, 0, -1));

        camera.setPitch(pitch);
        camera.setYaw(yaw);

    }


}
