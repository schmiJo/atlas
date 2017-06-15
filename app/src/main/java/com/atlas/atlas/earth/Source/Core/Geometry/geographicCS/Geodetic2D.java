package com.atlas.atlas.earth.Source.Core.Geometry.geographicCS;


public class Geodetic2D {

    private double longitude;
    private double latitude;


    public Geodetic2D(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Geodetic2D(Geodetic3D geodetic3D) {
        longitude = geodetic3D.getLongitude();
        latitude = geodetic3D.getLatitude();
    }


    public boolean EqualsEpsilon(Geodetic2D other, double epsilon) {
        return (Math.abs(longitude - other.longitude) <= epsilon) &&
                (Math.abs(latitude - other.latitude) <= epsilon);
    }
    public boolean Equals(Geodetic2D other) {
        return longitude == other.longitude && latitude == other.getLatitude();
    }


    /**
     * Getter & Setter
     */
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
}

