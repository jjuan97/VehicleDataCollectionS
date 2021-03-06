package com.tesis.vehicledatacollection.database;

import android.content.Context;

import androidx.room.Room;

public class VehicleDatabaseSingleton {

    private static VehicleDatabase database;
    private static final String BD_NAME = "vehicle_data.db";

    private VehicleDatabaseSingleton(){

    }

    public static VehicleDatabase createDatabaseInstance (Context context) {
        if (database == null){
            database = Room.databaseBuilder(context,
                    VehicleDatabase.class, BD_NAME).build();
        }
        return database;
    }

    public static VehicleDatabase getDatabaseInstance () {
        return database;
    }
}
