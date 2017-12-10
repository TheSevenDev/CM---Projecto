package com.example.pc.irmaosmartinhoeasprofissoes;

import android.content.Context;
import android.graphics.Rect;

/**
 * Classe que simula um GameObject num jogo, algo interágivel ou simplesmente uma imagem sem ser o fundo que seja necessário mostrar
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
