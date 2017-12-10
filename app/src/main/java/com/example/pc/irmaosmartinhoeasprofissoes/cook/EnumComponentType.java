package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Enumera os tipos de componentes
 */
public enum EnumComponentType
{
    SHAPE(R.drawable.cake_shapes),
    COATING(R.drawable.round_coatings),
    TOPPING(R.drawable.toppings);

    private int bitmapId;
    EnumComponentType(int bitmapId)
    {
        this.bitmapId = bitmapId;
    }

    public int getBitmapId()
    {
        return bitmapId;
    }
}
