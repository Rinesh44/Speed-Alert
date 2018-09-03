package com.example.darknight.tootlespeedalert.util;

import android.support.annotation.Nullable;

public class Constants {

    // API
    public static final String API_KEY = "api_key";

    // BookingSharedPreferences
    public static final String SHARED_BOOKING_STATE = "shared_booking_state";
    public static final String SHARED_PICKUP_LAT = "shared_pickup_lat";
    public static final String SHARED_PICKUP_LON = "shared_pickup_lon";
    public static final String SHARED_PICKUP_LOC = "shared_pickup_loc";
    public static final String SHARED_VEHICLE_ID = "shared_vehicle_id";
    public static final String SHARED_DROP_LAT = "shared_drop_lat";
    public static final String SHARED_DROP_LON = "shared_drop_lon";
    public static final String SHARED_DROP_LOC = "shared_drop_loc";
    public static final String SHARED_DRIVER_NOTE = "shared_driver_note";
    public static final String DRIVER_GENDER = "driver_gender";
    public static final String SHARED_LOCATION_ROUTE = "shared_location_route";
    public static final String SHARED_ESTIMATED_COST = "shared_estimated_cost";
    public static final String SHARED_ESTIMATED_DISTANCE = "shared_estimated_distance";
    public static final String SHARED_ESTIMATED_TIME = "shared_estimated_time";
    public static final String SHARED_CURRENT_BOOKING_ID = "shared_current_booking_id";
    public static final String SHARED_CLIENT_PAYMENT_PREF = "shared_client_payment_pref";
    public static final String SHARED_CLIENT_GENDER_PREF = "shared_client_gender_pref";
    //PricesSharedPreferences
    public static final String SHARED_DISTANCE_COST = "shared_distance_cost";
    public static final String SHARED_FIXED_FARE = "shared_fixed_fare";
    public static final String SHARED_FREE_DISTANCE = "shared_free_distance";
    public static final String SHARED_HIKE = "shared_hike";
    public static final String SHARED_MINUTE_COST = "shared_minute_cost";

    public static final String SERVER_CLIENT_HAS_A_BOOKING = "https://tootle.today/api/clients/hasBooking/";


    //Wallet SharedPreferences
    public static final String SHARED_WALLET_BALANCE = "shared_wallet_balance";


    //Firebase MAIN_URL
    public static final String SERVER = AppUtils.getBaseUrl();
    public static final String FIREBASE_ROOT = AppUtils.getFirebaseDatabaseUrl();
    public static final String FIREBASE_APP_VERSION = FIREBASE_ROOT + "app_version/";
    public static final String FIREBASE_APP_MESSAGE = FIREBASE_ROOT + "message/client";
    public static final String FIREBASE_APP_PROMO = FIREBASE_ROOT + "client_promo/";
    public static final String FIREBASE_CLIENT = FIREBASE_ROOT + "clients";
    public static final String FIREBASE_PRICES = FIREBASE_ROOT + "prices";
    public static final String FIREBASE_TRANSACTION = FIREBASE_ROOT + "transactions";
    public static final String FIREBASE_BOOKING = FIREBASE_ROOT + "bookings/Bike";
    public static final String FIREBASE_DRIVER = FIREBASE_ROOT + "drivers";
    public static final String FIREBASE_DRIVER_LOCATION = FIREBASE_ROOT + "driver_locations";
    public static final String SERVER_LOGIN_USER = SERVER + "api/users/checkCredentials";

    //Font name
    public static final String FONT_TRANSACTION = "digital.ttf";

    public static final String HANDSHAKE_TOKEN_CODE = "handshake_token_code";

    //SharedPreferences
    public static final String CURRENT_USER_ID = "current_user_id";
    public static final String CURRENT_USER_BOOKING = "current_user_booking";
    public static final String LOGGED_IN = "logged_in";

    //Booking table


    //Client table
    public static final String CLIENT_SQL_ID = "client_sql_id";
    public static final String CLIENT_NAME = "client_name";
    public static final String CLIENT_EMAIL = "client_email";
    public static final String CLIENT_PHONE = "client_phone";
    public static final String CLIENT_PASSWORD = "client_password";
    public static final String AUTH_TOKEN = "auth_token";
    public static final String CLIENT_STATUS = "client_status";
    public static final String CLIENT_PHOTO = "client_photo";
    public static final String CLIENT_GENDER = "gender";
    public static String CLIENT_PREFERRED_GENDER = "preferred_gender";
    public static String CLIENT_PAYMENT_PREF = "preferred_payment_method_id";


    public static final String USER_SQL_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_STATUS = "user_status";
    public static final String USER_IP_ACCESS = "user_ip_access";
    public static final String OFFICE_IP = "Three60";
    public static final String USER_LOGGED_IN = "user_logged_in";


    // Drivers table
//    public static final String DRIVER_ID = "driver_id";
//    public static final String DRIVER_NAME = "driver_name";
//    public static final String DRIVER_EMAIL = "driver_email";
//    public static final String DRIVER_PASSWORD = "driver_password";
//    public static final String DRIVER_PHONE = "driver_phone";
//    public static final String DRIVER_LISENSE = "driver_lisense";
//    public static final String DRIVER_CONTACT = "driver_contact";
//    public static final String DRIVER_PHOTO = "driver_photo";
//    public static final String DRIVER_STATUS = "driver_status";
//
//    //Booking status
//    public static final String BOOKING_PENDING = "Pending";
//    public static final String BOOKING_ACCEPTED = "Accepted";
//    public static final String BOOKING_ONWAY = "Onway";
//    public static final String BOOKING_PICKEDUP = "Pickedup";
//    public static final String BOOKING_DROPPED = "Dropped";

    public static final String BOOKED_DATE = "booked_date";
    public static final String BOOKED_SOURCE = "booked_source";
    public static final String BOOKED_TIME = "booked_time";
    public static final String DROP_TO_LOCATION = "drop_to_location";
    public static final String PICKUP_FROM_LOCATION = "pickup_from_location";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_LOCATION = " client_location";
    public static final String DETAILS = " booking_details";
    public static final String DRIVER_ID = "driver_id";
    public static final String DROP_TO_LATITUDE = "drop_to_latitude";
    public static final String DROP_TO_LONGITUDE = "drop_to_longitude";
    public static final String ESTIMATED_COST = "estimated_cost";
    public static final String MAX_SEAT = "max_seats";
    public static final String PICKUP_FROM_LATITUDE = "pickup_from_latitude";
    public static final String PICKUP_FROM_LONGITUDE = "pickup_from_longitude";
    public static final String REMARK = "remark";
    public static final String STATUS = "status";
    public static final String VEHICLE_ID = "vehicle_id";
    public static final String ESTIMATED_DISTANCE = "estimated_distance";
    public static final String ESTIMATED_TIME = "estimated_time";

    //Booking status
    public static final String BOOKING_PENDING = "Pending";
    public static final String BOOKING_ACCEPTED = "Accepted";
    public static final String BOOKING_ONWAY = "Onway";
    public static final String BOOKING_PICKEDUP = "Pickedup";
    public static final String BOOKING_DROPPED = "Dropped";

    //Bundle
    public static final String BUNDLE_BOOKING_ID = "bundle_booking_id";
    public static final String BUNDLE_DRIVER_ID = "bundle_client_id";
    public static final String BUNDLE_CLIENT_PICKUP_FROM_LAT = "bundle_client_pickup_from_lat";
    public static final String BUNDLE_CLIENT_PICKUP_FROM_LON = "bundle_client_pickup_from_lon";
    public static final String BUNDLE_CLIENT_PICKUP_LOCATION = "bundle_client_pickup_location";
    public static final String BUNDLE_ESTIMATED_COST = "bundle_estimated_cost";
    public static final String BUNDLE_ESTIMATED_DISTANCE = "bundle_estimated_distance";
    public static final String BUNDLE_ESTIMATED_TIME = "bundle_estimated_time";
    public static final String BUNDLE_REMARK = "bundle_remark";

    @Nullable
    public static final String BUNDLE_CLIENT_DROP_LOCATION = "bundle_client_drop_location";

    @Nullable
    public static final String BUNDLE_CLIENT_DROP_TO_LAT = "bundle_client_drop_to_lat";
    @Nullable
    public static final String BUNDLE_CLIENT_DROP_TO_LON = "bundle_client_drop_to_lon";
    public static final String BUNDLE_CLIENT_BOOKING_TIME = "client_booking_time";
    public static final String GET_AUTH_CODE = SERVER + "api/clients/setNewAuthCode";
    public static final String POST_NEW_PASSWORD = SERVER + "api/clients/forgotPassword";
    public static final String POST_NEW_CHANGED_PASSWORD = SERVER + "api/clients/changePassword";
    public static final String POST_NEW_CHANGED_EMAIL = SERVER + "api/clients/update";
    public static final String FIREBASE_PROMO_MESSAGE = FIREBASE_ROOT + "message/client/";
    public static final String MESSAGE_ID = "MESSAGE_ID";

}

