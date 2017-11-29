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
        fillComponentList();
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
    }
}
