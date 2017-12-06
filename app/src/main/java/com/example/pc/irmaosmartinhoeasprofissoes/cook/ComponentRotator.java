package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TheSeven on 28/11/2017.
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

    public ComponentRotator(Context context, Cake cake)
    {
        this.context = context;
        //randomizeTargetCake();

        fillComponentList();

        this.activeCake = cake;
    }

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

    private void randomizeTargetCake()
    {
        targetCake = new Cake((int)(Double.parseDouble(context.getResources().getString(R.string.cake_width)) * GamePanel.WIDTH),
                (int) (Double.parseDouble(context.getResources().getString(R.string.cake_height)) * GamePanel.HEIGHT),
                (int) (Double.parseDouble(context.getResources().getString(R.string.cake_x)) * GamePanel.WIDTH),
                (int) (Double.parseDouble(context.getResources().getString(R.string.cake_y)) * GamePanel.HEIGHT), context);

        targetCake.setShape(EnumCakeShape.values()[random.nextInt(EnumCakeShape.values().length)]);
        targetCake.setCoating(EnumCakeCoating.values()[random.nextInt(EnumCakeCoating.values().length)]);
        targetCake.setLastComponentPut(EnumComponentType.TOPPING);
        targetCake.switchTopping(EnumCakeTopping.values()[random.nextInt(EnumCakeTopping.values().length)]);
    }

    public void rotateLeft()
    {
        selectedComponent = components.get(components.indexOf(selectedComponent)-1);
        changeComponentPanel();
    }

    public void rotateRight()
    {
        selectedComponent = components.get(components.indexOf(selectedComponent)+1);
        changeComponentPanel();
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
        }
    }

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
    }

    private void changeComponentPanel()
    {
        componentPanel = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), selectedComponent.getBitmapId()),
                (int)(Double.parseDouble(context.getResources().getString(R.string.component_panel_width))* GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.component_panel_height))* GamePanel.HEIGHT), false);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(componentPanel,
                (int)(Double.parseDouble(context.getResources().getString(R.string.component_panel_x))* GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.component_panel_y))* GamePanel.HEIGHT),null);

        if(components.indexOf(selectedComponent) != 0)
            canvas.drawBitmap(leftArrow,
                (int)(Double.parseDouble(context.getResources().getString(R.string.left_arrow_x))* GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.left_arrow_y))* GamePanel.HEIGHT),null);

        if(components.indexOf(selectedComponent) != components.size() - 1
                && components.indexOf(activeCake.getLastComponentPut()) >= components.indexOf(selectedComponent))
            canvas.drawBitmap(rightArrow,
                (int)(Double.parseDouble(context.getResources().getString(R.string.right_arrow_x))* GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.right_arrow_y))* GamePanel.HEIGHT),null);
    }
}
