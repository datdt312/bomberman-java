package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;

import java.util.Random;

public class Balloom
{
    private Texture texture;
    private TextureRegion[] animation;
    private Sprite shape;
    private int countTime;

    public Balloom()
    {
        createAnimation();
        countTime =0;
    }

    private void createAnimation()
    {
        texture = new Texture("core/assets/Balloom.png");
        TextureRegion[][] textureRegions = TextureRegion.split(texture, 16,16);
        animation = new TextureRegion[textureRegions[0].length];
        for (int i=0; i<animation.length; i++)
            animation[i] = textureRegions[0][i];
    }

    private int calculateDirection()
    {
        RandomXS128 rand = new RandomXS128();
        return rand.nextInt(4);
    }


}
