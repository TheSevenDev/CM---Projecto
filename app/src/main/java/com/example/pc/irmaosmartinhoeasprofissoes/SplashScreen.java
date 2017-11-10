package com.example.pc.irmaosmartinhoeasprofissoes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //Fullscreen app
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Navigation Bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_splashscreen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    SharedPreferences sharedPref = SplashScreen.this.
                            getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

                    Intent intent = null;

                    if(sharedPref.contains("gender"))
                    {
                        intent = new Intent(getApplicationContext(), MainMenu.class);
                    }
                    else
                    {
                        intent = new Intent(getApplicationContext(), ChooseGender.class);
                    }

                    startActivity(intent);
                    finish();
                }
            }
        };
        myThread.start();
    }
}
