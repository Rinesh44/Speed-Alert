package com.example.darknight.tootlespeedalert.Model;

/**
 * Created by darknight on 4/2/18.
 */

import com.google.firebase.database.ServerValue;


public class DriverLocation {
    private Double lat;
    private Double lon;

    private Object timestamp;


    public DriverLocation() {

    }

    public DriverLocation(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public Object getTimestamp() {
        if (timestamp instanceof Long) {
            return timestamp;
        }
        return null;
    }

    @Override
    public String toString() {
        return "DriverLocation{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timestamp=" + timestamp +
                '}';
    }
}
