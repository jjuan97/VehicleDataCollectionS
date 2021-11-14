package com.tesis.vehicledatacollection.classes;

public class LastVehicleRecord {
    public int id;
    public int idTrip;

    public LastVehicleRecord(int id, int idTrip) {
        this.id = id;
        this.idTrip = idTrip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }
}
