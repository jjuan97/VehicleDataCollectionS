package com.tesis.vehicledatacollection.classes;

public class Trip {

    //TODO: Cambiar los datos para que coincidan con el UI

    private int idTrip;
    private String time;
    private int capturedData;
    private int nearcrashesData;
    private int meanFrequency;


    public Trip(int idTrip, String time, int capturedData, int nearcrashesData, int meanFrequency) {
        this.idTrip = idTrip;
        this.time = time;
        this.capturedData = capturedData;
        this.meanFrequency = meanFrequency;
        this.nearcrashesData = nearcrashesData;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public String getTime() {
        return time;
    }

    public int getCapturedData() {
        return capturedData;
    }

    public int getNearcrashesData() {
        return nearcrashesData;
    }

    public int getMeanFrequency() {
        return meanFrequency;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setcapturedData(int capturedData) {
        this.capturedData = capturedData;
    }

    public void setMeanFrequency(int meanFrequency) {
        this.meanFrequency = meanFrequency;
    }

    public void setNearcrashesData(int nearcrashesData) {
        this.nearcrashesData = nearcrashesData;
    }
}

