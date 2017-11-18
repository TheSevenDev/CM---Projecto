package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by Bruno on 18/11/2017.
 */

public class Player extends GameObject {

    private Bitmap player;
    private Rect rectangle;

    //Animations
    private Animation idle, walkUp, walkDown;
    private AnimationManager animationManager;


    public Player(Rect rectangle, Bitmap res, int x, int y, Context context) {
        this.rectangle = rectangle;
        this.x = x;
        this.y = y;
        this.width = rectangle.width();
        this.height = rectangle.height();
        this.context = context;
        //player = Bitmap.createScaledBitmap(res, width, height, false);



        BitmapFactory bf = new BitmapFactory();
        Bitmap idleimg = bf.decodeResource(context.getResources(), R.drawable.martinhopiloto);
        Bitmap walk1 = bf.decodeResource(context.getResources(), R.drawable.martinhopiloto);
        Bitmap walk2 = bf.decodeResource(context.getResources(), R.drawable.martinhopiloto);

        idle = new Animation(new Bitmap[]{idleimg}, 2);
        //walkUp = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        //walkUp = walkDown = idle;

        animationManager =  new AnimationManager(new Animation[]{idle});
    }


    public void draw(Canvas canvas) {

        //canvas.drawBitmap(player, x, y, null);
        animationManager.draw(canvas, rectangle);
    }

    public void update(Point point) {
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
}
