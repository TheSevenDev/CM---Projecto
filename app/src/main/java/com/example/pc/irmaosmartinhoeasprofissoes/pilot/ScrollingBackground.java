package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.Background;

/**
 * Created by TheSeven on 11/11/2017.
 */

public class ScrollingBackground extends Background
{
    private int dx;

    public ScrollingBackground(Bitmap res, int w, int h)
    {
        super(res, w, h);
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
        canvas.drawBitmap(super.image, x, y,null);
        if(x < 0){
            canvas.drawBitmap(image,x+GamePanel.WIDTH, y,null);
        }
    }

    public void setVector(int dx){
        this.dx = dx;
    }
}
