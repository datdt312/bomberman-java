package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Maps.MapCreator;


public class Bomb
{
    private float BOMB_WIDTH;
    private float BOMB_HEIGHT;

    private Texture textureBomb, textureFlame;

    private MapCreator map;
    private OrthographicCamera camera;

    private float posX;
    private float posY;

    private boolean exploding;
    private boolean waiting;
    private boolean done;
    private float timeLimitWaiting;
    private float timeLimitExploding;

    private TextureRegion[][] textureRegions;
    private TextureRegion[] animationBomb;
    private TextureRegion[] flameVertical, flameHorizontal, flameMiddle, flameRight, flameLeft, flameUp, flameDown;
    private float animationBombSize;
    private float animationFlameSize;
    private float elapsedTime;
    private int countTime;

    public Bomb(MapCreator map, Boomber player)
    {
        BOMB_WIDTH = map.getTileWidth() * map.getUNIT_SCALE() * 3f / 4f;
        BOMB_HEIGHT = map.getTileHeight() * map.getUNIT_SCALE() * 3f / 4f;

        posX = ((int)(player.getShape().getX()/map.getTileWidth()/map.getUNIT_SCALE()))*map.getTileWidth()*map.getUNIT_SCALE();
        posY = ((int)(player.getShape().getY()/map.getTileHeight()/map.getUNIT_SCALE()))*map.getTileHeight()*map.getUNIT_SCALE();
        System.out.println("position: " + posX + " " +posY);

        exploding = false;
        waiting = true;
        done = false;
        timeLimitWaiting = 2f;
        timeLimitExploding = 2f;
        elapsedTime = 0;
        countTime = 0;

        createBombAnimation();
        createFlameAnimation();
    }

    public void createBombAnimation()
    {
        textureBomb = new Texture("core/assets/BombGreen_16_16.png");
        textureRegions = TextureRegion.split(textureBomb, 16, 16);
        animationBombSize = (float) textureRegions[0].length;
        animationBomb = new TextureRegion[(int) animationBombSize];
        for (int i = 0; i < animationBombSize; i++)
            animationBomb[i] = textureRegions[0][i];

    }

    public void createFlameAnimation()
    {
        textureFlame = new Texture("core/assets/Flame_Green_16_16.png");
        textureRegions = TextureRegion.split(textureFlame, 16, 16);

        animationFlameSize = (float) textureRegions[0].length;

        flameVertical = new TextureRegion[(int) animationFlameSize];
        flameHorizontal = new TextureRegion[(int) animationFlameSize];
        flameMiddle = new TextureRegion[(int) animationFlameSize];
        flameRight = new TextureRegion[(int) animationFlameSize];
        flameLeft = new TextureRegion[(int) animationFlameSize];
        flameUp = new TextureRegion[(int) animationFlameSize];
        flameDown = new TextureRegion[(int) animationFlameSize];

        for (int i = 0; i < animationFlameSize; i++)
        {
            flameMiddle[i] = textureRegions[0][i];
            flameUp[i] = textureRegions[1][i];
            flameLeft[i] = textureRegions[2][i];
            flameRight[i] = textureRegions[3][i];
            flameDown[i] = textureRegions[4][i];
            flameVertical[i] = textureRegions[5][i];
            flameHorizontal[i] = textureRegions[6][i];
        }
    }

    public void update(float dt)
    {
        if (elapsedTime >= timeLimitWaiting)
        {
            waiting = false;
            exploding = true;
        }
        else
        {
            if (elapsedTime >= timeLimitWaiting / animationBombSize * (countTime + 1))
            {
                countTime++;
                if (countTime >= animationBombSize)
                {
                    waiting = false;
                    exploding = false;
                }
            }
        }

    }

    public void draw(Batch batch, float dt)
    {
        elapsedTime += dt;
        //System.out.println(elapsedTime);
        batch.begin();
        if (waiting)
        {
            batch.draw(animationBomb[countTime], posX, posY, BOMB_WIDTH, BOMB_HEIGHT);
        }
        else if (exploding)
        {


        }
        batch.end();
    }

    public boolean isDone()
    {
        return done;
    }
}
