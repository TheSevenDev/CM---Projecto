package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

public class ChooseMinigame extends GeneralActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_minigame);
    }

    public void fireFighterGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.firefighter.FirefighterActivity.class));
    }
}
