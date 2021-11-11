package com.tesis.vehicledatacollection.listeners;

import android.os.AsyncTask;
import android.util.Log;

import com.tesis.vehicledatacollection.database.VehicleDatabaseSingleton;

import java.util.TimerTask;

public class SavingDataTask extends TimerTask {

    @Override
    public void run() {
        class SaveData extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                // Saving the state
                VehicleDatabaseSingleton
                        .getDatabaseInstance()
                        .vehicleDataDAO()
                        .insertVehicleData( TmpVehicleDataState.getCurrentVehicleState() );
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // Log.d("DATABASE: ", "Vehicle Record saved");
            }
        }
        SaveData saveData = new SaveData();
        saveData.execute();
    }
}
