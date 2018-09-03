package com.example.darknight.tootlespeedalert.Model;

/**
 * Created by darknight on 4/2/18.
 */

public class GoogleResponse {
    private String route;
    private int distance;
    private int time;

    public GoogleResponse(String route, int distance, int time) {
        this.route = route;
        this.distance = distance;
        this.time = time;
    }

    public String getRoute() {
        return route;
    }

    public int getDistance() {
        return distance;
    }

    public int getTime() {
        return time;
    }
}
