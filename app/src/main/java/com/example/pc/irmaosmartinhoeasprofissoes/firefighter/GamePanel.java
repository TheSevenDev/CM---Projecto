package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;

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
    public void surfaceCreated(SurfaceHolder holder)
    {
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.firefighterbg));
        fires = new ArrayList<>();
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 470, 82));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 575, 82)); //+105x
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 680, 82));

        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 470, 168)); //+86y
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 575, 168));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 680, 168));

        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 470, 254));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 575, 254));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 680, 254));

        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 470, 340));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 575, 340));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 680, 340));

        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 470, 426));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 575, 426));
        fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.fire), 100, 100, 680, 426));

        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    public void update()
    {

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
}
