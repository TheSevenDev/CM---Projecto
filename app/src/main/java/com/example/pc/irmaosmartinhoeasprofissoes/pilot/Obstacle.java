package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.Animation;

import java.util.Random;

/**
 * Created by Bruno on 19/11/2017.
 */

public class Obstacle extends GameObject{

    private final int SPEED_CAP = 68;
    private Random rand = new Random();
    private int speed;
    private int score;
    private Animation animation = new Animation();

    private Bitmap spritesheet;

    public Obstacle(Bitmap bird,Bitmap madBird, int x, int y,int w, int h, int s,int numFrames) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        score = s;

        speed = 14 + (int) (rand.nextDouble() * score / 30);

        if(speed >= SPEED_CAP)
            speed = SPEED_CAP;

        spritesheet = bird;

        animation.setFrames(new Bitmap[]{spritesheet});
        animation.setDelay(100 - speed);
    }

    public void update(){
        x-=speed;
        animation.update();
    }

    public void draw(Canvas canvas){
        try {
            canvas.drawBitmap(animation.getImage(), x, y, new Paint());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

