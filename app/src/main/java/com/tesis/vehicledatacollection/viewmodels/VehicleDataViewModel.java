package com.tesis.vehicledatacollection.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tesis.vehicledatacollection.classes.LastVehicleRecord;
import com.tesis.vehicledatacollection.classes.Trip;
import com.tesis.vehicledatacollection.database.VehicleData;
import com.tesis.vehicledatacollection.database.VehicleDatabaseSingleton;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class VehicleDataViewModel extends ViewModel {

    private LiveData<List<Trip>> tripData;
    private LiveData<List<VehicleData>> vehicleData;
    private Single<List<LastVehicleRecord>> lastRecord;


    // Methods to define Trip Data

    public LiveData<List<Trip>> getTripData(int position){
        tripData = new MutableLiveData<List<Trip>>();
        loadTripData(position);
        return tripData;
    }

    private void loadTripData(int position) {
        // Do an asynchronous operation to fetch users.
        tripData = VehicleDatabaseSingleton
                .getDatabaseInstance()
                .vehicleDataDAO()
                .getAllTripData( position );
    }

    // Methods to define Vehicle Data

    public LiveData<List<VehicleData>> getVehicleData(int position) {

        // Get the vehicle data from database
        vehicleData = new MutableLiveData<List<VehicleData>>();
        loadVehicleData(position);
        return vehicleData;
    }

    private void loadVehicleData(int position) {
        // Do an asynchronous operation to fetch users.
                vehicleData = VehicleDatabaseSingleton
                .getDatabaseInstance()
                .vehicleDataDAO()
                .findVehicleDataById( position );
    }


    // Get the last vehicle id
    public Single<List<LastVehicleRecord>> getLastRecord(){
        lastRecord = VehicleDatabaseSingleton
                .getDatabaseInstance()
                .vehicleDataDAO()
                .getLastVehicleRecord();
        return lastRecord;
    }
}