package com.example.pc.irmaosmartinhoeasprofissoes.painter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.MotionEvent;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by Marcos on 27/11/2017.
 */

public class PaintingColor{

    private int cx, cy, radius;
    private boolean selected;
    private int color;

    private Context context;
    public PaintingColor(Context context, int cx, int cy, int radius, int color, boolean selected){
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.color = color;
        setSelected(selected);
        this.context = context;
    }

    public int getCx(){
        return cx;
    }

    public int getCy(){
        return cy;
    }

    public int getColor(){
        return color;
    }



    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawCircle(cx,cy, radius, paint );

        if(isSelected()){
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(context.getColor(R.color.selectedColor));
            canvas.drawCircle((int) cx,cy, 35, paint );
        }
    }

    public boolean onTouchEvent(int fX,  int fY) {
        return (Math.sqrt(Math.pow(fX - getCx(), 2) + Math.pow(fY - getCy(), 2)) < 35);
    }

}
