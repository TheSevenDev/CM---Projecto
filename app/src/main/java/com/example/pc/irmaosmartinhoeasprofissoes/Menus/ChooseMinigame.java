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
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.firefighter.FirefighterActivity;

public class ChooseMinigame extends GeneralActivity {
    private ImageButton firefighter, baker, teacher, painter, pilot;
    private ImageView chooseMale, chooseFemale;


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

        chooseMale = (ImageView)v.findViewById(R.id.img_chooseMale);
        chooseFemale = (ImageView)v.findViewById(R.id.img_chooseFemale);
        chooseFemale.setImageAlpha(50);

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
        chooseFemale.setImageAlpha(50);

        firefighter.setImageResource(R.drawable.jose_bombeiro);
        baker.setImageResource(R.drawable.jose_pasteleiro);
        teacher.setImageResource(R.drawable.jose_professor);
        painter.setImageResource(R.drawable.jose_pintor);
        pilot.setImageResource(R.drawable.jose_piloto);
        System.out.println("CHANGE TO MALE");

    }

    public void changeToFemale(View view){
        chooseMale.setImageAlpha(50);

        baker.setImageResource(R.drawable.maria_martinho_pasteleira);
        painter.setImageResource(R.drawable.maria_martinho_pintora);
        pilot.setImageResource(R.drawable.maria_martinho_piloto);
        System.out.println("CHANGE TO FEMALE");
    }
}
