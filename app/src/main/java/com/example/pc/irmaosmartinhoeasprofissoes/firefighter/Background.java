package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by TheSeven on 11/11/2017.
 */

public class Background
{
    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res)
    {
        image = res;
    }

    public void update()
    {
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
    }
}
