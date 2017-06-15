package com.atlas.atlas.earth.Source.Renderer;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Light {
    private Vector3F position;
    private Vector3F color;

    private final float distanceToEarth = 100;
    private float angle = 0;
    private float tilt = -20;

    public Light(Vector3F color) {
        this.color = color;
        this.position = new Vector3F(distanceToEarth, distanceToEarth, 0);
        calculateCoordinates();
    }

    public void increaseAngle(float angle) {
        this.angle += angle;
        calculateCoordinates();
    }
    public void increasePosition(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    private void calculateCoordinates() {
        position.x = (float) (distanceToEarth * Math.cos(angle));
        position.z = (float) (distanceToEarth * Math.sin(angle));
        position.y = (float) (distanceToEarth * Math.sin(tilt));
    }

    float incrementPerHour = (float) (Math.PI * 2) / 24;
    float incrementPerSecond = (float) (Math.PI * 2) / 60;

    public void calculateAngleByTime() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.HOUR_OF_DAY);

        angle = incrementPerSecond * calendar.get(Calendar.SECOND);
        //angle = (calendar.get(Calendar.MILLISECOND) * incrementPerSecond/1000);
        calculateCoordinates();
    }

    /**
     * Getter & Setter
     */
    public Vector3F getColor() {
        return color;
    }

    public void setColor(Vector3F color) {
        this.color = color;
    }

    public Vector3F getPosition() {
        return position;
    }

    public void setPosition(Vector3F position) {
        this.position = position;
    }
}
