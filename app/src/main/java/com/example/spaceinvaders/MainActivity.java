package com.example.spaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
         implements SensorEventListener {

    private MyView vista;
    private SensorManager sensorMgr;
    private Sensor sensorAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // instanciar los objetos para el sensor manager
        sensorMgr = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        sensorAcc =
            sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMgr.registerListener(this, sensorAcc,
                SensorManager.SENSOR_DELAY_NORMAL);

        vista = new MyView(this);
        setContentView(vista);

        //getSupportActionBar().hide();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor miSensor = sensorEvent.sensor;

        if(miSensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            vista.actualizaX(x); // método en clase MyView
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}