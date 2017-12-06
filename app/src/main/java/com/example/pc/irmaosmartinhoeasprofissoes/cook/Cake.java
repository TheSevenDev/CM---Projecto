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
    private EnumCakeShape shape;
    private EnumCakeCoating coating;
    private EnumCakeTopping topping;
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

    public void setCoating(EnumCakeCoating coating)
    {
        this.coating = coating;
    }

    public void setShape(EnumCakeShape shape)
    {
        this.shape = shape;
    }

    public void setTopping(EnumCakeTopping topping)
    {
        this.topping = topping;
    }

    public void setLastComponentPut(EnumComponentType componentType)
    {
        this.lastComponentPut = componentType;
    }

    public void switchShape(EnumCakeShape shape)
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
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_bases), 0.67);
                break;
        }
    }

    public void switchCoating(EnumCakeCoating coating)
    {
        this.coating = coating;

        switch(coating)
        {
            case PURPLE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.coatings_grid), 0);
                break;
            case BLUE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.coatings_grid), 0.335);
                break;
            case ORANGE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.coatings_grid), 0.67);
                break;
        }
    }

    public void switchTopping(EnumCakeTopping topping)
    {
        this.topping = topping;

        switch(coating)
        {
            case PURPLE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_spritesheet2), 0);
                break;
            case BLUE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_spritesheet2), 0.335);
                break;
            case ORANGE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_spritesheet2), 0.67);
                break;
        }
    }


    public void changeImage(Bitmap bitmap, double startingHeight)
    {
        Bitmap auxBitmap;

        switch(lastComponentPut)
        {
            case SHAPE:
                auxBitmap = Bitmap.createBitmap(bitmap,
                        0, (int)(bitmap.getHeight() * startingHeight),
                        bitmap.getWidth(), (int)(bitmap.getHeight() * 0.33));

                image = Bitmap.createScaledBitmap(auxBitmap,
                        getWidth(),
                        getHeight(), false);
                break;
            case COATING:
                switch(shape)
                {
                    case HEXAGON:
                        auxBitmap = Bitmap.createBitmap(bitmap,
                                (bitmap.getWidth() * 0), (int)(bitmap.getHeight() * startingHeight),
                                (int)(bitmap.getWidth() * 0.33), (int)(bitmap.getHeight() * 0.33));

                        image = Bitmap.createScaledBitmap(auxBitmap,
                                getWidth(),
                                getHeight(), false);
                        break;
                    case CIRCLE:
                        auxBitmap = Bitmap.createBitmap(bitmap,
                                (int)(bitmap.getWidth() * 0.33), (int)(bitmap.getHeight() * startingHeight),
                                (int)(bitmap.getWidth() * 0.33), (int)(bitmap.getHeight() * 0.33));

                        image = Bitmap.createScaledBitmap(auxBitmap,
                                getWidth(),
                                getHeight(), false);
                        break;
                    case SQUARE:
                        auxBitmap = Bitmap.createBitmap(bitmap,
                                (int)(bitmap.getWidth() * 0.67), (int)(bitmap.getHeight() * startingHeight),
                                (int)(bitmap.getWidth() * 0.33), (int)(bitmap.getHeight() * 0.33));

                        image = Bitmap.createScaledBitmap(auxBitmap,
                                getWidth(),
                                getHeight(), false);
                        break;
                }
                break;
            case TOPPING:
                switch(shape)
                {
                    case HEXAGON:
                        switch(topping)
                        {
                            case CHERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0 + 0.1108 * 0))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case CHOCOLATE:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0 + 0.1108 * 1))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case STRAWBERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0 + 0.1108 * 2))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                        }
                        break;
                    case SQUARE:
                        switch(topping)
                        {
                            case CHERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0.332 + 0.1108 * 0))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case CHOCOLATE:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0.332 + 0.1108 * 1))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case STRAWBERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0.332 + 0.1108 * 2))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                        }
                        break;
                    case CIRCLE:
                        switch(topping)
                        {
                            case CHERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0.666 + 0.1108 * 0))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case CHOCOLATE:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0.666 + 0.1108 * 1))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case STRAWBERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0.668 + 0.1108 * 2))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * 0.1108), (int)(bitmap.getHeight() * 0.33));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                        }
                        break;
                }
                break;
        }

    }

    public EnumComponentType getLastComponentPut()
    {
        return lastComponentPut;
    }

}
