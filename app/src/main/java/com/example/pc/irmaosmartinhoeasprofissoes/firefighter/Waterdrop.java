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

public class Waterdrop extends GameObject
{
    private Bitmap spritesheet;
    private long waterSpawnTime;
    private boolean despawn;

    private Animation animation = new Animation();
    private Double[] waterFramesX;

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

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public void update(long pausedTime)
    {
        animation.update();

        long timeElapsed = (System.nanoTime() - waterSpawnTime - pausedTime)/1000000;

        if(timeElapsed > Integer.parseInt(getContext().getString(R.string.water_disappear_time)) * 1000)
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
                    (int)(waterFramesX[i] * spritesheet.getWidth()), 0,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.waterdrop_frame_width)) * spritesheet.getWidth()),
                    spritesheet.getHeight());

            image[i] = Bitmap.createScaledBitmap(auxBitmap,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.waterdrop_sprite_width))*GamePanel.WIDTH),
                    (int)(Double.parseDouble(context.getResources().getString(R.string.waterdrop_sprite_height))*GamePanel.HEIGHT), false);
        }

        return image;
    }

    public Double[] waterFrameX()
    {
        Double[] array = new Double[2];

        array[0] = Double.parseDouble(context.getResources().getString(R.string.waterdrop_frame1_x));
        array[1] = Double.parseDouble(context.getResources().getString(R.string.waterdrop_frame2_x));

        return array;
    }
}
