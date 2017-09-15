package com.example.archit.droidsensors;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView xText, yText, zText, lText, tText;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private Sensor acceleroSensor;
    private Sensor lightSensor;
    private Sensor tempSensor;
    private SensorEventListener proximitySensorListener;
    private SensorEventListener acceleroSensorListener;
    private SensorEventListener lightSensorListener;
    private SensorEventListener tempSensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        acceleroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if (proximitySensor == null) {
            Toast.makeText(this, "Proximity sensor is not available !", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (acceleroSensor == null) {
            Toast.makeText(this, "Accelerometer is not available !", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (lightSensor == null) {
            Toast.makeText(this, "Photometer is not available !", Toast.LENGTH_SHORT).show();
            finish();
        }


        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //For proximity change
                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
            }
        };

        acceleroSensorListener = new SensorEventListener(){
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                xText.setText("X: " + sensorEvent.values[0] );
                yText.setText("Y: " + sensorEvent.values[1] );
                zText.setText("Z: " + sensorEvent.values[2] );
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                lText.setText("Lumosity: " + sensorEvent.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        tempSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                tText.setText("Temp (C): " + sensorEvent.values[0] );

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        tText = (TextView)findViewById(R.id.tText);
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
        lText = (TextView)findViewById(R.id.lText);

        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2 * 1000 * 1000 );
        sensorManager.registerListener(acceleroSensorListener, acceleroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(tempSensorListener, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
        sensorManager.unregisterListener(acceleroSensorListener);
        sensorManager.unregisterListener(lightSensorListener);
        sensorManager.unregisterListener(tempSensorListener);
    }
}
