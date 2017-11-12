package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by Diogo on 10/11/2017.
 */

public class MainMenu extends GeneralActivity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp = MediaPlayer.create(MainMenu.this,R.raw.mainmenu);
        mp.setLooping(true);
        mp.start();


        setContentView(R.layout.activity_mainmenu);
    }

    public void chooseMinigameScreen(View view)
    {
        startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.Menus.ChooseMinigame.class));
    }
}

