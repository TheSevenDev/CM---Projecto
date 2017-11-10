package com.example.pc.irmaosmartinhoeasprofissoes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ChooseGender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Navigation Bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setTheme(R.style.AppTheme);


        SharedPreferences sharedPref = ChooseGender.this.
                getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        if (sharedPref.contains("gender")) {
            startActivity(new Intent(getApplicationContext(), com.example.pc.irmaosmartinhoeasprofissoes.MainMenu.class));
        } else {
            setContentView(R.layout.activity_choose_gender);
        }
    }

    public void chooseMale()
    {
        /*SharedPreferences sharedPref = ChooseGender.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", 0);
        editor.apply();*/
    }

    public void chooseFemale()
    {
        /*SharedPreferences sharedPref = ChooseGender.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", 1);
        editor.apply();*/
    }
}
