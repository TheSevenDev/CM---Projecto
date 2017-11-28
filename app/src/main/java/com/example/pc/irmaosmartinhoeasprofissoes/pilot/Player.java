package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;


import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by Bruno on 18/11/2017.
 */

public class Player extends GameObject {
    private final int PILOT_ANIMATION_FRAMES = 7;

    private Bitmap player;
    private Bitmap images[];

    private Rect rectangle;

    //Animations
    private Animation pilotAnimation;
    private AnimationManager animationManager;

    public int score;
    public int health;
    private long startTime;

    public Player(Rect rectangle, int x, int y, Context context) {
        this.rectangle = rectangle;
        this.x = x;
        this.y = y;
        this.width = rectangle.width();
        this.height = rectangle.height();
        this.context = context;

        SharedPreferences sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        BitmapFactory bf = new BitmapFactory();

        images = new Bitmap[PILOT_ANIMATION_FRAMES];


        if(sharedPref.getInt("gender",0) == 0){
            //idleimg = bf.decodeResource(context.getResources(), R.drawable.josepiloto);
            player = bf.decodeResource(context.getResources(), R.drawable.animacao_josepiloto);

        }
        else
        {
            //idleimg = bf.decodeResource(context.getResources(), R.drawable.mariapiloto);
            player = bf.decodeResource(context.getResources(), R.drawable.animacao_mariapiloto);
        }


        for(int i = 0; i< PILOT_ANIMATION_FRAMES; i++){

            images[i] = Bitmap.createBitmap(player, i * (width +260), 0, width + 270, height + 120);
        }



        pilotAnimation = new Animation(new Bitmap[]{images[3]}, 3);

        animationManager =  new AnimationManager(new Animation[]{pilotAnimation});

        score = 0;
        health = 3;
        startTime = System.nanoTime();
    }


    public void draw(Canvas canvas) {

        animationManager.draw(canvas, rectangle);
    }

    public void update(Point point) {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }

        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);


        int state = 0;

        if (rectangle.left - oldLeft > 5) {
            state = 1;//RIGHT
        } else if (rectangle.left - oldLeft < -5) {
            state = 2;//LEFT
        }

        state = 0;

        animationManager.playAnim(state);
        animationManager.update();
    }
    public int getScore(){
        return score;
    }

    public void resetScore(){
        score = 0;
    }

    public void takeDamage(int d){
        health-=d;
    }

    public void restoreHealth(int r){
        if(health < 3)
            health+=r;
    }

    public int getHealth(){
        return health;
    }

    public void resetHealth(){
        health = 3;
    }

    public Rect getImageRectangle(){
        return rectangle;
    }

    public boolean isAlive(){
        return health>0;
    }
    public void rotate(Matrix m){
    }
}
