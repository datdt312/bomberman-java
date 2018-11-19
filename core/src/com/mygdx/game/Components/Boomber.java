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

public class Boomber
{
    private static final int TILE_WIDTH = 19;
    private static final int TILE_HEIGHT = 21;
    private static int BOMBER_WIDTH;
    private static int BOMBER_HEIGHT;

    private int moveSide;
    private boolean moving;
    private boolean standing;

    private Texture texture;
    private Sprite sprite, shape;

    private MapCreator map;
    private OrthographicCamera camera;


    private TextureRegion[][] animationRegion;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;

    private float speed;
    private int flame;
    private int bombs;

    private BombManager bombManager;


    public Boomber(MapCreator map, OrthographicCamera camera)
    {
        this.map = map;
        this.camera = camera;

        BOMBER_WIDTH = (int) (map.getTileWidth() * map.getUNIT_SCALE() * 6 / 7);
        BOMBER_HEIGHT = (int) (map.getTileHeight() * map.getUNIT_SCALE() * 6 / 7);

        speed = 1f;
        flame = 1;
        bombs = 1;

        shape = new Sprite();
        shape.setSize(BOMBER_WIDTH, BOMBER_HEIGHT * 1f / 2f);
        shape.setOriginCenter();
        shape.setPosition(this.map.getPosPlayer().x, this.map.getPosPlayer().y);

        texture = new Texture("core/assets/BBM_SPRITE_MOVE_19_21.png");
        sprite = new Sprite(texture);
        sprite.setOriginCenter();
        sprite.setPosition(shape.getX(), shape.getY());

        // MoveSide: 0 - DOWN _ _ _ 1 - RIGHT _ _ _ 2 - UP _ _ _ 3 - LEFT
        moveSide = 2;
        moving = false;
        standing = true;
        elapsedTime = 0;

        animationRegion = TextureRegion.split(texture, TILE_WIDTH, TILE_HEIGHT);

        bombManager = new BombManager();

    }

    public Sprite getShape()
    {
        return shape;
    }

    private void handleInput(float dt)
    {
        float step = speed;

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
            bombManager.addNewBomb(this.map, this);
        }
    }

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

    public void update(float dt)
    {
        handleInput(dt);

        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 1; i++)
            for (int j = 0; j < 4; j++)
                animationFrames[index++] = animationRegion[moveSide][j];
        animation = new Animation(1f / 4f, animationFrames);

        bombManager.update(dt);
    }

    public void draw(Batch batch, float dt)
    {
        bombManager.draw(batch,dt);
        batch.begin();
        if (moving)
        {
            elapsedTime += speed * dt;
            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), shape.getX(), shape.getY(), BOMBER_WIDTH, BOMBER_HEIGHT);
        }
        else
        {
            elapsedTime += 2 * speed * dt;
            batch.draw(animationRegion[10][(int) elapsedTime % 4], shape.getX(), shape.getY(), BOMBER_WIDTH, BOMBER_HEIGHT);
        }
        batch.end();


        moving = false;
    }


}
