package com.example.pc.irmaosmartinhoeasprofissoes.painter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import com.example.pc.irmaosmartinhoeasprofissoes.Background;
import com.example.pc.irmaosmartinhoeasprofissoes.Pause;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Marcos on 24/11/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    public final int NUMBER_OF_DRAWS = 3;

    private MainThread thread;
    private Bitmap background;
    private Pause pause;
    private Activity gameActivity;


    private Bitmap[] draws;
    private int currentDraw;
    private Random rand = new Random();



    public GamePanel(Context context, Activity activity){
        super(context);

        getHolder().addCallback(this);

        this.gameActivity = activity;
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        draws = new Bitmap[NUMBER_OF_DRAWS];

        currentDraw = rand.nextInt(3 );
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch (InterruptedException e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread.setRunning(true);
        thread.start();

        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.backgroundsemcores), WIDTH, HEIGHT, false);

        populateDraws();





    }

    public void populateDraws(){

        draws[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.professordraw), (int) (0.66*WIDTH) , (int) (0.68*HEIGHT), false);
        draws[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pilotodraw), (int) (0.66*WIDTH),(int)(0.68*HEIGHT), false);
        draws[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pintordraw), (int) (0.60*WIDTH),(int)(0.70*HEIGHT), false);

    }

    public void update(){


    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            int fX = (int) event.getX();
            int fY = (int) event.getY();
            Point p = new Point(fX, fY);

                //FloodFill(background, p, android.graphics.Color.WHITE, android.graphics.Color.RED);
        }

        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, new Paint());
        canvas.drawBitmap(draws[currentDraw], (int)(0.3*WIDTH),(int)(0.18*HEIGHT), new Paint());
        //84 WIDTH
        //12 HEIGGHT
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.DKGRAY);
        canvas.drawCircle((int) (0.066*WIDTH),(int)(0.65*HEIGHT), 35, paint );
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.MAGENTA);

        canvas.drawCircle((int) (0.066*WIDTH),(int)(0.65*HEIGHT), 35, paint );

        paint.setColor(Color.WHITE);
        canvas.drawCircle((int) (0.15*WIDTH),(int)(0.65*HEIGHT), 35, paint );
        paint.setColor(Color.RED);
        canvas.drawCircle((int) (0.15*WIDTH),(int)(0.53*HEIGHT), 35, paint );
        paint.setColor(getContext().getColor(R.color.colorOrange));
        canvas.drawCircle((int) (0.066*WIDTH),(int)(0.53*HEIGHT), 35, paint );
        paint.setColor(getContext().getColor(R.color.colorYellow));
        canvas.drawCircle((int) (0.066*WIDTH),(int)(0.41*HEIGHT), 35, paint );
        paint.setColor(getContext().getColor(R.color.colorGreen));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle((int) (0.15*WIDTH),(int)(0.41*HEIGHT), 35, paint );
        paint.setColor(getContext().getColor(R.color.titleColor));
        canvas.drawCircle((int) (0.066*WIDTH),(int)(0.29*HEIGHT), 35, paint );
        paint.setColor(getContext().getColor(R.color.colorPurple));
        canvas.drawCircle((int) (0.15*WIDTH),(int)(0.29*HEIGHT), 35, paint );
        paint.setColor(getContext().getColor(R.color.colorPink));
        canvas.drawCircle((int) (0.066*WIDTH),(int)(0.17*HEIGHT), 35, paint );
        paint.setColor(getContext().getColor(R.color.colorSkin));
        canvas.drawCircle((int) (0.15*WIDTH),(int)(0.17*HEIGHT), 35, paint );

    }

    private void FloodFill(Bitmap bmp, Point pt, int targetColor, int replacementColor){
        Queue<Point> q = new LinkedList<>();
        q.add(pt);
        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor)
                continue;

            Point w = n, e = new Point(n.x + 1, n.y);
            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor))
                    q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(w.x, w.y + 1) == targetColor))
                    q.add(new Point(w.x, w.y + 1));
                w.x--;
            }
            while ((e.x < bmp.getWidth() - 1)
                    && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);

                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor))
                    q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(e.x, e.y + 1) == targetColor))
                    q.add(new Point(e.x, e.y + 1));
                e.x++;
            }
        }}


}
