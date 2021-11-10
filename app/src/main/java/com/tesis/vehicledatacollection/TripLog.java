package com.tesis.vehicledatacollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tesis.vehicledatacollection.adapters.TripAdapter;
import com.tesis.vehicledatacollection.classes.Trip;
import com.tesis.vehicledatacollection.databinding.ActivityTripLogBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TripLog extends AppCompatActivity {

    private ActivityTripLogBinding binding;

    public TripAdapter adapter;

    ListView tripList;
    ArrayList<Trip> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTripLogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        tripList = binding.listViewTripHistory;

        // Add trip information
        fillArrayList();

        adapter = new TripAdapter(this,0, trips);
        tripList.setAdapter(adapter);

    }

    public void fillArrayList(){
        // Dummy data trips
        //System.currentTimeMillis();
        Trip trip1 = new Trip("1", "10-11-2021");
        Trip trip2 = new Trip("1", "10-11-2021");
        Trip trip3 = new Trip("1", "10-11-2021");
        Trip trip4 = new Trip("1", "10-11-2021");
        Trip trip5 = new Trip("1", "10-11-2021");
        Trip trip6 = new Trip("1", "10-11-2021");
        Trip trip7 = new Trip("1", "10-11-2021");
        Trip trip8 = new Trip("1", "10-11-2021");
        Trip trip9 = new Trip("1", "10-11-2021");
        Trip trip10 = new Trip("1", "10-11-2021");
        Trip trip11 = new Trip("1", "10-11-2021");
        Trip trip12 = new Trip("1", "10-11-2021");
        Trip trip13 = new Trip("1", "10-11-2021");
        Trip trip14 = new Trip("1", "10-11-2021");
        Trip trip15 = new Trip("1", "10-11-2021");
        Trip trip16 = new Trip("1", "10-11-2021");



        // Add Object trip to ArrayList
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        trips.add(trip4);
        trips.add(trip5);
        trips.add(trip6);
        trips.add(trip7);
        trips.add(trip8);
        trips.add(trip9);
        trips.add(trip10);
        trips.add(trip11);
        trips.add(trip12);
        trips.add(trip13);
        trips.add(trip14);
        trips.add(trip15);
        trips.add(trip16);

        Log.d("array", "Lleno");
    }
}