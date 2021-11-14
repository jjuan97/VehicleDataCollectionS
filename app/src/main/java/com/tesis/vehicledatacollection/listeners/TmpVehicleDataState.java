package com.tesis.vehicledatacollection.listeners;

import com.tesis.vehicledatacollection.database.VehicleData;

public class TmpVehicleDataState {

    private static VehicleData vehicleDataRecord = new VehicleData();

    public static void setIdTrip (int idTrip){
        vehicleDataRecord.setIdTrip(idTrip);
    }

    public static void setIdVehicle(String idVehicle){
        vehicleDataRecord.setIdVehicle(idVehicle);
    }

    public static void updateAccData (float[] accData) {
        vehicleDataRecord.setAccData(accData);
    }

    public static void updateGyrData (float[] gyrData) {
        vehicleDataRecord.setGyrData(gyrData);
    }

    public static void updateMagData (float[] magData) {
        vehicleDataRecord.setMagData(magData);
    }

    public static void updateGpsData (double latitude, double longitude, float speed) {
        vehicleDataRecord.setGpsData(latitude, longitude, speed);
    }

    public static VehicleData getCurrentVehicleState(){
        vehicleDataRecord.setTimestamp();
        return vehicleDataRecord;
    }


}
