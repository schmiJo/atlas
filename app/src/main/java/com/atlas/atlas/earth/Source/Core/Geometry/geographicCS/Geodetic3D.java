package com.atlas.atlas.earth.Source.Core.Geometry.geographicCS;



public class Geodetic3D {

    private double longitude;
    private double latitude;
    private double height;


    public Geodetic3D(double longitude, double latitude, double height) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.height = height;
    }
    public Geodetic3D(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        height = 0;
    }
    public Geodetic3D(Geodetic2D geodetic2D) {
        longitude = geodetic2D.getLongitude();
        latitude = geodetic2D.getLatitude();
        height = 0;
    }
    public Geodetic3D(Geodetic2D geodetic2D, double height) {
        longitude = geodetic2D.getLongitude();
        latitude = geodetic2D.getLatitude();
        this.height = height;
    }


    public boolean Equals(Geodetic3D other) {
        return longitude == other.longitude && latitude == other.latitude && height == other.height;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getHeight() {
        return height;
    }
}
