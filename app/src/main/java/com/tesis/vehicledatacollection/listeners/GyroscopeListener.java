package com.tesis.vehicledatacollection.listeners;

import static com.tesis.vehicledatacollection.listeners.VehicleDataState.updateGyrData;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class GyroscopeListener implements SensorEventListener {

    private final String GYROSCOPE = "GYROSCOPE";
    private final DecimalFormat formatNumbers = new DecimalFormat("##0.000");
    private ActivityMainBinding binding;

    public GyroscopeListener(ActivityMainBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateGyrData(event.values);
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
        binding.xGValue.setText(data[0]);
        binding.yGValue.setText(data[1]);
        binding.zGValue.setText(data[2]);

        /* String dataGyroscope = "Tmp: " + timestamp + "\t" +
                "xG = " + data[0] + "\t" +
                "yG = " + data[1] + "\t" +
                "zG = " + data[2]; */

        // Log.d(GYROSCOPE, dataGyroscope);
    }
}