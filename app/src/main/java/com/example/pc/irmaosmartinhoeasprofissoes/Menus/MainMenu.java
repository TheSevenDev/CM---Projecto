package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainMenu extends GeneralActivity implements LocationListener {

    private LocationManager lm;
    private float lastLongitude;
    private float lastLatitude;

    private Location lastKnownLocation;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isServiceRunning(MusicService.class)) {
            startService(new Intent(this, MusicService.class));
        }

        setContentView(R.layout.activity_mainmenu);

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        SharedPreferences sharedPref = null;
        try{
            lm.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,null);
            lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //SAVE LAST LOCATION
            sharedPref = MainMenu.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putFloat("lastLatitude", (float)lastKnownLocation.getLatitude());
            editor.putFloat("lastLongitude", (float)lastKnownLocation.getLongitude());
            editor.apply();
            System.out.println("=============LATITUDE=============" + lastKnownLocation.getLatitude());
            System.out.println("=============LONGITUDE=============" + lastKnownLocation.getLongitude());
        }
        catch (Exception e){

        }
    }

    public void chooseMinigameScreen(View view) {
        startActivity(new Intent(getApplicationContext(), ChooseMinigame.class));
    }


    public void gameSettings(View view) {
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    /*//ONPAUSE() OU ONSTOP()
    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        stopService(new Intent(this, MusicService.class));
    }
    */
    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MusicService.class));
    }

    //NO ACTION FOR BACK BUTTON
    @Override
    public void onBackPressed() {

    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
