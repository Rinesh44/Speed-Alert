package com.example.darknight.tootlespeedalert.Activities.MainActivity;


import android.media.MediaPlayer;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.darknight.tootlespeedalert.Activities.BaseActivity.BaseActivity;
import com.example.darknight.tootlespeedalert.Application.App;
import com.example.darknight.tootlespeedalert.Model.Driver;
import com.example.darknight.tootlespeedalert.R;
import com.example.darknight.tootlespeedalert.util.AppUtils;
import com.example.darknight.tootlespeedalert.util.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends BaseActivity implements
        GoogleMap.OnMapClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final float MAP_ZOOM = 14f;
    private static final float MAP_BEARING = 0f;
    private static final float MAP_TILT = 0f;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    MediaPlayer mp;
    private Realm realm = null;

   /* public static LongSparseArray<Marker> busyDriverMarkerList = new LongSparseArray<>();
    private Realm realm = null;*/

    public static ArrayMap<String, Driver> busyDriverList = new ArrayMap();
    public static ArrayList<Driver> speedyDrivers = new ArrayList<>();
    public TextView name, email, phone, liscenseNo, vehicleNo, speed, timestamp;
    private NestedScrollView bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    protected FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReferenceBusyDrivers;
    private ChildEventListener mDatabaseReferenceBusyDriversListener;
    private LongSparseArray<Marker> mBusyDriverMarkerList = new LongSparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        initGoogleApiClient();
        inflateLayouts();
        initializeViews();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initialize media player
        createMediaPlayer();

        firebaseListenerForBusyDrivers();
    }


    public void firebaseListenerForBusyDrivers() {
        mDatabaseReferenceBusyDrivers = mFirebaseDatabase.getReferenceFromUrl(Constants.FIREBASE_DRIVER);
        Query queryRef = mDatabaseReferenceBusyDrivers.orderByChild("status").equalTo("Busy");
        mDatabaseReferenceBusyDriversListener = queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String dataSnapshotKey = dataSnapshot.getKey();
                try {
                    Driver mDriver = dataSnapshot.getValue(Driver.class);
                    if (mDriver.getTimestamp() != null && mDriver.getTimestamp() >= AppUtils.getEpochTime24Hours()) {
                        AppUtils.showLog("status for busy drivers", mDriver.getName());
                        int markerType = 0;

                        String speedInkmPerHr = String.valueOf(Math.round((Double.valueOf(mDriver.getSpeed())) * 3.6));

                        AppUtils.showLog("driver's speed", speedInkmPerHr);

                        if (Double.valueOf(speedInkmPerHr) > 40)
                            markerType = R.drawable.busy_yellow;
                        if (Double.valueOf(speedInkmPerHr) > 80) {
                            markerType = R.drawable.busy_red;
                            speedyDrivers.add(mDriver);
                            addDataToDatabase(mDriver);
                        }

                        if (Double.valueOf(speedInkmPerHr) > 90) {
                            markerType = R.drawable.busy_red;
                            playAlertAudio();
                            speedyDrivers.add(mDriver);
                            addDataToDatabase(mDriver);
                        }
                        if (Double.valueOf(speedInkmPerHr) < 30) markerType = R.drawable.busy;

                        Marker marker = addDriverMarkerToMap(
                                new LatLng(mDriver.getLat(), mDriver.getLon()),
                                mDriver.getName() + " (" + speedInkmPerHr + "km/hr" + ") ",
                                relativeTime(mDriver.getTimestamp()),
                                BitmapDescriptorFactory.fromResource(markerType));

                        if (speedInkmPerHr.equals("0")) marker.remove();

                        mBusyDriverMarkerList.put(Long.parseLong(dataSnapshotKey), marker);
                        busyDriverList.put((marker.getId()), mDriver);

                    }
                } catch (
                        Exception e) {
                    Log.e(TAG, "error:" + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                onChildRemoved(dataSnapshot);
                onChildAdded(dataSnapshot, "");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String dataSnapshotKey = dataSnapshot.getKey();
                try {
                    Marker m = mBusyDriverMarkerList.get(Long.parseLong(dataSnapshotKey));
                    busyDriverList.remove(m.getId());
                    m.remove();
                    AppUtils.showLog(TAG, "busy driver marker removed:::");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addDataToDatabase(final Driver driver) {
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmList<Driver> driverRealmList = new RealmList<>();
                    driverRealmList.add(driver);
                    realm.insertOrUpdate(driverRealmList);
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    public String relativeTime(Long timestamp) {
        if (timestamp != null) {
            Long tsLong = System.currentTimeMillis();
            CharSequence rTime = DateUtils.getRelativeTimeSpanString(
                    timestamp,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS);
            return (rTime.toString());
        }
        return "No time info";
    }

    public void createMediaPlayer() {
        try {
            //set up MediaPlayer
            MediaPlayer mp = new MediaPlayer();
            Uri fileUri = Uri.parse("android.resource://com.example.darknight.tootlespeedalert/raw/alert");
            mp.setDataSource(this, fileUri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeViews() {

        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        liscenseNo = findViewById(R.id.liscense_no);
        vehicleNo = findViewById(R.id.vehicle_no);
        speed = findViewById(R.id.speed);
        timestamp = findViewById(R.id.timestamp);
    }

    public void inflateLayouts() {
        View activityView = mLayoutInflator.inflate(R.layout.activity_main, mFrameLayout, false);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(activityView);

        activityView.findViewById(R.id.imageview_opennav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

        try {
            removeEventListener();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Marker addDriverMarkerToMap(LatLng pos, String title, String snippetText, BitmapDescriptor icon) {
        return mMap.addMarker(new MarkerOptions().
                position(pos).
                title(title).
                snippet(snippetText).
                rotation(0).
                icon(icon));
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplication())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();
    }

    @Override
    public void openDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.openDrawer(GravityCompat.START);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // initially load camera to DurbarMarg
        initCamera(new LatLng(27.703259, 85.315724));

        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json1));
        drawRingRoad();


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String i = marker.getId();
                Driver mDriver = busyDriverList.get(i);

                name.setText(mDriver.getName());
                email.setText(mDriver.getEmail());
                phone.setText(mDriver.getPhone());
                liscenseNo.setText(mDriver.getLicense_no());
                vehicleNo.setText(mDriver.getVehicle_no());
                String speedInkmPerHr = String.valueOf(Math.round((Double.valueOf(mDriver.getSpeed())) * 3.6));
                StringBuilder sb = new StringBuilder(speedInkmPerHr);
                sb.append(" km/hr");
                speed.setText(sb);
                timestamp.setText(String.valueOf(mDriver.getTimestamp()));

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return false;
            }
        });

    }


    private void drawRingRoad() {

        List<LatLng> mpolylineLatLngList = PolyUtil.decode("elygDmlxgOod@m[ir@ajAuWse@k^dBieAkb@sXa@_R`OsYvIxBtWeNlTme@}LwVyF}e@zKe]}Emx@jb@eXxo@ph@|gA~Cj_Am@r]rk@tAnDpCrEjHpFf\\d]naArJxUbq@vFh{@lEtj@Qf[kP`C_MjEqYlJoQxFkq@ru@Ijh@gY~LmL|H_`@ve@qo@nDoJuNiV");
        mMap.addPolyline(new PolylineOptions()
                .addAll(mpolylineLatLngList)
                .width(10)
                .color(getResources().getColor(R.color.LightGray))
                .geodesic(true));
    }

    private void initCamera(LatLng mLatLng) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(mLatLng.latitude,
                        mLatLng.longitude))
                .zoom(MAP_ZOOM)
                .bearing(MAP_BEARING)
                .tilt(MAP_TILT)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    /**
     * override method called when marker isClicked
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        taskAfterMarkerClicked(marker);
        return false;
    }

    /**
     * Stuffs need to be done after marker or fabSwitcherIsClicked
     *
     * @param marker marker object
     */
    private void taskAfterMarkerClicked(Marker marker) {

        AppUtils.showLog(TAG, "marker clicked");

        if (busyDriverList.containsKey(marker.getId())) {
            try {
                AppUtils.showLog(TAG, "marker clicked busy driver");
                marker.setSnippet(relativeTime(busyDriverList.get(marker.getId()).getTimestamp()));
                marker.showInfoWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void playAlertAudio() {
        mp.start();
    }


    public void removeEventListener() {
        mDatabaseReferenceBusyDrivers.removeEventListener(mDatabaseReferenceBusyDriversListener);
    }
}
