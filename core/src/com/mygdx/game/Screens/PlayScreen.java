package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BomberManGame;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Managers.BalloomManager;
import com.mygdx.game.Managers.Music_SoundManager;
import com.mygdx.game.Maps.MapCreator;
import com.mygdx.game.Managers.MapManager;
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

    private Boomber player;
    private BalloomManager enemy_ballooms;

    //pause window
    private Skin skin;
    private boolean pause;
    private Stage stage;
    private Window pauseWindow;

    private Hud hud;

    /**
     * Construtor
     */
    public PlayScreen(BomberManGame game)
    {
        this.game = game;
        WIDTH_SCREEN = Gdx.graphics.getWidth();
        HEIGHT_SCREEN = Gdx.graphics.getHeight();

        mapManager = new MapManager();
        map = mapManager.getMapLevel(0);

        float width_camera = Gdx.graphics.getWidth();
        float height_camera = Gdx.graphics.getHeight();

        Gdx.graphics.setWindowedMode((int)width_camera, (int)height_camera);
        System.out.println(width_camera + " " + height_camera);

        camera = new OrthographicCamera(width_camera, height_camera);
        camera.translate(width_camera/2, height_camera/2, 0);
        camera.update();

        batch = new SpriteBatch();
        player = new Boomber(this.map, this.camera);

        enemy_ballooms = new BalloomManager(map);

        hud = new Hud(batch, 31, 13);
    }

    /**
     * Render Game
     * @param delta deltaTime
     */
    @Override
    public void render(float delta)
    {

        handleInput();
        if(!pause)
            update(delta);
        //update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.draw(batch);
        map.render();

        camera.position.x =  player.getShape().getX();
        player.draw(batch, delta);
        enemy_ballooms.draw(batch);

        pauseWindow.setVisible(pause);
        stage.draw();
        hud.draw(delta);
    }

    /**
     * Update Game
     * @param delta deltaTime
     */
    public void update(float delta)
    {
        map.update(delta);
        player.update(this.map, delta);
        enemy_ballooms.update(map, delta);
        hud.update(delta);
    }

    /**
     * Resize The Size Of Screen
     * @param width Width Of Screen
     * @param height Height Of Screen
     */
    @Override
    public void resize(int width, int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show()
    {

        Music_SoundManager.getInstance().playMusic("SOY.ogg", true);
        pause = false;

        skin = new Skin(Gdx.files.internal("core/uiskin/uiskin.json"));
        stage = new Stage(new FitViewport(WIDTH_SCREEN, HEIGHT_SCREEN), batch);
        pauseWindow = new Window("            Pause", skin);
        pauseWindow.setPosition((WIDTH_SCREEN - pauseWindow.getWidth()) / 2, (HEIGHT_SCREEN - pauseWindow.getHeight()) / 2);
        pauseWindow.setVisible(pause);

        TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause = false;
                Music_SoundManager.getInstance().playMusic();
            }
        });

        TextButton menuButton = new TextButton("Menu", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }

        });
        pauseWindow.add(continueButton).padBottom(16f);
        pauseWindow.row();
        pauseWindow.add(menuButton);

        stage.addActor(pauseWindow);
        Gdx.input.setInputProcessor(stage);

    }

    public void handleInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause = !pause;

            if (pause) {
                Music_SoundManager.getInstance().playSound("Pause.ogg");
                Music_SoundManager.getInstance().pauseMusic();
            } else {
                Music_SoundManager.getInstance().playMusic();
            }
        }
    }


    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

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
