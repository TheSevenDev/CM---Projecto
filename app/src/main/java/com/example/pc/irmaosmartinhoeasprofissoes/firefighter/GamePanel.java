package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.lang.reflect.Array;
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

    private double waterMeterValue;

    public GamePanel(Context context)
    {
        super(context);

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

        fires = new ArrayList<>();
        cats = new ArrayList<>();
        waterDrops = new ArrayList<>();
        fireX = new ArrayList<>();
        fireY = new ArrayList<>();

        populateFireCoords();
        rand = new Random();
        score = 0;
        timeRemaining = 60;
        waterMeterValue = 100;
        waterDecrease = Integer.parseInt(getResources().getString(R.string.water_decrease));
        waterGain = Integer.parseInt(getResources().getString(R.string.water_gain));

        fireStart = System.nanoTime();
        waterStart = System.nanoTime();

        /*
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_width))*WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_height))*HEIGHT),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x1))*WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y1))*HEIGHT)));

                */

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

            for(Fire f : fires)
            {
                if (x >= f.getX() && x < (f.getX() + f.getWidth())
                        && y >= f.getY() && y < (f.getY() + f.getHeight()))
                {
                    if(waterMeterValue >= waterDecrease)
                    {
                        fires.remove(f);
                        addScore(Integer.parseInt(getResources().getString(R.string.fire_score)));
                        decreaseWater();
                    }
                    return true;
                }
            }

            for(Waterdrop w : waterDrops)
            {
                if (x >= w.getX() && x < (w.getX() + w.getWidth())
                        && y >= w.getY() && y < (w.getY() + w.getHeight()))
                {
                    waterDrops.remove(w);
                    increaseWater();

                    return true;
                }
            }

            return true;
        }

        return true;
        //return onTouchEvent(event);
    }

    public void update()
    {
        long fireElapsed = (System.nanoTime()-fireStart)/1000000;
        long timerElapsed = (System.nanoTime()-timerStart)/1000000;
        long waterElapsed = (System.nanoTime()-waterStart)/1000000;

        if(fireElapsed > 3000)
        {
            //put here chance of cat
            int i = rand.nextInt(17);

            fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fogo),
                    (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_width))*WIDTH),
                    (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_height))*HEIGHT),
                    fireX.get(i), fireY.get(i), getContext()));

            fireStart = System.nanoTime();
        }

        if(timerElapsed > 1000)
        {
            timeRemaining--;

            if(timeRemaining < 0)
                timeRemaining = 0;

            timerStart = System.nanoTime();
        }

        if(waterElapsed > 7000)
        {
            //spawn water
            int maxW = (int)(WIDTH * 0.9);
            int minW = (int)(WIDTH * 0.1);

            int maxH = (int)(HEIGHT * 0.9);
            int minH = (int)(HEIGHT * 0.1);

            int x = rand.nextInt(maxW - minW + 1) + minW;
            int y = rand.nextInt(maxH - minH + 1) + minH;

            waterDrops.add(new Waterdrop(BitmapFactory.decodeResource(getResources(), R.drawable.gota),
                    (int)(Double.parseDouble(getResources().getString(R.string.waterdrop_sprite_width))*WIDTH),
                    (int)(Double.parseDouble(getResources().getString(R.string.waterdrop_sprite_height))*HEIGHT),
                    x, y, getContext()));

            waterStart = System.nanoTime();
        }

        checkDespawns();
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
            waterMeter.draw(canvas);
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
        canvas.drawText("TIME: " + timeRemaining, 0.02f*WIDTH, 0.05f*HEIGHT, paint);
        canvas.drawText("SCORE: " + score, WIDTH - (0.1f*WIDTH), 0.05f*HEIGHT, paint);
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
            w.update();
            if(w.isDespawn())
                waterDrops.remove(w);
        }

        for(Fire f : fires)
        {
            f.update();
            if(f.isDespawn())
                fires.remove(f);
        }
    }

    public void drawWaterLevel(Canvas canvas)
    {
        int barHeight = (int)(0.2535*HEIGHT);
        int barBottom = (int)(0.85*HEIGHT);
        int barDifference = (int)((barBottom - barHeight)*(1-(waterMeterValue/100)));

        Paint paint = new Paint();
        Rect r = new Rect((int)(0.049*WIDTH),
                barHeight + barDifference,
                (int)(0.08*WIDTH),
                barBottom);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#77B6E4"));
        canvas.drawRect(r, paint);
    }
}
