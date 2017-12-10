package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.firefighter.*;

/**
 * Atividade do jogo do piloto.
 */
public class PilotActivity extends AppCompatActivity {

    MediaPlayer mp;

    /**
     * Ao criar a atividade é posto o dispositivo no modo de ecrâ completo e é colocada a view do Painel de Jogo.
     * Para além disso é parado o serviço da música de fundo do menu principal e posta a tocar a
     * música temática do jogo do piloto.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hide navigationBar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(new GamePanel(this, this));

        stopService(new Intent(this, MusicService.class));

        mp = MediaPlayer.create(this, R.raw.pilot);
        mp.setLooping(true);
        mp.setVolume(100, 100);
        mp.start();
    }

    /**
     * Ao mudar o foco do ecrâ, não perder a característica de ecrâ inteiro.
     */
    @Override
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


    /**
     * Para a música
     */
    @Override
    protected void onPause() {
        mp.stop();
        super.onPause();
    }


    /**
     * Recomeça a música
     */
    @Override
    protected void onResume() {
        mp.start();
        super.onResume();
    }
}
