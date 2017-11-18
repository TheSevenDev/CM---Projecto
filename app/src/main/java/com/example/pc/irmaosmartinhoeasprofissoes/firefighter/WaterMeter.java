package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 13/11/2017.
 */

public class WaterMeter extends GameObject
{
    private Bitmap spritesheet;
    private boolean noWater;
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
        transparentPaint.setColor(Color.TRANSPARENT);

        spritesheet.setHasAlpha(true);
    }

    public void draw(Canvas canvas, boolean paused)
    {
        if(noWaterWarning)
            canvas.drawBitmap(spritesheet, x, y, transparentPaint);
        else if(paused)
        {
            Paint paint = new Paint();
            paint.setColorFilter(new LightingColorFilter(Color.rgb(123, 123, 123), 0));
            canvas.drawBitmap(spritesheet, x, y,paint);
        }
        else
            canvas.drawBitmap(spritesheet, x, y, null);
    }

    public void outOfWater()
    {
        warningStart = System.nanoTime();
        noWater = true;
        noWaterWarning = true;
    }

    public void update()
    {
        long timeElapsed = (System.nanoTime() - warningStart)/1000000;

        if(noWater)
        {
            if (timeElapsed > 100) {
                noWaterWarning = false;
            }

            if (timeElapsed > 200) {
                noWaterWarning = true;
            }

            if (timeElapsed > 300) {
                noWaterWarning = false;
            }

            if (timeElapsed > 400) {
                noWaterWarning = true;
            }

            if (timeElapsed > 500) {
                noWaterWarning = false;
                noWater = false;
            }

            if (timeElapsed > 600) {
                noWaterWarning = false;
                noWater = false;
            }

            if (timeElapsed > 700) {
                noWaterWarning = false;
                noWater = false;
            }

            if (timeElapsed > 800) {
                noWaterWarning = false;
                noWater = false;
            }

            if (timeElapsed > 900) {
                noWaterWarning = false;
                noWater = false;
            }
        }
    }

    public boolean isNoWaterWarning()
    {
        return noWaterWarning;
    }
}
