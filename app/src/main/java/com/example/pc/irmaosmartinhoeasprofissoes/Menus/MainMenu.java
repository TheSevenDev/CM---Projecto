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
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainMenu extends GeneralActivity {

public static MediaPlayer mp;
public static boolean muted;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //if (!isServiceRunning(MusicService.class)) {
          //  startService(new Intent(this, MusicService.class));
        //}
        if(mp == null){
            mp = MediaPlayer.create(this, R.raw.mainmenu);
            mp.setLooping(true);
            mp.setVolume(100, 100);
            mp.start();
            muted = false;
        }


        if(!mp.isPlaying()){
            mp.seekTo(mp.getCurrentPosition() - 100);
            mp.start();
        }

        setContentView(R.layout.activity_mainmenu);
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
    protected  void onPause(){
        super.onPause();
        //stopService(new Intent(this, MusicService.class));
        mp.pause();
    }

    @Override
    protected  void onStop(){
        super.onStop();
        //stopService(new Intent(this, MusicService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mp.isPlaying()) {
            mp.seekTo(mp.getCurrentPosition()- 100);
            mp.start();
        }
        //startService(new Intent(this, MusicService.class));
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

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
