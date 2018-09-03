package com.example.darknight.tootlespeedalert.Activities.Checklist;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.darknight.tootlespeedalert.Activities.MainActivity.MainActivity;
import com.example.darknight.tootlespeedalert.Adapter.DriverInfoAdapter;
import com.example.darknight.tootlespeedalert.Application.App;
import com.example.darknight.tootlespeedalert.Model.Driver;
import com.example.darknight.tootlespeedalert.R;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.inject.Inject;

public class Checklist extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static List<Driver> driverList;
    private DriverInfoAdapter adapter;
    public static ArrayList<Driver> getDriverDetails;
    public LinearLayout emptyView;
    Toolbar toolbar;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();

        mSwipeRefresh.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_light)
                , getResources().getColor(android.R.color.holo_orange_light)
                , getResources().getColor(android.R.color.holo_blue_light)
                , getResources().getColor(android.R.color.holo_red_light));

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(Checklist.this, "Refreshing...", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefresh.setRefreshing(true);
                        prepareDriverCard();
                        mSwipeRefresh.setRefreshing(false);
                    }
                }, 2000);

            }
        });


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareDriverCard();

    }

    public void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        driverList = new ArrayList<>();
        adapter = new DriverInfoAdapter(this, driverList);
        emptyView = findViewById(R.id.empty_view);
        mSwipeRefresh = findViewById(R.id.swipe_refresh);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checklist_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(android.R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Driver> filteredModelList = filter(driverList, newText);
                adapter.setSearchResult(filteredModelList);
                return true;
            }
        });
        return true;

    }

    private List<Driver> filter(List<Driver> models, String query) {
        query = query.toLowerCase();
        final List<Driver> filteredModelList = new ArrayList<>();
        for (Driver model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                clearData();
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    public void clearData() {
        MainActivity.speedyDrivers.clear();
        driverList.clear();
        notifyAdapterDataSetChanged();
        showEmptyView();
    }

    public void prepareDriverCard() {
        driverList.clear();
        getDriverDetails = MainActivity.speedyDrivers;
        for (int i = 0; i < getDriverDetails.size(); i++) {
            Driver driver = getDriverDetails.get(i);
            driverList.add(driver);
        }

        notifyAdapterDataSetChanged();

        if (driverList.isEmpty()) {
            hideRecyclerView();
            showEmptyView();
        } else {
            hideEmptyView();
            showRecyclerView();
        }

    }


    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
    }


    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
    }


    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }


    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }


    public void notifyAdapterDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}
