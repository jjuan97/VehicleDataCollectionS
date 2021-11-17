package com.tesis.vehicledatacollection;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.Manifest;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tesis.vehicledatacollection.classes.LastVehicleRecord;
import com.tesis.vehicledatacollection.database.VehicleDatabaseSingleton;
import com.tesis.vehicledatacollection.databinding.ActivityMainBinding;
import com.tesis.vehicledatacollection.listeners.GyroscopeListener;
import com.tesis.vehicledatacollection.listeners.MagnetometerListener;
import com.tesis.vehicledatacollection.listeners.AccelerometerListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.tesis.vehicledatacollection.listeners.SavingDataTask;
import com.tesis.vehicledatacollection.listeners.TmpVehicleDataState;
import com.tesis.vehicledatacollection.viewmodels.VehicleDataViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    String frequencyHz;
    private boolean recording = false;
    private boolean recordingNC = false;
    private final int FREQUENCYHz = 20;
    private final long gpsInterval = 1000/ FREQUENCYHz;

    // Saving into DB task
    TimerTask savingDataTask;
    Timer timer;

    VehicleDataViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Creating Database Instance
        VehicleDatabaseSingleton.createDatabaseInstance(getApplicationContext());
        model = new ViewModelProvider(this).get(VehicleDataViewModel.class);

        // Sensors Accelerometer, Gyroscope, Magnetometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Listener objects
        accelerometerListener = new AccelerometerListener(binding);
        gyroscopeListener = new GyroscopeListener(binding);
        magnetometerListener = new MagnetometerListener(binding);

        // GPS client provider
        gpsClientProvider();

        // Check for Accelerometer sensor in device
        checkSensors();

        // Button listener
        binding.buttonStartStop.setOnClickListener(view1 -> {

            // Capture number in frequency edit text
            frequencyHz = binding.tripFrecuency.getText().toString();
            String idVehicle = binding.idVehicle.getText().toString();

            // Check void frequency
            if(frequencyHz.equals("") ){
                Toast.makeText(this, getText(R.string.error_frequency), Toast.LENGTH_LONG).show();
            }
            else if(idVehicle.equals("")){
                Toast.makeText(this, getText(R.string.error_vehicle_id), Toast.LENGTH_LONG).show();
            }
            else {
                recording = !recording;
                String buttonMsg = recording ? getString(R.string.stop) : getString(R.string.start);
                binding.buttonStartStop.setText(buttonMsg);

                if (recording) {
                    // Transform frequency edit text to int
                    int f = Integer.parseInt(frequencyHz);

                    TmpVehicleDataState.setIdVehicle(idVehicle);
                    model.getLastRecord().subscribeOn(Schedulers.io())
                        .subscribe(
                            (lastRecord) -> {
                                int lastIdTrip = (lastRecord.size() == 0) ? -1 : lastRecord.get(0).idTrip;
                                Log.d("Database", "Last idTrip: " + lastIdTrip + "\n" +
                                        "Current idTrip: " + (lastIdTrip+1) );
                                TmpVehicleDataState.setIdTrip( lastIdTrip + 1 );
                                startRecordingData(f);
                            } );

                } else {
                    timer.cancel();
                    sensorManager.unregisterListener(accelerometerListener);
                    sensorManager.unregisterListener(gyroscopeListener);
                    sensorManager.unregisterListener(magnetometerListener);
                    stopLocationUpdates();
                }
            }
        });

        // Button register near-crash
        binding.buttonRegisterNc.setOnClickListener(viewNC -> {

            recordingNC = !recordingNC;
            String buttonMsg = recordingNC ? getString(R.string.stop_nc) : getString(R.string.start_nc);
            binding.buttonRegisterNc.setText(buttonMsg);
            TmpVehicleDataState.setEventClass(recordingNC);

        });

        // Go to Trip History
        binding.tripHistory.setOnClickListener(view1 -> {
            Intent toTripHistory = new Intent(this, TripLog.class);
            startActivity(toTripHistory);
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

    private void startRecordingData(int f){
        sensorManager.registerListener(accelerometerListener, accelerometer, 1000000 / FREQUENCYHz);
        sensorManager.registerListener(gyroscopeListener, gyroscope, 1000000 / FREQUENCYHz);
        sensorManager.registerListener(magnetometerListener, magnetometer, 1000000 / FREQUENCYHz);
        startLocationUpdates();

        savingDataTask = new SavingDataTask();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(savingDataTask, 250, 1000/ f);
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
                    /* String gpsData = "Latitude: " + latitude + "\t" +
                            "Longitude: " + longitude + "\t" +
                            "speed: " + speed + "\n";
                    Log.d("GPS", gpsData); */

                    TmpVehicleDataState.updateGpsData(latitude, longitude, speed);

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
            Log.d("Accelerometer Sensor", accelerometerSensors.toString());
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