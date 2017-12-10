package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

import com.example.pc.irmaosmartinhoeasprofissoes.GameObject;
import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Simula um bolo na aplicação, quer seja o bolo-alvo ou o bolo actual do utilizador
 */

public class Cake extends GameObject
{
    private Bitmap image;
    private EnumCakeShape shape;
    private EnumCakeCoating coating;
    private EnumCakeTopping topping;
    private EnumComponentType lastComponentPut;

    public Cake(int w, int h, int x, int y, Context context)
    {
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
        this.context = context;
    }

    //Retorna a imagem actual
    public Bitmap getImage()
    {
        return image;
    }

    //Desenha o objecto
    public void draw(Canvas canvas, boolean paused)
    {
        Paint paint = new Paint();

        if(paused)
        {
            paint.setColorFilter(new LightingColorFilter(Color.rgb(123, 123, 123), 0));
        }
        else
        {
            paint = null;
        }

        canvas.drawBitmap(image, x, y, paint);
    }

    //Define a cobertura actual
    public void setCoating(EnumCakeCoating coating)
    {
        this.coating = coating;
    }

    //Define a forma actual
    public void setShape(EnumCakeShape shape)
    {
        this.shape = shape;
    }

    //Define qual o ultimo componente a ser colocado
    public void setLastComponentPut(EnumComponentType componentType)
    {
        this.lastComponentPut = componentType;
    }

    public EnumCakeShape getShape() {
        return shape;
    }

    public EnumCakeCoating getCoating() {
        return coating;
    }

    public EnumCakeTopping getTopping() {
        return topping;
    }

    //Substitui a forma do bolo, exibindo apenas a mesma
    public void switchShape(EnumCakeShape shape)
    {
        this.shape = shape;

        switch(shape)
        {
            case HEXAGON:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_bases), 0);
                break;
            case CIRCLE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_bases),
                        Double.parseDouble(getContext().getString(R.string.one_third_sprite_shape)));
                break;
            case SQUARE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_bases),
                        Double.parseDouble(getContext().getString(R.string.two_thirds_sprite_shape)));
                break;
        }
    }

    //Substitui a cobertura do bolo, apresentando so a forma e a cobertura
    public void switchCoating(EnumCakeCoating coating)
    {
        this.coating = coating;

        switch(coating)
        {
            case PURPLE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.coatings_grid), 0);
                break;
            case BLUE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.coatings_grid),
                        Double.parseDouble(getContext().getString(R.string.one_third_sprite_coating)));
                break;
            case ORANGE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.coatings_grid),
                        Double.parseDouble(getContext().getString(R.string.two_thirds_sprite_coating)));
                break;
        }
    }

    //Substitui o topping do bolo
    public void switchTopping(EnumCakeTopping topping)
    {
        this.topping = topping;

        switch(coating)
        {
            case PURPLE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_spritesheet2), 0);
                break;
            case BLUE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_spritesheet2),
                        Double.parseDouble(getContext().getString(R.string.one_third_sprite_topping)));
                break;
            case ORANGE:
                changeImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.cake_spritesheet2),
                        Double.parseDouble(getContext().getString(R.string.two_thirds_sprite_topping)));
                break;
        }
    }

    //Muda a imagem do bolo, dependendo dos componentes ja existentes
    public void changeImage(Bitmap bitmap, double startingHeight)
    {
        Bitmap auxBitmap;

        double oneThirdSprite = Double.parseDouble(getContext().getString(R.string.one_third_sprite));
        double twoThirdsSprite = Double.parseDouble(getContext().getString(R.string.two_thirds_sprite));
        double cakeSpriteWidth = Double.parseDouble(getContext().getString(R.string.cake_sprite_width));
        double intervalSquare = Double.parseDouble(getContext().getString(R.string.interval_square));
        double intervalCircle = Double.parseDouble(getContext().getString(R.string.interval_circle));

        switch(lastComponentPut)
        {
            case SHAPE:
                auxBitmap = Bitmap.createBitmap(bitmap,
                        0, (int)(bitmap.getHeight() * startingHeight),
                        bitmap.getWidth(), (int)(bitmap.getHeight() * oneThirdSprite));

                image = Bitmap.createScaledBitmap(auxBitmap,
                        getWidth(),
                        getHeight(), false);
                break;
            case COATING:
                switch(shape)
                {
                    case HEXAGON:
                        auxBitmap = Bitmap.createBitmap(bitmap,
                                (bitmap.getWidth() * 0), (int)(bitmap.getHeight() * startingHeight),
                                (int)(bitmap.getWidth() * oneThirdSprite),
                                (int)(bitmap.getHeight() * oneThirdSprite));

                        image = Bitmap.createScaledBitmap(auxBitmap,
                                getWidth(),
                                getHeight(), false);
                        break;
                    case CIRCLE:
                        auxBitmap = Bitmap.createBitmap(bitmap,
                                (int)(bitmap.getWidth() * oneThirdSprite),
                                (int)(bitmap.getHeight() * startingHeight),
                                (int)(bitmap.getWidth() * oneThirdSprite),
                                (int)(bitmap.getHeight() * oneThirdSprite));

                        image = Bitmap.createScaledBitmap(auxBitmap,
                                getWidth(),
                                getHeight(), false);
                        break;
                    case SQUARE:
                        auxBitmap = Bitmap.createBitmap(bitmap,
                                (int)(bitmap.getWidth() * twoThirdsSprite),
                                (int)(bitmap.getHeight() * startingHeight),
                                (int)(bitmap.getWidth() * oneThirdSprite),
                                (int)(bitmap.getHeight() * oneThirdSprite));

                        image = Bitmap.createScaledBitmap(auxBitmap,
                                getWidth(),
                                getHeight(), false);
                        break;
                }
                break;
            case TOPPING:
                switch(shape)
                {
                    case HEXAGON:
                        switch(topping)
                        {
                            case CHERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0 + cakeSpriteWidth * 0))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case CHOCOLATE:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0 + cakeSpriteWidth * 1))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case STRAWBERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (0 + cakeSpriteWidth * 2))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                        }
                        break;
                    case SQUARE:
                        switch(topping)
                        {
                            case CHERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (intervalSquare + cakeSpriteWidth * 0))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case CHOCOLATE:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (intervalSquare + cakeSpriteWidth * 1))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case STRAWBERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (intervalSquare + cakeSpriteWidth * 2))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                        }
                        break;
                    case CIRCLE:
                        switch(topping)
                        {
                            case CHERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (intervalCircle + cakeSpriteWidth * 0))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case CHOCOLATE:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        (int)((bitmap.getWidth() * (intervalCircle + cakeSpriteWidth * 1))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                            case STRAWBERRY:
                                auxBitmap = Bitmap.createBitmap(bitmap,
                                        //if not working change to 0.668
                                        (int)((bitmap.getWidth() * (intervalCircle + cakeSpriteWidth * 2))),
                                        (int)(bitmap.getHeight() * startingHeight),
                                        (int)(bitmap.getWidth() * cakeSpriteWidth), (int)(bitmap.getHeight() *
                                                oneThirdSprite));

                                image = Bitmap.createScaledBitmap(auxBitmap,
                                        getWidth(),
                                        getHeight(), false);
                                break;
                        }
                        break;
                }
                break;
        }
    }

    public EnumComponentType getLastComponentPut()
    {
        return lastComponentPut;
    }
}
