package com.tesis.vehicledatacollection.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TripFirebase {
    public int tripLocalId;
    public String device;
    public String date;
    public String vehicle;
    public long kinematicData;
    public String route;

    public TripFirebase (){

    }

    public TripFirebase(int tripLocalId, String device, String date, String vehicle, long kinematicData, String route) {
        this.tripLocalId = tripLocalId;
        this.device = device;
        this.date = date;
        this.vehicle = vehicle;
        this.kinematicData = kinematicData;
        this.route = route;
    }
}
