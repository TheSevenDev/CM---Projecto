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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    public final int NUMBER_OF_DRAWS = 5;

    private MainThread thread;
    private Bitmap background;
    private Pause pause;
    private Activity gameActivity;

    private Bitmap leftArrow, rightArrow;

    private Bitmap[] draws;
    private int currentDraw;
    private Random rand = new Random();

    private ArrayList<PaintingColor> colors;
    private PaintingColor currentColor;

    private Context context;


    public GamePanel(Context context, Activity activity) {
        super(context);

        getHolder().addCallback(this);

        this.gameActivity = activity;
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        draws = new Bitmap[NUMBER_OF_DRAWS];

        currentDraw = rand.nextInt(NUMBER_OF_DRAWS);

        colors = new ArrayList<>();

        this.context = context;

        pause = new Pause(context);



    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();

        background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.backgroundsemcores), WIDTH, HEIGHT, false);

        populateDraws();
        populateColors();
        createArrows();

    }

    public void populateDraws() {

        draws[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pintordraw), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_width))) * WIDTH), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_height))) * HEIGHT), false);
        draws[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pilotodraw), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_width))) * WIDTH), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_height))) * HEIGHT), false);
        draws[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.professordraw), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_width))) * WIDTH), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_height))) * HEIGHT), false);
        draws[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.carrodraw), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_width))) * WIDTH), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_height))) * HEIGHT), false);
        draws[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mariapilotodraw), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_width))) * WIDTH), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_height))) * HEIGHT), false);

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void populateColors() {
        int radius = (int)((Double.parseDouble(context.getResources().getString(R.string.painting_colors_radius))) * WIDTH);

        colors.add(new PaintingColor(context, (int) (0.066 * WIDTH), (int) (0.65 * HEIGHT), radius, Color.DKGRAY, false));//PRETO
        colors.add(new PaintingColor(context, (int) (0.15 * WIDTH), (int) (0.65 * HEIGHT), radius, Color.WHITE, true)); //BRANCO


        colors.add(new PaintingColor(context, (int) (0.15 * WIDTH), (int) (0.53 * HEIGHT), radius, Color.RED, false)); //VERMELHO
        colors.add(new PaintingColor(context, (int) (0.066 * WIDTH), (int) (0.53 * HEIGHT), radius, getContext().getColor(R.color.colorOrange), false)); //LARANJA

        colors.add(new PaintingColor(context, (int) (0.066 * WIDTH), (int) (0.41 * HEIGHT), radius, getContext().getColor(R.color.colorYellow), false)); //AMARELO
        colors.add(new PaintingColor(context, (int) (0.15 * WIDTH), (int) (0.41 * HEIGHT), radius, getContext().getColor(R.color.colorGreen), false)); //VERDE

        colors.add(new PaintingColor(context, (int) (0.066 * WIDTH), (int) (0.29 * HEIGHT), radius, getContext().getColor(R.color.colorBlue), false)); //AZUL
        colors.add(new PaintingColor(context, (int) (0.15 * WIDTH), (int) (0.29 * HEIGHT), radius, getContext().getColor(R.color.colorPurple), false)); //ROXO

        colors.add(new PaintingColor(context, (int) (0.066 * WIDTH), (int) (0.17 * HEIGHT), radius, getContext().getColor(R.color.colorPink), false)); //PINK
        colors.add(new PaintingColor(context, (int) (0.15 * WIDTH), (int) (0.17 * HEIGHT), radius, getContext().getColor(R.color.colorSkin), false)); //PELE

        currentColor = colors.get(1);
    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int fX = (int) event.getX();
            int fY = (int) event.getY();

            if(!pause.isPaused()) {
                pause.onTouchPauseButton(fX, fY);
                boolean onCircle = false;
                for (PaintingColor c : colors) {

                    if (c.onTouchEvent(fX, fY)) {
                        onCircle = true;
                        break;
                    }
                }
                if (onCircle) {
                    for (PaintingColor c : colors) {
                        c.setSelected(false);
                        if (c.onTouchEvent(fX, fY)) {
                            c.setSelected(true);
                            currentColor = c;
                        }
                    }
                }

                if (fX >= (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_x))) * WIDTH) && fX < ((int) ((Double.parseDouble(context.getResources().getString(R.string.painting_x))) * WIDTH) + draws[currentDraw].getWidth())
                        && fY >= (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_y))) * HEIGHT) && fY < ((int) ((Double.parseDouble(context.getResources().getString(R.string.painting_y))) * HEIGHT) + draws[currentDraw].getHeight())) {
                    int x = fX - (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_x))) * WIDTH);
                    int y = fY - (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_y))) * HEIGHT);
                    int color = draws[currentDraw].getPixel(x,y);
                    if(color!= -16777216) // BLACK
                        floodFill(draws[currentDraw], new Point(x, y), draws[currentDraw].getPixel(x, y), currentColor.getColor());
                }

                onTouchArrows(fX,fY);
            }
            else if(pause.isPaused()){
                if(pause.onTouchPauseScreen(fX, fY))
                    gameActivity.onBackPressed();
            }
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, new Paint());
        canvas.drawBitmap(draws[currentDraw], (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_x))) * WIDTH), (int) ((Double.parseDouble(context.getResources().getString(R.string.painting_y))) * HEIGHT), new Paint());

        for (PaintingColor c : colors) {
            c.draw(canvas);
        }

        pause.draw(canvas);

        drawArrows(canvas);


    }

    private void floodFill(Bitmap image, Point node, int targetColor, int replacementColor) {
        int width = image.getWidth();
        int height = image.getHeight();

        int target = targetColor;
        int replacement = replacementColor;
        if (target != replacement) {
            Queue<Point> queue = new LinkedList<Point>();
            do {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getPixel(x - 1, y) == target) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getPixel(x, y) == target) {
                    image.setPixel(x, y, replacement);
                    if (!spanUp && y > 0 && image.getPixel(x, y - 1) == target) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0
                            && image.getPixel(x, y - 1) != target) {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1
                            && image.getPixel(x, y + 1) == target) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1
                            && image.getPixel(x, y + 1) != target) {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.poll()) != null);
        }
    }

    public void createArrows()
    {
        Bitmap auxBitmap;
        Bitmap originalImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrows);

        auxBitmap = Bitmap.createBitmap(originalImage,
                0, 0,
                (int)(0.5 * originalImage.getWidth()), originalImage.getHeight());

        leftArrow = Bitmap.createScaledBitmap(auxBitmap,
                (int)(Double.parseDouble(context.getResources().getString(R.string.arrow_width))*GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.arrow_height))*GamePanel.HEIGHT), false);

        auxBitmap = Bitmap.createBitmap(originalImage,
                (int)(0.5 * originalImage.getWidth()), 0,
                (int)(0.5 * originalImage.getWidth()), originalImage.getHeight());

        rightArrow = Bitmap.createScaledBitmap(auxBitmap,
                (int)(Double.parseDouble(context.getResources().getString(R.string.arrow_width))*GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.arrow_height))*GamePanel.HEIGHT), false);
    }

    public void onTouchArrows(float x, float y)
    {
        //left arrow
        if (x >= 0
                && x < (GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.arrow_width)))
                && y >= (int)(GamePanel.HEIGHT * 0.88)
                && y < ((int)(GamePanel.HEIGHT * 0.88)
                + (GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.arrow_height)))))

        {

            if(currentDraw==0)
                currentDraw = (NUMBER_OF_DRAWS-1);
            else
                currentDraw--;

        }

        //right arrow
        if (x >= (int)(GamePanel.WIDTH * 0.16)
                && x < ((int)(GamePanel.WIDTH * 0.16)
                + (GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.arrow_width))))
                && y >= (int)(GamePanel.HEIGHT * 0.88)
                && y < ((int)(GamePanel.HEIGHT * 0.88 ))
                + (GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.arrow_height))))

        {
            if(currentDraw==(NUMBER_OF_DRAWS-1))
                currentDraw=0;
            else
                currentDraw++;
        }

    }

    public void drawArrows(Canvas canvas){

        canvas.drawBitmap(leftArrow,
                0,
                (int) (0.88* GamePanel.HEIGHT), null);

        canvas.drawBitmap(rightArrow,
                (int) (0.16 * GamePanel.WIDTH),
                (int) (0.88 * GamePanel.HEIGHT), null);

    }
}
