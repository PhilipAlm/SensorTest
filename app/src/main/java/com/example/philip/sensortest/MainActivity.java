package com.example.philip.sensortest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView mPointer;
    private TextView deg;
    private Sensor mSensor;
    private Sensor aSensor;
    private SensorManager SM;
    private boolean aSensorSet = false;
    private boolean mSensorSet = false;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensor = SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        aSensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        deg = (TextView) findViewById(R.id.degText);
        mPointer = (ImageView) findViewById(R.id.imageView);

    }


    protected void onResume() {
        super.onResume();
        SM.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        SM.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_GAME);
    }


    protected void onPause() {
        super.onPause();
        SM.unregisterListener(this, mSensor);
        SM.unregisterListener(this, aSensor);
    }

    public void startAccelerometer(View view) {
        Intent intent = new Intent(this, Accelerometer.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == aSensor) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            aSensorSet = true;
        } else if (event.sensor == mSensor) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mSensorSet = true;
        }
        if (aSensorSet && mSensorSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float) (Math.toDegrees(azimuthInRadians) + 360) % 360;

            mCurrentDegree = -azimuthInDegress;
            deg.setText("deg: " +azimuthInDegress);
            RotateAnimation ra = new RotateAnimation(
                    mCurrentDegree,
                    -azimuthInDegress,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            ra.setDuration(250);

            ra.setFillAfter(true);

            mPointer.startAnimation(ra);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
