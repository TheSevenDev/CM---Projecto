package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;

/**
 * Created by TheSeven on 24/11/2017.
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

    public void addScore(int sum)
    {
        score += sum;
        calculateStars();
    }

    public int getScore()
    {
        return score;
    }

    public void draw(Canvas canvas)
    {
        for (int i = 0; i < stars.size(); i++)
        {
            canvas.drawBitmap(stars.get(i),
                    (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_x_mod) + (i*0.05)) * GamePanel.WIDTH) ,
                    (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_y_mod)) * GamePanel.HEIGHT), null);
        }
    }

    private void calculateStars()
    {
        stars = new ArrayList<>();

        int div = score/3;

        if(score == 0)
        {
            stars.add(scaledStar(EnumStarType.EMPTY_STAR));
            return;
        }

        for(int i = 0; i < div; i++)
        {
            stars.add(scaledStar(EnumStarType.FULL_STAR));
        }

        if((score % 3) == 1)
        {
            stars.add(scaledStar(EnumStarType.ONE_THIRD_STAR));
        }
        else if((score % 3) == 2)
        {
            stars.add(scaledStar(EnumStarType.TWO_THIRDS_STAR));
        }
    }

    private Bitmap scaledStar(EnumStarType starType)
    {
        Bitmap unscaledBitmap;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.score_star);

        unscaledBitmap = Bitmap.createBitmap(image,
                (int)(Double.parseDouble(context.getResources().getString(starType.getX())) * image.getWidth()), 0,
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_width_mod)) * image.getWidth()),
                image.getHeight());

        return Bitmap.createScaledBitmap(unscaledBitmap,
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_width_mod))*GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_height_mod))*GamePanel.HEIGHT), false);

        /*
        unscaledBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.score_star),
                Integer.parseInt(context.getResources().getString(starType.getX())), 0,
                Integer.parseInt(context.getResources().getString(R.string.star_width)),
                Integer.parseInt(context.getResources().getString(R.string.star_height)));

        return Bitmap.createScaledBitmap(unscaledBitmap,
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_width_mod))*GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.star_score_height_mod))*GamePanel.HEIGHT), false);*/
    }
}
