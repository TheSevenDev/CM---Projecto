package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

import java.util.ArrayList;

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

    public ComponentRotator(Context context)
    {
        this.context = context;
        fillComponentList();
    }

    private void fillComponentList()
    {
        this.components = new ArrayList<>();

        for(EnumComponentType c : EnumComponentType.values())
        {
            components.add(c);
        }

        selectedComponent = components.get(1);
        changeComponentPanel();

        createArrows();
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
                + (GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.arrow_height)))) && components.indexOf(selectedComponent) != 0)
        {
            rotateLeft();
        }

        //right arrow
        if (x >= (int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.right_arrow_x)))
                && x < ((int)(GamePanel.WIDTH * Double.parseDouble(context.getResources().getString(R.string.right_arrow_x)))
                + (GamePanel.WIDTH * Double.parseDouble(context.getString(R.string.arrow_width))))
                && y >= (int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.right_arrow_y)))
                && y < ((int)(GamePanel.HEIGHT * Double.parseDouble(context.getResources().getString(R.string.right_arrow_y)))
                + (GamePanel.HEIGHT * Double.parseDouble(context.getString(R.string.arrow_height)))) && components.indexOf(selectedComponent) != components.size() -1)
        {
            rotateRight();
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

        if(components.indexOf(selectedComponent) != components.size() - 1)
            canvas.drawBitmap(rightArrow,
                (int)(Double.parseDouble(context.getResources().getString(R.string.right_arrow_x))* GamePanel.WIDTH),
                (int)(Double.parseDouble(context.getResources().getString(R.string.right_arrow_y))* GamePanel.HEIGHT),null);
    }
}
