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
import com.example.pc.irmaosmartinhoeasprofissoes.EnumGame;
import com.example.pc.irmaosmartinhoeasprofissoes.Pause;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.GameOver;

/**
 * Classe que gere todos os componentes do jogo do pasteleiro
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    private MainThread thread;
    private Pause pause;
    private GameOver gameOver;
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
        musicBackground.setLooping(true);

        gameOver = new GameOver(getContext(), EnumGame.COOK);
        restartGame();

        pause = new Pause(getContext());


        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    //Gere a acçao do toque no jogo
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = event.getX();
            float y = event.getY();

            if(!pause.isPaused() && !gameOver.isGameOver())
            {
                componentRotator.onTouchArrows(x, y);
                componentRotator.onTouchComponentPanel(x, y);
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

    //Método corrido a cada frame
    public void update()
    {
        if(!pause.isPaused() && !gameOver.isGameOver())
        {
            if (!musicBackground.isPlaying()) {
                musicBackground.start();
            }
            componentRotator.update();
        }
        else
        {
            if(musicBackground.isPlaying())
                musicBackground.pause();
        }
    }

    //Desenha todos os componentes
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

                componentRotator.draw(canvas, true);

                componentRotator.getTargetCake().draw(canvas, true);
            }
            else
            {
                background.draw(canvas, false);

                if(cake.getImage() != null)
                    cake.draw(canvas, false);

                componentRotator.draw(canvas, false);

                componentRotator.getTargetCake().draw(canvas, false);
            }

            if(!gameOver.isGameOver())
                pause.draw(canvas);
            else
            {
                gameOver.draw(canvas);
                canvas.drawBitmap(componentRotator.getTargetCake().getImage(),
                        (int)(Double.parseDouble(getResources().getString(R.string.game_over_cake_x)) * WIDTH) ,
                        (int)(Double.parseDouble(getResources().getString(R.string.game_over_cake_y)) * HEIGHT), null);
            }

            canvas.restoreToCount(savedState);
        }
    }

    //Reinicia o jogo
    public void restartGame()
    {
        cake = new Cake((int) (Double.parseDouble(getResources().getString(R.string.cake_width)) * WIDTH),
                (int) (Double.parseDouble(getResources().getString(R.string.cake_height)) * HEIGHT),
                (int) (Double.parseDouble(getResources().getString(R.string.cake_x)) * WIDTH),
                (int) (Double.parseDouble(getResources().getString(R.string.cake_y)) * HEIGHT), getContext());

        componentRotator = new ComponentRotator(getContext(), cake, gameOver);
    }
}
