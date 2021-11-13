package com.tesis.vehicledatacollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    public TripAdapter adapter;

    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    VehicleDataViewModel model;
    List<Trip> trips = new ArrayList<>();
    List<VehicleData> vehicleDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTripLogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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
            //Trip trip = trips.get(mRecyclerView.getChildAdapterPosition(view1));

            //Toast.makeText(this,""+trip.getIdTrip(),Toast.LENGTH_SHORT).show();

            model.getVehicleData(trip.getIdTrip()).observe(this, vehicleData -> {
                // update UI
                Toast.makeText(this,""+vehicleData.get(0).getAccX(),Toast.LENGTH_SHORT).show();
                binding.columnValue1.setText(String.valueOf(vehicleData.get(0).getAccX()));
            });



            /*class getVehicleDataAsync extends AsyncTask <Void, Void, List<VehicleData>> {

                List<VehicleData> vehicleData;

                public getVehicleDataAsync(Activity activity) {
                    weakActivity = new WeakReference<>(activity);
                    this.email = email;
                    this.phone = phone;
                    this.license = license;
                }

                @Override
                protected List<VehicleData> doInBackground(Void... params) {

                    vehicleData = VehicleDatabaseSingleton
                            .getDatabaseInstance()
                            .vehicleDataDAO()
                            .findVehicleDataById( trip.getIdTrip() ); // Only interesting in send ID

                    return vehicleData;
                }
            }*/




            //binding.columnValue1.setText(String.valueOf(vehicleData.get(0).getLatitude()));
            binding.columnValue2.setText(String.valueOf(trip.getKinematicData()));
            binding.columnValue3.setText(String.valueOf(trip.getLocalizationData()));
            binding.columnValue4.setText(String.valueOf(trip.getNear_crashesData()));
        });




    }

    public void fillRecycler(){
        model.getTripData(3).observe(this, tripData -> {
            // update UI
            adapter.setTrips(tripData);
        });
    }

    /*public void fillArrayList(){
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
    }*/
}