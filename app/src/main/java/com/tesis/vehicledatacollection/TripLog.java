package com.tesis.vehicledatacollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.tesis.vehicledatacollection.adapters.TripAdapter;
import com.tesis.vehicledatacollection.classes.Trip;
import com.tesis.vehicledatacollection.databinding.ActivityTripLogBinding;

import java.util.ArrayList;

public class TripLog extends AppCompatActivity {

    private ActivityTripLogBinding binding;

    public TripAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    ArrayList<Trip> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTripLogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Add trip information
        fillArrayList();

        // Crete layout manager to attached recycler view
        mLayoutManager = new LinearLayoutManager(view.getContext());

        // Set trip item adapter and aggregate OnClickListener
        mRecyclerView = binding.recyclerViewTripHistory;

        adapter = new TripAdapter(trips);
        adapter.setOnClickListener(view1 -> {

            // Get object trip in item list
            Trip trip = trips.get(mRecyclerView.getChildAdapterPosition(view1));

            Toast.makeText(this,""+trip.getIdTrip(),Toast.LENGTH_SHORT).show();

            binding.columnValue1.setText(trip.getDate());
            binding.columnValue2.setText(String.valueOf(trip.getKinematicData()));
            binding.columnValue3.setText(String.valueOf(trip.getLocalizationData()));
            binding.columnValue4.setText(String.valueOf(trip.getNear_crashesData()));
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);


    }

    public void fillArrayList(){
        // Dummy data trips
        //System.currentTimeMillis();
        Trip trip1 = new Trip(1, "10-11-2021", 100, 80, 10);
        Trip trip2 = new Trip(2, "11-11-2021", 120, 90, 15);
        Trip trip3 = new Trip(3, "12-11-2021", 90, 70, 12);
        Trip trip4 = new Trip(1, "13-11-2021", 100, 80, 10);
        Trip trip5 = new Trip(2, "14-11-2021", 120, 90, 15);
        Trip trip6 = new Trip(3, "15-11-2021", 90, 70, 12);
        Trip trip7 = new Trip(1, "10-11-2021", 100, 80, 10);
        Trip trip8 = new Trip(2, "10-11-2021", 120, 90, 15);
        Trip trip9 = new Trip(3, "10-11-2021", 90, 70, 12);
        Trip trip10 = new Trip(1, "10-11-2021", 100, 80, 10);
        Trip trip11 = new Trip(2, "10-11-2021", 120, 90, 15);
        Trip trip12 = new Trip(3, "10-11-2021", 90, 70, 12);
        Trip trip13 = new Trip(1, "10-11-2021", 100, 80, 10);
        Trip trip14 = new Trip(2, "10-11-2021", 120, 90, 15);
        Trip trip15 = new Trip(3, "10-11-2021", 90, 70, 12);
        Trip trip16 = new Trip(1, "10-11-2021", 100, 80, 10);



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