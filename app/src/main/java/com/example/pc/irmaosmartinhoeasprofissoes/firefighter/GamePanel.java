package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    private ArrayList<Fire> fires;
    private ArrayList<Integer> fireX;
    private ArrayList<Integer> fireY;
    private long fireStart;

    private Random rand;

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
        fires = new ArrayList<>();
        fireX = new ArrayList<>();
        fireY = new ArrayList<>();

        populateFireCoords();
        rand = new Random();

        fireStart = System.nanoTime();

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
            System.out.println("wululu");

            //float x = event.getX() - 270;
            //float y = event.getY() - 180;
            float x = event.getX();
            float y = event.getY();

            float x1 = fires.get(0).getX();
            float x2 = fires.get(0).getX() + fires.get(0).getWidth();
            float x3 = fires.get(0).getY();
            float x4 = fires.get(0).getY() + fires.get(0).getHeight();


            for(Fire f : fires)
            {
                boolean a = (x >= f.getX());
                boolean b = (x < (f.getX() + f.getWidth()));
                boolean c = (y >= f.getY());
                boolean d = (y < (f.getY() + f.getHeight()));


                if (x >= f.getX() && x < (f.getX() + f.getWidth())
                        && y >= f.getY() && y < (f.getY() + f.getHeight())) {
                    fires.remove(f);
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

        long elapsed = (System.nanoTime()-fireStart)/1000000;

        System.out.println(elapsed);

        if(elapsed > 3000)
        {
            int i = rand.nextInt(17);

            fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire),
                    (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_width))*WIDTH),
                    (int)(Double.parseDouble(getResources().getString(R.string.fire_sprite_height))*HEIGHT),
                    fireX.get(i), fireY.get(i)));

            fireStart = System.nanoTime();
        }

    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);

            //draw fires
            for(Fire fire: fires)
            {
                fire.draw(canvas);
            }

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
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y5))*HEIGHT));;
        }

        fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y6))*HEIGHT));
        fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y6))*HEIGHT));

    }
}
