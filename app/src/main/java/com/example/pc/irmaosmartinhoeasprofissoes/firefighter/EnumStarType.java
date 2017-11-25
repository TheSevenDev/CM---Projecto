package com.example.pc.irmaosmartinhoeasprofissoes.firefighter;

import android.graphics.Bitmap;

import com.example.pc.irmaosmartinhoeasprofissoes.R;

/**
 * Created by TheSeven on 24/11/2017.
 */

public enum EnumStarType
{
    EMPTY_STAR(R.string.empty_star_x_mod),
    ONE_THIRD_STAR(R.string.one_third_star_x_mod),
    TWO_THIRDS_STAR(R.string.two_thirds_star_x_mod),
    FULL_STAR(R.string.full_star_x_mod);

    private int x;

    EnumStarType(int x)
    {
        this.x = x;
    }

    public int getX()
    {
        return x;
    }
}
