package com.tesis.vehicledatacollection;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import android.util.Log;
import android.view.View;

import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;
import com.tesis.vehicledatacollection.listeners.GyroscopeListener;
import com.tesis.vehicledatacollection.listeners.MagnetometerListener;
import com.tesis.vehicledatacollection.listeners.AccelerometerListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // Variables for kinematic data.
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor magnetometer;
    private AccelerometerListener accelerometerListener;
    private GyroscopeListener gyroscopeListener;
    private MagnetometerListener magnetometerListener;

    // Variables for GPS data.
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallback;
    private ActivityResultLauncher<String> locationPermissionRequest;
    double latitude, longitude, altitude;
    float speed;

    // Variables that define how the data is captured.
    private boolean recording = false;
    private final int FRECUENCYHz = 50;
    // Milli seconds. TODO: check what interval is the correct
    private final long gpsInterval = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Sensors Accelerometer, Gyroscope, Magnetometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Listener objects
        accelerometerListener = new AccelerometerListener(binding, this);
        gyroscopeListener = new GyroscopeListener(binding);
        magnetometerListener = new MagnetometerListener(binding);

        // GPS client provider
        gpsClientProvider();

        // Check for Accelerometer sensor in device
        checkSensors();

        // Button listener
        binding.buttonStartStop.setOnClickListener(view1 -> {
            recording = !recording;
            String buttonMsg = recording ? getString(R.string.stop) : getString(R.string.start);
            binding.buttonStartStop.setText(buttonMsg);

            if (recording) {
                sensorManager.registerListener(accelerometerListener, accelerometer, 1000000 / FRECUENCYHz);
                sensorManager.registerListener(gyroscopeListener, gyroscope, 1000000 / FRECUENCYHz);
                sensorManager.registerListener(magnetometerListener, magnetometer, 1000000 / FRECUENCYHz);
                startLocationUpdates();
            } else {
                sensorManager.unregisterListener(accelerometerListener);
                sensorManager.unregisterListener(gyroscopeListener);
                sensorManager.unregisterListener(magnetometerListener);
                stopLocationUpdates();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // GPS functions
    private void gpsClientProvider() {

        // Define parameters to location request
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = LocationRequest.create()
                .setFastestInterval(gpsInterval)
                .setMaxWaitTime(gpsInterval)
                .setInterval(gpsInterval)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Check permissions
        locationPermissionRequest = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
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

        //Define location callback, in this section the changing values of the location are shown.
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
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
                        speed = 0.0f;
                    }
                    String gpsData = "Latitude: " + latitude + "\t" +
                            "Longitude: " + longitude + "\t" +
                            "speed: " + speed + "\n";

                    Log.d("GPS", gpsData);
                    binding.latitudeValue.setText(String.valueOf(latitude));
                    binding.longitudeValue.setText(String.valueOf(longitude));
                    binding.altitudeValue.setText(String.valueOf(altitude));
                    // TODO: transform to km/h actually is m/s
                    binding.speedValue.setText(String.valueOf(speed));
                }
            }
        };
    }

    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.getMainLooper());
        }
        else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            locationPermissionRequest.launch(
                Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void checkSensors() {
        // Get a list of all Sensors in the device
        // List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // Check the different Accelerometer sensors in this device
            List<Sensor> accelerometerSensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            Log.d("Acelerometer Sensor", accelerometerSensors.toString());
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            Log.d("error_sensor", "No accelerometer sensor in this device");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            List<Sensor> gyroscopeSensors = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
            Log.d("Gyroscope Sensor", gyroscopeSensors.toString());
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        } else {
            Log.d("error_sensor", "No gyroscope sensor in this device");
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            List<Sensor> magnetometerSensors = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
            Log.d("Magnetometer Sensor", magnetometerSensors.toString());
            magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        } else {
            Log.d("error_sensor", "No magnetometer sensor in this device");
        }
    }

}