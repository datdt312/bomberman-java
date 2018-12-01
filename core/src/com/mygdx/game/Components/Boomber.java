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
import com.mygdx.game.Managers.BalloomManager;
import com.mygdx.game.Managers.BombManager;
import com.mygdx.game.Managers.EnemyManager;
import com.mygdx.game.Managers.Music_SoundManager;
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
    private boolean isDie;
    private boolean justPlanBomb;
    private Bomb bombStandingOn;

    private Texture texture;
    private Sprite shape;

    private MapCreator map;
    private OrthographicCamera camera;


    private TextureRegion[][] animationRegion;
    private TextureRegion[] animationFrames;
    private Animation animation;
    private TextureRegion[] animationDead;
    private int lengthAnimationDead;
    private float elapsedTime;
    private float timeDie;
    private int frameDie;

    private float maxSpeed;
    private int lengthFlame;
    private int maxBombs;
    private int count = 0;

    private BombManager bombManager;


    /**
     * Constructor
     *
     * @param map    map of the game
     * @param camera dont know for what :v
     */
    public Boomber(MapCreator map, OrthographicCamera camera)
    {
        this.map = map;
        this.camera = camera;

        BOMBER_WIDTH = (int) (map.getTileWidth() * map.getUNIT_SCALE() * 6 / 7);
        BOMBER_HEIGHT = (int) (map.getTileHeight() * map.getUNIT_SCALE() * 6 / 7);
        count = 0;

        maxSpeed = 2f;
        lengthFlame = 2;
        maxBombs = 2;

        shape = new Sprite();
        shape.setSize(BOMBER_WIDTH, BOMBER_HEIGHT * 1f / 2f);
        shape.setOriginBasedPosition(map.getPosPlayer().x, map.getPosPlayer().y);

        texture = new Texture("core/assets/BBM_SPRITE_MOVE_19_21.png");

        // MoveSide: 0 - DOWN _ _ _ 1 - RIGHT _ _ _ 2 - UP _ _ _ 3 - LEFT
        moveSide = 2;
        moving = false;
        isDie = false;
        timeDie = 0f;
        frameDie = 0;
        justPlanBomb = false;
        elapsedTime = 0;

        animationRegion = TextureRegion.split(texture, TILE_WIDTH, TILE_HEIGHT);

        createAnimationDead();

        bombManager = new BombManager(this);


    }

    /**
     * Create died animation
     */
    private void createAnimationDead()
    {
        Texture txt = new Texture("core/assets/BBM_SPRITE_DIE_23_23.png");
        TextureRegion[][] regions = TextureRegion.split(txt, 23, 23);
        lengthAnimationDead = regions[0].length;
        animationDead = new TextureRegion[lengthAnimationDead];
        for (int i = 0; i < lengthAnimationDead; i++)
            animationDead[i] = regions[0][i];
    }

    /**
     * get Shape (Main Player Not sprite =)))) )
     *
     * @return
     */
    public Sprite getShape()
    {
        return shape;
    }

    /**
     * Handle Input Of Player
     *
     * @param dt deltaTime
     */
    private void handleInput(MapCreator map, float dt)
    {

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            moveSide = 1;
            updateMovement();
            if (detectCollision(map))
                revertMovement();
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            moveSide = 2;
            updateMovement();
            if (detectCollision(map))
                revertMovement();
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            moveSide = 3;
            updateMovement();
            if (detectCollision(map))
                revertMovement();
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            moveSide = 0;
            updateMovement();
            if (detectCollision(map))
                revertMovement();
            moving = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            justPlanBomb = bombManager.addNewBomb(this.map, this);
        }
    }

    /**
     * Update movement
     */
    private void updateMovement()
    {
        switch (moveSide)
        {
            case 0:
                shape.translateY(- maxSpeed);
                break;
            case 1:
                shape.translateX(maxSpeed);
                break;
            case 2:
                shape.translateY(maxSpeed);
                break;
            case 3:
                shape.translateX(- maxSpeed);
                break;
        }
    }

    /**
     * Revert movement
     */
    private void revertMovement()
    {
        switch (moveSide)
        {
            case 0:
                shape.translateY(maxSpeed);
                break;
            case 1:
                shape.translateX(- maxSpeed);
                break;
            case 2:
                shape.translateY(- maxSpeed);
                break;
            case 3:
                shape.translateX(maxSpeed);
                break;
        }
    }

    /**
     * Detect Collision With Map
     *
     * @return true if collision; false if not
     */
    public boolean detectCollision(MapCreator map)
    {
        for (Bomb b : bombManager.getBomb_manage())
            if (b != bombStandingOn)
            {
                if (shape.getBoundingRectangle().overlaps(b.getShape().getBoundingRectangle()))
                    return true;
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
     * Update Player
     *
     * @param dt deltaTime
     */
    public void update(MapCreator map, float dt)
    {

        if (! isDie)
        {
            handleInput(map, dt);
            bombStandingOn = checkStandingOnBomb();

            animationFrames = new TextureRegion[4];
            int index = 0;
            for (int i = 0; i < 1; i++)
                for (int j = 0; j < 4; j++)
                    animationFrames[index++] = animationRegion[moveSide][j];
            animation = new Animation(1f / 4f, animationFrames);
        }
        else
        {
            count++;
            timeDie += dt;
            if (timeDie >= 0.15f)
            {
                frameDie++;
                timeDie = 0f;
            }
        }
        if (count == 1)
            Music_SoundManager.getInstance().playSound("playerdie.mp3");

        bombManager.update(this, dt);
    }

    /**
     * Check player with bomb
     * @return
     */
    private Bomb checkStandingOnBomb()
    {
        for (Bomb b : bombManager.getBomb_manage())
        {
            if (shape.getBoundingRectangle().overlaps(b.getShape().getBoundingRectangle()))
                return b;
        }
        return null;
    }

    /**
     * Draw Player And Bombs
     *
     * @param batch
     * @param dt
     */
    public void draw(Batch batch, float dt, EnemyManager enemyManager)
    {
        bombManager.draw(batch, this.map, this, enemyManager);

        batch.begin();
        if (! isDie)
        {

            if (moving)
            {
                elapsedTime += maxSpeed * dt;
                batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true), shape.getX(), shape.getY(), BOMBER_WIDTH, BOMBER_HEIGHT);
            }
            else
            {
                elapsedTime += 1.5f * maxSpeed * dt;
                batch.draw(animationRegion[10][(int) elapsedTime % 4], shape.getX(), shape.getY(), BOMBER_WIDTH, BOMBER_HEIGHT);
            }
        }
        else
        {
            if (frameDie < lengthAnimationDead)
            {
                batch.draw(animationDead[frameDie], shape.getX(), shape.getY(), BOMBER_WIDTH, BOMBER_HEIGHT);
            }
        }

        batch.end();

        moving = false;
    }

    public void upSpeed()
    {
        maxSpeed += 0.5f;
    }

    public void upBomb()
    {
        maxBombs += 1;
    }

    public void upFlame()
    {
        lengthFlame += 1;
    }

    public boolean isDeadNoHopeAndEndGame()
    {
        return frameDie > lengthAnimationDead;
    }

    /**
     * Get Position Of Main Player On X-axis
     *
     * @return Position Of Main Player On X-axis
     */
    public float getPosX()
    {
        return shape.getX() + shape.getWidth() / 2;
    }

    /**
     * Get Position Of Main Player On Y-axis
     *
     * @return Position Of Main Player On Y-axis
     */
    public float getPosY()
    {
        return shape.getY() + shape.getHeight() / 2;
    }

    /**
     * Get Speed Of Player
     *
     * @return Speed Of Player
     */
    public float getMaxSpeed()
    {
        return maxSpeed;
    }

    /**
     * Get Length Of Flame Of Bomb
     *
     * @return Length Of Flame Of Bomb
     */
    public int getLengthFlame()
    {
        return lengthFlame;
    }

    /**
     * Get Number Of Bombs Player have
     *
     * @return Number Of Bombs Player have
     */
    public int getMaxBombs()
    {
        return maxBombs;
    }

    public boolean isDie()
    {
        return isDie;
    }

    public void setDie()
    {
        isDie = true;
    }
    
    public BombManager getBombManager()
    {
        return bombManager;
    }
}
