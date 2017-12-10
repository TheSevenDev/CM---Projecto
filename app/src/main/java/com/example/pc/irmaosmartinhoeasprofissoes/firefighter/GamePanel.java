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
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.Background;
import com.example.pc.irmaosmartinhoeasprofissoes.EnumGame;
import com.example.pc.irmaosmartinhoeasprofissoes.GameOver;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.Pause;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Diogo on 10/11/2017.
 * Esta classe representa o Painel de Jogo
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{

    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final int TIMEOUT = 60;

    private MainThread thread;
    private Background background;
    private WaterMeter waterMeter;
    private Pause pause;
    private GameOver gameOver;
    private Activity gameActivity;
    private MediaPlayer musicBackground;

    private ArrayList<Fire> fires;
    private ArrayList<Integer> fireX;
    private ArrayList<Integer> fireY;

    private ArrayList<Cat> cats;
    private ArrayList<Integer> catX;
    private ArrayList<Integer> catY;

    private ArrayList<Waterdrop> waterDrops;

    private Random rand;

    private Score score;
    private int waterDecrease;
    private int waterGain;
    private double waterMeterValue;

    private int timeRemaining;
    private long timerStart;
    private long fireStart;
    private long waterStart;

    private long pausedTimeStart;
    private long pausedTimeFire;
    private long pausedTimeWater;
    private long pausedTimeTimer;

    private int spawnerTime;
    private final int MIN_SPAWNER_TIME = Integer.parseInt(getContext().getString(R.string.spawner_min_time));
    private final int MAX_SPAWNER_TIME = Integer.parseInt(getContext().getString(R.string.spawner_max_time));

    private int waterSpawnerTime;
    private final int MIN_WATER_SPAWNER_TIME = Integer.parseInt(getContext().getString(R.string.spawner_min_time));
    private final int MAX_WATER_SPAWNER_TIME = Integer.parseInt(getContext().getString(R.string.spawner_max_time));

    private final int CAT_CHANCE = Integer.parseInt(getContext().getString(R.string.cat_spawn_chance));

    //Construtor
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

        musicBackground.stop();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.bg_teste), WIDTH, HEIGHT);
        waterMeter = new WaterMeter(BitmapFactory.decodeResource(getResources(), R.drawable.barraagua),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_sprite_width)) * WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_sprite_height)) * HEIGHT),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_x)) * WIDTH),
                (int)(Double.parseDouble(getResources().getString(R.string.water_meter_y)) * HEIGHT));

        musicBackground = MediaPlayer.create(getContext(), R.raw.firefighter);
        musicBackground.start();
        musicBackground.setLooping(true);

        restartGame();

        fireX = new ArrayList<>();
        fireY = new ArrayList<>();
        catX = new ArrayList<>();
        catY = new ArrayList<>();

        populateFireCoords();
        populateCatCoords();
        rand = new Random();
        waterDecrease = Integer.parseInt(getResources().getString(R.string.water_decrease));
        waterGain = Integer.parseInt(getResources().getString(R.string.water_gain));

        spawnerTime = Integer.parseInt(getContext().getString(R.string.spawner_start_time));
        waterSpawnerTime = Integer.parseInt(getContext().getString(R.string.water_spawner_start_time));

        pause = new Pause(getContext());
        gameOver = new GameOver(getContext(), EnumGame.FIREFIGHTER);

        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    //Este método verifica se o toque do utilizador
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = event.getX() - 50;
            float y = event.getY();

            if(!pause.isPaused() && !gameOver.isGameOver())
            {
                for (Fire f : fires)
                {
                    if (x >= f.getX() && x < (f.getX() + f.getWidth())
                            && y >= f.getY() && y < (f.getY() + f.getHeight())) {
                        if (waterMeterValue >= waterDecrease) {
                            fires.remove(f);
                            score.addScore(Integer.parseInt(getResources().getString(R.string.fire_score)));
                            decreaseWater();
                            MusicService.playSound(getContext(), R.raw.fire);
                        } else {
                            waterMeter.outOfWater();
                            MusicService.playSound(getContext(), R.raw.error);
                        }

                        return true;
                    }
                }

                for (Waterdrop w : waterDrops)
                {
                    if (x >= w.getX() && x < (w.getX() + w.getWidth())
                            && y >= w.getY() && y < (w.getY() + w.getHeight())) {
                        waterDrops.remove(w);
                        MusicService.playSound(getContext(), R.raw.waterdrop);
                        increaseWater();

                        return true;
                    }
                }

                for (Cat c : cats)
                {
                    if (x >= c.getX() && x < (c.getX() + c.getWidth())
                            && y >= c.getY() && y < (c.getY() + c.getHeight()))
                    {
                            cats.remove(c);
                        score.addScore(Integer.parseInt(getResources().getString(R.string.cat_score)));
                            MusicService.playSound(getContext(), R.raw.cat);

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
    }

    //Este método resfresca os elementos que estão presentes no jogo
    public void update()
    {
        if(!pause.isPaused() && !gameOver.isGameOver())
        {
            if(!musicBackground.isPlaying())
            {
                musicBackground.start();
            }

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

            if (fireElapsed > spawnerTime)
            {
                int chance = rand.nextInt(100);
                int i;

                if(chance > CAT_CHANCE)
                {
                    i = rand.nextInt(fireX.size());

                    fires.add(new Fire(BitmapFactory.decodeResource(getResources(), R.drawable.animacao_fogo),
                             (int) (Double.parseDouble(getResources().getString(R.string.fire_sprite_width)) * WIDTH),
                             (int) (Double.parseDouble(getResources().getString(R.string.fire_sprite_height)) * HEIGHT),
                             fireX.get(i), fireY.get(i), getContext(), 7));
                }
                else
                {
                    i = rand.nextInt(catX.size());

                    cats.add(new Cat(BitmapFactory.decodeResource(getResources(), R.drawable.animacao_gato),
                            (int) (Double.parseDouble(getResources().getString(R.string.cat_sprite_width)) * WIDTH),
                            (int) (Double.parseDouble(getResources().getString(R.string.cat_sprite_height)) * HEIGHT),
                            catX.get(i), catY.get(i), getContext(), 3));

                }

                fireStart = System.nanoTime();
                pausedTimeFire = 0;
                spawnerTime = rand.nextInt(MAX_SPAWNER_TIME + 1 - MIN_SPAWNER_TIME) + MIN_SPAWNER_TIME;
            }


            if (timerElapsed > 1000) {
                timeRemaining--;

                if (timeRemaining < 0)
                    timeRemaining = 0;

                timerStart = System.nanoTime();
                pausedTimeTimer = 0;
            }

            if (waterElapsed > waterSpawnerTime)
            {
                //spawn water
                int maxW = (int) (WIDTH * 0.9);
                int minW = (int) (WIDTH * 0.1);

                int maxH = (int) (HEIGHT * 0.9);
                int minH = (int) (HEIGHT * 0.1);

                int x = rand.nextInt(maxW - minW + 1) + minW;
                int y = rand.nextInt(maxH - minH + 1) + minH;

                waterDrops.add(new Waterdrop(BitmapFactory.decodeResource(getResources(), R.drawable.waterdrop),
                        (int) (Double.parseDouble(getResources().getString(R.string.waterdrop_sprite_width)) * WIDTH),
                        (int) (Double.parseDouble(getResources().getString(R.string.waterdrop_sprite_height)) * HEIGHT),
                        x, y, getContext(), 2));

                waterStart = System.nanoTime();
                pausedTimeWater = 0;

                waterSpawnerTime = rand.nextInt(MAX_WATER_SPAWNER_TIME + 1 - MIN_WATER_SPAWNER_TIME) + MIN_WATER_SPAWNER_TIME;
            }

            checkDespawns();

            waterMeter.update();

            if(timeRemaining == 0)
            {
                fires.clear();
                cats.clear();
                waterDrops.clear();
                gameOver.setGameOver(true);
                musicBackground.pause();
            }
        }
        else if(pause.isPaused())
        {
            if(musicBackground.isPlaying())
                musicBackground.pause();

            if(pausedTimeStart == 0)
                pausedTimeStart = System.nanoTime();
        }
    }

    //Este método desenha todos os elementos aptos para este estado
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
            }

            score.draw(canvas);

            if(!gameOver.isGameOver())
                pause.draw(canvas);
            else
            {
                gameOver.draw(canvas);
                score.drawScoreGameOver(canvas);
            }


            if(!waterMeter.isNoWaterWarning())
                drawWaterLevel(canvas);

            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clock),
                    (int)(0.025*WIDTH), (int)(0.12*HEIGHT),null);

            drawText(canvas);

            canvas.restoreToCount(savedState);
        }
    }

    //Este método popula o array com as coordenadas
    public void populateFireCoords()
    {
        for(int i = 0; i < 4; i++)
        {
            fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x1))*WIDTH));
            fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x2))*WIDTH));
            fireX.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_x3))*WIDTH));
        }

        for(int i = 0; i < 3; i++)
        {
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y1))*HEIGHT));
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y2))*HEIGHT));
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y3))*HEIGHT));
            fireY.add((int)(Double.parseDouble(getResources().getString(R.string.fire_spawn_y4))*HEIGHT));
        }
    }

    //Este método popula o array com as coordenadas
    public void populateCatCoords()
    {
        catX.add((int)(Double.parseDouble(getResources().getString(R.string.cat_spawn_x1))*WIDTH));
        catX.add((int)(Double.parseDouble(getResources().getString(R.string.cat_spawn_x2))*WIDTH));
        catX.add((int)(Double.parseDouble(getResources().getString(R.string.cat_spawn_x3))*WIDTH));

        catY.add((int)(Double.parseDouble(getResources().getString(R.string.cat_spawn_y1))*HEIGHT));
        catY.add((int)(Double.parseDouble(getResources().getString(R.string.cat_spawn_y2))*HEIGHT));
        catY.add((int)(Double.parseDouble(getResources().getString(R.string.cat_spawn_y3))*HEIGHT));
    }

    //Este método desenha o texto do tempo no ecrã
    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("" + timeRemaining, 0.05f*WIDTH, 0.145f*HEIGHT, paint);
    }

    //Este método decrementa a água na barra
    public void decreaseWater()
    {
        waterMeterValue -= waterDecrease;
    }

    //Este método incrementa a água na barra
    public void increaseWater()
    {
        waterMeterValue += waterGain;

        if(waterMeterValue > 100)
            waterMeterValue = 100;
    }

    //Este método remove os elementos aptos para remoção
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

        for(Cat c : cats)
        {
            c.update(pausedTimeFire);
            if(c.isDespawn())
                cats.remove(c);
        }

    }

    //Este método desenha a barra de água
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

    //Este método reinicia o jogo
    public void restartGame()
    {
        fires = new ArrayList<>();
        waterDrops = new ArrayList<>();
        cats = new ArrayList<>();
        waterMeterValue = 100;
        timeRemaining = TIMEOUT;
        score = new Score(getContext());
        //timerStart = System.nanoTime();
        fireStart = System.nanoTime();
        waterStart = System.nanoTime();

    }
}
