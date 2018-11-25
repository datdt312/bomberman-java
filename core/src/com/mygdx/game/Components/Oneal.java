package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Maps.MapCreator;

public class Oneal
{
    private int ONEAL_WIDTH;
    private int ONEAL_HEIGHT;

    private Texture texture;
    private TextureRegion[] animation;
    private Sprite shape;

    private int animationLength;
    private int moveLength;
    private int countTime;

    private float elapsedTime;
    private float speed;
    private int moveSide;

    private int countDie;
    private float timeDie;

    private boolean isDie;
    private boolean done;

    public Oneal(MapCreator map, float posX, float posY)
    {
        ONEAL_WIDTH = (int) (map.getTileWidth() * map.getUNIT_SCALE() * 6 / 7);
        ONEAL_HEIGHT = (int) (map.getTileHeight() * map.getUNIT_SCALE() * 6 / 7);
        shape = new Sprite();
        shape.setSize(ONEAL_WIDTH, ONEAL_HEIGHT*2f/3f);
        shape.setOriginBasedPosition(posX, posY);

        createAnimation();
        countTime =0;
        speed = 1f;
        moveSide = 0;

        isDie = false;
        done = false;
        countTime = moveLength;
    }

    private void createAnimation()
    {
        texture = new Texture("core/assets/Oneal.png");
        TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
        animationLength = regions[0].length;
        moveLength = 8;
        animation = new TextureRegion[animationLength];
        for (int i=0; i<animationLength; i++)
            animation[i] = regions[0][i];
    }

    private boolean detectCollision(MapCreator map)
    {
        for (Rectangle i : map.getWalls())
            if (shape.getBoundingRectangle().overlaps(i))
                return true;
        for (Rectangle i : map.getBricks())
            if (shape.getBoundingRectangle().overlaps(i))
                return true;
        return false;
    }
}
