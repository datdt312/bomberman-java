package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MenuScreen;

/**
 * Game BomberMan By Team HaNoiDienBienPhu
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
public class BomberManGame extends Game
{

    public SpriteBatch batch;

    public SpriteBatch getSpriteBatch(){return batch;}

    /**
     * create a batch to come in MenuScreen first
     */
    @Override
    public void create()
    {
        batch = new SpriteBatch();
        //setScreen(new PlayScreen());
        setScreen(new MenuScreen(this));

    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    public void pause()
    {
        super.pause();
    }

    @Override
    public void resume()
    {
        super.resume();
    }

    @Override
    public void render() { super.render(); }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }
}
