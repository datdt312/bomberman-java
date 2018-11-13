package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Boomber
{
    private static final int TILE_WIDTH = 19;
    private static final int TILE_HEIGHT = 21;

    private int moveSide;
    private boolean moving;
    private boolean standing;
    private boolean turning;

    private Texture texture;
    private Sprite sprite;
    private World world;
    private Body body;

    private TextureRegion[][] animationRegion;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;

    private float speed;
    private int flame;
    private int bombs;


    public Boomber()
    {
        speed = 1f;
        flame = 1;
        bombs = 1;

        //texture = new Texture("core/assets/BomberManMoveNew.png");
        texture = new Texture("core/assets/BBM_SPRITE_MOVE_18_21.png");
        sprite = new Sprite(texture);

        sprite.setScale(2f, 2f);
        sprite.setPosition(100, 100);
        moveSide = 2;
        moving = false;
        standing = true;
        turning = false;
        elapsedTime = 0;

        animationRegion = TextureRegion.split(texture, TILE_WIDTH, TILE_HEIGHT);

        world = new World(new Vector2(0, 0), true);

        PolygonShape shape = new PolygonShape();
        //shape.setAsBox(sprite.getWidth()/2)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;


    }

    public Texture getTexture()
    {
        return texture;
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public void handleInput(float dt)
    {
        float step = speed;
        if (!moving)
        {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            {
                sprite.translateX(step);
                moveSide = 1;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                sprite.translateY(step);
                moveSide = 2;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            {
                sprite.translateX(- step);
                moveSide = 3;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                sprite.translateY(- step);
                moveSide = 0;
                moving = true;
            }
            else
            {
                turning = true;
            }
        }
    }

    public void update(float dt)
    {
        handleInput(dt);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 1; i++)
            for (int j = 0; j < 4; j++)
                animationFrames[index++] = animationRegion[moveSide][j];
        animation = new Animation(1f / 5f, animationFrames);
    }

    public void draw(Batch batch, float dt)
    {
        if (moving)
        {
            elapsedTime += speed * dt;
            batch.begin();
            System.out.println(dt);
            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), sprite.getX(), sprite.getY(), 30, 30);
            batch.end();
        }
        else
        {
            elapsedTime += 2*speed*dt;
            batch.begin();
            batch.draw(animationRegion[10][(int)elapsedTime%4], sprite.getX(), sprite.getY(), 30, 30);
            batch.end();
        }
        moving = false;
    }


}
