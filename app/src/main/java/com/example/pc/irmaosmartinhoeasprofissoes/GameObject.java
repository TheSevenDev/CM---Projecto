package com.example.pc.irmaosmartinhoeasprofissoes;

import android.content.Context;
import android.graphics.Rect;

/**
 * Created by Diogo on 10/11/2017.
 */

public class GameObject
{
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Context context;

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public Rect getRectangle()
    {
        return new Rect(x, y, x+width, y+height);
    }
    public Context getContext() {return context;}
}
