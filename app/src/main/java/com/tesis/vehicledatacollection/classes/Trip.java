package com.tesis.vehicledatacollection.classes;

public class Trip {

    private String idTrip;
    private String date;

    public Trip(String idTrip, String date) {
        this.idTrip = idTrip;
        this.date = date;
    }

    public String getIdTrip() {
        return idTrip;
    }

    public String getDate() {
        return date;
    }

    public void setIdTrip(String idTrip) {
        this.idTrip = idTrip;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

