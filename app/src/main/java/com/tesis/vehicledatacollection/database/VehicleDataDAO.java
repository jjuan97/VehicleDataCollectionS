package com.tesis.vehicledatacollection.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VehicleDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertVehicleData(VehicleData data);

    @Query("SELECT * FROM VehicleData")
    public List<VehicleData> getAllData();



}
