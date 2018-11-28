package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Maps.MapCreator;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;

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

    private Pixmap pm1 = null;
    private Pixmap pm2 = null;
    private int count = 50;
    @Override
    public void create()
    {
        batch = new SpriteBatch();
        //setScreen(new PlayScreen());
        setScreen(new MenuScreen(this));

        pm1 = new Pixmap(Gdx.files.internal("core/img/cursor1.png"));
        pm2 =  new Pixmap(Gdx.files.internal("core/img/cursor2.png"));

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
    public void render()
    {
        super.render();

        /*
        if(Gdx.input.justTouched()) {
            if(count < 50)
                count++;
            else count = 1;

        }

        else  Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap
               (Gdx.files.internal("core/img/pokecur/"+ (count) + ".png")),0 ,10));
               */
        if(Gdx.input.isTouched())
        {
            Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm2,0 ,10));
            count--;
        }

        else {
            if(count != 0)
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm1, 0, 10));
        }
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }
}
