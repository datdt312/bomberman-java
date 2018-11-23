package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BomberManGame;
import com.mygdx.game.Managers.Music_SoundManager;
import com.mygdx.game.Screens.SpriteMenu.BombApearSprite;

public class MenuScreen implements Screen {
    private BomberManGame game;
    private BombApearSprite bomApear;
    private SpriteBatch batch;
    private Stage stage;

    private Animation<TextureRegion> jingleBell1;
    private Animation<TextureRegion> jingleBell2;
    private Animation<TextureRegion> title;
    private Sprite start_game;
    private Sprite quit_game;
    private Sprite titlesprite;

    private Image img_bg;

    private int currentoption;
    private float statetime;

    public MenuScreen(BomberManGame game)
    {
        this.game = game;
        this.batch = game.getSpriteBatch();

        bomApear = new BombApearSprite(game.batch, 0,0);


        String path = "core/img/";
        Texture background = new Texture(path + "Background.png");
        img_bg = new Image(background);
        img_bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        img_bg.setPosition(0,0);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(new TextureAtlas(path + "start.pack")
                    .findRegion("start_sprite"), i * 97, 0, 97, 97));
        }

        jingleBell1 = new Animation<TextureRegion>(0.15f, frames, Animation.PlayMode.LOOP_PINGPONG);
        start_game = new Sprite(jingleBell1.getKeyFrame(0));
        start_game.setBounds(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 3.5f,  97 * 3, 97 * 3 );
        frames.clear();

        for(int i = 0; i< 5; i++)
        {
            frames.add(new TextureRegion(new TextureAtlas(path + "quit.pack")
                    .findRegion("quit_sprite"), i * 97, 0, 97, 97));

        }

        jingleBell2 = new Animation<TextureRegion>(0.15f, frames, Animation.PlayMode.LOOP_PINGPONG);
        quit_game = new Sprite(jingleBell2.getKeyFrame(0));
        quit_game.setBounds(Gdx.graphics.getWidth() / 2.5f, Gdx.graphics.getHeight() / 3.5f,  97 * 3 , 97 * 3 );
        frames.clear();

        for(int i = 0; i < 10; i++)
            frames.add(new TextureRegion(new TextureAtlas(path + "Title_sprite.pack")
                    .findRegion("Title_fixed"), 0, i * 171, 597, 171));
        title = new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.LOOP_PINGPONG);
        titlesprite = new Sprite(title.getKeyFrame(0));
        titlesprite.setBounds(Gdx.graphics.getWidth() / 3.6f, Gdx.graphics.getHeight() / 1.3f, 597, 171);
        frames.clear();

    }

    private void handleinput()
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            Music_SoundManager.getInstance().playSound("menuselect.wav");
            if(currentoption > 0)
                currentoption--;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            Music_SoundManager.getInstance().playSound("menuselect.wav");
            if(currentoption < 1)
                currentoption++;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Music_SoundManager.getInstance().playSound("selectchoice.wav");
            select();

        }
    }

    private void select()
    {
        if(currentoption == 0)
        {
            RunnableAction runnableAction = new RunnableAction();
            runnableAction.setRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new ControlScreen(game));
                }
            });
            stage.addAction(new SequenceAction(Actions.delay(0.1f), Actions.fadeOut(0.2f), runnableAction));
        }
        else if(currentoption == 1)
            Gdx.app.exit();
    }
    @Override
    public void show() {
        Music_SoundManager.getInstance().playMusic("WARRIORS.ogg", true);
        FitViewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, batch);

        stage.addActor(img_bg);

    }

    @Override
    public void render(float delta) {

        statetime += delta;
        start_game.setRegion(jingleBell1.getKeyFrame(statetime));
        quit_game.setRegion(jingleBell2.getKeyFrame(statetime));
        titlesprite.setRegion(title.getKeyFrame(statetime));

        handleinput();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        game.batch.begin();
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            titlesprite.setPosition(2000,800);
            start_game.setPosition(2000, 800);
        }

        titlesprite.draw(batch);
        if(currentoption == 0) {
            start_game.draw(batch);
        }


        if(currentoption == 1) {
            quit_game.draw(batch);

        }


        game.batch.end();

        bomApear.draw(delta);

    }

    @Override
    public void dispose() {

        stage.dispose();
        bomApear.dispose();

    }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() {
        Music_SoundManager.getInstance().stopMusic();
        dispose();
    }
}
