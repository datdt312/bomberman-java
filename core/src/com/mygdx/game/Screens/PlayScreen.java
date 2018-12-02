package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BomberManGame;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Components.Portal;
import com.mygdx.game.Managers.*;
import com.mygdx.game.Maps.MapCreator;
import com.mygdx.game.Scene.Hud;

/**
 * Play Screen Of Game
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
public class PlayScreen implements Screen
{
    private float WIDTH_SCREEN;
    private float HEIGHT_SCREEN;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private BomberManGame game;

    private MapManager mapManager;
    private MapCreator map;

    private Portal portal;
    private Boomber player;
    private EnemyManager enemyManager;
    private ItemsManager itemsManager;

    //pause window
    private Skin skin;
    private boolean pause;
    private Stage stage;
    private Window pauseWindow;

    private Hud hud;

    private final String[] list = {"victory.mp3", "BAAM.ogg", "BADBOY.ogg", "DDU.ogg", "SOY.ogg", "WARRIORS.mp3", "KDA.mp3"};

    private Integer level;

    /**
     * Construtor
     *
     * @param game draw objects, actions
     */
    public PlayScreen(BomberManGame game, int level, float maxSpeed, int lengthFlame, int maxBombs)
    {
        this.game = game;
        this.level = level;

        WIDTH_SCREEN = Gdx.graphics.getWidth();
        HEIGHT_SCREEN = Gdx.graphics.getHeight();

        mapManager = new MapManager();

        switch (level)
        {
            case 4:
                map = mapManager.getMapLevel(3);
                Music_SoundManager.getInstance().playMusic(list[3], true);
                break;
            case 3:
                map = mapManager.getMapLevel(2);
                Music_SoundManager.getInstance().playMusic(list[6], true);
                break;
            case 2:
                map = mapManager.getMapLevel(1);
                Music_SoundManager.getInstance().playMusic(list[4], true);
                break;
            case 1:
            default:
                map = mapManager.getMapLevel(0);
                Music_SoundManager.getInstance().playMusic(list[1], true);
                break;
        }

        float width_camera = Gdx.graphics.getWidth();
        float height_camera = Gdx.graphics.getHeight();

        Gdx.graphics.setWindowedMode((int) width_camera, (int) height_camera);
        System.out.println(width_camera + " " + height_camera);

        camera = new OrthographicCamera(width_camera, height_camera);
        camera.translate(width_camera / 2, height_camera / 2, 0);
        camera.update();

        batch = new SpriteBatch();
        player = new Boomber(this.map, this.camera, maxSpeed, lengthFlame, maxBombs);
        portal = new Portal(map);

        enemyManager = new EnemyManager(map);
        itemsManager = new ItemsManager(map);

        hud = new Hud(batch, level);

        System.out.println(level);
    }

    /**
     * Render Game
     *
     * @param delta deltaTime
     */
    @Override
    public void render(float delta)
    {

        handleInput();

        if (! pause)
            update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.render();
        map.draw(batch);

        itemsManager.draw(batch);

        portal.draw(batch);
        player.draw(batch, delta, enemyManager);
        enemyManager.draw(batch);

        pauseWindow.setVisible(pause);

        stage.draw();
        stage.act();

        hud.draw(delta);
    }

    /**
     * Update Game
     *
     * @param delta deltaTime
     */
    public void update(float delta)
    {
        map.update(delta);
        itemsManager.update(map, player, delta);
        portal.update(map, player, enemyManager, delta);
        player.update(this.map, delta);
        enemyManager.update(map, player, delta);
        hud.update(delta);

        if (player.isDeadNoHopeAndEndGame() && hud.getLiveCount() > 0)
        {
            player = new Boomber(map, camera, 1.5f, 1, 1);
            hud.decreaseLiveCount();

        }
        if (hud.getLiveCount() == 0 || hud.getTimeCount() == 0)
        {

            Music_SoundManager.getInstance().stopMusic();
            game.setScreen(new GameOverScreen(game));

        }

        if (portal.getStandTime() >= 2f && level < 4)
        {
            Music_SoundManager.getInstance().stopMusic();
            game.setScreen(new PlayScreen(game, level + 1, this.player.getMaxSpeed(), this.player.getLengthFlame(), this.player.getMaxBombs()));
        }
        else if (portal.getStandTime() >= 2f && level + 1 == 5)
        {

            Music_SoundManager.getInstance().stopMusic();
            game.setScreen(new VictoryScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7))
        {
            if (level < 4)
            {
                Music_SoundManager.getInstance().stopMusic();
                game.setScreen(new PlayScreen(game, level + 1, this.player.getMaxSpeed(), this.player.getLengthFlame(), this.player.getMaxBombs()));
            }
            else if (level + 1 == 5)
            {
                Music_SoundManager.getInstance().stopMusic();
                game.setScreen(new VictoryScreen(game));
            }
        }
    }

    /**
     * Resize The Size Of Screen
     *
     * @param width  Width Of Screen
     * @param height Height Of Screen
     */
    @Override
    public void resize(int width, int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    /**
     * show Playmusic, PauseWindow
     */
    @Override
    public void show()
    {

        pause = false;

        skin = new Skin(Gdx.files.internal("core/uiskin/uiskin.json"));
        stage = new Stage(new FitViewport(WIDTH_SCREEN, HEIGHT_SCREEN), batch);
        pauseWindow = new Window("            Pause", skin);
        pauseWindow.setPosition((WIDTH_SCREEN - pauseWindow.getWidth()) / 2, (HEIGHT_SCREEN - pauseWindow.getHeight()) / 2);
        pauseWindow.setVisible(pause);

        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                pause = false;
                Music_SoundManager.getInstance().playMusic();
            }
        });

        TextButton menuButton = new TextButton("Menu", skin);
        menuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new MenuScreen(game));
            }

        });
        pauseWindow.add(continueButton).padBottom(16f);
        pauseWindow.row();
        pauseWindow.add(menuButton);

        stage.addActor(pauseWindow);
        Gdx.input.setInputProcessor(stage);

    }

    /**
     * input from keyboard to do something
     */
    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            pause = ! pause;

            if (pause)
            {
                Music_SoundManager.getInstance().playSound("Pause.ogg");
                Music_SoundManager.getInstance().pauseMusic();
            }
            else
            {
                Music_SoundManager.getInstance().playMusic();
            }
        }
    }


    @Override
    public void hide()
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
        map.dispose();
    }

    public float getWIDTH_SCREEN()
    {
        return WIDTH_SCREEN;
    }

    public float getHEIGHT_SCREEN()
    {
        return HEIGHT_SCREEN;
    }
}
