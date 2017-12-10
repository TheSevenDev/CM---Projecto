package com.example.pc.irmaosmartinhoeasprofissoes.pilot;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Classe que contem um conjunto de animações.
 * Permite desenhar a animação atual no ecrâ.
 */

public class AnimationManager {
    private Animation[] animations;

    private int animationIndex = 0 ;

    public AnimationManager(Animation[] animations){
        this.animations = animations;
    }


    public void playAnim(int index){
        for(int i = 0; i < animations.length; i++){
            if(i == index) {
                if(!animations[index].isPlaying())
                    animations[i].play();
            }
            else
                animations[i].stop();
        }
        animationIndex = index;
    }

    public Animation getCurrentAnimation(){
        return animations[animationIndex];
    }

    public void draw(Canvas canvas, Rect rect){
        getCurrentAnimation().draw(canvas, rect);
    }

    public void update(){
        getCurrentAnimation().update();
    }


}