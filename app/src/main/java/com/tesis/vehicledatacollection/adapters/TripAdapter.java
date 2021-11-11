package com.tesis.vehicledatacollection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.tesis.vehicledatacollection.R;
import com.tesis.vehicledatacollection.classes.Trip;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    ArrayList<Trip> item;
    private View.OnClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView idTrip;
        private final TextView dateTrip;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            idTrip = view.findViewById(R.id.id_trip);
            dateTrip = view.findViewById(R.id.date_trip);
        }
    }

    // Adapter constructor
    public TripAdapter(ArrayList<Trip> items) {
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
        itemTripHolder.dateTrip.setText(currentTrip.getDate());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    //card view listener
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

}
