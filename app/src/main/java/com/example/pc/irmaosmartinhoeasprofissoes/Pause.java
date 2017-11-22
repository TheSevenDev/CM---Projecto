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
    private Context context;

    public Pause(Context context)
    {
        this.context = context;

        pauseButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pause),
                (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_button_width_mod))),
                (int)(GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.pause_button_height_mod))), false);

        paused = false;

        pauseScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_splash),
                (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_screen_width_mod))),
                (int)(GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.pause_screen_height_mod))), false);

        resumeButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_resume_width_mod))),
                (int)(GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.pause_resume_height_mod))), false);

        exitButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.exit),
                (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_exit_width_mod))),
                (int)(GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.pause_exit_height_mod))), false);

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
            canvas.drawBitmap(pauseScreen,
                    (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_screen_x_mod))),
                    (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_screen_y_mod))), null);

            canvas.drawBitmap(resumeButton,
                    (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_resume_x_mod))),
                    (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_resume_y_mod))), null);

            canvas.drawBitmap(exitButton,
                    (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_exit_x_mod))),
                    (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_exit_y_mod))), null);
        }
        else
        {
            canvas.drawBitmap(pauseButton,
                    (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_button_x_mod))),
                    (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_button_y_mod))), null);
        }
    }

    public boolean onTouchPauseScreen(int x, int y)
    {
        //resume
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_resume_x_mod)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_resume_x_mod)))
                + (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_resume_width_mod))))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_resume_y_mod)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_resume_y_mod)))
                + (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_resume_height_mod)))))
        {
            paused = false;
            return false;
        }

        //exit
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_exit_x_mod)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_exit_x_mod)))
                + (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_exit_width_mod))))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_exit_y_mod)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_exit_y_mod)))
                + (int)(GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.pause_exit_height_mod)))))
        {
            return true;
        }

        return false;
    }

    public void onTouchPauseButton(int x, int y)
    {
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_button_x_mod)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.pause_button_x_mod))) + exitButton.getWidth())
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_button_y_mod)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.pause_button_y_mod))) + exitButton.getHeight()))
        {
            paused = true;
        }
    }
}
