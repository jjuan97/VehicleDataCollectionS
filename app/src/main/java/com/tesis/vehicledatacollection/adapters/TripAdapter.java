package com.tesis.vehicledatacollection.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tesis.vehicledatacollection.R;
import com.tesis.vehicledatacollection.classes.Trip;

import java.util.ArrayList;

public class TripAdapter extends ArrayAdapter<Trip> implements View.OnClickListener{

    private View.OnClickListener listener;

    // Adapter constructor
    public TripAdapter(@NonNull Context context, int resource, ArrayList<Trip> items) {
        super(context, resource, items);
    }

    // Add item list view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get item position
        Trip currentTrip = getItem(position);

        // Inflate view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trip, parent, false);
        }

        // Layout features
        //layout.setOnClickListener(this);

        // Set info in item
        TextView idTrip = convertView.findViewById(R.id.id_trip);
        TextView dateTrip = convertView.findViewById(R.id.date_trip);

        idTrip.setText(currentTrip.getIdTrip());
        dateTrip.setText(currentTrip.getIdTrip());

        return convertView;

        //return super.getView(position, convertView, parent);
    }

    // Card view listener

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }
}
