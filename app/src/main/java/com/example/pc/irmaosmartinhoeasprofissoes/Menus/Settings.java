package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Set;

public class Settings extends GeneralActivity {
private boolean muted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getAudioState();

    }

    public void backToMainMenu(View view){
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
    }

    public void tracker(View view){
        startActivity(new Intent(getApplicationContext(),  Tracker.class));
    }

    public void credits(View view){
        startActivity(new Intent(getApplicationContext(), Credits.class));
    }

    private void getAudioState(){
        ImageView audioIcon = (ImageButton) findViewById(R.id.volumeIcon);
        if(muted){
            audioIcon.setImageResource(R.drawable.volume_down);
        }else{
            audioIcon.setImageResource(R.drawable.volume_up);
        }
    }

    public void manageAudio(View view){
        SharedPreferences sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(!sharedPref.contains("musicState"))
        {

            editor.putBoolean("musicState", false);

        }

        ImageView audioIcon = (ImageButton) findViewById(R.id.volumeIcon);
        if(muted){
            MainMenu.mp.setVolume(1,1);
            muted = false;
            audioIcon.setImageResource(R.drawable.volume_up);
        }else{
            MainMenu.mp.setVolume(0,0);
            muted = true;
            editor.putBoolean("musicState", true);
            audioIcon.setImageResource(R.drawable.volume_down);
        }
        editor.apply();

    }



    @Override
    protected void onResume() {
        super.onResume();
        //if(!isServiceRunning(MusicService.class)){
        //    startService(new Intent(this, MusicService.class));
        //}
        getAudioState();
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
