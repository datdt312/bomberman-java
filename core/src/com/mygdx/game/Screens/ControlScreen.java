package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BomberManGame;

import java.security.Key;

public class ControlScreen implements Screen {
    private BomberManGame game;

    private Stage stage;
    private SpriteBatch batch;

    private Texture doorLeft;
    private Texture doorRight;

    private Animation<TextureRegion> helpAnim;
    private Sprite helpSpr;

    private float timeLeft = -980;
    private float timeRight = 910;
    private float timecount;

    private final float scale = 2.4f;
    private final String path = "core/img/";

    private float statetime = 0;

    public ControlScreen(BomberManGame game)
    {
        this.game = game;
        this.batch = game.getSpriteBatch();

        doorLeft = new Texture(path + "wl.png");
        doorRight = new Texture(path + "wr.png");


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i <= 10; i++)
            frames.add(new TextureRegion(new TextureAtlas(path + "help.pack").findRegion(i+""),
                    0,0, 479, 320));
        helpAnim = new Animation<TextureRegion>(0.5f, frames, Animation.PlayMode.NORMAL);
        helpSpr = new Sprite(helpAnim.getKeyFrame(0));
        helpSpr.setBounds(150,50, 479 * 2, 320 * 2);

    }

    @Override
    public void show() {
        FitViewport viewport = new FitViewport(1366,  768);
        stage = new Stage(viewport, batch);

        stage.addActor(new Image(new Texture(path + "null.png")));

    }

    @Override
    public void render(float delta) {
        timeLeft += 8;
        timeRight -= 8;
        statetime += delta;

        helpSpr.setRegion(helpAnim.getKeyFrame(statetime));

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            RunnableAction runnableAction = new RunnableAction();
            runnableAction.setRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new PlayScreen(game));
                }
            });
            stage.addAction(new SequenceAction(Actions.delay(0.2f), Actions.fadeOut(0.2f), runnableAction));
        }
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if (timeLeft < 0)
            game.batch.draw(doorLeft, timeLeft, 0,
                    doorLeft.getWidth() * scale + 230, doorLeft.getHeight() * scale);
        else if(timeLeft>=0) {
            timecount += delta;
            game.batch.draw(doorLeft, 0, 0,
                    doorLeft.getWidth() * scale + 230, doorLeft.getHeight() * scale);
        }
        else if(timecount >= 10f) {
            timeLeft -= 10;
            game.batch.draw(doorLeft, timeLeft, 0,
                    doorLeft.getWidth() * scale + 230, doorLeft.getHeight() * scale);
        }

        if (timeRight > -90)
            game.batch.draw(doorRight, timeRight, 0,
                    doorRight.getWidth() * scale + 230, doorRight.getHeight() * scale);
        else game.batch.draw(doorRight, -90, 0,
                doorRight.getWidth() * scale + 230,doorRight.getHeight() * scale);


        helpSpr.draw(batch);

        game.batch.end();

        stage.act(delta);
        stage.draw();
        System.out.println(timecount);




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
