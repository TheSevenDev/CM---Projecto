package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.Tracker;

public class Settings extends GeneralActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }

    public void backToMainMenu(View view){
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
    }

    public void tracker(View view){
        startActivity(new Intent(getApplicationContext(),  Tracker.class));
    }

    public void manageAudio(View view){

        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if(manager.getStreamVolume(AudioManager.STREAM_SYSTEM) > 0){
            manager.adjustVolume(AudioManager.ADJUST_MUTE,0);
        }else{
            manager.adjustVolume(AudioManager.ADJUST_UNMUTE,0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MusicService.class));
    }
}
