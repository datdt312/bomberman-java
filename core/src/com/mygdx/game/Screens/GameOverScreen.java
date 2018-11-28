package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BomberManGame;

public class GameOverScreen implements Screen {

    private SpriteBatch batch;
    private BomberManGame game;

    private FitViewport viewport;
    private Stage stage;

    private Image bgimg;

    public GameOverScreen(BomberManGame game)
    {
        this.game = game;
        this.batch = game.getSpriteBatch();

        Texture bg = new Texture("core/img/gameover.png");
        bgimg = new Image(bg);
        bgimg.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }
    @Override
    public void show() {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, batch);

        stage.addActor(bgimg);

        stage.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.fadeOut(2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new MenuScreen(game));
                    }
                })));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.draw();
        stage.act(delta);

        batch.begin();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
