package com.tesis.vehicledatacollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tesis.vehicledatacollection.adapters.TripAdapter;
import com.tesis.vehicledatacollection.classes.SimpleVehicleData;
import com.tesis.vehicledatacollection.classes.Trip;
import com.tesis.vehicledatacollection.database.VehicleData;
import com.tesis.vehicledatacollection.database.VehicleDatabaseSingleton;
import com.tesis.vehicledatacollection.databinding.ActivityTripLogBinding;
import com.tesis.vehicledatacollection.firebase.TripFirebase;
import com.tesis.vehicledatacollection.viewmodels.VehicleDataViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class TripLog extends AppCompatActivity {

    private ActivityTripLogBinding binding;
    private Button sendDataButton;
    private DatabaseReference firebaseDB;
    private ProgressBar progressBar;

    public TripAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;

    VehicleDataViewModel model;
    int idTrip;
    int position;
    Trip trip;
    List<Trip> trips = new ArrayList<>();
    List<VehicleData> vehicleDataList = new ArrayList<>();

    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTripLogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        firebaseDB = FirebaseDatabase.getInstance().getReference();

        // Inflate button
        sendDataButton = binding.buttonSendData;
        sendDataButton.setEnabled(false);

        // Create View Model for Async task
        model = new ViewModelProvider(this).get(VehicleDataViewModel.class);

        // Create trip item adapter
        adapter = new TripAdapter(trips);

        // Create layout manager to attached recycler view
        mLayoutManager = new LinearLayoutManager(view.getContext());

        // Progress bar
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.GONE);

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
            position = mRecyclerView.getChildAdapterPosition(view1);
            trip = adapter.getTrip(position);

            // Add data about trip
            binding.columnValue1.setText(String.valueOf(trip.getIdVehicle()));
            binding.columnValue2.setText(String.valueOf(trip.getCapturedData()));
            binding.columnValue3.setText(String.valueOf(trip.getNearcrashesData()));
            binding.columnValue4.setText(String.valueOf(trip.getMeanFrequency()));
            binding.columnValue5.setText("");

            // Add functionality to send button
            idTrip = trip.getIdTrip();
            sendDataButton.setEnabled(true);
            String buttonMsg = getString(R.string.send_trip_data) + " " + idTrip;
            sendDataButton.setText(buttonMsg);
        });

        adapter.setOnLongClickListener(v -> {
            int pos = mRecyclerView.getChildAdapterPosition(v);
            int idTrip = adapter.getTrip(pos).getIdTrip();
            showRemoveTripDialog(idTrip, pos);
            return true;
        });

        sendDataButton.setOnClickListener(viewB -> {
            //Toast.makeText(this,"Estoy Disponible",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            sendDataFirebase();
        });

    }

    public void fillRecycler(){
        model.getTripData().observe(this, tripData -> {
            // update UI
            adapter.setTrips(tripData);
        });
    }

    public void showRemoveTripDialog(int idTrip, int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msg_remove_trip) + idTrip)
                .setTitle("Title")
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        progressBar.setVisibility(View.VISIBLE);
                        removeTrip(idTrip, pos);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void removeTrip(int idTrip, int pos){
        Log.d("Deleting", "id: "+idTrip);
        model.removeATrip(idTrip).subscribeOn(Schedulers.io())
                .subscribe((n) -> {
                    Log.d("Deleting", ""+n);
                    adapter.notifyItemRemoved(pos);
                }, (throwable) -> Log.e("Error DB", throwable.getMessage()) );
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,"Datos borrados",Toast.LENGTH_SHORT).show();
    }

    public void hideTrip(int idTrip, int pos){
        Log.d("Hidding", "id: "+idTrip);
        model.hideATrip(idTrip).subscribeOn(Schedulers.io())
                .subscribe((n) -> {
                    Log.d("Hidding", ""+n);
                    adapter.notifyItemRemoved(pos);
                }, (throwable) -> Log.e("Error DB", throwable.getMessage()) );
    }

    public void sendDataFirebase(){
        String pattern = "dd-MMM-yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String device = "Smartphone";
        String dateTrip = simpleDateFormat.format(new Date( trip.getTime() ) );
        String vehicle = binding.columnValue1.getText().toString();
        long kinematicData = Long.parseLong( binding.columnValue2.getText().toString() );

        TripFirebase tripFirebase = new TripFirebase(device, dateTrip, vehicle, kinematicData);
        DatabaseReference ref = firebaseDB.child("tripList").push();
        ref.setValue(tripFirebase).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                String key = ref.getKey();
                Log.d("firebase", "Document saved: "+key);

                model.getVehicleData(idTrip).subscribeOn(Schedulers.io())
                    .subscribe((tripList)-> {
                        Map <String, Object> tripRecords = listToMap(tripList);
                        firebaseDB.child("tripData/smartphone/"+key)
                            .updateChildren(tripRecords)
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()){
                                    hideTrip(idTrip, position);
                                    Log.d("firebase", "All records uploaded");
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(this,"Datos enviados",Toast.LENGTH_SHORT).show();
                                }
                            });
                    });
            }
        });
    }

    public Map<String, Object> listToMap(List<SimpleVehicleData> list) {
        Map<String, Object> map = new HashMap<>();
        for (SimpleVehicleData data : list){
            map.put( String.valueOf(data.getId()), data);
        }
        return map;
    }
}