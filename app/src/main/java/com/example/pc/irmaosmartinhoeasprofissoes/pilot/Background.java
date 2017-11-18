package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by TheSeven on 11/11/2017.
 */

public class Background
{
    private Bitmap image;

    private int x, y, width, height, dx;

    public Background(Bitmap res, int w, int h)
    {
        image = Bitmap.createScaledBitmap(res, w, h, false);

        width = w;
        height = h;
    }

    public void update()
    {
        x+=dx;
        if(x<-GamePanel.WIDTH)
            x=0;
        /*if((Math.abs(storedLightValue - lightData.getLightValue())) > 50.0) {
            if (lightData.getLightValue() >= MAX_DAYLIGHT)
                currentImage = day;
            else
                currentImage = night;

        }*/
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
        if(x < 0){
            canvas.drawBitmap(image,x+GamePanel.WIDTH, y,null);
        }
    }

    public void setVector(int dx){
        this.dx = dx;
    }
}
