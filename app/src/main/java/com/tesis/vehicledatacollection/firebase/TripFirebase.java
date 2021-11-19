package com.tesis.vehicledatacollection.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TripFirebase {
    public String date;
    public String vehicle;
    public long kinematicData;

    public TripFirebase (){

    }

    public TripFirebase(String date, String vehicle, long kinematicData) {
        this.date = date;
        this.vehicle = vehicle;
        this.kinematicData = kinematicData;
    }
}
