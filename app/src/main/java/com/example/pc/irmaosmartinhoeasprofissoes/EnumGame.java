package com.example.pc.irmaosmartinhoeasprofissoes;

/**
 * Enumera os diferentes jogos em que existe um "game over screen" e as imagens utilizadas nos mesmos
 */

public enum EnumGame
{
    FIREFIGHTER(R.drawable.jose_bombeiro, R.drawable.maria_bombeira, R.drawable.profimg_firefighter),
    PILOT(R.drawable.jose_piloto, R.drawable.maria_pilota, R.drawable.profimg_pilot),
    COOK(R.drawable.jose_pasteleiro, R.drawable.maria_pasteleira, R.drawable.profimg_cook);

    private int maleImage;
    private int femaleImage;
    private int profImage;

    EnumGame(int maleImage, int femaleImage, int profImage)
    {
        this.maleImage = maleImage;
        this.femaleImage = femaleImage;
        this.profImage = profImage;
    }

    public int getMaleImage() {
        return maleImage;
    }

    public int getFemaleImage() {
        return femaleImage;
    }

    public int getProfImage() {
        return profImage;
    }
}
