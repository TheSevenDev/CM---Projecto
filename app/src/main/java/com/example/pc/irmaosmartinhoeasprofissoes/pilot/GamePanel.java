package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.pilot.Background;
import com.example.pc.irmaosmartinhoeasprofissoes.pilot.MainThread;

/**
 * Created by Bruno on 17/11/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    //public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    //public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    public static final int WIDTH = 856;
    public static final int HEIGHT = 428;

    private MainThread thread;
    private Background background;

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
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.pilotbg), WIDTH, HEIGHT);
        background.setVector(-5);

        thread = new MainThread(getHolder(), this);


        thread.setRunning(true);
        thread.start();
    }


    public void update()
    {
        background.update();
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
            canvas.restoreToCount(savedState);
        }
    }

}
