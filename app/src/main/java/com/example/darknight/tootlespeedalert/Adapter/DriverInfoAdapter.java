package com.example.darknight.tootlespeedalert.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.darknight.tootlespeedalert.Model.Driver;
import com.example.darknight.tootlespeedalert.R;

import java.util.List;

/**
 * Created by darknight on 4/19/18.
 */

public class DriverInfoAdapter extends RecyclerView.Adapter<DriverInfoAdapter.MyViewHolder> {

    private Context mContext;
    private List<Driver> driverList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, email, phone, liscenseNo, vehicleNo, lat, lon, speed;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            email = view.findViewById(R.id.email);
            phone = view.findViewById(R.id.phone);
            liscenseNo = view.findViewById(R.id.liscense_no);
            vehicleNo = view.findViewById(R.id.vehicle_no);
            lat = view.findViewById(R.id.latitude);
            lon = view.findViewById(R.id.longitude);
            speed = view.findViewById(R.id.speed);
        }
    }


    public DriverInfoAdapter(Context mContext, List<Driver> driverList) {
        this.mContext = mContext;
        this.driverList = driverList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.driver_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Driver driver = driverList.get(position);
        holder.name.setText(driver.getName().toUpperCase());
        holder.email.setText(driver.getEmail());
        holder.phone.setText(driver.getPhone());
        holder.liscenseNo.setText(driver.getLicense_no());
        holder.vehicleNo.setText(driver.getVehicle_no());
        holder.lat.setText(String.format("%.4f", driver.getLat()));
        holder.lon.setText(String.format("%.4f", driver.getLon()));

        String speedInkmPerHr = String.valueOf(Math.round((Double.valueOf(driver.getSpeed())) * 3.6));
        StringBuilder sb = new StringBuilder();
        sb.append(speedInkmPerHr);
        sb.append(" KM/HR");
        holder.speed.setText(sb);


        // loading placeholder if image is empty using Glide library
        if (driver.getPhoto().isEmpty())
            Glide.with(mContext).load(R.drawable.placeholder).into(holder.thumbnail);
        else Glide.with(mContext).load(driver.getPhoto()).into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return driverList.size();
    }

    public void setSearchResult(List<Driver> result) {
        driverList = result;
        notifyDataSetChanged();
    }
}