package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

public class Credits extends GeneralActivity {
    private final int ANIMATION_DURATION = 30000;

    private TextView creditsText;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        animation = AnimationUtils.loadAnimation(this, R.anim.credits);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(ANIMATION_DURATION);
        //creditsText = findViewById(R.id.creditsText);
        //creditsText.setText(creditsText());

     //   creditsText.startAnimation(animation);
    }

    public void backToSettings(View view){
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    private String creditsText(){

        String str = "";
        str+="Desenvolvido por\nBárbara Teixeira\nBruno Pereira\nDiogo Abreu\nMarcos Letras \nRicardo Fernandes";
        str+="\n\nCom colaboração de\nsamonasta";




        return str;
    }
}
