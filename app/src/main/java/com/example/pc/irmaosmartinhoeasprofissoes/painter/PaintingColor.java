package com.example.pc.irmaosmartinhoeasprofissoes.painter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Classe criada para gerir os círculos com as diferentes cores usados para selecionar a cor e posteriomente pintar.
 * Created by Marcos on 27/11/2017.
 */

public class PaintingColor{

    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
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

    /**
     * Método utilizado para retornar a coordenada X do círculo criado.
     */
    public int getCx(){
        return cx;
    }

    /**
     * Método utilizado para retornar a coordenada Y do círculo.
     */
    public int getCy(){
        return cy;
    }

    /**
     * Método utilizado para retornar a cor que o círculo representará.
     */
    public int getColor(){
        return color;
    }


    /**
     * Método que retorna se o círculo está selecionado ou não
     */
    public boolean isSelected() {
        return selected;
    }


    /**
     * Método utilizado selecionar o círculo.
     */
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
            canvas.drawCircle((int) cx,cy, (int)((Double.parseDouble(context.getResources().getString(R.string.painting_colors_radius))) * WIDTH), paint );
        }
    }

    public boolean onTouchEvent(int fX,  int fY) {
        return (Math.sqrt(Math.pow(fX - getCx(), 2) + Math.pow(fY - getCy(), 2)) < 35);
    }

}
