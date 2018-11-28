package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Maps.MapCreator;

public class Item
{

    private String name;

    private Texture texture;
    private TextureRegion[][] regions;
    private TextureRegion[] animation;
    private int animationLength;

    private Sprite shape;

    private boolean hidingBehindBrick;
    private boolean eaten;
    private boolean equipped;

    private float elapsedTime;
    private int frames;

    public Item(String name, float x, float y, float width, float height)
    {
        this.name = name;

        shape = new Sprite();
        shape.setSize(width, height);
        shape.setOriginBasedPosition(x, y);

        createStatus();

        texture = new Texture("core/assets/items/items_16_16.png");
        regions = TextureRegion.split(texture, 16, 16);
        // Bomb  - 0
        // Flame - 1
        // Speed - 2
        if ("Bomb".equals(name))
        {
            createBombItem();
        }
        else if ("Flame".equals(name))
        {
            createFlameItem();
        }
        else if ("Speed".equals(name))
        {
            createSpeedItem();
        }
    }

    private void createStatus()
    {
        eaten = false;
        hidingBehindBrick = true;
        elapsedTime = 0f;
        frames = 0;
    }

    private void createSpeedItem()
    {
        animationLength = regions[2].length;
        animation = new TextureRegion[animationLength];
        for (int i = 0; i < animationLength; i++)
        {
            animation[i] = regions[2][i];
        }
    }

    private void createFlameItem()
    {
        animationLength = regions[1].length;
        animation = new TextureRegion[animationLength];
        for (int i = 0; i < animationLength; i++)
        {
            animation[i] = regions[1][i];
        }
    }

    private void createBombItem()
    {
        animationLength = regions[0].length;
        animation = new TextureRegion[animationLength];
        for (int i = 0; i < animationLength; i++)
        {
            animation[i] = regions[0][i];
        }
    }

    public void update(MapCreator map, Boomber player, float dt)
    {
        hidingBehindBrick = false;
        for (Rectangle r:map.getBricks())
        {
            if (shape.getBoundingRectangle().overlaps(r))
            {
                hidingBehindBrick = true;
                break;
            }
        }
        if (!hidingBehindBrick)
        {
            elapsedTime += dt;
            if (elapsedTime >= 0.1f)
            {
                elapsedTime = 0f;
                frames = (frames + 1) % animationLength;
            }
        }
        if (shape.getBoundingRectangle().overlaps(player.getShape().getBoundingRectangle()))
        {
            setEaten();
        }
        if (isEaten())
        {
            if ("Bomb".equals(name))
            {
                player.upBomb();
                equipped = true;
            }
            else if ("Flame".equals(name))
            {
                player.upFlame();
                equipped = true;
            }
            else if ("Speed".equals(name))
            {
                player.upSpeed();
                equipped = true;
            }
        }
    }

    public void draw(Batch batch)
    {
        batch.begin();
        if (!eaten)
        {
            if (!hidingBehindBrick)
                batch.draw(animation[frames], shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
        }
        batch.end();
    }

    public void setEaten()
    {
        this.eaten = true;
    }

    public boolean isEaten()
    {
        return eaten;
    }

    public Sprite getShape()
    {
        return shape;
    }

    public String getName()
    {
        return name;
    }

    public boolean isEquipped()
    {
        return equipped;
    }
}
