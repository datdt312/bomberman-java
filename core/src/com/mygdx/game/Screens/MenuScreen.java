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

    private static final float start_xpos = 1366 / 2.5f;
    private static final float start_ypos = 768 / 2f;
    private static final float quit_xpos = 1366 / 2.5f;
    private static final float quit_ypos = 768 / 3f;
    private static final float buttonWidth = 1366 / 5.3f;
    private static final float buttonHeigth = 768 / 16f;

    ;
    private BomberManGame game;
    private BombApearSprite bomApear;
    private final SpriteBatch batch;
    private Stage stage;

    private Texture startSelect;
    private Texture startDeselect;
    private Texture quitSelect;
    private Texture quitDeselect;

    private Animation jingleBell1;
    private Animation jingleBell2;
    private Animation loadAnim;
    private Sprite start_game;
    private Sprite quit_game;
    private Sprite loadSpri;

    private Image img_tiltle;
    private Image img_bg;

    private int currentoption;
    private float statetime;


    public MenuScreen(BomberManGame game)
    {
        this.game = game;
        this.batch = game.getSpriteBatch();

        bomApear = new BombApearSprite(game.batch, 0,0);

        startSelect = new Texture("core/img/start_select.png");
        startDeselect = new Texture("core/img/start_deselect.png");
        quitSelect = new Texture("core/img/quit_select.png");
        quitDeselect = new Texture("core/img/quit_deselect.png");

        Texture title = new Texture("core/img/Title_fixed.png");
        img_tiltle = new Image(title);
        img_tiltle.setSize(1366 / 1.6f,768 / 4.8f);
        img_tiltle.setPosition(1366 / 5.3f, 768 / 1.3f);

        Texture background = new Texture("core/img/Background.png");
        img_bg = new Image(background);
        img_bg.setSize(1366, 768);
        img_bg.setPosition(0,0);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(new TextureAtlas("core/img/start.pack")
                    .findRegion("start_sprite"), i * 97, 0, 97, 97));
        }

        jingleBell1 = new Animation(0.15f, frames, Animation.PlayMode.LOOP_PINGPONG);
        start_game = new Sprite((TextureRegion) jingleBell1.getKeyFrame(0));
        start_game.setBounds(1366 / 2.3f, 768 / 1.9f,  97 * 2, 97 * 2 );
        frames.clear();

        for(int i = 0; i< 5; i++)
        {
            frames.add(new TextureRegion(new TextureAtlas("core/img/quit.pack")
                    .findRegion("quit_sprite"), i * 97, 0, 97, 97));

        }

        jingleBell2 = new Animation(0.15f, frames, Animation.PlayMode.LOOP_PINGPONG);
        quit_game = new Sprite((TextureRegion) jingleBell2.getKeyFrame(0));
        quit_game.setBounds(1366 / 2.3f, 768 / 1.9f,  97 * 2 , 97 * 2 );
        frames.clear();

        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(new TextureAtlas("core/img/loading.pack")
                    .findRegion("stage_select"), i * 72, 0, 72, 74));
        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(new TextureAtlas("core/img/loading.pack")
                    .findRegion("stage_select"), i * 72, 74, 72, 74));
        for(int i = 0; i < 5; i++)
            frames.add(new TextureRegion(new TextureAtlas("core/img/loading.pack")
                    .findRegion("stage_select"), i * 72, 74 * 2, 72, 74));
        loadAnim = new Animation(0.3f, frames, Animation.PlayMode.LOOP);
        loadSpri = new Sprite((TextureRegion) loadAnim.getKeyFrame(0));
        loadSpri.setBounds(1366 / 2.1f, 768 / 2.4f, 72, 74);


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
                    game.setScreen(new PlayScreen());
                }
            });
            stage.addAction(new SequenceAction(Actions.delay(1f), Actions.fadeOut(1f), runnableAction));
        }
        else if(currentoption == 1)
            Gdx.app.exit();
    }
    @Override
    public void show() {
        Music_SoundManager.getInstance().playMusic("DRA.ogg", true);
        FitViewport viewport = new FitViewport(1366, 768);
        stage = new Stage(viewport, batch);


        stage.addActor(img_bg);
        stage.addActor(img_tiltle);

    }

    @Override
    public void render(float delta) {

        statetime += delta;
        start_game.setRegion((TextureRegion) jingleBell1.getKeyFrame(statetime));
        quit_game.setRegion((TextureRegion) jingleBell2.getKeyFrame(statetime));

        handleinput();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        game.batch.begin();
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            loadSpri.setRegion((TextureRegion) loadAnim.getKeyFrame(statetime));
            loadSpri.draw(batch);

            select();
        }



        if(currentoption == 0) {
            start_game.draw(batch);
            game.batch.draw(startSelect, 0.9f * start_xpos, start_ypos - 60, 1.5f * buttonWidth, 1.2f * buttonHeigth);
        }
        else game.batch.draw(startDeselect,start_xpos, start_ypos - 60, buttonWidth, buttonHeigth);

        if(currentoption == 1) {
            quit_game.draw(batch);
            game.batch.draw(quitSelect, 0.9f * quit_xpos, quit_ypos - 60, 1.5f * buttonWidth,   1.2f * buttonHeigth);
        }
        else game.batch.draw(quitDeselect, quit_xpos, quit_ypos - 60, buttonWidth, buttonHeigth);
        // bomApear.draw(batch);

        game.batch.end();

        bomApear.draw(delta);

    }

    @Override
    public void dispose() {

        stage.dispose();
        bomApear.dispose();
        //bomApear.dispose();

    }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
}
