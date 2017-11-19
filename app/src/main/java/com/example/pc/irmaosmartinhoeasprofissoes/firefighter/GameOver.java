package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 19/11/2017.
 */

public class GameOver
{
    private Bitmap gameOverScreen;
    private Bitmap restartButton;
    private Bitmap exitButton;
    private boolean gameOver;
    private int score;

    public GameOver(Context context)
    {
        gameOver = false;

        gameOverScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_splash),
                (int)(GamePanel.WIDTH*0.65),
                (int)(GamePanel.HEIGHT*0.65), false);

        restartButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                (int)(GamePanel.WIDTH*0.25),
                (int)(GamePanel.HEIGHT*0.15), false);

        exitButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                (int)(GamePanel.WIDTH*0.25),
                (int)(GamePanel.HEIGHT*0.15), false);

    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean b)
    {
        gameOver = b;
    }

    public void draw(Canvas canvas)
    {
        if(gameOver)
        {
            canvas.drawBitmap(gameOverScreen, (int) (GamePanel.WIDTH * 0.175), (int) (GamePanel.HEIGHT * 0.175), null);
            canvas.drawBitmap(restartButton, (int) (GamePanel.WIDTH * 0.375), (int) (GamePanel.HEIGHT * 0.40), null);
            canvas.drawBitmap(exitButton, (int) (GamePanel.WIDTH * 0.375), (int) (GamePanel.HEIGHT * 0.60), null);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(80);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText(""+score, 0.48f*(GamePanel.WIDTH), 0.3f*GamePanel.HEIGHT, paint);
        }
    }

    public int onTouchPauseScreen(int x, int y)
    {
        //resume
        if (x >= (int)(GamePanel.WIDTH*0.375) && x < ((int)(GamePanel.WIDTH*0.375) + (int)(GamePanel.WIDTH*0.25))
                && y >= (int)(GamePanel.HEIGHT*0.40) && y < ((int)(GamePanel.HEIGHT*0.40) + (int)(GamePanel.HEIGHT*0.15)))
        {
            gameOver = false;
            return 1;
        }

        //exit
        if (x >= (int)(GamePanel.WIDTH*0.375) && x < ((int)(GamePanel.WIDTH*0.375) + (int)(GamePanel.WIDTH*0.25))
                && y >= (int)(GamePanel.HEIGHT*0.60) && y < ((int)(GamePanel.HEIGHT*0.60) + (int)(GamePanel.HEIGHT*0.15)))
        {
            return 2;
        }

        return 0;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}
