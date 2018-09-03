package com.example.darknight.tootlespeedalert.Activities.ChecklistHistory;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.darknight.tootlespeedalert.Adapter.DriverInfoAdapter;
import com.example.darknight.tootlespeedalert.Model.Driver;
import com.example.darknight.tootlespeedalert.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class ChecklistHistory extends AppCompatActivity {
    public static String TAG = ChecklistHistory.class.getSimpleName();
    private RecyclerView recyclerView;
    public static List<Driver> driverList;
    private DriverInfoAdapter adapter;
    public static List<Driver> getDriverDetails;
    public LinearLayout emptyView;
    Toolbar toolbar;
    Realm realm;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_history);

        Log.e(TAG, "calledOnCreate");

        realm = Realm.getDefaultInstance();

        toolbar = findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();

        if (realm != null) adapter = new DriverInfoAdapter(this, driverList);

        mSwipeRefresh.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_light)
                , getResources().getColor(android.R.color.holo_orange_light)
                , getResources().getColor(android.R.color.holo_blue_light)
                , getResources().getColor(android.R.color.holo_red_light));

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ChecklistHistory.this, "Refreshing...", Toast.LENGTH_SHORT).show();
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
        if (adapter != null) recyclerView.setAdapter(adapter);
        else Toast.makeText(this, "Empty Adapter", Toast.LENGTH_SHORT).show();

        prepareDriverCard();
    }

    public void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        driverList = new ArrayList<>();
        mSwipeRefresh = findViewById(R.id.swipe_refresh);
        emptyView = findViewById(R.id.empty_view);
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
                deleteAllData();
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }

    private void prepareDriverCard() {
        try {
            realm = Realm.getDefaultInstance();
            driverList.clear();
            getDriverDetails = new ArrayList<>(realm.where(Driver.class).findAll());
            for (int i = 0; i < getDriverDetails.size(); i++) {
                Driver driver = getDriverDetails.get(i);
                driverList.add(driver);
            }

        } finally {
            if (realm != null) realm.close();
        }

        adapter.notifyDataSetChanged();

        if (driverList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    private void deleteAllData() {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.deleteAll();
                    Toast.makeText(ChecklistHistory.this, "All data deleted", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    emptyView.setVisibility(View.VISIBLE);
                }
            });
        } finally {
            realm.close();
            Toast.makeText(this, "All data deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
