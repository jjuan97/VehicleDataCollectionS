package com.tesis.vehicledatacollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tesis.vehicledatacollection.adapters.TripAdapter;
import com.tesis.vehicledatacollection.classes.Trip;
import com.tesis.vehicledatacollection.database.VehicleData;
import com.tesis.vehicledatacollection.databinding.ActivityTripLogBinding;
import com.tesis.vehicledatacollection.viewmodels.VehicleDataViewModel;

import java.util.ArrayList;
import java.util.List;

public class TripLog extends AppCompatActivity {

    private ActivityTripLogBinding binding;
    private Button sendDataButton;

    public TripAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;

    VehicleDataViewModel model;
    int idTrip;
    List<Trip> trips = new ArrayList<>();
    List<VehicleData> vehicleDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTripLogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Inflate button
        sendDataButton = binding.buttonSendData;
        sendDataButton.setEnabled(false);

        // Create View Model for Async task
        model = new ViewModelProvider(this).get(VehicleDataViewModel.class);

        // Create trip item adapter
        adapter = new TripAdapter(trips);

        // Create layout manager to attached recycler view
        mLayoutManager = new LinearLayoutManager(view.getContext());

        // Create recycler view and add features
        mRecyclerView = binding.recyclerViewTripHistory;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        // Add trip information
        fillRecycler();

        // Aggregate OnClickListener to adapter
        adapter.setOnClickListener(view1 -> {

            // Get object trip in item list
            Trip trip = adapter.getTrip(mRecyclerView.getChildAdapterPosition(view1));

            // Add data about trip
            binding.columnValue1.setText(String.valueOf(trip.getIdTrip()));
            binding.columnValue2.setText(String.valueOf(trip.getCapturedData()));
            binding.columnValue3.setText(String.valueOf(trip.getNearcrashesData()));
            binding.columnValue4.setText(String.valueOf(trip.getMeanFrequency()));

            // Add functionality to send button
            idTrip = trip.getIdTrip();
            sendDataButton.setEnabled(true);
            String buttonMsg = getString(R.string.send_trip_data) + " " + idTrip;
            sendDataButton.setText(buttonMsg);

        });

        sendDataButton.setOnClickListener(viewB -> {
            //Toast.makeText(this,"Estoy Disponible",Toast.LENGTH_SHORT).show();
            sendDataMongoDB();
        });

    }

    public void fillRecycler(){
        model.getTripData(10).observe(this, tripData -> {
            // update UI
            adapter.setTrips(tripData);
        });
    }

    public void sendDataMongoDB(){

        // Send all data from trip to MongoDB
        model.getVehicleData(idTrip).observe(this, vehicleData -> {
            // update UI
            Toast.makeText(this,""+vehicleData.get(0).getAccX(),Toast.LENGTH_SHORT).show();
            //binding.columnValue1.setText(String.valueOf(vehicleData.get(0).getAccX()));
        });
    }
}