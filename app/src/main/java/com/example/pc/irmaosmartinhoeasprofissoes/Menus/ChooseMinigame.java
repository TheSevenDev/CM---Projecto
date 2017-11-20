package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.firefighter.FirefighterActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.pilot.PilotActivity;

public class ChooseMinigame extends GeneralActivity {
    private ImageButton firefighter, baker, teacher, painter, pilot;
    private ImageView chooseMale, chooseFemale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_minigame);

        firefighter = (ImageButton)findViewById(R.id.imgbtn_firefighter);
        baker = (ImageButton)findViewById(R.id.imgbtn_baker);
        teacher = (ImageButton)findViewById(R.id.imgbtn_teacher);
        painter = (ImageButton)findViewById(R.id.imgbtn_painter);
        pilot = (ImageButton)findViewById(R.id.imgbtn_pilot);

        chooseMale = (ImageView)findViewById(R.id.img_chooseMale);
        chooseFemale = (ImageView)findViewById(R.id.img_chooseFemale);
        chooseFemale.setImageAlpha(50);

        SharedPreferences sharedPref = ChooseMinigame.this.
                getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_choose_minigame,null);

        if(sharedPref.getInt("gender",0) == 0)
            changeToMale(v);
        else
            changeToFemale(v);
    }


    public void backToMainMenu(View view){
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
    }

    public void fireFighterGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), FirefighterActivity.class));
    }

    public void pilotGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), PilotActivity.class));
    }

    public void changeToMale(View view){
        SharedPreferences sharedPref = ChooseMinigame.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", 0);
        editor.apply();

        chooseMale.setImageAlpha(255);
        chooseFemale.setImageAlpha(127);

        firefighter.setImageResource(R.drawable.jose_bombeiro);
        baker.setImageResource(R.drawable.jose_pasteleiro);
        teacher.setImageResource(R.drawable.jose_professor);
        painter.setImageResource(R.drawable.jose_pintor);
        pilot.setImageResource(R.drawable.jose_piloto);
    }

    public void changeToFemale(View view){
        SharedPreferences sharedPref = ChooseMinigame.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", 1);
        editor.apply();

        chooseFemale.setImageAlpha(255);
        chooseMale.setImageAlpha(127);

        baker.setImageResource(R.drawable.maria_pasteleira);
        painter.setImageResource(R.drawable.maria_pintora);
        pilot.setImageResource(R.drawable.maria_pilota);
        firefighter.setImageResource(R.drawable.maria_bombeira);
        teacher.setImageResource(R.drawable.maria_professora);
    }
}
