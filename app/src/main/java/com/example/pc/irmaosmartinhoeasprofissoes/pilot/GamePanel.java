package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by Bruno on 17/11/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    //public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    //public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private final float MAX_DAYLIGHT = 70.0f;

    private MainThread thread;
    private Background background;

    private Player player;
    private Point playerPoint;

    private OrientationData orientationData;
    private LightData lightData;


    private long frameTime;//time elapsed between frames
    private long initTime;

    private Context context;

    public GamePanel(Context context)
    {
        super(context);
        this.context = context;

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);

        orientationData = new OrientationData(context);
        orientationData.register();

        lightData = new LightData(context);
        lightData.register();

        frameTime = System.currentTimeMillis();
        initTime = System.currentTimeMillis();

        Rect playerRect = new Rect(150,150,300,275);
        player = new Player(playerRect, BitmapFactory.decodeResource(getResources(), R.drawable.martinhopiloto),50, 50, context);

        playerPoint = new Point(170, HEIGHT/3);
        player.update(playerPoint);
        player.update(new Point(500, HEIGHT/3));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            }catch(InterruptedException e){e.printStackTrace();}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.pilotbg_day), WIDTH, HEIGHT);
        background.setVector(-5);

        thread = new MainThread(getHolder(), this);


        thread.setRunning(true);
        thread.start();
    }


    public void update()
    {
        if(frameTime < initTime)
        frameTime = initTime;

        int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();
        if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
            float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];    //Y DIRECTION
            float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];     //X DIRECTION

            float xSpeed = 2* roll * WIDTH/800f;
            float ySpeed = 2* pitch * HEIGHT/800f; //1SECOND TO FULLFILL THE SCREEN

            playerPoint.y -= Math.abs(xSpeed* elapsedTime) > 3 ? xSpeed* elapsedTime : 0;
        }


        //if(lightData.getLightValue() >= MAX_DAYLIGHT){
        //    background.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.pilotbg_night));
        //}
        //else{
        //    background.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.pilotbg_day));
        //}

        //BOUNDS
        if(playerPoint.x < 0)
            playerPoint.x = 0;
        else if(playerPoint.x > WIDTH)
            playerPoint.x = WIDTH;

        if(playerPoint.y < 0)
            playerPoint.y = 0;
        else if(playerPoint.y > HEIGHT)
            playerPoint.y = HEIGHT;

        background.update();
        player.update(playerPoint);
        //System.out.println(" " + lightData.getLightValue());
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null)
        {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

}
