package com.tesis.vehicledatacollection.classes;

public class Trip {

    private int idTrip;
    private String date;
    private int kinematicData;
    private int localizationData;
    private int near_crashesData;

    public Trip(int idTrip, String date, int kinematicData, int localizationData, int near_crashesData) {
        this.idTrip = idTrip;
        this.date = date;
        this.kinematicData = kinematicData;
        this.localizationData = localizationData;
        this.near_crashesData = near_crashesData;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public String getDate() {
        return date;
    }

    public int getKinematicData() {
        return kinematicData;
    }

    public int getLocalizationData() {
        return localizationData;
    }

    public int getNear_crashesData() {
        return near_crashesData;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setKinematicData(int kinematicData) {
        this.kinematicData = kinematicData;
    }

    public void setLocalizationData(int localizationData) {
        this.localizationData = localizationData;
    }

    public void setNear_crashesData(int near_crashesData) {
        this.near_crashesData = near_crashesData;
    }
}

