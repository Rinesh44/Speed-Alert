package com.example.darknight.tootlespeedalert.Activities.BaseActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.darknight.tootlespeedalert.Activities.Checklist.Checklist;
import com.example.darknight.tootlespeedalert.Activities.ChecklistHistory.ChecklistHistory;
import com.example.darknight.tootlespeedalert.Activities.MainActivity.MainActivity;
import com.example.darknight.tootlespeedalert.Application.App;
import com.example.darknight.tootlespeedalert.R;
import com.example.darknight.tootlespeedalert.util.AppUtils;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected SharedPreferences mSharedPreferences;
    protected NavigationView mNavigationView;
    protected FrameLayout mFrameLayout;
    protected LayoutInflater mLayoutInflator;
    protected Toolbar mToolbar;
    protected DrawerLayout mDrawer;
    private static String ssid1;
    protected CoordinatorLayout mCoordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtils.showLog(TAG, "onCreate()");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        setContentView(R.layout.activity_base);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        ssid1 = (wifiInfo.getSSID());

        initViews();

    }

    public void initViews() {

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mLayoutInflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.GONE);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert mDrawer != null;
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_dashboard:
                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                mDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_checklist:
                Intent showChecklist = new Intent(BaseActivity.this, Checklist.class);
                showChecklist.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(showChecklist);
                mDrawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_checklist_history:
                Intent showChecklistHistory = new Intent(BaseActivity.this, ChecklistHistory.class);
                showChecklistHistory.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(showChecklistHistory);
                mDrawer.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    public void openDrawer() {
        assert mDrawer != null;
        if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }
}