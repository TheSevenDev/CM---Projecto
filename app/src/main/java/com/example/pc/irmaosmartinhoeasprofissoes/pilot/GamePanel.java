package com.example.pc.irmaosmartinhoeasprofissoes.pilot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.pc.irmaosmartinhoeasprofissoes.EnumGame;
import com.example.pc.irmaosmartinhoeasprofissoes.Pause;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.GameOver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe que representa o painel de jogo do piloto.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    public final int MIN_HEIGHT_BOUND = Integer.parseInt(getContext().getString(R.string.min_height_bound));
    public final int MAX_HEIGHT_BOUND = (HEIGHT - Integer.parseInt(getContext().getString(R.string.max_height_bound)));
    private final int GRAVITY = Integer.parseInt(getContext().getString(R.string.gravity));
    private final float MIN_DAYLIGHT = Float.parseFloat(getContext().getString(R.string.min_daylight));
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

    private long frameTime;
    private long initTime;

    private Context context;
    private Activity gameActivity;

    private boolean recordChecked;
    private boolean recordBroken;

    private Random rand = new Random();

    public GamePanel(Context context, Activity activity)
    {
        super(context);
        this.context = context;
        this.gameActivity = activity;

        getHolder().addCallback(this);

        setFocusable(true);

        orientationData = new OrientationData(context);
        orientationData.register();

        lightData = new LightData(context);
        lightData.register();


        frameTime = System.currentTimeMillis();
        initTime = System.currentTimeMillis();

        SharedPreferences sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        if(!sharedPref.contains("pilotRecord"))
        {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("pilotRecord", 0);
            editor.apply();
        }
        recordChecked = false;
        recordBroken = false;
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

    /**
     * Cria os objetos necessários ao jogo(Background, jogador) e popula a lista de obstáculos.
     * Chama o metodo resetGame() para estabelecer os parâmetros iniciais.
     * @param holder
     */
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

    /**
     * Leitura dos cliques no ecrâ para a interação no menu de pausa e GameOver
     */
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
            return true;
        }
        return true;
    }

    /**
     * Volta a colocar o jogo num estado inicial.
     * Score do jogador = 0.
     * Vida do jogador = Máximo.
     * Recoloca o jogador na posição inicial.
     */
    public void resetGame()
    {
        Rect playerRect = new Rect((int)(Double.parseDouble(context.getResources().getString(R.string.pilot_rect_left))*WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.pilot_rect_top))*HEIGHT),
                (int)(Double.parseDouble(context.getResources().getString(R.string.pilot_rect_right))*WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.pilot_rect_bot))*HEIGHT));

        player = new Player(playerRect, context);
        player.resetScore();
        player.resetHealth();


        playerPoint = new Point((int)(Double.parseDouble(context.getResources().getString(R.string.pilot_initial_x))*WIDTH), HEIGHT/2);
        player.update(playerPoint);

        obstacles = new ArrayList<>();
        gameOver.setGameOver(false);

        recordChecked = false;
        recordBroken = false;
    }

    public void update()
    {
        if(!player.isAlive()) {
            gameOver.setGameOver(true);
            player.resetHealth();
        }
        else if (!pause.isPaused() && !gameOver.isGameOver()) {
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


    /**
     * Lê e atualiza  a posição do jogador consoante os valores do magnómetro e giroscópio.
     */
    public void orientation(){
        int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
        frameTime = System.currentTimeMillis();

        if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
            float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];     //X DIRECTION

            float xSpeed = 2 * roll * WIDTH / 1100f;

            playerPoint.y -= Math.abs(xSpeed * elapsedTime) > 3 ? xSpeed * elapsedTime : 0;
        }
    }

    /**
     * Não permite que o jogador saia fora da tela do ecrã de jogo.
     */
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

    /**
     * Verifica se houve colisões entre o jogador e algum obstáculo da lista.
     */
    public void checkCollision(){
        Iterator<Obstacle> it = obstacles.iterator();
        Obstacle aux;
        while (it.hasNext()) {
            aux = it.next();
            if (aux.getX() < -WIDTH)
                it.remove();
            else {
                aux.update();
                if (collision(aux.getRectangle(), player.getImageRectangle())) {
                    Vibrator v = (Vibrator) gameActivity.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                        player.takeDamage(1);
                    it.remove();
                }
            }
        }
    }

    /**
     * Popula a lista dos obstáculos com passáros(classe Obstacle) em posições aleatórias no ecrã.
     */
    public void spawnObstacles(){
        long obstaclesElapsed = (System.nanoTime() - obstacleStartTime) / 1000000;
        if (obstaclesElapsed > (1000 - player.getScore()/4)) {
            int randomY = rand.nextInt(MAX_HEIGHT_BOUND - 30) + 30;
            Bitmap bird = BitmapFactory.decodeResource(getResources(), R.drawable.pilot_obstacle);
            Bitmap madBird = BitmapFactory.decodeResource(getResources(), R.drawable.pilot_obstacle_fast);

            obstacles.add(new Obstacle(bird, madBird, WIDTH + 10, randomY, player.getScore()));

            obstacleStartTime = System.nanoTime();
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

            if (lightData.getLightValue() >= MIN_DAYLIGHT) {//Caso seja detetado luz diurna
                backgroundDay.draw(canvas);
            } else {
                backgroundNight.draw(canvas);
            }

            player.draw(canvas);
            canvas.drawBitmap(health[player.getHealth()], WIDTH * Float.parseFloat(getContext().getString(R.string.pilot_health_x)),
                    HEIGHT * Float.parseFloat(getContext().getString(R.string.pilot_health_y)), null);
            for (Obstacle ob : obstacles) {
                ob.draw(canvas);
            }


            if (!gameOver.isGameOver())
                pause.draw(canvas);
            else {
                gameOver.draw(canvas);
                drawScore(canvas);
            }

            canvas.restoreToCount(savedState);
        }

    }

    /**
     * Verifica se o record de pontuação foi batido. Em caso afirmativo atualiza o record em memória.
     */
    public boolean checkRecord(){
        SharedPreferences sharedPref = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        if(player.getScore() > sharedPref.getInt("pilotRecord", 0)){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("pilotRecord", player.getScore());
            editor.apply();
            recordChecked = true;
            return true;
        }
        recordChecked = true;
        return false;
    }

    /**
     * Escreve a pontuação do jogo no ecrâ de Game Over
     */
    public void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(53);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        if (!recordChecked) {
            recordBroken = checkRecord();
        }

        if (recordBroken) {
            paint.setColor(Color.parseColor("#FFFECF"));
            canvas.drawText("RECORDE", Float.parseFloat(getContext().getString(R.string.pilot_record_x)) * WIDTH,
                    Float.parseFloat(getContext().getString(R.string.pilot_record_y)) * HEIGHT, paint);
        }
        paint.setColor(Color.BLACK);
        canvas.drawText((player.getScore() + " metros"), Float.parseFloat(getContext().getString(R.string.pilot_score_x)) * WIDTH,
                Float.parseFloat(getContext().getString(R.string.pilot_score_y)) * HEIGHT, paint);

    }

    /**
     * Deteta a colisão entre dois rectângulos
     */
    public boolean collision(Rect a, Rect b){
        return Rect.intersects(a,b);
    }

}
