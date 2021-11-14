package com.tesis.vehicledatacollection.classes;

public class Trip {


    private int idTrip;
    private long time;
    private long maxTime;
    private String idVehicle;
    private int capturedData;
    private int nearcrashesData;
    private float meanFrequency;


    public Trip(int idTrip, long time, long maxTime, String idVehicle, int capturedData, int nearcrashesData, float meanFrequency) {
        this.idTrip = idTrip;
        this.time = time;
        this.maxTime = maxTime;
        this.idVehicle = idVehicle;
        this.capturedData = capturedData;
        this.meanFrequency = meanFrequency;
        this.nearcrashesData = nearcrashesData;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public long getTime() {
        return time;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public String getIdVehicle() {
        return idVehicle;
    }

    public int getCapturedData() {
        return capturedData;
    }

    public int getNearcrashesData() {
        return nearcrashesData;
    }

    public float getMeanFrequency() {
        return (float) (capturedData*1000)/(maxTime-time);
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setIdVehicle(String idVehicle) {
        this.idVehicle = idVehicle;
    }

    public void setCapturedData(int capturedData) {
        this.capturedData = capturedData;
    }

    public void setMeanFrequency(float meanFrequency) {
        this.meanFrequency = meanFrequency;
    }

    public void setNearcrashesData(int nearcrashesData) {
        this.nearcrashesData = nearcrashesData;
    }
}

