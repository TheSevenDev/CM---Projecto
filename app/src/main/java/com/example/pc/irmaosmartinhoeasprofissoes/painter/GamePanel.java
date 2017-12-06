package com.example.pc.irmaosmartinhoeasprofissoes.painter;

import android.annotation.TargetApi;
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

    private ArrayList<PaintingColor> colors;
    private PaintingColor currentColor;

    private Context context;


    public GamePanel(Context context, Activity activity){
        super(context);

        getHolder().addCallback(this);

        this.gameActivity = activity;
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        draws = new Bitmap[NUMBER_OF_DRAWS];

        currentDraw = rand.nextInt(3 );

        colors = new ArrayList<>();

        this.context = context;
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
        populateColors();

  }

    public void populateDraws(){

        draws[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.professordraw), (int) (0.66*WIDTH) , (int) (0.68*HEIGHT), false);
        draws[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pilotodraw), (int) (0.66*WIDTH),(int)(0.68*HEIGHT), false);
        draws[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pintordraw), (int) (0.60*WIDTH),(int)(0.70*HEIGHT), false);

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void populateColors()
    {
        colors.add(new PaintingColor(context,(int) (0.066*WIDTH), (int)(0.65*HEIGHT), (int)0.03, Color.DKGRAY, false));//PRETO
        colors.add(new PaintingColor(context,(int) (0.15*WIDTH), (int)(0.65*HEIGHT), (int)0.03, Color.WHITE, true)); //BRANCO

        colors.add(new PaintingColor(context,(int) (0.15*WIDTH), (int)(0.53*HEIGHT), (int)0.03, Color.RED, false)); //VERMELHO
        colors.add(new PaintingColor(context,(int) (0.066*WIDTH), (int)(0.53*HEIGHT), (int)0.03,  getContext().getColor(R.color.colorOrange), false)); //LARANJA

        colors.add(new PaintingColor(context, (int) (0.066*WIDTH), (int)(0.41*HEIGHT), (int)0.03, getContext().getColor(R.color.colorYellow), false)); //AMARELO
        colors.add(new PaintingColor(context, (int) (0.15*WIDTH), (int)(0.41*HEIGHT), (int)0.03, getContext().getColor(R.color.colorGreen), false)); //VERDE

        colors.add(new PaintingColor(context,(int) (0.066*WIDTH), (int)(0.29*HEIGHT), (int)0.03, getContext().getColor(R.color.colorBlue), false)); //AZUL
        colors.add(new PaintingColor(context, (int) (0.15*WIDTH), (int)(0.29*HEIGHT), (int)0.03, getContext().getColor(R.color.colorPurple), false)); //ROXO

        colors.add(new PaintingColor(context,(int) (0.066*WIDTH), (int)(0.17*HEIGHT), (int)0.03, getContext().getColor(R.color.colorPink), false)); //PINK
        colors.add(new PaintingColor(context,(int) (0.15*WIDTH), (int)(0.17*HEIGHT), (int)0.03, getContext().getColor(R.color.colorSkin), false)); //PELE


    }

    public void update(){


    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int fX = (int) event.getX();
            int fY = (int) event.getY();

            Point p = new Point(fX, fY);

            boolean onCircle = false;
            for(PaintingColor c : colors){

                if(c.onTouchEvent(fX, fY)) {
                    onCircle = true;
                    break;
                }
            }

            if(onCircle) {
                for (PaintingColor c : colors) {
                    c.setSelected(false);
                    if (c.onTouchEvent(fX, fY)) {
                        c.setSelected(true);
                    }
                }
            }

            if (fX >= (0.66*WIDTH) && fX < ((0.66*WIDTH) + WIDTH)
                    && fY >= (0.68*HEIGHT) && fY < (0.68*HEIGHT) + HEIGHT) {
                FloodFill(draws[currentDraw],p,Color.WHITE,currentColor.getColor());
            }


        }
        return true;
    }




                    //FloodFill(background, p, android.graphics.Color.WHITE, android.graphics.Color.RED);


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, new Paint());
        canvas.drawBitmap(draws[currentDraw], (int)(0.3*WIDTH),(int)(0.18*HEIGHT), new Paint());

        for (PaintingColor c: colors) {
            c.draw(canvas);
        }


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
