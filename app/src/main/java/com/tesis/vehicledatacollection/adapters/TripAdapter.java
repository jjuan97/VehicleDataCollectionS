package com.tesis.vehicledatacollection.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.tesis.vehicledatacollection.R;
import com.tesis.vehicledatacollection.classes.Trip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    List<Trip> item;
    private View.OnClickListener listener;
    private View.OnLongClickListener longClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView idTrip;
        private final TextView dateTrip;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            idTrip = view.findViewById(R.id.id_trip);
            dateTrip = view.findViewById(R.id.date_trip);
            view.setOnLongClickListener(longClickListener);
        }

    }

    // Adapter constructor
    public TripAdapter(List<Trip> items) {
        this.item = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);

        // View features
        view.setOnClickListener(this);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder itemTripHolder = (ViewHolder) holder;

        Trip currentTrip = item.get(position);
        itemTripHolder.idTrip.setText(String.valueOf(currentTrip.getIdTrip()));

        String pattern = "dd-MMM-yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date(currentTrip.getTime()));
        itemTripHolder.dateTrip.setText(date);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    // Fill adapter with List of Trip objects
    public void setTrips(List<Trip> item){
        this.item = item;
        notifyDataSetChanged();
    }

    // Get selected item in recycler view

    public Trip getTrip(int position){
        return item.get(position);
    }

    //card view listener
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener){
        this.longClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

}
