package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

public class MainMenu extends GeneralActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isServiceRunning(MusicService.class)) {
            startService(new Intent(this, MusicService.class));
        }
        setContentView(R.layout.activity_mainmenu);
    }

    public void chooseMinigameScreen(View view)
    {
        startActivity(new Intent(getApplicationContext(), ChooseMinigame.class));
    }


    public void settings(View view)
    {
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
}
