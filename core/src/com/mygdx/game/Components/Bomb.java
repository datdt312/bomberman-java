package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Managers.Music_SoundManager;
import com.mygdx.game.Maps.MapCreator;
import org.jetbrains.annotations.NotNull;


public class Bomb implements Comparable<Bomb>
{
    private float BOMB_WIDTH;
    private float BOMB_HEIGHT;
    private float FLAME_SIZE_WIDTH;
    private float FLAME_SIZE_HEIGHT;
    private float DISTANCE_WIDTH;
    private float DISTANCE_HEIGHT;

    private float x_posFlameHorizontal, y_posFlameHorizontal;
    private float x_posFlameVertical, y_posFlameVertical;

    private Texture textureBomb, textureFlame;

    private float posX;
    private float posY;
    private int lengthFlame;

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
        FLAME_SIZE_WIDTH = map.getTileWidth() * map.getUNIT_SCALE();
        FLAME_SIZE_HEIGHT = map.getTileHeight() * map.getUNIT_SCALE();
        DISTANCE_WIDTH = Math.abs(FLAME_SIZE_WIDTH - BOMB_WIDTH);
        DISTANCE_HEIGHT = Math.abs(FLAME_SIZE_HEIGHT - BOMB_HEIGHT);

        lengthFlame = player.getLengthFlame();

        createStatus();
        createPositionOfBombAndSetupFlame(player, map);
        createBombAnimation();
        createFlameAnimation();
    }

    public void createStatus()
    {
        exploding = false;
        waiting = true;
        done = false;
        timeLimitWaiting = 2f;
        timeLimitExploding = 1f;
        elapsedTime = 0;
        countTime = 0;
    }

    public void createPositionOfBombAndSetupFlame(Boomber player, MapCreator map)
    {
        posX = player.getPosX();
        posX = (int) (posX / (map.getTileWidth() * map.getUNIT_SCALE()));
        posX = posX * map.getTileWidth() * map.getUNIT_SCALE();
        x_posFlameHorizontal = posX;
        x_posFlameVertical = posX;

        posY = player.getPosY();
        posY = (int) (posY / (map.getTileHeight() * map.getUNIT_SCALE()));
        posY = posY * map.getTileHeight() * map.getUNIT_SCALE();
        y_posFlameHorizontal = posY;
        y_posFlameVertical = posY;

        posX += map.getTileWidth() * map.getUNIT_SCALE() / 2f;
        posX -= BOMB_WIDTH / 2f;
        posY += map.getTileHeight() * map.getUNIT_SCALE() / 2f;
        posY -= BOMB_HEIGHT / 2f;

        x_posFlameVertical += DISTANCE_HEIGHT/2f;
        y_posFlameHorizontal += DISTANCE_WIDTH/2f;
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
        elapsedTime += dt;
        if (waiting)
        {
            if (elapsedTime >= timeLimitWaiting / animationBombSize * (countTime + 1))
            {
                countTime++;
                if (countTime >= animationBombSize)
                {
                    waiting = false;
                    exploding = true;
                    countTime = 0;
                    elapsedTime = 0;
                }
            }
        }
        if (exploding)
        {
            if (elapsedTime >= timeLimitExploding / animationFlameSize * (countTime + 1))
            {
                Music_SoundManager.getInstance().playSound("Explosion.ogg");
                countTime++;
                if (countTime >= animationFlameSize)
                {
                    done = true;
                }
            }
        }
    }

    public void draw(Batch batch)
    {
        //System.out.println(elapsedTime);
        if (waiting)
        {
            if (countTime < animationBombSize)
                drawWaiting(batch);
        }
        else if (exploding)
        {
            if (countTime < animationFlameSize)
                drawExploding(batch);
        }
    }

    private void drawWaiting(Batch batch)
    {
        batch.begin();

        Sprite sprite = new Sprite(animationBomb[countTime]);
        batch.draw(sprite, posX, posY, BOMB_WIDTH, BOMB_HEIGHT);

        batch.end();
    }

    private void drawExploding(Batch batch)
    {

        drawHorizontalExploding(batch);
        drawVerticalExploding(batch);

        batch.begin();
        batch.draw(flameMiddle[countTime], posX, posY, BOMB_WIDTH, BOMB_HEIGHT);
        batch.end();
    }

    private void drawHorizontalExploding(Batch batch)
    {
        batch.begin();
        int i;
        for (i = 0; i < lengthFlame; i++)
        {
            batch.draw(flameHorizontal[countTime], x_posFlameHorizontal - FLAME_SIZE_WIDTH * i, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
            batch.draw(flameHorizontal[countTime], x_posFlameHorizontal + FLAME_SIZE_WIDTH * i, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
        }
        batch.draw(flameLeft[countTime], x_posFlameHorizontal - FLAME_SIZE_WIDTH * lengthFlame, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
        batch.draw(flameRight[countTime], x_posFlameHorizontal + FLAME_SIZE_WIDTH * lengthFlame, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
        batch.end();
    }

    private void drawVerticalExploding(Batch batch)
    {
        batch.begin();
        int i;
        for (i = 0; i < lengthFlame; i++)
        {
            batch.draw(flameVertical[countTime], x_posFlameVertical, y_posFlameVertical - FLAME_SIZE_HEIGHT * i, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
            batch.draw(flameVertical[countTime], x_posFlameVertical, y_posFlameVertical + FLAME_SIZE_HEIGHT * i, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
        }
        batch.draw(flameUp[countTime], x_posFlameVertical, y_posFlameVertical + FLAME_SIZE_HEIGHT * lengthFlame, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
        batch.draw(flameDown[countTime], x_posFlameVertical, y_posFlameVertical - FLAME_SIZE_HEIGHT * lengthFlame, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
        batch.end();
    }

    public boolean isDone()
    {
        return done;
    }

    public float getPosX()
    {
        return posX;
    }

    public float getPosY()
    {
        return posY;
    }

    @Override
    public int compareTo(@NotNull Bomb o)
    {
        if (this.getPosX() != o.getPosX() || this.getPosY() != o.getPosY())
            return 1;
        return 0;
    }
}
