package com.example.pc.irmaosmartinhoeasprofissoes.pilot;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.Animation;

import java.util.Random;

/**
 * Obstáculo do jogo do piloto.
 */

public class Obstacle extends GameObject{

    private final int SPEED_CAP = 68;
    private Random rand = new Random();
    private int speed;
    private Animation animation = new Animation();

    private Bitmap spritesheet;

    public Obstacle(Bitmap bird,Bitmap madBird, int x, int y, int score) {
        this.x = x;
        this.y = y;

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

