package com.tesis.vehicledatacollection.listeners;

import android.os.AsyncTask;
import android.util.Log;

import com.tesis.vehicledatacollection.database.VehicleData;
import com.tesis.vehicledatacollection.database.VehicleDatabaseSingleton;

public class VehicleDataState {

    private static VehicleData vehicleDataRecord = new VehicleData();
    private static double lastLatitude;
    private static double lastLongitude;
    private static float lastSpeed;

    private static boolean accDataTaken = false;
    private static boolean gyrDataTaken = false;
    private static boolean magDataTaken = false;

    public static void updateAccData (float[] accData) {
        if (!accDataTaken) {
            vehicleDataRecord.setAccData(accData);
            accDataTaken = true;
            checkCompleteData();
        }
    }

    public static void updateGyrData (float[] gyrData) {
        if (!gyrDataTaken){
            vehicleDataRecord.setGyrData(gyrData);
            gyrDataTaken = true;
            checkCompleteData();
        }
    }

    public static void updateMagData (float[] magData) {
        if (!magDataTaken) {
            vehicleDataRecord.setMagData(magData);
            magDataTaken = true;
            checkCompleteData();
        }
    }

    public static void updateGpsData (double latitude, double longitude, float speed) {
        lastLatitude = latitude;
        lastLongitude = longitude;
        lastSpeed = speed;
    }

    public static void checkCompleteData(){
        // boolean completeData= (accDataTaken && gyrDataTaken && magDataTaken && gpsDataTaken);
        if (accDataTaken && gyrDataTaken && magDataTaken) {
            vehicleDataRecord.setTimestamp();
            vehicleDataRecord.setGpsData(lastLatitude, lastLongitude, lastSpeed);
            saveData();
        }
    }

    private static void clearVehicleDataRecord () {
        accDataTaken = false;
        gyrDataTaken = false;
        magDataTaken = false;
        vehicleDataRecord = new VehicleData();
    }

    private static void saveData() {
        class SaveData extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                VehicleDatabaseSingleton.getDatabaseInstance()
                        .vehicleDataDAO().insertVehicleData(vehicleDataRecord);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                clearVehicleDataRecord();
                //Log.d("DATABASE: ", "Vehicle Record saved");
            }
        }
        SaveData saveData = new SaveData();
        saveData.execute();
    }


}
