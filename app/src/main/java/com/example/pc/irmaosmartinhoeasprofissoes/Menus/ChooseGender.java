package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Cria a atividade do menu para escolher o género.
 * Este menu só é mostrado caso a aplicação esteja a ser executada pela primeira vez no dispositivo.
 */
public class ChooseGender extends GeneralActivity {

    /**
     * Corre o splashscreen durante 500 milisegundos(no mínimo).
     * Caso já exista informação acerca do género escolhido é iniciada a atividade do mainmenu.
     */
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
            goToMainMenu();
        } else {
            setContentView(R.layout.activity_choose_gender);
        }


    }

    /**
     * Guarda a informação que o género escolhido pelo utilizador é masculino.
     */
    public void chooseMale(View view)
    {
        SharedPreferences sharedPref = ChooseGender.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", Integer.parseInt(this.getResources().getString(R.string.choose_male)));
        editor.apply();
        goToMainMenu();
    }

    /**
     * Guarda a informação que o género escolhido pelo utilizador é feminino.
     */
    public void chooseFemale(View view)
    {
        SharedPreferences sharedPref = ChooseGender.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", Integer.parseInt(this.getResources().getString(R.string.choose_female)));
        editor.apply();
        goToMainMenu();
    }

    /**
     * Inicia a actividade do menu principal.
     */
    public void goToMainMenu()
    {
        startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.Menus.MainMenu.class));
    }
}
