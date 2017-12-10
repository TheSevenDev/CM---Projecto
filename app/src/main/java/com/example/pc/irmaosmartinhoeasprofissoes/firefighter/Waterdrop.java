package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.Animation;
import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 12/11/2017.
 * Esta classe representa a gota de água
 */

public class Waterdrop extends GameObject
{
    private Bitmap spritesheet;
    private long waterSpawnTime; //tempo de vida da gota
    private boolean despawn; //atributo de controlo de retirada da gota

    private Animation animation = new Animation(); // animação
    private Double[] waterFramesX; //array de posições para retirar as imagens do sprite

    //construtor
    public Waterdrop(Bitmap res, int w, int h, int x, int y, Context context, int numFrames)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        waterSpawnTime = System.nanoTime();
        despawn = false;

        spritesheet = res;

        waterFramesX = waterFrameX();
        animation.setFrames(constructAnimation(numFrames));
        animation.setDelay(500);
    }

    //Este método desenha a animação
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    //Este método serve para refrescar a animação e retirar a gota caso o seu tempo tenha expirado
    public void update(long pausedTime)
    {
        animation.update();

        long timeElapsed = (System.nanoTime() - waterSpawnTime - pausedTime)/1000000;

        if(timeElapsed > Integer.parseInt(getContext().getString(R.string.water_disappear_time)) * 1000)
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
                    (int)(waterFramesX[i] * spritesheet.getWidth()), 0,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.waterdrop_frame_width)) * spritesheet.getWidth()),
                    spritesheet.getHeight());

            image[i] = Bitmap.createScaledBitmap(auxBitmap,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.waterdrop_sprite_width))*GamePanel.WIDTH),
                    (int)(Double.parseDouble(context.getResources().getString(R.string.waterdrop_sprite_height))*GamePanel.HEIGHT), false);
        }

        return image;
    }

    //Este método retira as imagens do sprite para o array
    public Double[] waterFrameX()
    {
        Double[] array = new Double[2];

        array[0] = Double.parseDouble(context.getResources().getString(R.string.waterdrop_frame1_x));
        array[1] = Double.parseDouble(context.getResources().getString(R.string.waterdrop_frame2_x));

        return array;
    }
}
