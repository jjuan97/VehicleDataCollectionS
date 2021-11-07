package com.tesis.vehicledatacollection;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    Sensor mAccelerometerSensor;
    int hertz = 50; // Hertz
    long gpsInterval = 500; // miliseconds TODO: check what interval is the correct
    private boolean recording = false;
    private DecimalFormat formatNumbers = new DecimalFormat("##0.000");

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallback;
    private ActivityResultLauncher<String> locationPermissionRequest;
    double latitude, longitude, altitude;
    float speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //sensors Accelerometer, Gyroscope, Magnetometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL); //Get a list of all Sensors in the device
        mAccelerometerSensor = null;

        //GPS client provider
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = LocationRequest.create()
                .setInterval(gpsInterval)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                            Log.d("permissions", "Checking");
                            if (isGranted) {
                                // Precise location access granted.
                                Log.d("permissions", "Yes");
                            } else {
                                // No location access granted.
                                Log.d("permissions", "No");
                            }
                        }
                );

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    if (location != null) {
                        // Logic to handle location object
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        altitude = location.getAltitude();
                        speed = location.getSpeed();
                    } else {
                        latitude = 0.0;
                        longitude = 0.0;
                        altitude = 0.0;
                        speed = 0.1f;
                    }
                    Log.d("permissions1", String.valueOf(latitude));
                    binding.latitudeValue.setText(String.valueOf(latitude));
                    binding.longitudeValue.setText(String.valueOf(longitude));
                    binding.altitudeValue.setText(String.valueOf(altitude));
                    binding.speedValue.setText(String.valueOf(speed));  //TODO: transform to km/h actually is m/s
                }
            }
        };


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
                startLocationUpdates();
            }
            else{
                sensorManager.unregisterListener(this);
                stopLocationUpdates();
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

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(mLocationRequest,
                        locationCallback,
                        Looper.getMainLooper());
        } else {
        // You can directly ask for the permission.
        // The registered ActivityResultCallback gets the result of this request.
        locationPermissionRequest.launch(
                Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}