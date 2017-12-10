package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.Animation;
import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 12/11/2017.
 * Esta classe representa o gato
 */

public class Cat extends GameObject
{
    private Bitmap spritesheet;
    private long catSpawnTime; //tempo de vida do gato
    private boolean despawn; //atributo de controlo de retirada do gato

    private Animation animation = new Animation(); // animação
    private Double[] catFramesX; //array de posições para retirar as imagens do sprite

    //Construtor do objecto Cat
    public Cat(Bitmap res, int w, int h, int x, int y, Context context, int numFrames)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        catSpawnTime = System.nanoTime();
        despawn = false;

        spritesheet = res;

        catFramesX = catFrameX();
        animation.setFrames(constructAnimation(numFrames));
        animation.setDelay(50);
    }

    //Este método desenha a animação
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    //Este método serve para refrescar a animação e retirar o gato caso o seu tempo tenha expirado
    public void update(long pausedTime)
    {
        animation.update();

        long timeElapsed = (System.nanoTime()-catSpawnTime - pausedTime)/1000000;

        if(timeElapsed > Integer.parseInt(getContext().getString(R.string.cat_disappear_time)) * 1000)
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
                    (int)(catFramesX[i] * spritesheet.getWidth()), 0,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.cat_frame_width)) * spritesheet.getWidth()),
                    spritesheet.getHeight());

            image[i] = Bitmap.createScaledBitmap(auxBitmap,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.cat_sprite_width))*GamePanel.WIDTH),
                    (int)(Double.parseDouble(context.getResources().getString(R.string.cat_sprite_height))*GamePanel.HEIGHT), false);
        }

        return image;
    }

    //Este método retira as imagens do sprite para o array
    public Double[] catFrameX()
    {
        Double[] array = new Double[3];

        array[0] = Double.parseDouble(context.getResources().getString(R.string.cat_frame1_x));
        array[1] = Double.parseDouble(context.getResources().getString(R.string.cat_frame2_x));
        array[2] = Double.parseDouble(context.getResources().getString(R.string.cat_frame3_x));

        return array;
    }
}
