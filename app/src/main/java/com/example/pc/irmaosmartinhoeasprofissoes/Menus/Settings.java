package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MusicService.class));
    }
}
