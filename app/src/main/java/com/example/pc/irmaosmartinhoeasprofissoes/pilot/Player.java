package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;


import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by Bruno on 18/11/2017.
 */

public class Player extends GameObject {
    private final int PILOT_ANIMATION_FRAMES = 7;
    public int score;
    public int health;
    private Bitmap player;
    private Bitmap images[];
    private Rect rectangle;
    //Animations
    private Animation pilotAnimation;
    private AnimationManager animationManager;
    private long startTime;

    private boolean damaged, blinkingDamage;
    private long startDamageTime;

    public Player(Rect rectangle, Context context) {
        this.rectangle = rectangle;
        this.width = rectangle.width();
        this.height = rectangle.height();
        this.context = context;

        SharedPreferences sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        BitmapFactory bf = new BitmapFactory();

        images = new Bitmap[PILOT_ANIMATION_FRAMES];


        if(sharedPref.getInt("gender",0) == Integer.parseInt(context.getString(R.string.choose_male))){
            player = BitmapFactory.decodeResource(context.getResources(), R.drawable.animacao_josepiloto);

        }
        else
        {
            player = BitmapFactory.decodeResource(context.getResources(), R.drawable.animacao_mariapiloto);
        }


        for(int i = 0; i< PILOT_ANIMATION_FRAMES; i++){
            if(i==0)
                images[i] = Bitmap.createBitmap(player, i * (player.getWidth() / PILOT_ANIMATION_FRAMES), 0, (player.getWidth() / PILOT_ANIMATION_FRAMES) - (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.04), player.getHeight());
            else if(i==1)
                images[i] = Bitmap.createBitmap(player, i * (player.getWidth() / PILOT_ANIMATION_FRAMES) - (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.04), 0, (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0), player.getHeight());
            else if(i==2)
                images[i] = Bitmap.createBitmap(player, i * (player.getWidth() / PILOT_ANIMATION_FRAMES) - (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.04), 0, (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0), player.getHeight());
            else if (i == 3)
                images[i] = Bitmap.createBitmap(player, i * (player.getWidth() / PILOT_ANIMATION_FRAMES) - (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.025), 0, (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.01), player.getHeight());
            else if(i==4)
                images[i] = Bitmap.createBitmap(player, i * (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.01), 0, (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0), player.getHeight());
            else if(i==5)
                images[i] = Bitmap.createBitmap(player, i * (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.05), 0, (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES)* 0), player.getHeight());
            else if (i == 6)
                images[i] = Bitmap.createBitmap(player, i * (player.getWidth() / PILOT_ANIMATION_FRAMES) + (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.045), 0, (player.getWidth() / PILOT_ANIMATION_FRAMES) - (int) ((player.getWidth() / PILOT_ANIMATION_FRAMES) * 0.045), player.getHeight());
        }

        pilotAnimation = new Animation(images, 0.45f);
        animationManager =  new AnimationManager(new Animation[]{pilotAnimation});

        resetScore();
        resetHealth();
        startTime = System.nanoTime();
    }


    public void draw(Canvas canvas)
    {
        if(!blinkingDamage)
            animationManager.draw(canvas, rectangle);
    }

    public void update(Point point) {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }

        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);

        animationManager.playAnim(0);
        animationManager.update();

        if(damaged)
            animationBlink();
    }

    public void animationBlink()
    {
        long timeElapsed = (System.nanoTime() - startDamageTime)/1000000;

        if (timeElapsed > 100) {
            blinkingDamage = true;
        }

        if (timeElapsed > 200) {
            blinkingDamage = false;
        }

        if (timeElapsed > 300) {
            blinkingDamage = true;
        }

        if (timeElapsed > 400) {
            blinkingDamage = false;
        }

        if (timeElapsed > 500) {
            blinkingDamage = true;
        }

        if (timeElapsed > 600) {
            blinkingDamage = false;
        }

        if (timeElapsed > 700) {
            blinkingDamage = true;
        }

        if (timeElapsed > 800) {
            blinkingDamage = false;
            damaged = false;
        }
    }

    public int getScore(){
        return score;
    }

    public void resetScore(){
        score = 0;
    }

    public void takeDamage(int d){
        health-=d;

        startDamageTime = System.nanoTime();
        damaged = true;
    }

    public int getHealth(){
        return health;
    }

    public void resetHealth(){
        health = Integer.parseInt(context.getString(R.string.pilot_max_health));
    }

    public Rect getImageRectangle(){
        return rectangle;
    }

    public boolean isAlive(){
        return health>0;
    }

}
