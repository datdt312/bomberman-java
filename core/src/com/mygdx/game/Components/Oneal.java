/**
 * Generate Oneal enemy
 * @author HNDBP
 * @since 2018/11/29
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
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Managers.Music_SoundManager;
import com.mygdx.game.Maps.MapCreator;

public class Oneal
{
    private float TILE_WIDTH;
    private float TILE_HEIGHT;
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
    private float timeCatching;
    private boolean catching;
    private int moveSide;

    private int countDie;
    private float timeDie;
    private int count;

    private boolean isDie;
    private boolean done;

    /**
     * Constructor
     * @param map map
     * @param posX position x of Oneal
     * @param posY position y of oneal
     */
    public Oneal(MapCreator map, float posX, float posY)
    {
        TILE_WIDTH = map.getTileWidth() * map.getUNIT_SCALE();
        TILE_HEIGHT = map.getTileHeight() * map.getUNIT_SCALE();
        ONEAL_WIDTH = (int) (map.getTileWidth() * map.getUNIT_SCALE() * 6 / 7);
        ONEAL_HEIGHT = (int) (map.getTileHeight() * map.getUNIT_SCALE() * 6 / 7);
        shape = new Sprite();
        shape.setSize(ONEAL_WIDTH, ONEAL_HEIGHT * 2f / 3f);
        shape.setOriginBasedPosition(posX, posY);

        createAnimation();
        countTime = 0;
        speed = 0.8f;
        count = 0;

        catching = true;
        timeCatching = 0f;

        moveSide = 0;

        isDie = false;
        done = false;
        countDie = moveLength;
    }

    /**
     * Create oneal's animation
     */
    private void createAnimation()
    {
        texture = new Texture("core/assets/Oneal.png");
        TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
        animationLength = regions[0].length;
        moveLength = 8;
        animation = new TextureRegion[animationLength];
        for (int i = 0; i < animationLength; i++)
            animation[i] = regions[0][i];
    }

    /**
     * Delete Collision
     * @param map map
     * @param player bomber
     * @return false
     */
    private boolean detectCollision(MapCreator map, Boomber player)
    {
        for (Bomb b : player.getBombManager().getBomb_manage())
        {
            if (shape.getBoundingRectangle().overlaps(b.getShape().getBoundingRectangle()))
            {
                return true;
            }
        }
        for (Rectangle i : map.getWalls())
            if (shape.getBoundingRectangle().overlaps(i))
                return true;
        for (Rectangle i : map.getBricks())
            if (shape.getBoundingRectangle().overlaps(i))
                return true;
        return false;
    }

    /**
     * Random movement for oneal
     * @return random integer
     */
    private int calculateMoveSide()
    {
        RandomXS128 rand = new RandomXS128();
        return rand.nextInt(4);
    }

    /**
     * Follow player when being same posx or posy
     * @param player bomber
     * @param map map
     */
    private void calculateDirection(Boomber player, MapCreator map)
    {
        float m_x = 0f;
        float m_y = 0f;
        if (shape.getX() > player.getShape().getX())
        {
            m_x -= speed;
        }

        if (shape.getX() < player.getShape().getX())
        {
            m_x += speed;
        }

        if (shape.getY() > player.getShape().getY())
        {
            m_y -= speed;
        }

        if (shape.getY() < player.getShape().getY())
        {
            m_y += speed;
        }

        updateMovement(m_x, m_y);
        while (detectCollision(map, player))
        {
            revertMovement(m_x, m_y);

            updateMovement(m_x, 0);
            while (detectCollision(map, player))
            {
                revertMovement(m_x, 0);

                updateMovement(- m_x, 0);
                while (detectCollision(map, player))
                {
                    revertMovement(- m_x, 0);

                    updateMovement(0, m_y);
                    while (detectCollision(map, player))
                    {
                        revertMovement(0, m_y);

                        updateMovement(0, - m_y);
                        while (detectCollision(map, player))
                        {
                            revertMovement(0, - m_y);
                        }
                    }
                }
            }
        }


    }

    /**
     * Change speed up or down randomately
     * @return
     */
    private Vector2 convertMoveSide()
    {
        // 0-DOWN _ _ _ 1 - UP _ _ _ 2 - LEFT _ _ _ 3 - RIGHT
        Vector2 res = new Vector2();
        switch (moveSide)
        {
            case 0:
                res = new Vector2(0, - speed);
                break;
            case 1:
                res = new Vector2(0, speed);
                break;
            case 2:
                res = new Vector2(- speed, 0);
                break;
            case 3:
                res = new Vector2(speed, 0);
                break;
        }
        return res;
    }

    /**
     * Update movement
     * @param m_x posx
     * @param m_y poxy
     */
    private void updateMovement(float m_x, float m_y)
    {
        shape.translate(m_x, m_y);
    }

    /**
     * Revert move side
     * @param m_x posx
     * @param m_y poxy
     */
    private void revertMovement(float m_x, float m_y)
    {
        shape.translate(- m_x, - m_y);
    }

    /**
     * Touch player died
     * @param player bomber
     * @return
     */
    private boolean updateCatching(Boomber player)
    {
        if (Math.abs(player.getShape().getX() - shape.getX()) < TILE_WIDTH / 2)
            return true;
        if (Math.abs(player.getShape().getY() - shape.getY()) < TILE_HEIGHT / 2)
            return true;
        if (catching)
            moveSide = calculateMoveSide();
        return false;
    }

    /**
     * Update
     * @param map map
     * @param player bomber
     * @param dt time
     */
    public void update(MapCreator map, Boomber player, float dt)
    {
        elapsedTime += 3 * speed * dt;
        countTime = (int) elapsedTime % moveLength;

        if (! isDie)
        {
            catching = updateCatching(player);

            if (catching)
            {
                speed = 1.2f;
                calculateDirection(player, map);
            }
            else
            {
                speed = 0.8f;

                Vector2 v = convertMoveSide();
                updateMovement(v.x, v.y);
                if (detectCollision(map, player))
                {
                    while (detectCollision(map, player))
                    {
                        revertMovement(v.x, v.y);

                    }
                    moveSide = calculateMoveSide();
                }
            }
            if (shape.getBoundingRectangle().overlaps(player.getShape().getBoundingRectangle()) && ! player.isDie())
            {
                player.setDie();
            }

        }
        else
        {
            timeDie += 2.5 * dt;
            if (timeDie >= 1f)
            {
                countDie++;
                timeDie = 0f;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8))
        {
            killed();
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
            batch.draw(animation[countTime], shape.getX(), shape.getY(), ONEAL_WIDTH, ONEAL_HEIGHT);
        }
        else
        {

            if (! done && countDie < animationLength)
            {
                count++;
                batch.draw(animation[countDie], shape.getX(), shape.getY(), ONEAL_WIDTH, ONEAL_HEIGHT);
            }
            else
            {
                done = true;
            }
        }
        if(count == 20)
            Music_SoundManager.getInstance().playSound("EnemyDie.ogg");
        batch.end();
    }

    /**
     * Kill oneal
     */
    public void killed()
    {
        isDie = true;
        done = false;
        timeDie = 0;
    }

    /**
     * Is oneal died?
     * @return true or false
     */
    public boolean isDie()
    {
        return isDie;
    }

    /**
     * Is oneal's died animation done?
     * @return done
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
}
