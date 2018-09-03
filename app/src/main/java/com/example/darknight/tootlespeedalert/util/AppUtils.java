package com.example.darknight.tootlespeedalert.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.darknight.tootlespeedalert.BuildConfig;


public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();

    public static void showSnackBar(View view, String message, Context context) {

        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

    }


    public static String getDriverId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.CLIENT_SQL_ID, "0");
    }

    public static void showLog(String tag, String message) {
        if (tag != null && message != null) {
            Log.v("APP_FLOW--->" + tag, message);
        }

    }

    public static boolean checkIfFemale(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String gender = prefs.getString(Constants.DRIVER_GENDER, "0");
        if (gender.equals("Female")) {
            return true;
        } else {
            return false;
        }
    }


    public static int getAppVersionCode(Context context) {

        int versionCode = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            /**
             * Get version Name
             String version = packageInfo.versionName;
             */
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;

    }


    public static String getFirebaseDatabaseUrl() {

        if (BuildConfig.PLAYSTORE_MODE) {
            return "https://clientcabio.firebaseio.com/";
        } else {
            return "https://check-12cc0.firebaseio.com/";
        }

    }

    public static void removeAllSavedPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public static String getBaseUrl() {

        if (BuildConfig.PLAYSTORE_MODE) {
            return "https://tootle.today/";
        } else {
            return "http://demo.tootle.today/";
        }

    }

    public static double getEpochTimeBeforeOneHour() {

        long epochTime = System.currentTimeMillis();
        AppUtils.showLog(TAG, "currentEpochTime: " + epochTime);
        long oneHour = 3600000; // TODO 3600*1000 millisecond and for 100 hrs 3600000*100 hours
        AppUtils.showLog(TAG, "oneHour: " + epochTime);
        long beforeOneHour = epochTime - oneHour;
        AppUtils.showLog(TAG, "beforeOneHour: " + beforeOneHour);
        return beforeOneHour;
    }

    public static double getEpochTimeHalfAnHour() {

        long epochTime = System.currentTimeMillis();
        AppUtils.showLog(TAG, "currentEpochTime: " + epochTime);
        long oneHour = 3600000 / 2; // TODO 3600*1000 millisecond and for 100 hrs 3600000*100 hours
        AppUtils.showLog(TAG, "oneHour: " + epochTime);
        long beforeOneHour = epochTime - oneHour;
        AppUtils.showLog(TAG, "beforeOneHour: " + beforeOneHour);
        return beforeOneHour;
    }

    public static double getEpochTimeAnHour() {

        long epochTime = System.currentTimeMillis();
        AppUtils.showLog(TAG, "currentEpochTime: " + epochTime);
        long oneHour = 3600000; // TODO 3600*1000 millisecond and for 100 hrs 3600000*100 hours
        AppUtils.showLog(TAG, "oneHour: " + epochTime);
        long beforeOneHour = epochTime - oneHour;
        AppUtils.showLog(TAG, "beforeOneHour: " + beforeOneHour);
        return beforeOneHour;
    }

    public static double getEpochTime24Hours() {

        long epochTime = System.currentTimeMillis();
        AppUtils.showLog(TAG, "currentEpochTime: " + epochTime);
        long oneHour = 3600000 * 24; // TODO 3600*1000 millisecond and for 100 hrs 3600000*100 hours
        long before24Hour = epochTime - oneHour;
        return before24Hour;
    }

    public static double getEpochTimeTenMins() {

        long epochTime = System.currentTimeMillis();
        AppUtils.showLog(TAG, "currentEpochTime: " + epochTime);
        long tenMins = 600000; // TODO 600 * 1000 millisecond and for 100 hrs 3600000*100 hours
        AppUtils.showLog(TAG, "tenMins: " + epochTime);
        long beforeTenMins = epochTime - tenMins;
        AppUtils.showLog(TAG, "beforeTenMins: " + beforeTenMins);
        return beforeTenMins;
    }
}
