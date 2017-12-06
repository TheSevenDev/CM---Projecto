package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.Background;
import com.example.pc.irmaosmartinhoeasprofissoes.R;


/**
 * Created by TheSeven on 19/11/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    private MainThread thread;
    private Background background;
    private MediaPlayer musicBackground;
    private ComponentRotator componentRotator;

    private Activity gameActivity;
    private Cake cake;

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
    public void surfaceDestroyed(SurfaceHolder holder)
    {
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

        musicBackground.stop();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background_cook), WIDTH, HEIGHT);
        musicBackground = MediaPlayer.create(getContext(), R.raw.cook);
        musicBackground.start();
        musicBackground.setLooping(true);

        cake = new Cake((int) (Double.parseDouble(getResources().getString(R.string.cake_width)) * WIDTH),
                (int) (Double.parseDouble(getResources().getString(R.string.cake_height)) * HEIGHT),
                (int) (Double.parseDouble(getResources().getString(R.string.cake_x)) * WIDTH),
                (int) (Double.parseDouble(getResources().getString(R.string.cake_y)) * HEIGHT), getContext());

        componentRotator = new ComponentRotator(getContext(), cake);

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

            componentRotator.onTouchArrows(x, y);
            componentRotator.onTouchComponentPanel(x, y);

            return true;
        }

        return true;
        //return onTouchEvent(event);
    }

    public void update()
    {

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

            background.draw(canvas, false);

            componentRotator.draw(canvas);

            if(cake.getImage() != null)
                cake.draw(canvas);

            componentRotator.getTargetCake().draw(canvas);

            canvas.restoreToCount(savedState);
        }
    }
}
