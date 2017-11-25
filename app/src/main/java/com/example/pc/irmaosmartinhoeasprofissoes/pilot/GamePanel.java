package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;
import android.support.constraint.solver.widgets.Rectangle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.EnumGame;
import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.Pause;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.firefighter.GameOver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Bruno on 17/11/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final int GRAVITY = 7;
    public final int MIN_HEIGHT_BOUND = 20;
    public final int MAX_HEIGHT_BOUND = (HEIGHT - 150);


    private final float MIN_DAYLIGHT = 20.0f; //VERIFICAR QUAL O VALOR ESTIMADO À LUZ DO DIA
    private MainThread thread;
    private ScrollingBackground backgroundDay;
    private ScrollingBackground backgroundNight;

    private Player player;
    private Point playerPoint;

    private ArrayList<Obstacle> obstacles;
    private long obstacleStartTime;

    private Bitmap health[];

    private Pause pause;
    private GameOver gameOver;

    private OrientationData orientationData;
    private LightData lightData;

    private long frameTime;//time elapsed between frames
    private long initTime;

    private Context context;
    private Activity gameActivity;

    private Random rand = new Random();

    public GamePanel(Context context, Activity activity)
    {
        super(context);
        this.context = context;
        this.gameActivity = activity;

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

        health = new Bitmap[4];
        health[0] = BitmapFactory.decodeResource(getResources(), R.drawable.slot0);
        health[1] = BitmapFactory.decodeResource(getResources(), R.drawable.slot1);
        health[2] = BitmapFactory.decodeResource(getResources(), R.drawable.slot2);
        health[3] = BitmapFactory.decodeResource(getResources(), R.drawable.slot3);

        obstacles = new ArrayList<>();
        obstacleStartTime = System.nanoTime();

        pause = new Pause(getContext());
        gameOver = new GameOver(getContext(), EnumGame.PILOT);


        thread = new MainThread(getHolder(), this);

        resetGame();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            if(!pause.isPaused() && !gameOver.isGameOver())
                pause.onTouchPauseButton((int)x, (int)y);
            else if(pause.isPaused())
            {
                if(pause.onTouchPauseScreen((int) x, (int) y))
                    gameActivity.onBackPressed();
            }
            else if(gameOver.isGameOver())
            {
                int option = gameOver.onTouchPauseScreen((int) x, (int) y);
                if(option == Integer.parseInt(getResources().getString(R.string.game_over_restart_option)))
                    resetGame();
                else if(option == Integer.parseInt(getResources().getString(R.string.game_over_exit_option)))
                    gameActivity.onBackPressed();
            }
        }
        return true;
    }


    public void resetGame(){

        Rect playerRect = new Rect(150,150,315,275);
        player = new Player(playerRect, 50, 50, context);
        player.resetScore();
        player.resetHealth();


        playerPoint = new Point(170, HEIGHT/2);
        player.update(playerPoint);

        obstacles = new ArrayList<>();
        gameOver.setGameOver(false);
    }

    public void update()
    {
        if(!player.isAlive())
            gameOver.setGameOver(true);
        else{
            if(!pause.isPaused() && !gameOver.isGameOver()) {
                if (frameTime < initTime)
                    frameTime = initTime;

                backgroundDay.update();
                backgroundNight.update();
                spawnObstacles();

                orientation();
                playerPoint.y += GRAVITY;
                checkBounds();
                player.update(playerPoint);

                checkCollision();
            }
        }
    }



    public void orientation(){
        int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();

        if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
            float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];    //Y DIRECTION
            float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];     //X DIRECTION

            float xSpeed = 2 * roll * WIDTH / 1100f;

            playerPoint.y -= Math.abs(xSpeed * elapsedTime) > 3 ? xSpeed * elapsedTime : 0;
        }
    }

    public void checkBounds(){
        if (playerPoint.x < 0)
            playerPoint.x = 0;
        else if (playerPoint.x > WIDTH)
            playerPoint.x = WIDTH;

        if (playerPoint.y < MIN_HEIGHT_BOUND)
            playerPoint.y = MIN_HEIGHT_BOUND;
        else if (playerPoint.y > MAX_HEIGHT_BOUND)
            playerPoint.y = MAX_HEIGHT_BOUND;
    }

    public void checkCollision(){
        Iterator<Obstacle> it = obstacles.iterator();
        Obstacle aux = null;
        while (it.hasNext()) {
            aux = it.next();
            if (aux.getX() < -WIDTH)
                it.remove();
            else {
                aux.update();
                if (collision(aux.getRectangle(), player.getImageRectangle())) {
                        player.takeDamage(1);
                    it.remove();
                }
            }
        }
    }

    public void spawnObstacles(){
        long obstaclesElapsed = (System.nanoTime() - obstacleStartTime) / 1000000;
        if (obstaclesElapsed > (1000 - player.getScore()/4)) {
            int randomY = (int) (rand.nextInt(MAX_HEIGHT_BOUND - 90) + 90);
            Bitmap bird = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_day);
            Bitmap madBird = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_day_mad);

            if (obstacles.isEmpty()) {
                obstacles.add(new Obstacle(bird, madBird, WIDTH + 10, randomY, 66, 69, player.getScore(), 7));
            } else {
                obstacles.add(new Obstacle(bird, madBird, WIDTH + 10, randomY, 66, 69, player.getScore(), 7));
            }

            obstacleStartTime = System.nanoTime();
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
            canvas.drawBitmap(health[player.getHealth()],WIDTH*0.01f, HEIGHT*0.03f, null); //CHANGE VALUES TO SCALE
            for(Obstacle ob : obstacles){
                ob.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }

        if(!gameOver.isGameOver())
            pause.draw(canvas);

        gameOver.draw(canvas);
    }

    public boolean collision(Rect a, Rect b){
        return Rect.intersects(a,b);
    }

}
