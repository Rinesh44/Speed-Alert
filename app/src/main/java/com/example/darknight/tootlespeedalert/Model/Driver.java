package com.example.darknight.tootlespeedalert.Model;

/**
 * Created by darknight on 4/2/18.
 */

import android.support.annotation.Nullable;

import io.realm.RealmObject;

public class Driver extends RealmObject{

    private String name;
    private String email;
    private String password;
    private String phone;
    private String license_no;
    private String photo;
    private String vehicle_no;
    private String vehicle_model;
    private String vehiche_seats;
    private String vehicle_description;
    private String device_code;
    private String status;
    private String contact;
    private Double lon;
    private Double lat;
    private String speed;
    private String bearing;

    private Long timestamp;


    public Driver() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicense_no() {
        return license_no;
    }

    public String getPhoto() {
        return photo;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getVehicle_seats() {
        return vehiche_seats;
    }

    public String getVehicle_description() {
        return vehicle_description;
    }

    public String getDevice_code() {
        return device_code;
    }

    public String getStatus() {
        return status;
    }

    public String getContact() {
        return contact;
    }

    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }

    public String getSpeed() {
        return speed;
    }

    public String getBearing() {
        return bearing;
    }

    @Nullable
    public Long getTimestamp() {
        if (timestamp == null) {
            return 0L;
        }
        return timestamp;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", license_no='" + license_no + '\'' +
                ", photo='" + photo + '\'' +
                ", vehicle_no='" + vehicle_no + '\'' +
                ", vehicle_model='" + vehicle_model + '\'' +
                ", vehicle_seats='" + vehiche_seats + '\'' +
                ", vehicle_description='" + vehicle_description + '\'' +
                ", device_code='" + device_code + '\'' +
                ", status='" + status + '\'' +
                ", contact='" + contact + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", speed=" + speed +
                ", bearing=" + bearing +
                '}';
    }

}