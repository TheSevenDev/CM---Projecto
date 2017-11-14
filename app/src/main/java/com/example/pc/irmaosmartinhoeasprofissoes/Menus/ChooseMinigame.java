package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.firefighter.FirefighterActivity;

public class ChooseMinigame extends GeneralActivity {
    private ImageButton firefighter, baker, teacher, painter, pilot;
    private ImageButton chooseMale, chooseFemale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_minigame);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_choose_minigame,null);

        firefighter = (ImageButton)v.findViewById(R.id.imgbtn_firefighter);
        baker = (ImageButton)v.findViewById(R.id.imgbtn_baker);
        teacher = (ImageButton)v.findViewById(R.id.imgbtn_teacher);
        painter = (ImageButton)v.findViewById(R.id.imgbtn_painter);
        pilot = (ImageButton)v.findViewById(R.id.imgbtn_pilot);

        chooseMale = (ImageButton)v.findViewById(R.id.imgbtn_chooseMale);
        chooseFemale = (ImageButton)v.findViewById(R.id.imgbtn_chooseFemale);

        SharedPreferences sharedPref = ChooseMinigame.this.
                getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
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

    public void changeToMale(View view){
        chooseFemale.setAlpha(0.5f);

        firefighter.setImageResource(R.drawable.martinho_bombeiro);
        baker.setImageResource(R.drawable.martinho_pasteleiro);
        teacher.setImageResource(R.drawable.martinho_professor);
        painter.setImageResource(R.drawable.martinho_pintor);
        pilot.setImageResource(R.drawable.martinho_piloto);
    }

    public void changeToFemale(View view){
        chooseMale.setAlpha(0.5f);

        baker.setImageResource(R.drawable.maria_martinho_pasteleira);
        painter.setImageResource(R.drawable.maria_martinho_pintora);
        pilot.setImageResource(R.drawable.maria_martinho_piloto);

    }


}
