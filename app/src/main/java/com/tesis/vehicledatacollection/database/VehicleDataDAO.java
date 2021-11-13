package com.tesis.vehicledatacollection.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tesis.vehicledatacollection.classes.Trip;

import java.util.List;

@Dao
public interface VehicleDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertVehicleData(VehicleData data);

    @Query("SELECT * FROM VehicleData")
    public List<VehicleData> getAllData();

    @Query("SELECT * FROM VehicleData")
    public LiveData<List<VehicleData>> getAllVehicleData();

    @Query("SELECT id AS idTrip," + //TODO: Change id to idTravel
            " timestamp AS time," +
            " COUNT(accX) AS capturedData," +
            " SUM(eventClass) AS nearcrashesData," +
            " AVG(id) AS meanFrequency" +  //TODO: change to captured frequency
            " FROM VehicleData GROUP BY latitude LIMIT :dataId") //TODO: Add GROUP BY VehicleID
    public LiveData<List<Trip>> getAllTripData(int dataId);

    @Query("SELECT * FROM VehicleData WHERE id = :dataId")
    public LiveData<List<VehicleData>> findVehicleDataById(int dataId); //TODO: Change id to idTravel



}
