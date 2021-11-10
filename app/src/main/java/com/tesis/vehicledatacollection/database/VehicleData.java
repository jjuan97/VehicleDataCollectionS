package com.tesis.vehicledatacollection.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VehicleData {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public int idViaje;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public long timestamp;
    public float velocidad;
    public float accX;
    public float accY;
    public float accZ;
    public float velAngX;
    public float velAngY;
    public float velAngZ;
    public float magX;
    public float magY;
    public float magZ;
    public double latitud;
    public double longitud;
    public boolean clase;

}
