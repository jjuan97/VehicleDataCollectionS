package com.tesis.vehicledatacollection.database;

import android.content.Context;

import androidx.room.Room;

public class VehicleDatabaseSingleton {

    private static VehicleDatabase database;
    private static final String BD_NAME = "vehicle_data";

    private VehicleDatabaseSingleton(){

    }

    public static VehicleDatabase getDatabaseInstance (Context context) {
        if (database == null){
            database = Room.databaseBuilder(context,
                    VehicleDatabase.class, BD_NAME).build();
        }
        return database;
    }
}
