package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;

/**
 * Created by TheSeven on 19/11/2017.
 */

public class Cake extends GameObject
{
    private Bitmap image;
    private EnumShape shape;
    private EnumCakeCover cover;
    private EnumTopping topping;

    public Cake(Bitmap res, int w, int h, int x, int y, Context context)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        image = Bitmap.createScaledBitmap(res, w, h, false);
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

    public void changePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

}
