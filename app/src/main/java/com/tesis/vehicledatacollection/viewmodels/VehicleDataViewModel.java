package com.tesis.vehicledatacollection.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tesis.vehicledatacollection.classes.LastVehicleRecord;
import com.tesis.vehicledatacollection.classes.Trip;
import com.tesis.vehicledatacollection.database.VehicleData;
import com.tesis.vehicledatacollection.database.VehicleDatabaseSingleton;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class VehicleDataViewModel extends ViewModel {

    private LiveData<List<Trip>> tripData;
    private LiveData<List<VehicleData>> vehicleData;
    private Single<List<LastVehicleRecord>> lastRecord;


    // Methods to define Trip Data

    public LiveData<List<Trip>> getTripData(){
        tripData = new MutableLiveData<List<Trip>>();
        loadTripData();
        return tripData;
    }

    private void loadTripData() {
        // Do an asynchronous operation to fetch users.
        tripData = VehicleDatabaseSingleton
                .getDatabaseInstance()
                .vehicleDataDAO()
                .getAllTripData();
    }

    // Methods to define Vehicle Data

    public Single<List<VehicleData>> getVehicleData(int position) {
        // Get the vehicle data from database
        return VehicleDatabaseSingleton
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

    // Remove a trip
    public Single<Integer> removeATrip(int idTrip){
         return VehicleDatabaseSingleton
                 .getDatabaseInstance()
                 .vehicleDataDAO()
                 .removeATrip(idTrip);
    }
}
