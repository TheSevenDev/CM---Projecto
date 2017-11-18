package com.example.pc.irmaosmartinhoeasprofissoes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

import com.example.pc.irmaosmartinhoeasprofissoes.firefighter.GamePanel;

/**
 * Created by TheSeven on 17/11/2017.
 */

public class Pause
{
    private Bitmap pauseButton;
    private Bitmap pauseScreen;
    private Bitmap resumeButton;
    private Bitmap exitButton;
    private boolean paused;

    public Pause(Context context)
    {
        pauseButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pause_sign),
                (int)(GamePanel.WIDTH *0.07),
                (int)(GamePanel.HEIGHT*0.09), false);

        paused = false;

        pauseScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_splash),
                (int)(GamePanel.WIDTH*0.7),
                (int)(GamePanel.HEIGHT*0.7), false);

        resumeButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                (int)(GamePanel.WIDTH*0.25),
                (int)(GamePanel.HEIGHT*0.15), false);

        exitButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                (int)(GamePanel.WIDTH*0.25),
                (int)(GamePanel.HEIGHT*0.15), false);

    }

    public boolean isPaused()
    {
        return paused;
    }

    public void setPaused(boolean b)
    {
        paused = b;
    }

    public void draw(Canvas canvas)
    {
        if(paused)
        {
            canvas.drawBitmap(pauseScreen, (int) (GamePanel.WIDTH * 0.15), (int) (GamePanel.HEIGHT * 0.15), null);
            canvas.drawBitmap(resumeButton, (int) (GamePanel.WIDTH * 0.375), (int) (GamePanel.HEIGHT * 0.40), null);
            canvas.drawBitmap(exitButton, (int) (GamePanel.WIDTH * 0.375), (int) (GamePanel.HEIGHT * 0.60), null);
        }
        else
        {
            canvas.drawBitmap(pauseButton, (int) (GamePanel.WIDTH * 0.92), (int) (GamePanel.HEIGHT * 0.05), null);
        }
    }

    public boolean onTouchPauseScreen(int x, int y)
    {
        //resume
        if (x >= (int)(GamePanel.WIDTH*0.375) && x < ((int)(GamePanel.WIDTH*0.375) + (int)(GamePanel.WIDTH*0.25))
                && y >= (int)(GamePanel.HEIGHT*0.40) && y < ((int)(GamePanel.HEIGHT*0.40) + (int)(GamePanel.HEIGHT*0.15)))
        {
            paused = false;
            return false;
        }

        //exit
        if (x >= (int)(GamePanel.WIDTH*0.375) && x < ((int)(GamePanel.WIDTH*0.375) + (int)(GamePanel.WIDTH*0.25))
                && y >= (int)(GamePanel.HEIGHT*0.60) && y < ((int)(GamePanel.HEIGHT*0.60) + (int)(GamePanel.HEIGHT*0.15)))
        {
            return true;
        }

        return false;
    }

    public void onTouchPauseButton(int x, int y)
    {
        if (x >= (int)(GamePanel.WIDTH * 0.92) && x < ((GamePanel.WIDTH * 0.92) + (int)(GamePanel.WIDTH *0.07))
                && y >= (int)(GamePanel.HEIGHT * 0.05) && y < ((int)(GamePanel.HEIGHT * 0.05) + (int)(GamePanel.HEIGHT*0.09)))
        {
            paused = true;
        }
    }
}
