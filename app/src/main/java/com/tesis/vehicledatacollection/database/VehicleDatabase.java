package com.tesis.vehicledatacollection.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {VehicleData.class, AccData.class}, version = 1)
public abstract class VehicleDatabase extends RoomDatabase {
    public abstract VehicleDataDAO vehicleDataDAO();
    public abstract AccDataDAO accDataDAO();
}
