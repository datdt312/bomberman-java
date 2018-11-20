package com.mygdx.game.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Managers.BombManager;
import com.mygdx.game.Maps.MapCreator;

/**
 * Define A Player
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
public class Boomber
{
    private static final int TILE_WIDTH = 19;
    private static final int TILE_HEIGHT = 21;
    private static int BOMBER_WIDTH;
    private static int BOMBER_HEIGHT;

    private int moveSide;
    private boolean moving;
    private boolean justPlanBomb;

    private Texture texture;
    private Sprite sprite, shape;

    private MapCreator map;
    private OrthographicCamera camera;


    private TextureRegion[][] animationRegion;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;

    private float maxSpeed;
    private int lengthFlame;
    private int maxBombs;

    private BombManager bombManager;


    /**
     * Constructor
     * @param map map of the game
     * @param camera dont know for what :v
     */
    public Boomber(MapCreator map, OrthographicCamera camera)
    {
        this.map = map;
        this.camera = camera;

        BOMBER_WIDTH = (int) (map.getTileWidth() * map.getUNIT_SCALE() * 6 / 7);
        BOMBER_HEIGHT = (int) (map.getTileHeight() * map.getUNIT_SCALE() * 6 / 7);

        maxSpeed = 2f;
        lengthFlame = 2;
        maxBombs = 2;

        shape = new Sprite();
        shape.setSize(BOMBER_WIDTH, BOMBER_HEIGHT * 1f / 2f);
        shape.setOriginBasedPosition(this.map.getPosPlayer().x, this.map.getPosPlayer().y);

        texture = new Texture("core/assets/BBM_SPRITE_MOVE_19_21.png");
        sprite = new Sprite(texture);
        sprite.setOriginCenter();
        sprite.setPosition(shape.getX(), shape.getY());

        // MoveSide: 0 - DOWN _ _ _ 1 - RIGHT _ _ _ 2 - UP _ _ _ 3 - LEFT
        moveSide = 2;
        moving = false;
        justPlanBomb = false;
        elapsedTime = 0;

        animationRegion = TextureRegion.split(texture, TILE_WIDTH, TILE_HEIGHT);

        bombManager = new BombManager(this);

    }

    /**
     * get Shape (Main Player Not sprite =)))) )
     * @return
     */
    public Sprite getShape()
    {
        return shape;
    }

    /**
     * Handle Input Of Player
     * @param dt deltaTime
     */
    private void handleInput(float dt)
    {
        float step = maxSpeed;

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            shape.translateX(step);
            if (detectCollision())
            {
                shape.translateX(- step);
            }
            moveSide = 1;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            shape.translateY(step);
            if (detectCollision())
            {
                shape.translateY(- step);
            }
            moveSide = 2;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            shape.translateX(- step);
            if (detectCollision())
            {
                shape.translateX(step);
            }
            moveSide = 3;

            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            shape.translateY(- step);
            if (detectCollision())
            {
                shape.translateY(step);
            }
            moveSide = 0;
            moving = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            justPlanBomb = bombManager.addNewBomb(this.map, this);
        }
    }

    /**
     * Detect Collision With Map
     * @return true if collision; false if not
     */
    public boolean detectCollision()
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
     * Update Player
     * @param dt deltaTime
     */
    public void update(float dt)
    {
        handleInput(dt);

        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 1; i++)
            for (int j = 0; j < 4; j++)
                animationFrames[index++] = animationRegion[moveSide][j];
        animation = new Animation(1f / 4f, animationFrames);

        bombManager.update(this.map, this, dt);
    }

    /**
     * Draw Player And Bombs
     *
     * @param batch
     * @param dt
     */
    public void draw(Batch batch, float dt)
    {
        bombManager.draw(batch, this.map);
        batch.begin();
        if (moving)
        {
            elapsedTime += maxSpeed * dt;
            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), shape.getX(), shape.getY(), BOMBER_WIDTH, BOMBER_HEIGHT);
        }
        else
        {
            elapsedTime += 2 * maxSpeed * dt;
            batch.draw(animationRegion[10][(int) elapsedTime % 4], shape.getX(), shape.getY(), BOMBER_WIDTH, BOMBER_HEIGHT);
        }
        batch.end();

        moving = false;
    }

    /**
     * Get Position Of Main Player On X-axis
     * @return Position Of Main Player On X-axis
     */
    public float getPosX()
    {
        return shape.getX() + shape.getWidth() / 2;
    }

    /**
     * Get Position Of Main Player On Y-axis
     * @return Position Of Main Player On Y-axis
     */
    public float getPosY()
    {
        return shape.getY() + shape.getHeight() / 2;
    }

    /**
     * Get Speed Of Player
     * @return Speed Of Player
     */
    public float getMaxSpeed()
    {
        return maxSpeed;
    }

    /**
     * Get Length Of Flame Of Bomb
     * @return Length Of Flame Of Bomb
     */
    public int getLengthFlame()
    {
        return lengthFlame;
    }

    /**
     * Get Number Of Bombs Player have
     * @return Number Of Bombs Player have
     */
    public int getMaxBombs()
    {
        return maxBombs;
    }
}
