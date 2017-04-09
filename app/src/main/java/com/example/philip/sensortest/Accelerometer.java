package com.example.philip.sensortest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity implements SensorEventListener{

    private TextView x, y, z;
    private Sensor sensor;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SM.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);


        x = (TextView)findViewById(R.id.xText);
        y = (TextView)findViewById(R.id.yText);
        z = (TextView)findViewById(R.id.zText);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values!=null) {
            x.setText("X: " + event.values[0]);
            y.setText("Y: " + event.values[1]);
            z.setText("Z: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
