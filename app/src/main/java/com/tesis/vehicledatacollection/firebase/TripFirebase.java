package com.tesis.vehicledatacollection.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TripFirebase {
    public String device;
    public String date;
    public String vehicle;
    public long kinematicData;
    public String route;

    public TripFirebase (){

    }

    public TripFirebase(String device, String date, String vehicle, long kinematicData, String route) {
        this.device = device;
        this.date = date;
        this.vehicle = vehicle;
        this.kinematicData = kinematicData;
        this.route = route;
    }
}
