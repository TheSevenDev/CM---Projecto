package com.example.pc.irmaosmartinhoeasprofissoes.cook;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 28/11/2017.
 */

public enum EnumComponentType
{
    SHAPE(R.drawable.cake_shapes),
    COATING(-1),
    TOPPING(-1);

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
