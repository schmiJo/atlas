package com.atlas.atlas.earth;


public class EarthViewOptions {
    private static boolean fullLightning = false;

    public static void disableFullLightning(){
        fullLightning = false;
    }

    public static void enableFullLightning(){
        fullLightning = true;
    }

    public static boolean isFullLightning() {
        return fullLightning;
    }
}
