package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;

/**
 * Created by TheSeven on 12/11/2017.
 */

public class Waterdrop extends GameObject
{
    private Bitmap spritesheet;

    public Waterdrop(Bitmap res, int w, int h, int x, int y)
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
