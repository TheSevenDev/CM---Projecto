package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TheSeven on 11/11/2017.
 */

public class Fire extends GameObject
{
    private Bitmap spritesheet;
    private long fireSpawnTime;
    private boolean despawn;

    public Fire(Bitmap res, int w, int h, int x, int y, Context context)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        spritesheet = Bitmap.createScaledBitmap(res, w, h, false);
        fireSpawnTime = System.nanoTime();
        despawn = false;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }

    public void update()
    {
        long timeElapsed = (System.nanoTime()-fireSpawnTime)/1000000;

        if(timeElapsed > Integer.parseInt(getContext().getString(R.string.fire_disappear_time)) * 1000)
        {
            despawn = true;
        }
    }

    public boolean isDespawn()
    {
        return despawn;
    }

}
