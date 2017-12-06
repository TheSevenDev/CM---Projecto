package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.Tracker;

public class Settings extends GeneralActivity {
private boolean muted = false;
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

        ImageView audioIcon = (ImageButton) findViewById(R.id.volumeIcon);
        if(muted){
            MainMenu.mp.setVolume(100,100);
            muted = false;
            audioIcon.setImageResource(R.drawable.volume_up);
        }else{
            MainMenu.mp.setVolume(0,0);
            muted = true;
            audioIcon.setImageResource(R.drawable.volume_down);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //if(!isServiceRunning(MusicService.class)){
        //    startService(new Intent(this, MusicService.class));
        //}
        if(!MainMenu.mp.isPlaying()) {
            MainMenu.mp.seekTo(MainMenu.mp.getCurrentPosition() - 100);
            MainMenu.mp.start();
        }
    }
    @Override
    protected  void onPause(){
        super.onPause();
        MainMenu.mp.pause();
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        MainMenu.mp.stop();
        MainMenu.mp = null;
    }
}
