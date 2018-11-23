package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Maps.MapCreator;

import java.util.Map;
import java.util.Random;

public class Balloom
{
    private int BALLOOM_WIDTH;
    private int BALLOOM_HEIGHT;

    private Texture texture;
    private TextureRegion[] animation;
    private Sprite shape;

    private int lengthAnimation;
    private int moveLength;
    private int countTime;

    private float elapsedTime;
    private float speed;
    private int moveSide;

    private int countDie;
    private boolean isDie;
    private boolean justDie;

    public Balloom(MapCreator map, float posX, float posY)
    {
        BALLOOM_WIDTH = (int) (map.getTileWidth() * map.getUNIT_SCALE() * 6 / 7);
        BALLOOM_HEIGHT = (int) (map.getTileHeight() * map.getUNIT_SCALE() * 6 / 7);
        shape = new Sprite();
        shape.setSize(BALLOOM_WIDTH, BALLOOM_HEIGHT*2f/3f);
        shape.setOriginBasedPosition(posX, posY);

        createAnimation();
        countTime =0;

        speed = 1f;
        moveSide = 0;

        isDie = false;
        justDie = false;
    }

    private void createAnimation()
    {
        texture = new Texture("core/assets/Balloom.png");
        TextureRegion[][] textureRegions = TextureRegion.split(texture, 16,16);
        lengthAnimation = textureRegions[0].length;
        moveLength = 6;
        animation = new TextureRegion[lengthAnimation];
        for (int i=0; i<animation.length; i++)
            animation[i] = textureRegions[0][i];
    }

    private int calculateDirection()
    {
        RandomXS128 rand = new RandomXS128();
        return rand.nextInt(4);
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

    public void update(MapCreator map, float dt)
    {
        elapsedTime+=2*dt;
        countTime = (int) elapsedTime%moveLength;
        updateMovement();

        if (detectCollision(map))
        {
            revertMovement();
            moveSide = calculateDirection();
        }
        if (isDie)
        {

        }

    }

    public void updateMovement()
    {
        // 0-DOWN _ _ _ 1 - UP _ _ _ 2 - LEFT _ _ _ 3 - RIGHT
        switch (moveSide)
        {
            case 0:
                shape.translateY(-speed);
                break;
            case 1:
                shape.translateY(speed);
                break;
            case 2:
                shape.translateX(-speed);
                break;
            case 3:
                shape.translateX(speed);
                break;
        }
    }
    public void revertMovement()
    {
        switch (moveSide)
        {
            case 0:
                shape.translateY(speed);
                break;
            case 1:
                shape.translateY(-speed);
                break;
            case 2:
                shape.translateX(speed);
                break;
            case 3:
                shape.translateX(-speed);
                break;
        }
    }

    public void draw(Batch batch)
    {
        batch.begin();
        if (!isDie)
        {
            batch.draw(animation[countTime], shape.getX(), shape.getY(), BALLOOM_WIDTH, BALLOOM_HEIGHT);
        }
        else
        {
            batch.draw()
        }
        batch.end();

    }


}
