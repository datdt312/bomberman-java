package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BomberManGame;
import com.mygdx.game.Characters.Boomber;
import com.mygdx.game.Tools.MapCreator;

public class PlayScreen implements Screen
{
    public SpriteBatch batch;
    private MapCreator map;
    public OrthographicCamera camera;
    private Boomber player;
    private BomberManGame game;

    public PlayScreen()
    {
        batch = new SpriteBatch();
        player = new Boomber();
    }

    @Override
    public void render(float delta)
    {
        player.update(delta);
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        map.getRenderer().setView(camera);
        map.getRenderer().render();

        player.draw(batch, delta);
        batch.begin();
        //player.getSprite().draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width/2f, height/2f, 0);
        camera.update();
    }

    @Override
    public void show()
    {
        map = new MapCreator("core/maps/level1.tmx");


        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        map.getMap().dispose();
        map.getRenderer().dispose();
    }
}
