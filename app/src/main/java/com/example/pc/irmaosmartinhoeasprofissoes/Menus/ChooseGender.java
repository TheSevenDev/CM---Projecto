package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

public class ChooseGender extends GeneralActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPref = ChooseGender.this.
                getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        if (sharedPref.contains("gender")) {
            startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.Menus.MainMenu.class));
        } else {
            setContentView(R.layout.activity_choose_gender);
        }


    }

    public void chooseMale(View view)
    {
        SharedPreferences sharedPref = ChooseGender.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", Integer.parseInt(this.getResources().getString(R.string.choose_male)));
        editor.apply();
        goToMainMenu();
    }

    public void chooseFemale(View view)
    {
        SharedPreferences sharedPref = ChooseGender.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", Integer.parseInt(this.getResources().getString(R.string.choose_female)));
        editor.apply();
        goToMainMenu();
    }

    public void goToMainMenu()
    {
        startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.Menus.MainMenu.class));
    }
}
