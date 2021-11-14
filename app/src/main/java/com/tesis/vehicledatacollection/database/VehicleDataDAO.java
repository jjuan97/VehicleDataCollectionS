package com.tesis.vehicledatacollection.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tesis.vehicledatacollection.classes.LastVehicleRecord;
import com.tesis.vehicledatacollection.classes.Trip;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface VehicleDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertVehicleData(VehicleData data);

    @Query("SELECT * FROM VehicleData")
    public List<VehicleData> getAllData();

    @Query("SELECT * FROM VehicleData")
    public LiveData<List<VehicleData>> getAllVehicleData();

    @Query("SELECT id AS idTrip," +
            " timestamp AS date," +
            " COUNT(accX) AS kinematicData," +
            " COUNT(latitude) AS localizationData," +
            " COUNT(eventClass) AS near_crashesData" +
            " FROM VehicleData GROUP BY latitude LIMIT :dataId") //TODO: Change id to idTravel, Add GROUP BY VehicleID, eventClass identify zero data and not count
    public LiveData<List<Trip>> getAllTripData(int dataId);

    @Query("SELECT * FROM VehicleData WHERE id = :dataId")
    public LiveData<List<VehicleData>> findVehicleDataById(int dataId);

    @Query("SELECT id,idTrip FROM VehicleData ORDER BY id DESC LIMIT 1")
    public Single<List<LastVehicleRecord>> getLastVehicleRecord();

}
