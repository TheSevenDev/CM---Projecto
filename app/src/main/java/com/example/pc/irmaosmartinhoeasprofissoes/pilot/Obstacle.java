package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.Animation;

import java.util.Random;

/**
 * Created by Bruno on 19/11/2017.
 */

public class Obstacle extends GameObject{

    private final int SPEED_CAP = 40;
    private Random rand = new Random();
    private int speed;
    private int score;
    private Animation animation = new Animation();

    private Bitmap spritesheet;

    public Obstacle(Bitmap res, int x, int y,int w, int h, int s,int numFrames, Context context) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        score = s;

        speed = 7 + (int)(rand.nextDouble() * score/30);

        if(speed >= SPEED_CAP)
            speed = SPEED_CAP;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i= 0 ;i < image.length; i++){
            image[i] = Bitmap.createBitmap(spritesheet, 0, i*height, width,height);
        }

        animation.setFrames(image);
        animation.setDelay(100 - speed);
    }

    public void update(){
        x-=speed;
        animation.update();
    }

    public void draw(Canvas canvas){
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getWidht(){
        return width-10;
    }






}
