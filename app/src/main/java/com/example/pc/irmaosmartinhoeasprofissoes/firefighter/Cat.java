package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;

/**
 * Created by TheSeven on 12/11/2017.
 */

public class Cat extends GameObject
{
    private Bitmap spritesheet;

    public Cat(Bitmap res, int w, int h, int x, int y, Context context)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        spritesheet = Bitmap.createScaledBitmap(res, w, h, false);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }
}
