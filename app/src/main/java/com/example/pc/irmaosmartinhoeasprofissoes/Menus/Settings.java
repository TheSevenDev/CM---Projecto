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

    public void manageAudio(){
        System.out.print("Tenta");
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        System.out.print("Consegue" + manager);
        if(manager.getStreamVolume(AudioManager.STREAM_SYSTEM) > 0){
            System.out.print("Dá mute");
            //manager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
        }else{
            System.out.print("No mute");
           // manager.setStreamVolume(AudioManager.STREAM_SYSTEM, 100,0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MusicService.class));
    }
}
