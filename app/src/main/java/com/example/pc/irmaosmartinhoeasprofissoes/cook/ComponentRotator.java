package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

import com.example.pc.irmaosmartinhoeasprofissoes.GameOver;
import com.example.pc.irmaosmartinhoeasprofissoes.MusicService;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Gere a maior parte da actividade do jogo Pasteleiro. Gere a apresentação e selecção do painel de componentes e setas
 * de transiçao
 */

public class ComponentRotator
{
    private ArrayList<EnumComponentType> components;
    private EnumComponentType selectedComponent;
    private Bitmap componentPanel;
    private Bitmap leftArrow, rightArrow;
    private Context context;
    private Cake activeCake;
    private Cake targetCake;
    private Random random = new Random();
    private Bitmap crossError;
    private boolean isError, blinkingError;
    private long errorStartTime;
    private GameOver gameOver;

    public ComponentRotator(Context context, Cake cake, GameOver gameOver)
    {
        this.context = context;
        randomizeTargetCake();

        fillComponentList();

        this.activeCake = cake;

        crossError = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_mark),
            (int)(0.4 * GamePanel.WIDTH),
            (int)(0.4 * GamePanel.HEIGHT), false);

        this.gameOver = gameOver;
    }

    //Introduz os itens necessario na lista de componentes
    private void fillComponentList()
    {
        this.components = new ArrayList<>();

        for(EnumComponentType c : EnumComponentType.values())
        {
            components.add(c);
        }

        selectedComponent = components.get(0);
        changeComponentPanel();

        createArrows();
    }

    //Cria um bolo-alvo aleatorio
    private void randomizeTargetCake()
    {
        targetCake = new Cake((int)(Double.parseDouble(context.getResources().getString(R.string.cake_width)) * GamePanel.WIDTH),
                (int) (Double.parseDouble(context.getResources().getString(R.string.cake_height)) * GamePanel.HEIGHT),
                (int) ((0.06) * GamePanel.WIDTH),
                (int) ((0.12) * GamePanel.HEIGHT), context);

        targetCake.setShape(EnumCakeShape.values()[random.nextInt(EnumCakeShape.values().length)]);
        targetCake.setCoating(EnumCakeCoating.values()[random.nextInt(EnumCakeCoating.values().length)]);
        targetCake.setLastComponentPut(EnumComponentType.TOPPING);
        targetCake.switchTopping(EnumCakeTopping.values()[random.nextInt(EnumCakeTopping.values().length)]);
    }

    public Cake getTargetCake()
    {
        return targetCake;
    }

    //Roda o painel de componentes para a esquerda
    public void rotateLeft()
    {
        selectedComponent = components.get(components.indexOf(selectedComponent)-1);
        changeComponentPanel();
    }

    //Roda o painel de componentes para a direita
    public void rotateRight()
    {
        selectedComponent = components.get(components.indexOf(selectedComponent)+1);
        changeComponentPanel();
    }

    //Parametriza o desenho das setas do spritesheet
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

    //Gere o toque nas setas
    public void onTouchArrows(float x, float y)
    {
        //left arrow
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.left_arrow_x)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.left_arrow_x)))
                + (GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.arrow_width))))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.left_arrow_y)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.left_arrow_y)))
                + (GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.arrow_height))))
                && components.indexOf(selectedComponent) != 0)
        {
            rotateLeft();
        }

        //right arrow
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.right_arrow_x)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.right_arrow_x)))
                + (GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.arrow_width))))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.right_arrow_y)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.right_arrow_y)))
                + (GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.arrow_height))))
                && components.indexOf(selectedComponent) != components.size() -1
                && components.indexOf(activeCake.getLastComponentPut()) >= components.indexOf(selectedComponent))
        {
            rotateRight();
        }

    }

    //Gere o toque no painel de componentes
    public void onTouchComponentPanel(float x, float y)
    {
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.component_panel_x)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.component_panel_x)))
                + (GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.component_panel_width))))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.component_panel_y)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.component_panel_y)))
                + (GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.component_panel_height)))))
        {

            activeCake.setLastComponentPut(selectedComponent);

            changeCakeComponent(y);

            if(selectedComponent != EnumComponentType.TOPPING)
                MusicService.playSound(context, R.raw.drill);
        }
    }

    //Muda um determinado componente no bolo actual
    public void changeCakeComponent(float y)
    {
        if(y >= (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.component_panel_y)))
                && y <= (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.component_panel_y))
                +  componentPanel.getHeight()*0.33))
        {
            switch(selectedComponent)
            {
                case SHAPE:
                    activeCake.switchShape(EnumCakeShape.HEXAGON);
                    break;
                case COATING:
                    activeCake.switchCoating(EnumCakeCoating.PURPLE);
                    break;
                case TOPPING:
                    activeCake.switchTopping(EnumCakeTopping.CHERRY);
                    break;
            }

        }
        else if(y > (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.component_panel_y))
                + componentPanel.getHeight()*0.33)
                && y <= (GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.component_panel_y))
                +  componentPanel.getHeight()*0.66))
        {
            switch(selectedComponent)
            {
                case SHAPE:
                    activeCake.switchShape(EnumCakeShape.CIRCLE);
                    break;
                case COATING:
                    activeCake.switchCoating(EnumCakeCoating.BLUE);
                    break;
                case TOPPING:
                    activeCake.switchTopping(EnumCakeTopping.CHOCOLATE);
                    break;
            }
        }
        else
        {
            switch(selectedComponent)
            {
                case SHAPE:
                    activeCake.switchShape(EnumCakeShape.SQUARE);
                    break;
                case COATING:
                    activeCake.switchCoating(EnumCakeCoating.ORANGE);
                    break;
                case TOPPING:
                    activeCake.switchTopping(EnumCakeTopping.STRAWBERRY);
                    break;
            }
        }

        if(selectedComponent == EnumComponentType.TOPPING)
            matchCakes();
    }

    //Muda os componentes do painel
    private void changeComponentPanel()
    {
        componentPanel = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), selectedComponent.getBitmapId()),
                (int)(Double.parseDouble(context.getResources().getString(R.string.component_panel_width))* GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.component_panel_height))* GamePanel.HEIGHT), false);
    }

    //Desenha os objectos referidos
    public void draw(Canvas canvas, boolean paused)
    {
        if(paused)
        {
            Paint paint = new Paint();
            paint.setColorFilter(new LightingColorFilter(Color.rgb(123, 123, 123), 0));

            canvas.drawBitmap(componentPanel,
                    (int) (Double.parseDouble(context.getResources().getString(R.string.component_panel_x)) * GamePanel.WIDTH),
                    (int) (Double.parseDouble(context.getResources().getString(R.string.component_panel_y)) * GamePanel.HEIGHT), paint);

            if (components.indexOf(selectedComponent) != 0)
                canvas.drawBitmap(leftArrow,
                        (int) (Double.parseDouble(context.getResources().getString(R.string.left_arrow_x)) * GamePanel.WIDTH),
                        (int) (Double.parseDouble(context.getResources().getString(R.string.left_arrow_y)) * GamePanel.HEIGHT), paint);

            if (components.indexOf(selectedComponent) != components.size() - 1
                    && components.indexOf(activeCake.getLastComponentPut()) >= components.indexOf(selectedComponent))
                canvas.drawBitmap(rightArrow,
                        (int) (Double.parseDouble(context.getResources().getString(R.string.right_arrow_x)) * GamePanel.WIDTH),
                        (int) (Double.parseDouble(context.getResources().getString(R.string.right_arrow_y)) * GamePanel.HEIGHT), paint);
        }
        else
        {
            canvas.drawBitmap(componentPanel,
                    (int) (Double.parseDouble(context.getResources().getString(R.string.component_panel_x)) * GamePanel.WIDTH),
                    (int) (Double.parseDouble(context.getResources().getString(R.string.component_panel_y)) * GamePanel.HEIGHT), null);

            if (components.indexOf(selectedComponent) != 0)
                canvas.drawBitmap(leftArrow,
                        (int) (Double.parseDouble(context.getResources().getString(R.string.left_arrow_x)) * GamePanel.WIDTH),
                        (int) (Double.parseDouble(context.getResources().getString(R.string.left_arrow_y)) * GamePanel.HEIGHT), null);

            if (components.indexOf(selectedComponent) != components.size() - 1
                    && components.indexOf(activeCake.getLastComponentPut()) >= components.indexOf(selectedComponent))
                canvas.drawBitmap(rightArrow,
                        (int) (Double.parseDouble(context.getResources().getString(R.string.right_arrow_x)) * GamePanel.WIDTH),
                        (int) (Double.parseDouble(context.getResources().getString(R.string.right_arrow_y)) * GamePanel.HEIGHT), null);

            if (blinkingError)
                canvas.drawBitmap(crossError,
                        (int) (0.10 * GamePanel.WIDTH),
                        (int) (0.47 * GamePanel.HEIGHT), null);
        }
    }

    //Verifica se o bolo-alvo e actual coincidem, apresentando erro ou fim de jogo
    public void matchCakes()
    {
        if(activeCake.getShape().equals(targetCake.getShape()) &&
                activeCake.getCoating().equals(targetCake.getCoating()) &&
                activeCake.getTopping().equals(targetCake.getTopping()))
        {
            gameOver.setGameOver(true);
        }
        else
        {
            isError = true;
            errorStartTime = System.nanoTime();
            MusicService.playSound(context, R.raw.error);
        }
    }

    //Usado para piscar aviso de erro
    public void update()
    {
        long timeElapsed = (System.nanoTime() - errorStartTime)/1000000;

        if(isError)
        {
            if (timeElapsed > 100) {
                blinkingError = false;
            }

            if (timeElapsed > 200) {
                blinkingError = true;
            }

            if (timeElapsed > 300) {
                blinkingError = false;
            }

            if (timeElapsed > 400) {
                blinkingError = true;
            }

            if (timeElapsed > 500) {
                blinkingError = false;
            }

            if (timeElapsed > 600) {
                blinkingError = true;
            }

            if (timeElapsed > 700) {
                blinkingError = false;
            }

            if (timeElapsed > 800) {
                blinkingError = true;
            }

            if (timeElapsed > 900) {
                blinkingError = false;
                isError = false;
            }
        }
    }
}
