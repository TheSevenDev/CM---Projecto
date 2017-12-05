package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.Animation;
import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 12/11/2017.
 */

public class Cat extends GameObject
{
    private Bitmap spritesheet;
    private long catSpawnTime;
    private boolean despawn;

    private Animation animation = new Animation();
    private Double[] catFramesX;

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

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public void update(long pausedTime)
    {
        animation.update();

        long timeElapsed = (System.nanoTime()-catSpawnTime - pausedTime)/1000000;

        if(timeElapsed > Integer.parseInt(getContext().getString(R.string.cat_disappear_time)) * 1000)
        {
            despawn = true;
        }
    }

    public boolean isDespawn()
    {
        return despawn;
    }

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

    public Double[] catFrameX()
    {
        Double[] array = new Double[3];

        array[0] = Double.parseDouble(context.getResources().getString(R.string.cat_frame1_x));
        array[1] = Double.parseDouble(context.getResources().getString(R.string.cat_frame2_x));
        array[2] = Double.parseDouble(context.getResources().getString(R.string.cat_frame3_x));

        return array;
    }
}
