/**
 * Generate Balloom
 * @author HNDBP
 * @since 2018/11/23
 */
package com.mygdx.game.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    /**
     * Constructor
     * @param map map
     * @param posX positionx
     * @param posY positiony
     */
    public Balloom(MapCreator map, float posX, float posY)
    {
        BALLOOM_WIDTH = (int) (map.getTileWidth() * map.getUNIT_SCALE() * 6 / 7);
        BALLOOM_HEIGHT = (int) (map.getTileHeight() * map.getUNIT_SCALE() * 6 / 7);
        shape = new Sprite();
        shape.setSize(BALLOOM_WIDTH, BALLOOM_HEIGHT * 2f / 3f);
        shape.setOriginBasedPosition(posX, posY);

        createAnimation();
        countTime = 0;

        speed = 1f;
        moveSide = 0;

        isDie = false;
        done = false;
        countDie = moveLength;
    }

    /**
     * create animation
     */
    private void createAnimation()
    {
        texture = new Texture("core/assets/Balloom.png");
        TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
        animationLength = regions[0].length;
        moveLength = 6;
        animation = new TextureRegion[animationLength];
        for (int i = 0; i < animationLength; i++)
            animation[i] = regions[0][i];
    }

    /**
     * Random movement
     * @return random integer
     */
    private int calculateDirection()
    {
        RandomXS128 rand = new RandomXS128();
        return rand.nextInt(4);
    }

    /**
     * Delete collision
     * @param map map
     * @return true or false
     */
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

    /**
     * Update
     * @param map map
     * @param player bomber
     * @param dt time
     */
    public void update(MapCreator map,Boomber player, float dt)
    {
        elapsedTime += 2 * dt;
        countTime = (int) elapsedTime % moveLength;
        if (! isDie)
        {
            updateMovement();

            if (detectCollision(map))
            {
                revertMovement();
                moveSide = calculateDirection();
            }
            if (shape.getBoundingRectangle().overlaps(player.getShape().getBoundingRectangle()) && !player.isDie())
            {
                player.setDie();
            }
            for (Bomb b:player.getBombManager().getBomb_manage())
            {
                if (shape.getBoundingRectangle().overlaps(b.getShape().getBoundingRectangle()))
                {
                    while (shape.getBoundingRectangle().overlaps(b.getShape().getBoundingRectangle()))
                    {
                        revertMovement();
                    }
                    moveSide = calculateDirection();
                }
            }
        }
        else
        {
            timeDie += 2.5*dt;
            if (timeDie >= 1f)
            {
                countDie++;
                timeDie = 0f;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9))
        {
            killed();
        }
    }

    /**
     * Update movement
     */
    public void updateMovement()
    {
        // 0-DOWN _ _ _ 1 - UP _ _ _ 2 - LEFT _ _ _ 3 - RIGHT
        switch (moveSide)
        {
            case 0:
                shape.translateY(- speed);
                break;
            case 1:
                shape.translateY(speed);
                break;
            case 2:
                shape.translateX(- speed);
                break;
            case 3:
                shape.translateX(speed);
                break;
        }
    }

    /**
     * Revert movement
     */
    public void revertMovement()
    {
        switch (moveSide)
        {
            case 0:
                shape.translateY(speed);
                break;
            case 1:
                shape.translateY(- speed);
                break;
            case 2:
                shape.translateX(speed);
                break;
            case 3:
                shape.translateX(- speed);
                break;
        }
    }

    /**
     * Draw
     * @param batch
     */
    public void draw(Batch batch)
    {
        batch.begin();
        if (! isDie)
        {
            batch.draw(animation[countTime], shape.getX(), shape.getY(), BALLOOM_WIDTH, BALLOOM_HEIGHT);
        }
        else
        {
            if (!done && countDie < animationLength)
            {
                batch.draw(animation[countDie], shape.getX(), shape.getY(), BALLOOM_WIDTH, BALLOOM_HEIGHT);
            }
            else
            {
                done = true;
            }
        }
        batch.end();
    }

    /**
     * is balloom killed
     */
    public void killed()
    {
        isDie = true;
        done = false;
        timeDie = 0;
    }

    /**
     * Is Balloom died?
     * @return
     */
    public boolean isDone()
    {
        return done;
    }

    /**
     * getter
     * @return shape
     */
    public Sprite getShape()
    {
        return shape;
    }

    /**
     * Is balloom died?
     * @return
     */
    public boolean isDie()
    {
        return isDie;
    }


}
