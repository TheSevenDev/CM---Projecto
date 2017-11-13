package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.io.IOException;

/**
 * Created by Diogo on 10/11/2017.
 */

public class MainMenu extends GeneralActivity {

    private static MediaPlayer mp;
    private static boolean isMusicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp = MediaPlayer.create(this, R.raw.mainmenu);
        mp.setLooping(true); // Set looping
        mp.setVolume(100,100);

        if(!isMusicPlaying)
            playMenuMusic();

        setContentView(R.layout.activity_mainmenu);
    }

    public void playMenuMusic()
    {
        mp.start();
        isMusicPlaying = true;
    }

    public void stopMenuMusic()
    {
        mp.stop();
        isMusicPlaying = false;
    }


    public void chooseMinigameScreen(View view)
    {
        startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.Menus.ChooseMinigame.class));
    }


}

