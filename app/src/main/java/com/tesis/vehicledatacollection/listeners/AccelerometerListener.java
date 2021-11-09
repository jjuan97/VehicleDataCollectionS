package com.tesis.vehicledatacollection.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class AccelerometerListener implements SensorEventListener {
    private final String ACCELEROMETER = "ACCELEROMETER";

    //Variables that define how the data is captured.
    private DecimalFormat formatNumbers = new DecimalFormat("##0.000");
    private ActivityMainBinding binding;

    //Constructor
    public AccelerometerListener(ActivityMainBinding binding){
        this.binding = binding;
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        //The accelerometer return 3 values, one for each axis.
        float xA = event.values[0];
        float yA = event.values[1];
        float zA = event.values[2];

        //event can return timestamp
        Long time = event.timestamp;

        //Do something with this sensor value.
        String [] data = new String[3];
        data[0] = formatNumbers.format(xA);
        data[1] = formatNumbers.format(yA);
        data[2] = formatNumbers.format(zA);

        showData(data, time);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    private void showData(String[] data, long timestamp) {
        binding.xAValue.setText(data[0]);
        binding.yAValue.setText(data[1]);
        binding.zAValue.setText(data[2]);

        String dataAccelerometer = "Tmp: " + timestamp + "\t" +
                "xA = " + data[0] + "\t" +
                "yA = " + data[1] + "\t" +
                "zA = " + data[2];

        Log.d(ACCELEROMETER, dataAccelerometer);
    }
}
