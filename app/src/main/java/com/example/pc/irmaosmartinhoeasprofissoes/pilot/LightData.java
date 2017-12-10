package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Classe que permite a leitura do sensor de luz do dispositivo.
 */

public class LightData implements SensorEventListener {
    private SensorManager manager;
    private Sensor light;
    private Context context;
    private float lightValue;


    public LightData(Context context){
        manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        light = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    public void register(){
        manager.registerListener(this, light, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            lightValue = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getLightValue(){
        return lightValue;
    }
}
