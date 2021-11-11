package com.tesis.vehicledatacollection.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class MagnetometerListener implements SensorEventListener {

    private final DecimalFormat formatNumbers = new DecimalFormat("##0.000");
    private final String MAGNETOMETER = "MAGNETOMETER";
    private ActivityMainBinding binding;

    public MagnetometerListener (ActivityMainBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TmpVehicleDataState.updateMagData(event.values);

        String [] data = new String[3];
        data[0] = formatNumbers.format(event.values[0]);
        data[1] = formatNumbers.format(event.values[1]);
        data[2] = formatNumbers.format(event.values[2]);

        this.showData(data, event.timestamp);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void showData(String[] data, long timestamp) {
        binding.xMValue .setText(data[0]);
        binding.yMValue.setText(data[1]);
        binding.zMValue.setText(data[2]);

        /* String dataMagnetometer = "Tmp: " + timestamp + "\t" +
                "xM = " + data[0] + "\t" +
                "yM = " + data[1] + "\t" +
                "zM = " + data[2]; */

        // Log.d(MAGNETOMETER, dataMagnetometer);
    }
}