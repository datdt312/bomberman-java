package com.mygdx.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Maps.MapCreator;
import org.jetbrains.annotations.NotNull;

/**
 * Define A Bomb
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
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

    /**
     * Constructor
     * @param map map of the game
     * @param player Player of the game
     */
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

    /**
     * Create Status For Bomb
     */
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

    /**
     * Create Position For Bomb And Flames
     * @param player Player of the game
     * @param map map of the game
     */
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

        x_posFlameVertical += DISTANCE_HEIGHT / 2f;
        y_posFlameHorizontal += DISTANCE_WIDTH / 2f;
    }

    /**
     * Create Bomb Waiting Animation
     */
    public void createBombAnimation()
    {
        textureBomb = new Texture("core/assets/BombGreen_16_16.png");
        textureRegions = TextureRegion.split(textureBomb, 16, 16);
        animationBombSize = (float) textureRegions[0].length;
        animationBomb = new TextureRegion[(int) animationBombSize];
        for (int i = 0; i < animationBombSize; i++)
            animationBomb[i] = textureRegions[0][i];

    }

    /**
     * Create Flame Exploding Animation
     */
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

    /**
     * Update Current Animation And Status
     * @param dt deltaTime
     */
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
                countTime++;
                if (countTime >= animationFlameSize)
                {
                    done = true;
                }
            }
        }
    }

    /**
     * Draw Animation
     *
     * @param batch . . .
     * @param map   map of the game
     */
    public void draw(Batch batch, MapCreator map)
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
                drawExploding(batch, map);
        }
    }

    /**
     * Draw Bomb When Waiting For Exploding
     * @param batch . . .
     */
    private void drawWaiting(Batch batch)
    {
        batch.begin();

        Sprite sprite = new Sprite(animationBomb[countTime]);
        batch.draw(sprite, posX, posY, BOMB_WIDTH, BOMB_HEIGHT);

        batch.end();
    }

    /**
     * Draw Flame When Exploding
     *
     * @param batch . . .
     * @param map   map of the game
     */
    private void drawExploding(Batch batch, MapCreator map)
    {

        drawHorizontalExploding(batch, map);
        drawVerticalExploding(batch, map);

        batch.begin();
        batch.draw(flameMiddle[countTime], posX, posY, BOMB_WIDTH, BOMB_HEIGHT);
        batch.end();
    }

    /**
     * Draw Horizontal Flame
     *
     * @param batch . . .
     * @param map   map of the game
     */
    private void drawHorizontalExploding(Batch batch, MapCreator map)
    {
        boolean checkLeft = true;
        boolean checkRight = true;
        batch.begin();

        for (int i = 0; i < lengthFlame; i++)
        {
            // Check Horizontal Flame On The Left Side
            if (checkLeft)
            {
                Rectangle rect = new Rectangle(x_posFlameHorizontal - FLAME_SIZE_WIDTH * i, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
                checkLeft = detectCollision(rect, map);
                if (checkLeft)
                    batch.draw(flameHorizontal[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }

            // Check Horizontal Flame On The Right Side
            if (checkRight)
            {
                Rectangle rect = new Rectangle(x_posFlameHorizontal + FLAME_SIZE_WIDTH * i, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
                checkRight = detectCollision(rect, map);
                if (checkRight)
                    batch.draw(flameHorizontal[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
        }

        // Check Last Horizontal Flame On The Left Side
        if (checkLeft)
        {
            Rectangle rect = new Rectangle(x_posFlameHorizontal - FLAME_SIZE_WIDTH * lengthFlame, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
            checkLeft = detectCollision(rect, map);
            if (checkLeft)
                batch.draw(flameLeft[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        // Check Last Horizontal Flame On The Right Side
        if (checkRight)
        {
            Rectangle rect = new Rectangle(x_posFlameHorizontal + FLAME_SIZE_WIDTH * lengthFlame, y_posFlameHorizontal, FLAME_SIZE_WIDTH, BOMB_HEIGHT);
            checkRight = detectCollision(rect, map);
            if (checkRight)
                batch.draw(flameRight[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        batch.end();
    }

    /**
     * Draw Vertical Flame
     *
     * @param batch . . .
     * @param map   map of the game
     */
    private void drawVerticalExploding(Batch batch, MapCreator map)
    {
        boolean checkUp = true;
        boolean checkDown = true;
        batch.begin();

        for (int i = 0; i < lengthFlame; i++)
        {
            if (checkUp)
            {
                Rectangle rect = new Rectangle(x_posFlameVertical, y_posFlameVertical + FLAME_SIZE_HEIGHT * i, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
                checkUp = detectCollision(rect, map);
                if (checkUp)
                    batch.draw(flameVertical[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }

            if (checkDown)
            {
                Rectangle rect = new Rectangle(x_posFlameVertical, y_posFlameVertical - FLAME_SIZE_HEIGHT * i, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
                checkDown = detectCollision(rect, map);
                if (checkDown)
                    batch.draw(flameVertical[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
        }

        if (checkUp)
        {
            Rectangle rect = new Rectangle(x_posFlameVertical, y_posFlameVertical + FLAME_SIZE_HEIGHT * lengthFlame, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
            checkUp = detectCollision(rect, map);
            if (checkUp)
                batch.draw(flameUp[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
        if (checkDown)
        {
            Rectangle rect = new Rectangle(x_posFlameVertical, y_posFlameVertical - FLAME_SIZE_HEIGHT * lengthFlame, BOMB_WIDTH, FLAME_SIZE_HEIGHT);
            checkDown = detectCollision(rect, map);
            if (checkDown)
                batch.draw(flameDown[countTime], rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }

        batch.end();
    }

    /**
     * detect Collision
     *
     * @param rect bound of sprite flame
     * @param map  map of the game
     * @return false if flame collision with some bricks, walls; true if not
     */
    private boolean detectCollision(Rectangle rect, MapCreator map)
    {
        for (Rectangle r : map.getWalls())
        {
            if (rect.overlaps(r))
            {
                return false;
            }
        }
        for (Rectangle r : map.getBricks())
        {
            if (rect.overlaps(r))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Check when bomb is exploded
     * @return values of done
     */
    public boolean isDone()
    {
        return done;
    }

    /**
     * Get position X-axis of bomb
     * @return position X-axis
     */
    public float getPosX()
    {
        return posX;
    }

    /**
     * Get position Y-axis of bomb
     * @return position Y-axis
     */
    public float getPosY()
    {
        return posY;
    }

    /**
     * Comapre Two Bombs By Position
     * @param o Not null bomb
     * @return 0 if 2 bombs is one; 1 if not
     */
    @Override
    public int compareTo(@NotNull Bomb o)
    {
        if (this.getPosX() != o.getPosX() || this.getPosY() != o.getPosY())
            return 1;
        return 0;
    }
}
