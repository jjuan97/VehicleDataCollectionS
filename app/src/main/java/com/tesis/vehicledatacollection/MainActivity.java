package com.tesis.vehicledatacollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;
import com.tesis.vehicledatacollection.listeners.GyroscopeListener;
import com.tesis.vehicledatacollection.listeners.MagnetometerListener;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private final int PERIODOmS = 100;

    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Sensor magnetometer;
    private GyroscopeListener gyroscopeListener;
    private MagnetometerListener magnetometerListener;
    private boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View  view = binding.getRoot();
        setContentView(view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        gyroscopeListener = new GyroscopeListener(binding);
        magnetometerListener = new MagnetometerListener(binding);

        binding.buttonStartStop.setOnClickListener(view1 -> {
            recording = !recording;
            String buttonMsg = recording ? getString(R.string.stop) : getString(R.string.start);
            binding.buttonStartStop.setText(buttonMsg);

            if (recording){
                sensorManager.registerListener(gyroscopeListener, gyroscope, PERIODOmS*1000);
                sensorManager.registerListener(magnetometerListener, magnetometer, PERIODOmS*1000);
            }
            else{
                sensorManager.unregisterListener(gyroscopeListener);
                sensorManager.unregisterListener(magnetometerListener);
            }
        });
    }


}