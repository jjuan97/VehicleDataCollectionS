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

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    Sensor mAccelerometerSensor;
    int hertz = 50;
    private boolean recording = false;
    private DecimalFormat formatNumbers = new DecimalFormat("##0.000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View  view = binding.getRoot();
        setContentView(view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL); //Get a list of all Sensors in the device
        mAccelerometerSensor = null;

        //Check for Accelerometer sensor
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){

            //Check the different Accelerometer sensors in this device
            List<Sensor> accelerometerSensors = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
            Log.d("sensor",accelerometerSensors.toString());

            mAccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            Log.d("error_sensor","No accelerometer sensor in this device");
        }

        //button to make capture action
        binding.buttonStartStop.setOnClickListener(view1 -> {
            recording = !recording;
            String buttonMsg = recording ? "PARAR" : "COMENZAR";
            binding.buttonStartStop.setText(buttonMsg);

            if (recording){
                sensorManager.registerListener(this, mAccelerometerSensor, 1000000/hertz);
            }
            else{
                sensorManager.unregisterListener(this);
            }
        });

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The accelerometer return 3 values, one for each axis.
        float xA = event.values[0];
        float yA = event.values[1];
        float zA = event.values[2];
        // Do something with this sensor value.

        Long time = event.timestamp;

        //
        binding.xAValue.setText(formatNumbers.format(xA));
        binding.yAValue.setText(formatNumbers.format(yA));
        binding.zAValue.setText(formatNumbers.format(zA));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}