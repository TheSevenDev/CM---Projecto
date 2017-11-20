package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.Background;
import com.example.pc.irmaosmartinhoeasprofissoes.Pause;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Diogo on 10/11/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    //PROCURAR MELHOR SOLUÇAO, NET DIZ QUE TEM CONFLITOS SE A NAV BAR ESTIVER VISIVEL
    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    //PODE SER MELHOR
    //DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    //int a = displayMetrics.widthPixels;
    //int b = displayMetrics.heightPixels;

    private MainThread thread;
    private Background background;
    private WaterMeter waterMeter;

    private ArrayList<Fire> fires;
    private ArrayList<Cat> cats;
    private ArrayList<Waterdrop> waterDrops;

    private ArrayList<Integer> fireX;
    private ArrayList<Integer> fireY;

    private Random rand;

    private int score;
    private int waterDecrease;
    private int waterGain;

    private int timeRemaining;
    private long timerStart;
    private long fireStart;
    private long waterStart;

    private long pausedTimeStart;
    private long pausedTimeFire;
    private long pausedTimeWater;
    private long pausedTimeTimer;

    private double waterMeterValue;

    private Pause pause;
    private GameOver gameOver;

    private Activity gameActivity;

    public GamePanel(Context context, Activity activity)
    {
        super(context);

        gameActivity = activity;

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
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
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.firefighterbg), WIDTH, HEIGHT);
        waterMeter = new WaterMeter(BitmapFactory.decodeResource(getResources(), R.drawable.barraagua),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_sprite_width)) * WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_sprite_height)) * HEIGHT),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_x)) * WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_y)) * HEIGHT));

        restartGame();

        fireX = new ArrayList<>();
        fireY = new ArrayList<>();
        populateFireCoords();

        rand = new Random();
        waterDecrease = Integer.parseInt(getResources().getString(R.string.water_decrease));
        waterGain = Integer.parseInt(getResources().getString(R.string.water_gain));

        /*
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_width))*WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_height))*HEIGHT),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x1))*WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y1))*HEIGHT)));

                */

        pause = new Pause(getContext());
        gameOver = new GameOver(getContext());

        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = event.getX();
            float y = event.getY();

            if(!pause.isPaused() && !gameOver.isGameOver())
            {
                for (Fire f : fires) {
                    if (x >= f.getX() && x < (f.getX() + f.getWidth())
                            && y >= f.getY() && y < (f.getY() + f.getHeight())) {
                        if (waterMeterValue >= waterDecrease) {
                            fires.remove(f);
                            addScore(Integer.parseInt(getResources().getString(R.string.fire_score)));
                            decreaseWater();
                        } else {
                            waterMeter.outOfWater();
                        }

                        return true;
                    }
                }

                for (Waterdrop w : waterDrops) {
                    if (x >= w.getX() && x < (w.getX() + w.getWidth())
                            && y >= w.getY() && y < (w.getY() + w.getHeight())) {
                        waterDrops.remove(w);
                        increaseWater();

                        return true;
                    }
                }

                pause.onTouchPauseButton((int)x, (int)y);
            }
            else if(pause.isPaused())
            {
                if(pause.onTouchPauseScreen((int) x, (int) y))
                    gameActivity.onBackPressed();
            }
            else if(gameOver.isGameOver())
            {
                int option = gameOver.onTouchPauseScreen((int) x, (int) y);

                if(option == Integer.parseInt(getResources().getString(R.string.game_over_restart_option)))
                    restartGame();
                else if(option == Integer.parseInt(getResources().getString(R.string.game_over_exit_option)))
                    gameActivity.onBackPressed();
            }

            return true;
        }

        return true;
        //return onTouchEvent(event);
    }

    public void update()
    {
        if(!pause.isPaused() && !gameOver.isGameOver())
        {
            if(pausedTimeStart != 0)
            {
                pausedTimeFire = System.nanoTime() - pausedTimeStart;
                pausedTimeTimer = System.nanoTime() - pausedTimeStart;
                pausedTimeWater = System.nanoTime() - pausedTimeStart;
                pausedTimeStart = 0;
            }

            long fireElapsed = (System.nanoTime() - fireStart - pausedTimeFire) / 1000000;
            long timerElapsed = (System.nanoTime() - timerStart - pausedTimeTimer) / 1000000;
            long waterElapsed = (System.nanoTime() - waterStart - pausedTimeWater) / 1000000;

            if (fireElapsed > 3000)
            {
                //put here chance of cat
                int i = rand.nextInt(fires.size());

                fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fogo),
                        (int) (Double.parseDouble(getResources().getString(R.string.fire_sprite_width)) * WIDTH),
                        (int) (Double.parseDouble(getResources().getString(R.string.fire_sprite_height)) * HEIGHT),
                        fireX.get(i), fireY.get(i), getContext()));

                fireStart = System.nanoTime();
                pausedTimeFire = 0;
            }

            if (timerElapsed > 1000) {
                timeRemaining--;

                if (timeRemaining < 0)
                    timeRemaining = 0;

                timerStart = System.nanoTime();
                pausedTimeTimer = 0;
            }

            if (waterElapsed > 7000)
            {
                //spawn water
                int maxW = (int) (WIDTH * 0.9);
                int minW = (int) (WIDTH * 0.1);

                int maxH = (int) (HEIGHT * 0.9);
                int minH = (int) (HEIGHT * 0.1);

                int x = rand.nextInt(maxW - minW + 1) + minW;
                int y = rand.nextInt(maxH - minH + 1) + minH;

                waterDrops.add(new Waterdrop(BitmapFactory.decodeResource(getResources(), R.drawable.gota),
                        (int) (Double.parseDouble(getResources().getString(R.string.waterdrop_sprite_width)) * WIDTH),
                        (int) (Double.parseDouble(getResources().getString(R.string.waterdrop_sprite_height)) * HEIGHT),
                        x, y, getContext()));

                waterStart = System.nanoTime();
                pausedTimeWater = 0;
            }

            checkDespawns();

            waterMeter.update();

            if(timeRemaining == 0)
            {
                gameOver.setScore(score);
                gameOver.setGameOver(true);
            }
        }
        else if(pause.isPaused())
        {
            if(pausedTimeStart == 0)
                pausedTimeStart = System.nanoTime();
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

            if(pause.isPaused() || gameOver.isGameOver())
            {
                background.draw(canvas, true);
                waterMeter.draw(canvas, true);
            }
            else
            {
                background.draw(canvas, false);
                waterMeter.draw(canvas, false);
            }

            if(!waterMeter.isNoWaterWarning())
                drawWaterLevel(canvas);

            //draw fires
            for(Fire fire: fires)
            {
                fire.draw(canvas);
            }

            //draw cats
            for(Cat cat: cats)
            {
                cat.draw(canvas);
            }

            //draw drops
            for(Waterdrop water: waterDrops)
            {
                water.draw(canvas);
            }

            drawText(canvas);

            pause.draw(canvas);
            gameOver.draw(canvas);

            canvas.restoreToCount(savedState);
        }
    }

    public void populateFireCoords()
    {
        for(int i = 0; i < 5; i++)
        {
            fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x1))*WIDTH));
            fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x2))*WIDTH));
            fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x3))*WIDTH));
        }

        fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x1))*WIDTH));
        fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x3))*WIDTH));

        for(int i = 0; i < 3; i++)
        {
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y1))*HEIGHT));
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y2))*HEIGHT));
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y3))*HEIGHT));
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y4))*HEIGHT));
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y5))*HEIGHT));
        }

        fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y6))*HEIGHT));
        fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y6))*HEIGHT));
    }

    public void addScore(int score)
    {
        this.score += score;
    }

    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE: " + score, 0.02f*WIDTH, 0.05f*HEIGHT, paint);
        canvas.drawText("TIME: " + timeRemaining, 0.02f*WIDTH, 0.10f*HEIGHT, paint);
    }

    public void decreaseWater()
    {
        waterMeterValue -= waterDecrease;
    }

    public void increaseWater()
    {
        waterMeterValue += waterGain;

        if(waterMeterValue > 100)
            waterMeterValue = 100;
    }

    public void checkDespawns()
    {
        for(Waterdrop w : waterDrops)
        {
            w.update(pausedTimeWater);
            if(w.isDespawn())
                waterDrops.remove(w);
        }

        for(Fire f : fires)
        {
            f.update(pausedTimeFire);
            if(f.isDespawn())
                fires.remove(f);
        }
    }

    public void drawWaterLevel(Canvas canvas)
    {
        int barHeight = (int)(Double.parseDouble(getResources().getString(R.string.water_bar_height_mod))*HEIGHT);
        int barBottom = (int)(Double.parseDouble(getResources().getString(R.string.water_bar_bottom_mod))*HEIGHT);
        int barDifference = (int)((barBottom - barHeight)*(1-(waterMeterValue/100)));

        Paint paint = new Paint();
        Rect r = new Rect((int)(Double.parseDouble(getResources().getString(R.string.water_bar_left_mod))*WIDTH),
                barHeight + barDifference,
                (int)(Double.parseDouble(getResources().getString(R.string.water_bar_right_mod))*WIDTH),
                barBottom);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#77B6E4"));

        if(pause.isPaused() || gameOver.isGameOver())
            paint.setColorFilter(new LightingColorFilter(Color.rgb(123, 123, 123), 0));

        canvas.drawRect(r, paint);
    }

    public void restartGame()
    {
        fires = new ArrayList<>();
        waterDrops = new ArrayList<>();
        cats = new ArrayList<>();
        waterMeterValue = 100;
        timeRemaining = 60;
        score = 0;
        timerStart = System.nanoTime();
        fireStart = System.nanoTime();
        waterStart = System.nanoTime();
    }
}
