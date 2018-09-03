package com.example.darknight.tootlespeedalert.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.darknight.tootlespeedalert.Model.GoogleResponse;
import com.example.darknight.tootlespeedalert.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    private static final int MAP_ZOOM_FACTOR = 120;
    private static final LatLng KATHMANDU_CENTER = new LatLng(27.708348, 85.315489);
    private static final int RADIUS_BOUNDS = 11000;


    public static String makeURL(LatLng mSource, LatLng mDestination) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");
        urlString.append(mSource.latitude);
        urlString.append(",");
        urlString.append(mSource.longitude);
        urlString.append("&destination=");
        urlString.append(mDestination.latitude);
        urlString.append(",");
        urlString.append(mDestination.longitude);
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append(R.string.google_api_key);
        return urlString.toString();
    }


    /**
     * Client side username and password checks for basic validation
     *
     * @param phone    Phone number entered by client
     * @param password Password entered by client
     * @return True if basic validity are met
     */
    public static boolean checkInputValidity(String phone, String password) {
        return !(phone.length() != 10 || password.length() <= 6);
    }

    public static LatLngBounds toBounds(LatLng center) {
        LatLng southwest = SphericalUtil.computeOffset(center, MAP_ZOOM_FACTOR * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, MAP_ZOOM_FACTOR * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    public static Map<String, String> loginPostParameters(String username, String password, String phone_token) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", username);
        params.put("password", password);
        params.put("phone_token", phone_token);
        return params;
    }

    public static Map<String, String> userLoginPostParameters(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        return params;
    }

    public static Map<String, String> userTokenParameters(Integer client_id, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", String.valueOf(client_id));
        params.put("phone_token", token);
        return params;
    }

    public static Map<String, String> topupParameters(Integer client_id, String hash) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", String.valueOf(client_id));
        params.put("hash", hash);
        return params;
    }

    public static Map<String, String> cancelPendingBookingParamerters(String booking_id) {
        Map<String, String> params = new HashMap<>();
        params.put("booking_id", booking_id);
        params.put("remark", " ");
        return params;
    }

    public static Map<String, String> userAuthPhone(String name, String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("phone", phone);
        return params;
    }

    public static Map<String, String> userRegister(final int id, final String refer_code_used, final String Password, final String Email, final String auth_token, final String gender) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", String.valueOf(id));
        params.put("referred_by", refer_code_used);
        params.put("password", Password);
        params.put("email", Email);
        params.put("auth_token", auth_token);
        params.put("gender", gender);
        Log.e("params->", String.valueOf(params));

        return params;
    }

    public static Map<String, String> getWalletAmountPostParameters(Integer client_sql_id) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", String.valueOf(client_sql_id));
        return params;
    }

    public static JSONObject bookingPostParameters(int client_sql_id, String preferred_gender, String preferred_payment_method_id,
                                                   String source_lat, String source_lon, String source_loc, String destination_lat, String destination_lon, String destination_loc, Location current_location, int calculated_fare, int estimated_time, int estimated_distance, String note_driver) {
        JSONObject params = new JSONObject();

        try {
            if (destination_loc == null) {
            } else {
                params.put(Constants.DROP_TO_LOCATION, destination_loc);
                params.put(Constants.DROP_TO_LATITUDE, destination_lat);
                params.put(Constants.DROP_TO_LONGITUDE, destination_lon);
                params.put(Constants.ESTIMATED_DISTANCE, estimated_distance);
                params.put(Constants.ESTIMATED_COST, calculated_fare);
                params.put(Constants.ESTIMATED_TIME, estimated_time);

            }

            params.put(Constants.BOOKED_SOURCE, "Client Android App");
            params.put(Constants.PICKUP_FROM_LOCATION, source_loc);
            params.put(Constants.CLIENT_ID, client_sql_id);
            params.put(Constants.CLIENT_LOCATION, prettyprintLocation(current_location));
            params.put(Constants.PICKUP_FROM_LATITUDE, source_lat);
            params.put(Constants.PICKUP_FROM_LONGITUDE, source_lon);
            params.put(Constants.REMARK, note_driver);
            params.put(Constants.STATUS, "Pending");
            params.put(Constants.CLIENT_PREFERRED_GENDER, preferred_gender);
            params.put(Constants.CLIENT_PAYMENT_PREF,preferred_payment_method_id);

            params.put("vehicle_type", "Bike");
            Log.e("params->", String.valueOf(params));
        } catch (Exception e) {

        }
        return params;
    }


    public static JSONObject saveTransactionPostParameters(String booking_id, Integer client_id, Integer client_star, String client_comment) {
        JSONObject params = new JSONObject();
        try {
            params.put("booking_id", booking_id);
            params.put("client_id", client_id);
            params.put("client_star", client_star);
            params.put("client_comment", client_comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private static String prettyprintLocation(Location location) {
        if (location == null) {
            return "";
        }
        return ("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
    }

    public static boolean locationNotReadable(String name) {
        return name == null || name.equals("") || !name.matches(".*[a-zA-Z]+.*") || name.contains("°");
    }

    public static GoogleResponse parseResponseforDistanceTimePath(String response) {
        final JSONObject json;
        try {
            json = new JSONObject(response);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONArray legsArray = routes.getJSONArray("legs");
            int distance = legsArray.getJSONObject(0).getJSONObject("distance").getInt("value");
            int time = legsArray.getJSONObject(0).getJSONObject("duration").getInt("value");
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String route = overviewPolylines.getString("points");
            return new GoogleResponse(route, distance, time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int calculateFare(Context context) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        double fixed_fare =
                Double.parseDouble(mSharedPreferences.getString(Constants.SHARED_FIXED_FARE,
                        "0"));
        double hike =
                Double.parseDouble(mSharedPreferences.getString(Constants.SHARED_HIKE,
                        "0"));
        double minute_cost =
                Double.parseDouble(mSharedPreferences.getString(Constants.SHARED_MINUTE_COST,
                        "0"));
        double free_distance =
                Double.parseDouble(mSharedPreferences.getString(Constants.SHARED_FREE_DISTANCE,
                        "0"));
        double distance_cost =
                Double.parseDouble(mSharedPreferences.getString(Constants.SHARED_DISTANCE_COST,
                        "0"));
        double mTotalDistance =
                mSharedPreferences.getInt(Constants.SHARED_ESTIMATED_DISTANCE, 0);
        double mTotalTime =
                mSharedPreferences.getInt(Constants.SHARED_ESTIMATED_TIME, 0);
        mTotalDistance = mTotalDistance - free_distance;

        if (mTotalDistance < 0) {
            mTotalDistance = 0;
        }

        double total_distance_cost = ((mTotalDistance / 1000) * distance_cost);
        double duration_cost = (Math.ceil((mTotalTime / 60) * minute_cost));
        return (int) Math.ceil(fixed_fare +
                (total_distance_cost + (duration_cost * hike)));
    }

    public static boolean validLocationBoundry(LatLng mLatLng) {
        Log.e("latlng boundry ->", String.valueOf(mLatLng));
        return getKathmanduLatLngBounds().contains(mLatLng);
    }

    private static LatLngBounds getKathmanduLatLngBounds() {
        LatLng southwest = SphericalUtil.computeOffset(KATHMANDU_CENTER, RADIUS_BOUNDS * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(KATHMANDU_CENTER, RADIUS_BOUNDS * Math.sqrt(2.0), 45);
        Log.e("southbounds ->", String.valueOf(southwest));
        Log.e("northbounds ->", String.valueOf(northeast));
        return new LatLngBounds(southwest, northeast);
    }
}
