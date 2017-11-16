package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 13/11/2017.
 */

public class WaterMeter extends GameObject
{
    private Bitmap spritesheet;
    private boolean noWaterWarning;
    private long warningStart;
    private Paint transparentPaint;

    public WaterMeter(Bitmap res, int w, int h, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;

        spritesheet = Bitmap.createScaledBitmap(res, w, h, false);
        transparentPaint = new Paint();
        transparentPaint.setAlpha(50);
    }

    public void draw(Canvas canvas)
    {

        if(noWaterWarning)
        {
            canvas.drawBitmap(spritesheet, x, y, transparentPaint);
        }
        else
            canvas.drawBitmap(spritesheet, x, y, null);
    }

    public void outOfWater()
    {
        warningStart = System.nanoTime();
        noWaterWarning = true;
    }

    public void update()
    {
        long timeElapsed = (System.nanoTime() - warningStart)/1000000;

        if(timeElapsed > 1000 && noWaterWarning)
        {
            noWaterWarning = false;
        }
    }
}
