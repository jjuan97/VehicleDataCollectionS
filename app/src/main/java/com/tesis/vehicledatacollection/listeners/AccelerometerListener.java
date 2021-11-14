package com.tesis.vehicledatacollection.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class AccelerometerListener implements SensorEventListener {
    private final String ACCELEROMETER = "ACCELEROMETER";

    private final DecimalFormat formatNumbers = new DecimalFormat("##0.000");
    private final ActivityMainBinding binding;

    public AccelerometerListener(ActivityMainBinding binding){
        this.binding = binding;
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        TmpVehicleDataState.updateAccData(event.values);

        float xA = event.values[0];
        float yA = event.values[1];
        float zA = event.values[2];

        //Do something with this sensor value.
        String [] data = new String[3];
        data[0] = formatNumbers.format(xA);
        data[1] = formatNumbers.format(yA);
        data[2] = formatNumbers.format(zA);

        showData(data, event.timestamp);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    private void showData(String[] data, long timestamp) {
        binding.xAValue.setText(data[0]);
        binding.yAValue.setText(data[1]);
        binding.zAValue.setText(data[2]);

    }
}
