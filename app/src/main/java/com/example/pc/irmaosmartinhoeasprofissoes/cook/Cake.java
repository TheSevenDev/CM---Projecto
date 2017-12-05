package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 19/11/2017.
 */

public class Cake extends GameObject
{
    private Bitmap image;
    private EnumShape shape;
    private EnumCakeCover cover;
    private EnumTopping topping;
    private EnumComponentType lastComponentPut;

    public Cake(int w, int h, int x, int y, Context context)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;

        //image = Bitmap.createScaledBitmap(res, w, h, false);
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

    public void setLastComponentPut(EnumComponentType componentType)
    {
        this.lastComponentPut = componentType;
    }

    public void switchShape(EnumShape shape)
    {
        this.shape = shape;

        switch(shape)
        {
            case HEXAGON:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_bases), 0);
                break;
            case CIRCLE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_bases), 0.33);
                break;
            case SQUARE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_bases), 0.66);
                break;
        }
    }


    public void changeImage(Bitmap bitmap, double startingHeight)
    {
        Bitmap auxBitmap;

        auxBitmap = Bitmap.createBitmap(bitmap,
                0, (int)(bitmap.getHeight() * startingHeight),
                bitmap.getWidth(), (int)(bitmap.getHeight() * 0.33));

        image = Bitmap.createScaledBitmap(auxBitmap,
                getWidth(),
                getHeight(), false);
    }

    public EnumComponentType getLastComponentPut()
    {
        return lastComponentPut;
    }

}
