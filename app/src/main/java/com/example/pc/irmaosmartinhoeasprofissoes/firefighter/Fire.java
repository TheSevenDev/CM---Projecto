package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TheSeven on 11/11/2017.
 */

public class Fire extends GameObject
{
    private Bitmap spritesheet;

    public Fire(Bitmap res, int w, int h, int x, int y)
    {
        this.x = x;
        this.y = y;
        dy = 0;
        this.height = h;
        this.width = w;

        spritesheet = Bitmap.createScaledBitmap(res, w, h, false);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }



}
