package com.atlas.atlas.earth;


public class EarthViewOptions {
    private static boolean fullLightning = false;

    private static boolean friendModeEnabled = false;
    private static boolean postModeEnabled   = false;
    private static boolean eventModeEnabled  = false;


    public static void disableFullLightning(){
        fullLightning = false;
    }

    public static void enableFullLightning(){
        fullLightning = true;
    }

    public static boolean isFullLightning() {
        return fullLightning;
    }

    public static boolean isFriendModeEnabled() {
        return friendModeEnabled;
    }

    public static void setFriendModeEnabled(boolean friendModeEnabled) {
        EarthViewOptions.friendModeEnabled = friendModeEnabled;
        //// TODO: 6/15/2017 set friends on EarthView
    }

    public static boolean isPostModeEnabled() {
        return postModeEnabled;
    }

    public static void setPostModeEnabled(boolean postModeEnabled) {
        EarthViewOptions.postModeEnabled = postModeEnabled;
        //// TODO: 6/15/2017 set posts on EarthView
    }

    public static boolean isEventModeEnabled() {
        return eventModeEnabled;
    }

    public static void setEventModeEnabled(boolean eventModeEnabled) {
        EarthViewOptions.eventModeEnabled = eventModeEnabled;
        //// TODO: 6/15/2017 set events on EarthView
    }
}
