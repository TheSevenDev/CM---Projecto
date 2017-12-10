package com.example.pc.irmaosmartinhoeasprofissoes;

import android.graphics.Bitmap;

/**
 * Classe que gere as animações do projecto
 */
public class Animation
{
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    //Define as frames da animação
    public void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    //Define o intervalo de tempo entre cada frame
    public void setDelay(long d){delay = d;}

    //Percorre as frames caso ja tenha passado o tempo necessario
    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed>delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }

    //Retorna a frame no momento
    public Bitmap getImage(){
        return frames[currentFrame];
    }
}
