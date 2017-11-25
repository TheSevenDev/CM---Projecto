package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.pc.irmaosmartinhoeasprofissoes.EnumGame;
import com.example.pc.irmaosmartinhoeasprofissoes.Menus.ChooseMinigame;
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
    private Context context;
    private Bitmap character;
    private Bitmap professionItem;
    private Bitmap scoreImage;
    private double xScore, yScore;

    public GameOver(Context context, EnumGame enumGame)
    {
        gameOver = false;
        this.context = context;

        setBackgroundImages(enumGame);

        gameOverScreen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_panel),
                (int)(GamePanel.WIDTH*Double.parseDouble(context.getResources().getString(R.string.game_over_screen_width_mod))),
                (int)(GamePanel.HEIGHT*Double.parseDouble(context.getResources().getString(R.string.game_over_screen_height_mod))), false);

        restartButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.restart),
                (int)(GamePanel.WIDTH*Double.parseDouble(context.getResources().getString(R.string.game_over_restart_width_mod))),
                (int)(GamePanel.HEIGHT*Double.parseDouble(context.getResources().getString(R.string.game_over_restart_height_mod))), false);

        exitButton = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.exit),
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

            canvas.drawBitmap(character,
                    (int) (GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.game_over_character_x_mod))),
                    (int) (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.game_over_character_y_mod))), null);

            /*
            canvas.drawBitmap(scoreImage,
                    (int) (GamePanel.WIDTH * xScore),
                    (int) (GamePanel.HEIGHT * yScore), null);*/

        }
    }

    public int onTouchPauseScreen(int x, int y)
    {
        //restart
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
            return 2;
        return 0;
    }

    private void setBackgroundImages(EnumGame enumGame)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        int gender = sharedPref.getInt("gender",0);
        Bitmap auxCharacter = null, auxProfessionItem = null;

        switch(enumGame)
        {
            case FIREFIGHTER:
                if(gender == Integer.parseInt(context.getResources().getString(R.string.choose_male)))
                    auxCharacter = BitmapFactory.decodeResource(context.getResources(), R.drawable.jose_bombeiro);
                else
                    auxCharacter = BitmapFactory.decodeResource(context.getResources(), R.drawable.maria_bombeira);

                //auxProfessionItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.bombeiro);
                break;
            case COOK:
                if(gender == Integer.parseInt(context.getResources().getString(R.string.choose_male)))
                    auxCharacter = BitmapFactory.decodeResource(context.getResources(), R.drawable.jose_pasteleiro);
                else
                    auxCharacter = BitmapFactory.decodeResource(context.getResources(), R.drawable.maria_pasteleira);

                //auxProfessionItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.bombeiro);
                break;
            case PILOT:
                if(gender == Integer.parseInt(context.getResources().getString(R.string.choose_male)))
                    auxCharacter = BitmapFactory.decodeResource(context.getResources(), R.drawable.jose_piloto);
                else
                    auxCharacter = BitmapFactory.decodeResource(context.getResources(), R.drawable.maria_pilota);

                //auxProfessionItem = BitmapFactory.decodeResource(context.getResources(), R.drawable.bombeiro);
                break;
        }

        character = Bitmap.createScaledBitmap(auxCharacter,
                (int)(GamePanel.WIDTH*Double.parseDouble(context.getResources().getString(R.string.game_over_character_width_mod))),
                (int)(GamePanel.HEIGHT*Double.parseDouble(context.getResources().getString(R.string.game_over_character_height_mod))), false);
    }

    private void setScoreImage(Bitmap image, double x, double y)
    {
        scoreImage = image;
        xScore = x;
        yScore = y;
    }
}
