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
    private Context context;

    public GameOver(Context context)
    {
        gameOver = false;
        this.context = context;

        gameOverScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_splash),
                (int)(GamePanel.WIDTH*Double.parseDouble(context.getResources().getString(R.string.game_over_screen_width_mod))),
                (int)(GamePanel.HEIGHT*Double.parseDouble(context.getResources().getString(R.string.game_over_screen_height_mod))), false);

        restartButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                (int)(GamePanel.WIDTH*Double.parseDouble(context.getResources().getString(R.string.game_over_restart_width_mod))),
                (int)(GamePanel.HEIGHT*Double.parseDouble(context.getResources().getString(R.string.game_over_restart_height_mod))), false);

        exitButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.play),
                (int)(GamePanel.WIDTH*Double.parseDouble(context.getResources().getString(R.string.game_over_exit_width_mod))),
                (int)(GamePanel.HEIGHT*Double.parseDouble(context.getResources().getString(R.string.game_over_exit_height_mod))), false);

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
            canvas.drawBitmap(gameOverScreen,
                    (int) (GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_screen_x_mod))),
                    (int) (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_screen_y_mod))), null);

            canvas.drawBitmap(restartButton,
                    (int) (GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_restart_x_mod))),
                    (int) (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_restart_y_mod))), null);

            canvas.drawBitmap(exitButton,
                    (int) (GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_exit_x_mod))),
                    (int) (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_exit_y_mod))), null);

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
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_restart_x_mod)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_restart_x_mod)))
                + (GamePanel.WIDTH * restartButton.getWidth()))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_restart_y_mod)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_restart_y_mod)))
                + (GamePanel.HEIGHT * restartButton.getHeight())))
        {
            gameOver = false;
            return 1;
        }

        //exit
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_exit_x_mod)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_exit_x_mod)))
                + (GamePanel.WIDTH * exitButton.getWidth()))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_exit_y_mod)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_exit_y_mod)))
                + (GamePanel.HEIGHT * exitButton.getHeight())))
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
