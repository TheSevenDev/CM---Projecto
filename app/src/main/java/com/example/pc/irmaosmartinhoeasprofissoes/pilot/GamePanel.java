package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Bruno on 17/11/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    //public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    //public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public final int MIN_HEIGHT_BOUND = 20;
    public final int MAX_HEIGHT_BOUND = (HEIGHT - 150);


    private final float MIN_DAYLIGHT = 57.0f; //VERIFICAR QUAL O VALOR ESTIMADO À LUZ DO DIA

    private MainThread thread;
    private ScrollingBackground backgroundDay;
    private ScrollingBackground backgroundNight;

    private Player player;
    private Point playerPoint;

    private ArrayList<Obstacle> obstacles;
    private long obstacleStartTime;
    private long ostacleElapsedTime;

    private OrientationData orientationData;
    private LightData lightData;

    private long frameTime;//time elapsed between frames
    private long initTime;

    private Context context;

    private Random rand = new Random();

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
        backgroundDay = new ScrollingBackground(BitmapFactory.decodeResource(getResources(), R.drawable.pilotbg_day) ,WIDTH, HEIGHT);
        backgroundDay.setVector(-5);

        backgroundNight = new ScrollingBackground(BitmapFactory.decodeResource(getResources(), R.drawable.pilotbg_night) ,WIDTH, HEIGHT);
        backgroundNight.setVector(-5);

        Rect playerRect = new Rect(150,150,300,275);
        player = new Player(playerRect, BitmapFactory.decodeResource(getResources(), R.drawable.martinhopiloto),50, 50, context);

        playerPoint = new Point(170, HEIGHT/2);
        player.update(playerPoint);


        obstacles = new ArrayList<>();
        obstacleStartTime = System.nanoTime();


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

        //BOUNDS
        if(playerPoint.x < 0)
            playerPoint.x = 0;
        else if(playerPoint.x > WIDTH)
            playerPoint.x = WIDTH;

        if(playerPoint.y < MIN_HEIGHT_BOUND)
            playerPoint.y = MIN_HEIGHT_BOUND;
        else if(playerPoint.y > MAX_HEIGHT_BOUND)
            playerPoint.y = MAX_HEIGHT_BOUND;

        backgroundDay.update();
        backgroundNight.update();

        player.update(playerPoint);


        long obstaclesElapsed = (System.nanoTime() - obstacleStartTime) / 1000000;
        if(obstaclesElapsed > 1000) {
            int randomY = (int)(rand.nextInt(MAX_HEIGHT_BOUND - 90) + 90);
            if (obstacles.isEmpty()) {
                obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_day), WIDTH + 10, randomY, 90, 85, player.getScore(), 7));
            } else {
                obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_day),WIDTH + 10, randomY,90,85, player.getScore(), 7));
            }
            //reset timer
            obstacleStartTime = System.nanoTime();
        }

        for(Obstacle ob : obstacles){
            ob.update();
            if(collision(ob,player)){
                //obstacles.remove()
                System.out.println("REBENTOU");
            }
        }

        Iterator<Obstacle> it = obstacles.iterator();
        Obstacle aux = null;
        while(it.hasNext()){
            aux = it.next();
            if(aux.getX() < -WIDTH)
                it.remove();
            else{
                aux.update();
                if(collision(aux,player)){
                    System.out.println("REBENTOU");
                }
            }
        }
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

            if(lightData.getLightValue() >= MIN_DAYLIGHT) {
                backgroundDay.draw(canvas);
            }
            else {
                backgroundNight.draw(canvas);
            }

            player.draw(canvas);
            for(Obstacle ob : obstacles){
                ob.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }

    public boolean collision(GameObject a, GameObject b){
        return Rect.intersects(a.getRectangle(),b.getRectangle());
    }

}
