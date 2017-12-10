package com.example.pc.irmaosmartinhoeasprofissoes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * Classe que gere o fundo de ecrã de um jogo
 */

public class Background
{
    protected Bitmap image;
    protected int x, y, width, height;

    public Background(Bitmap res, int w, int h)
    {
        image = Bitmap.createScaledBitmap(res, w, h, false);
        width = w;
        height = h;
    }

    //Desenha a imagem
    public void draw(Canvas canvas, boolean paused)
    {
        if(!paused)
            canvas.drawBitmap(image, x, y,null);
        else
        {
            Paint paint = new Paint();
            paint.setColorFilter(new LightingColorFilter(Color.rgb(123, 123, 123), 0));
            canvas.drawBitmap(image, x, y,paint);
        }
    }
}
