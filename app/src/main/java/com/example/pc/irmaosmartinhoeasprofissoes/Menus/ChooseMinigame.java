package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

public class ChooseMinigame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_minigame);
    }

    public void fireFighterGame()
    {
        startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.firefighter.FirefighterActivity.class));
    }
}
