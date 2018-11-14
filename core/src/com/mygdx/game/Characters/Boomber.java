package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.MapCreator;

public class Boomber
{
    private static final int TILE_WIDTH = 19;
    private static final int TILE_HEIGHT = 21;

    private int moveSide;
    private boolean moving;
    private boolean standing;
    private boolean turning;

    private Texture texture, textureShape;
    private Sprite sprite, shape;

    private MapCreator map;
    private World world;

    private Body body;
    private Rectangle rectangle;

    private TextureRegion[][] animationRegion;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private float elapsedTime;

    private float speed;
    private int flame;
    private int bombs;

    private PlayScreen screen;


    public Boomber(MapCreator map)
    {
        this.map = map;
        //this.world = new World(new Vector2(0,0),true);
        System.out.println(this.world);
        speed = 4f;
        flame = 1;
        bombs = 1;

        //texture = new Texture("core/assets/BomberManMoveNew.png");
        texture = new Texture("core/assets/BBM_SPRITE_MOVE_18_21.png");
        sprite = new Sprite(texture);
        textureShape = new Texture("core/assets/Characters.png");
        shape = new Sprite(textureShape);
        System.out.println(shape.getBoundingRectangle().width + " " + shape.getBoundingRectangle().height);

        sprite.setScale(2f, 2f);
        sprite.setPosition(100, 100);
        sprite.setOrigin((float)TILE_WIDTH/2,(float)TILE_HEIGHT/4);
        shape.setPosition(sprite.getX(), sprite.getY());
        shape.setOriginCenter();
        moveSide = 2;
        moving = false;
        standing = true;
        turning = false;
        elapsedTime = 0;

        rectangle = new Rectangle(0,0,320,16);

        animationRegion = TextureRegion.split(texture, TILE_WIDTH, TILE_HEIGHT);
    }

    /*
    public void defineBoomber()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(sprite.getX(), sprite.getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15);

        fdef.shape = shape;
        body.createFixture(fdef);
    }
    */

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

    private void handleInput(float dt)
    {
        float step = speed;
        if (!moving)
        {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            {
                //sprite.translateX(step);
                shape.translateX(step);
                if (shape.getBoundingRectangle().overlaps(rectangle))
                {
                    //sprite.translateX(- step);
                    shape.translateX(- step);
                }
                moveSide = 1;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                //sprite.translateY(step);
                shape.translateY(step);
                if (shape.getBoundingRectangle().overlaps(rectangle))
                {
                    //sprite.translateY(- step);
                    shape.translateY(- step);
                }
                moveSide = 2;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            {
                //sprite.translateX(- step);
                shape.translateX(-step);
                if (shape.getBoundingRectangle().overlaps(rectangle))
                {
                    //sprite.translateX(step);
                    shape.translateX(step);
                }
                moveSide = 3;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                //sprite.translateY(- step);
                shape.translateY(-step);
                if (shape.getBoundingRectangle().overlaps(rectangle))
                {
                    //sprite.translateY(step);
                    shape.translateY(step);
                }
                moveSide = 0;
                moving = true;
            }
            else
            {
                turning = true;
            }
        }
        sprite.setPosition(shape.getX(), shape.getY());
        //System.out.println(shape.getX() + " " + shape.getY() +" " + shape.getWidth()+" " + shape.getHeight() + "\n " +rectangle.getX() + " " +rectangle.getY()+" " +rectangle.getWidth() + " " + rectangle.getHeight());
    }

    public boolean getCollision()
    {
        return true;
    }

    public void update(float dt)
    {
        float step = speed;
        handleInput(dt);
        if (shape.getBoundingRectangle().contains(rectangle))
            System.out.println("Intersects. . .");
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
            //System.out.println(dt);
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
        batch.begin();
        batch.draw(shape,shape.getX(),shape.getY());
        batch.end();

        moving = false;
    }


}
