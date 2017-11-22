package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

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

    public Cat(Bitmap res, int w, int h, int x, int y, Context context)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        spritesheet = Bitmap.createScaledBitmap(res, w, h, false);
        catSpawnTime = System.nanoTime();
        despawn = false;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }

    public void update(long pausedTime)
    {
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
}
