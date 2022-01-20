package com.tesis.vehicledatacollection.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tesis.vehicledatacollection.classes.LastVehicleRecord;
import com.tesis.vehicledatacollection.classes.SimpleVehicleData;
import com.tesis.vehicledatacollection.classes.Trip;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface VehicleDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertVehicleData(VehicleData data);

    @Query("SELECT * FROM VehicleData")
    public List<VehicleData> getAllData();

    @Query("SELECT * FROM VehicleData")
    public LiveData<List<VehicleData>> getAllVehicleData();

    @Query("SELECT idTrip," +
            " MIN(timestamp) AS time," +
            " MAX(timestamp) AS maxTime, " +
            " idVehicle," +
            " COUNT(accX) AS capturedData," +
            " SUM(eventClass) AS nearcrashesData," +
            " AVG(id) AS meanFrequency," +
            " route" +
            " FROM VehicleData WHERE active = 1 " +
            " GROUP BY idTrip")
    public LiveData<List<Trip>> getAllTripData();

    @Query("SELECT id, idVehicle, route, idTrip, timestamp, eventClass," +
            " accX, accY, accZ," +
            " velAngX, velAngY, velAngZ," +
            " magX, magY, magZ," +
            " speed, latitude, longitude" +
            " FROM VehicleData WHERE idTrip = :dataId")
    public Single<List<SimpleVehicleData>> findVehicleDataById(int dataId);

    @Query("SELECT id,idTrip FROM VehicleData ORDER BY id DESC LIMIT 1")
    public Single<List<LastVehicleRecord>> getLastVehicleRecord();

    @Query("DELETE FROM vehicledata WHERE idTrip = :idTrip")
    public Single<Integer> removeATrip(int idTrip);

    @Query("UPDATE vehicledata SET active = 0 WHERE idTrip = :idTrip")
    public Single<Integer> hideATrip(int idTrip);

}
