package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Maps.MapCreator;
import com.mygdx.game.Managers.MapManager;

public class PlayScreen implements Screen
{
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private MapManager mapManager;
    private MapCreator map;

    private Boomber player;

    public PlayScreen()
    {
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
    }

    @Override
    public void render(float delta)
    {
        update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.render();

        camera.position.x =  player.getShape().getX();
        player.draw(batch, delta);
    }

    public void update(float delta)
    {
        player.update(delta);
    }

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


    }

    @Override
    public void hide()
    {
        dispose();
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
}
