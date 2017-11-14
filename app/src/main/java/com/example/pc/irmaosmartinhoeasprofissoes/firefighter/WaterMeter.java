package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;

/**
 * Created by TheSeven on 13/11/2017.
 */

public class WaterMeter extends GameObject
{
    private Bitmap spritesheet;

    public WaterMeter(Bitmap res, int w, int h, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;

        spritesheet = Bitmap.createScaledBitmap(res, w, h, false);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }
}
