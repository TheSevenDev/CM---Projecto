package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
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
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.firefighterbg));
        fires = new ArrayList<>();
        fireX = new ArrayList<>();
        fireY = new ArrayList<>();

        populateFireCoords();
        rand = new Random();

        fireStart = System.nanoTime();

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

            float x = event.getX();
            float y = event.getY();

            for(Fire f : fires)
            {
                if (x >= f.getX() && x < (f.getX() + f.getWidth())
                        && y >= f.getY() && y < (f.getY() + f.getHeight())) {
                    System.out.println("welele");
                    return true;
                }
            }

            return true;
        }

        return true;
        //return true;
    }

    public void update()
    {

        long elapsed = (System.nanoTime()-fireStart)/1000000;

        System.out.println(elapsed);

        if(elapsed > 3000)
        {
            int x = fireX.remove(rand.nextInt(4));
            int y = fireY.remove(rand.nextInt(4));

            fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, x, y));

            fireStart = System.nanoTime();
        }

    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        final float scaleFactorX = getWidth()/(WIDTH*2.f);
        final float scaleFactorY = getHeight()/(HEIGHT*2.f);


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
            fireX.add(470);
            fireX.add(575);
            fireX.add(680);
        }

        for(int i = 0; i < 3; i++)
        {
            fireY.add(82);
            fireY.add(168);
            fireY.add(254);
            fireY.add(340);
            fireY.add(426);
        }
    }
}
