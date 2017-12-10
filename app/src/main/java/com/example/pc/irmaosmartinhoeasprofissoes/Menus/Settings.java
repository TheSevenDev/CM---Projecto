package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.Tracker;

/**
 * Gere as definições da aplicaçao
 */

public class Settings extends GeneralActivity {
    private ImageView audioIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        audioIcon = (ImageButton) findViewById(R.id.volumeIcon);
        getAudioState();

    }

    //Regressa ao menu principal
    public void backToMainMenu(View view){
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
    }

    //Inicia a actividade de tracker gps
    public void tracker(View view){
        startActivity(new Intent(getApplicationContext(),  Tracker.class));
    }

    //Apresenta os creditos da aplicaçao
    public void credits(View view){
        startActivity(new Intent(getApplicationContext(), Credits.class));
    }

    //Retorna o estado do audio
    private void getAudioState(){
        if(MainMenu.muted){
            audioIcon.setImageResource(R.drawable.volume_down);
        }else{
            audioIcon.setImageResource(R.drawable.volume_up);
        }
    }

    //Gere o audio da aplicaçao - activa/desactiva
    public void manageAudio(View view){
        if(MainMenu.muted){
            MainMenu.mp.setVolume(1,1);
            MainMenu.muted = false;
            audioIcon.setImageResource(R.drawable.volume_up);
        }else{
            MainMenu.mp.setVolume(0,0);
            MainMenu.muted = true;
            audioIcon.setImageResource(R.drawable.volume_down);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

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
