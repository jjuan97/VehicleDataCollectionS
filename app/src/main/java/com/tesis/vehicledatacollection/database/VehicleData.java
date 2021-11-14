package com.tesis.vehicledatacollection.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity
public class VehicleData {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String idVehicle;
    public int idTrip;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public long timestamp;
    public float speed;
    public float accX;
    public float accY;
    public float accZ;
    public float velAngX;
    public float velAngY;
    public float velAngZ;
    public float magX;
    public float magY;
    public float magZ;
    public double latitude;
    public double longitude;
    public boolean eventClass;

    public VehicleData () {

    }

    public void setIdVehicle(String idVehicle) {
        this.idVehicle = idVehicle;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setAccData(float[] accData) {
        this.accX = accData[0];
        this.accY = accData[1];
        this.accZ = accData[2];
    }

    public void setGyrData(float[] gyrData) {
        this.velAngX = gyrData[0];
        this.velAngY = gyrData[1];
        this.velAngZ = gyrData[2];
    }

    public void setMagData(float[] setMagData) {
        this.magX = setMagData[0];
        this.magY = setMagData[1];
        this.magZ = setMagData[2];
    }

    public void setGpsData(double latitude, double longitude, float speed){
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public long setTimestamp (){
        this.timestamp = System.currentTimeMillis();
        return timestamp;
    }

    public void setEventClass(boolean eventClass) {
        this.eventClass = eventClass;
    }

    public long getId() {
        return id;
    }

    public String getIdVehicle(){
        return idVehicle;
    }

    public int getidTrip() {
        return idTrip;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAccX() {
        return accX;
    }

    public float getAccY() {
        return accY;
    }

    public float getAccZ() {
        return accZ;
    }

    public float getVelAngX() {
        return velAngX;
    }

    public float getVelAngY() {
        return velAngY;
    }

    public float getVelAngZ() {
        return velAngZ;
    }

    public float getMagX() {
        return magX;
    }

    public float getMagY() {
        return magY;
    }

    public float getMagZ() {
        return magZ;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
