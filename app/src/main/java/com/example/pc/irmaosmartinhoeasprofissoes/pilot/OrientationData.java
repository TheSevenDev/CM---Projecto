package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Bruno on 29/10/2017.
 */

public class OrientationData implements SensorEventListener {
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    //store data
    private float[] accelOutput;
    private float[] magOutput;

    private float[] orientation = new float[3];
    public float[] getOrientation(){
        return orientation;
    }

    private float[] startOrientation = null;
    public float[] getStartOrientation(){
        return startOrientation;
    }

    //Track startOrientation everytime you (re)start the game
    public void newGame(){
        startOrientation = null;
    }


    public OrientationData(Context context){
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register(){
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelOutput = event.values;
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;

        if(accelOutput != null && magOutput != null){
            float[] R = new float[9];//3x3 matrix
            float[] I = new float[9];//3x3 matrix

            boolean success = SensorManager.getRotationMatrix(R, I , accelOutput, magOutput);
            if(success){
                SensorManager.getOrientation(R, orientation);
                if(startOrientation == null){
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0 , orientation.length);
                }
            }
        }
    }
}
