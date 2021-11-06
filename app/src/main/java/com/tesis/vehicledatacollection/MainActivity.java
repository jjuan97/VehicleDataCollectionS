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

public class MainActivity extends AppCompatActivity {

    private final int PERIODOMS = 20;
    private final String GYROSCOPE = "GYROSCOPE";

    private ActivityMainBinding binding;
    private DecimalFormat formatNumbers = new DecimalFormat("##0.000");
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private GyroscopeListener gyroscopeListener = new GyroscopeListener();
    private boolean recording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View  view = binding.getRoot();
        setContentView(view);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        binding.buttonStartStop.setOnClickListener(view1 -> {
            recording = !recording;
            String buttonMsg = recording ? getString(R.string.stop) : getString(R.string.start);
            binding.buttonStartStop.setText(buttonMsg);

            if (recording){
                sensorManager.registerListener(gyroscopeListener, gyroscope, PERIODOMS*1000);
            }
            else{
                sensorManager.unregisterListener(gyroscopeListener);
            }
            //String texto = binding.xAValue.getText().toString();
            //Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
        });
    }


    private class GyroscopeListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event) {
            // The light sensor returns a single value.
            // Many sensors return 3 values, one for each axis.
            String xG = formatNumbers.format(event.values[0]);
            String yG = formatNumbers.format(event.values[1]);
            String zG = formatNumbers.format(event.values[2]);
            long timestamp = event.timestamp;

            binding.xGValue.setText(xG);
            binding.yGValue.setText(yG);
            binding.zGValue.setText(zG);

            String dataGyroscope = "Tmp: " + timestamp + "\n" +
                    "xG = " + xG + "\n" +
                    "yG = " + yG + "\n" +
                    "zG = " + zG;

            Log.d(GYROSCOPE, dataGyroscope);
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

}