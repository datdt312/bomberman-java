package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BomberManGame;
import com.mygdx.game.Characters.Boomber;
import com.mygdx.game.Tools.MapCreator;

public class PlayScreen implements Screen
{
    private SpriteBatch batch;
    private OrthographicCamera camera;

    private MapCreator map;

    private World world = new World(new Vector2(0,0),true);
    private Boomber player;
    private BomberManGame game;

    public PlayScreen()
    {
        map = new MapCreator("core/maps/level1.tmx");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch = new SpriteBatch();
        player = new Boomber(this.map);
    }

    @Override
    public void render(float delta)
    {
        player.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
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

    public World getWorld()
    {
        return this.world;
    }
}
