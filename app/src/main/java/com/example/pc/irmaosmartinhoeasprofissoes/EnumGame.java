package com.example.pc.irmaosmartinhoeasprofissoes;

/**
 * Created by TheSeven on 25/11/2017.
 */

public enum EnumGame
{
    FIREFIGHTER(R.drawable.jose_bombeiro, R.drawable.maria_bombeira, 3),
    PILOT(R.drawable.jose_piloto, R.drawable.maria_pilota, 3),
    COOK(R.drawable.jose_pasteleiro, R.drawable.maria_pasteleira, 3);

    private int maleImage;
    private int femaleImage;
    private int profImage;

    EnumGame(int maleImage, int femaleImage, int profImage)
    {
        this.maleImage = maleImage;
        this.femaleImage = femaleImage;
        this.profImage = profImage;
    }
}
