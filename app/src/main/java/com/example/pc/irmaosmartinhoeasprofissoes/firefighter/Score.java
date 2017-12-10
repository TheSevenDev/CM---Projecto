package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;

/**
 * Gere a pontuaçao no jogo
 */

public class Score
{
    private int score;
    private ArrayList<Bitmap> stars;
    private Context context;

    public Score(Context context)
    {
        score = 0;
        stars = new ArrayList<>();
        this.context = context;
        calculateStars();
    }

    //Incrementa a pontuaçao com um determinado valor
    public void addScore(int sum)
    {
        score += sum;
        calculateStars();
    }

    //Desenha o componente
    public void draw(Canvas canvas)
    {
        for (int i = 0; i < stars.size(); i++)
        {
            canvas.drawBitmap(stars.get(i),
                    (int)((Double.parseDouble(context.getResources().getString(R.string.star_score_x_mod)) + (i*0.04)) * GamePanel.WIDTH) ,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_y_mod)) * GamePanel.HEIGHT), null);
        }
    }

    //Calcula o numero de estrelas dependendo da pontuaçao - 3 pontos é uma estrela etc
    private void calculateStars()
    {
        stars = new ArrayList<>();

        int div = score/3;

        if(score == 0)
        {
            stars.add(scaledStar(EnumStarType.EMPTY_STAR, false));
            return;
        }

        for(int i = 0; i < div; i++)
        {
            stars.add(scaledStar(EnumStarType.FULL_STAR, false));
        }

        if((score % 3) == 1)
        {
            stars.add(scaledStar(EnumStarType.ONE_THIRD_STAR, false));
        }
        else if((score % 3) == 2)
        {
            stars.add(scaledStar(EnumStarType.TWO_THIRDS_STAR, false));
        }
    }

    //Gere o desenho das estrela
    private Bitmap scaledStar(EnumStarType starType, boolean gameOver)
    {
        Bitmap unscaledBitmap;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.score_star);

        unscaledBitmap = Bitmap.createBitmap(image,
                (int)(Double.parseDouble(context.getResources().getString(starType.getX())) * image.getWidth()), 0,
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_width_mod)) * image.getWidth()),
                image.getHeight());

        if(!gameOver)
            return Bitmap.createScaledBitmap(unscaledBitmap,
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_width_mod))*GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_height_mod))*GamePanel.HEIGHT), false);
        else
            return Bitmap.createScaledBitmap(unscaledBitmap,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_width_mod))*GamePanel.WIDTH)*2,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_height_mod))*GamePanel.HEIGHT)*2, false);
    }

    //Apresenta a pontuaçao do ecrã "game over" - uma estrela com o numero de estrelas completas no jogo
    public void drawScoreGameOver(Canvas canvas)
    {
        Bitmap bitAux = null;

        if(score < 3)
        {
            bitAux = scaledStar(EnumStarType.EMPTY_STAR, true);
        }
        else
        {
            bitAux = scaledStar(EnumStarType.FULL_STAR, true);
        }

        canvas.drawBitmap(bitAux,
                (int)(0.46* GamePanel.WIDTH) ,
                (int)(0.23 * GamePanel.HEIGHT), null);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(75);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("" + (score/3), 0.49f * GamePanel.WIDTH, 0.32f * GamePanel.HEIGHT, paint);

    }
}
