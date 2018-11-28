package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Managers.BalloomManager;
import com.mygdx.game.Maps.MapCreator;

public class Portal
{
    private float PORTAL_WIDTH;
    private float PORTAL_HEIGHT;

    private Texture texture;
    private TextureRegion[] animation;
    private int animationLength;
    private Sprite shape;

    private float elapsedTime;
    private int frame;

    private boolean hidingBehindBrick;
    private boolean canConnect;
    private float standTime;

    public Portal(MapCreator map)
    {
        PORTAL_WIDTH = map.getTileWidth() * map.getUNIT_SCALE();
        PORTAL_HEIGHT = map.getTileHeight() * map.getUNIT_SCALE();

        shape = new Sprite();
        shape.setSize(PORTAL_WIDTH, PORTAL_HEIGHT);
        shape.setOriginBasedPosition(map.getPosPortal().x, map.getPosPortal().y);

        hidingBehindBrick = true;
        canConnect = false;
        standTime = 0f;

        createAnimation();
    }

    private void createAnimation()
    {
        texture = new Texture("core/assets/Portal_99_114.png");
        TextureRegion[][] regions = TextureRegion.split(texture, 99, 114);
        animationLength = regions[0].length;
        animation = new TextureRegion[animationLength];
        for (int i = 0; i < animationLength; i++)
        {
            animation[i] = regions[0][i];
        }
    }

    public void update(MapCreator map, Boomber player, BalloomManager balloomManager, float dt)
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
        if (balloomManager.getBallooms().isEmpty())
        {
            canConnect = true;
        }
        if (canConnect)
        {
            elapsedTime += dt;
            if (elapsedTime>=0.1f)
            {
                frame = (frame+1)%animationLength;
                elapsedTime = 0f;
            }

            if (shape.getBoundingRectangle().overlaps(player.getShape().getBoundingRectangle()))
            {
                standTime += dt;
            }
            else
            {
                standTime = 0f;
            }
            if (standTime >= 2f)
            {
                // Next Level or Winner
            }
        }

    }

    public void draw(Batch batch)
    {
        batch.begin();

        if (! hidingBehindBrick)
        {
            if (! canConnect)
            {
                batch.draw(animation[0], shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
            }
            else
            {
                batch.draw(animation[frame], shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
            }
        }
        batch.end();
    }
}
