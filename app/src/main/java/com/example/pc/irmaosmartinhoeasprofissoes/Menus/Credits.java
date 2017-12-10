package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Apresenta os creditos da aplicação, que aparecerao de baixo para cima como normalmente visto na cultura pop
 */

public class Credits extends GeneralActivity {
    private final int ANIMATION_DURATION = 25000;

    private TextView creditsText , creditsText2;
    private Animation animation, animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        animation = AnimationUtils.loadAnimation(this, R.anim.credits);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(ANIMATION_DURATION);

        animation2 = AnimationUtils.loadAnimation(this, R.anim.credits2);
        animation2.setRepeatCount(Animation.INFINITE);
        animation2.setDuration(ANIMATION_DURATION);

        creditsText = findViewById(R.id.creditsText);
        creditsText.setText(creditsText());

        creditsText.startAnimation(animation);

        creditsText2 = findViewById(R.id.creditsText2);
        creditsText2.setText(creditsText2());

        creditsText2.startAnimation(animation2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if(!isServiceRunning(MusicService.class)){
        //    startService(new Intent(this, MusicService.class));
        //}
        if(!MainMenu.mp.isPlaying()) {
            MainMenu.mp.seekTo(MainMenu.mp.getCurrentPosition() - 100);
            MainMenu.mp.start();
        }
    }
    @Override
    protected  void onPause(){
        super.onPause();
        MainMenu.mp.pause();
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(MainMenu.mp != null) {
            MainMenu.mp.stop();
            MainMenu.mp = null;
        }
    }

    public void backToSettings(View view){
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    //Apresenta a primeira parte do texto dos crétidos
    private String creditsText(){

        String str = "";
        str+="Desenvolvido por\nBárbara Teixeira\nBruno Pereira\nDiogo Abreu\nMarcos Letras \nRicardo Fernandes";
        str+="\n\nCom colaboração de\nrh0 de opengameart.org\nbevoullin.com de opengameart.org\nProfessora Rossana Santos";
        return str;
    }

    //Apresenta a segunda parte do texto dos crétidos
    private String creditsText2(){
        String str = "";
        str+="\n\nMúsica de\nNicolai Heidlas - KLONKY DONKEY\nNicolai Heidlas - Cartoon\n " +
                "Nicolai Heidlas - Back InSummer\nMattia Cupelli - Action Cartoon Music\n" +
                "Klubz Productions - Cartoon Music Curious Fun\nAudio Lounge - Happy Piano Background Music";

        str+="\n\nInstituto Politécnico de Setúbal";
        str+="\n\u00A9 2018";
        return str;
    }
}
