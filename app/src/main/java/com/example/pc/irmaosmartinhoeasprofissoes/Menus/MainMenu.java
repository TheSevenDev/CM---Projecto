package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

public class MainMenu extends GeneralActivity {

public static MediaPlayer mp;
public static boolean muted;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    //Muda para a actividade de escolher um mini-jogo
    public void chooseMinigameScreen(View view) {
        startActivity(new Intent(getApplicationContext(), ChooseMinigame.class));
    }

    //Muda para a actividade de definiçoes de jogo
    public void gameSettings(View view) {
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

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
    }

    //Botão de voltar atrás não faz nada
    @Override
    public void onBackPressed() {

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
