package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.Animation;
import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TheSeven on 11/11/2017.
 * Esta classe representa o fogo
 */

public class Fire extends GameObject
{
    private Bitmap spritesheet;
    private long fireSpawnTime;  //tempo de vida do fogo
    private boolean despawn; //atributo de controlo de retirada do fogo

    private Animation animation = new Animation(); // animação
    private Double[] fireFramesX, fireFramesWidth; //array de posições para retirar as imagens do sprite

    //Construtor
    public Fire(Bitmap res, int w, int h, int x, int y, Context context, int numFrames)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        fireSpawnTime = System.nanoTime();
        despawn = false;

        spritesheet = res;

        fireFramesX = fireFrameX();
        fireFramesWidth = fireFrameWidth();

        animation.setFrames(constructAnimation(numFrames));
        animation.setDelay(50);
    }

    public Bitmap getBitmap()
    {
        return animation.getImage();
    }

    //Este método desenha a animação
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    //Este método serve para refrescar a animação e retirar o fogo caso o seu tempo tenha expirado
    public void update(long pausedTime)
    {
        animation.update();

        long timeElapsed = (System.nanoTime()-fireSpawnTime - pausedTime)/1000000;

        if(timeElapsed > Integer.parseInt(getContext().getString(R.string.fire_disappear_time)) * 1000)
        {
            despawn = true;
        }
    }

    //Método get do atributo despawn
    public boolean isDespawn()
    {
        return despawn;
    }

    //Método que constrói a animação
    public Bitmap[] constructAnimation(int numFrames)
    {
        Bitmap[] image = new Bitmap[numFrames];

        Bitmap auxBitmap;

        for(int i = 0; i < numFrames; i++)
        {
            auxBitmap = Bitmap.createBitmap(spritesheet,
                    (int)(fireFramesX[i] * spritesheet.getWidth()), 0,
                    (int)(fireFramesWidth[i] * spritesheet.getWidth()), spritesheet.getHeight());

            image[i] = Bitmap.createScaledBitmap(auxBitmap,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.fire_sprite_width))*GamePanel.WIDTH),
                    (int)(Double.parseDouble(context.getResources().getString(R.string.fire_sprite_height))*GamePanel.HEIGHT), false);
        }

        return image;
    }

    //Este método retira as imagens do sprite para o array
    public Double[] fireFrameX()
    {
        Double[] array = new Double[7];

        array[0] = Double.parseDouble(context.getResources().getString(R.string.fire_frame1_x));
        array[1] = Double.parseDouble(context.getResources().getString(R.string.fire_frame2_x));
        array[2] = Double.parseDouble(context.getResources().getString(R.string.fire_frame3_x));
        array[3] = Double.parseDouble(context.getResources().getString(R.string.fire_frame4_x));
        array[4] = Double.parseDouble(context.getResources().getString(R.string.fire_frame5_x));
        array[5] = Double.parseDouble(context.getResources().getString(R.string.fire_frame6_x));
        array[6] = Double.parseDouble(context.getResources().getString(R.string.fire_frame7_x));

        return array;
    }

    //Este método retira as imagens do sprite para o array
    public Double[] fireFrameWidth()
    {
        Double[] array = new Double[7];

        array[0] = Double.parseDouble(context.getResources().getString(R.string.fire_frame1_width));
        array[1] = Double.parseDouble(context.getResources().getString(R.string.fire_frame2_width));
        array[2] = Double.parseDouble(context.getResources().getString(R.string.fire_frame3_width));
        array[3] = Double.parseDouble(context.getResources().getString(R.string.fire_frame4_width));
        array[4] = Double.parseDouble(context.getResources().getString(R.string.fire_frame5_width));
        array[5] = Double.parseDouble(context.getResources().getString(R.string.fire_frame6_width));
        array[6] = Double.parseDouble(context.getResources().getString(R.string.fire_frame7_width));

        return array;
    }
}
