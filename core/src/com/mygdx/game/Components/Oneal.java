package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Oneal
{
    private Texture texture;
    private TextureRegion[] animation;
    private int animationLength;
    private Sprite shape;

    private int countTime;
    private int countDie;
    private float elapsedTime;

    public Oneal()
    {


    }

    private void createAnimation()
    {
        texture = new Texture("core/assets/Oneal.png");
        TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
        animationLength = regions[0].length;

        animation = new TextureRegion[animationLength];
    }
}
