package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.Background;


/**
 * Imagem de fundo do jogo.
 * A coordenada x da imagem vai diminuindo ao longo do update do ecrâ.
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
