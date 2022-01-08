package com.tesis.vehicledatacollection.classes;

import androidx.room.ColumnInfo;

public class SimpleVehicleData {

    public long id;
    public String idVehicle;
    public int idTrip;
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
    public int eventClass;

    public SimpleVehicleData(long id, String idVehicle, int idTrip, long timestamp, float speed, float accX, float accY, float accZ, float velAngX, float velAngY, float velAngZ, float magX, float magY, float magZ, double latitude, double longitude, int eventClass) {
        this.id = id;
        this.idVehicle = idVehicle;
        this.idTrip = idTrip;
        this.timestamp = timestamp;
        this.speed = speed;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.velAngX = velAngX;
        this.velAngY = velAngY;
        this.velAngZ = velAngZ;
        this.magX = magX;
        this.magY = magY;
        this.magZ = magZ;
        this.latitude = latitude;
        this.longitude = longitude;
        this.eventClass = eventClass;
    }

    public long getId() {
        return id;
    }

    public String getIdVehicle() {
        return idVehicle;
    }

    public int getIdTrip() {
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

    public int getEventClass() {
        return eventClass;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdVehicle(String idVehicle) {
        this.idVehicle = idVehicle;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAccX(float accX) {
        this.accX = accX;
    }

    public void setAccY(float accY) {
        this.accY = accY;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
    }

    public void setVelAngX(float velAngX) {
        this.velAngX = velAngX;
    }

    public void setVelAngY(float velAngY) {
        this.velAngY = velAngY;
    }

    public void setVelAngZ(float velAngZ) {
        this.velAngZ = velAngZ;
    }

    public void setMagX(float magX) {
        this.magX = magX;
    }

    public void setMagY(float magY) {
        this.magY = magY;
    }

    public void setMagZ(float magZ) {
        this.magZ = magZ;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setEventClass(int eventClass) {
        this.eventClass = eventClass;
    }
}
