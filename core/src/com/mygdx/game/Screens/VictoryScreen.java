/**
 * This screen apears after win all of levels
 * @author hndbp
 * @since 2018/11/29
 */
package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BomberManGame;
import com.mygdx.game.Managers.Music_SoundManager;

public class VictoryScreen implements Screen {
    private SpriteBatch batch;
    private BomberManGame game;

    private FitViewport viewport;
    private Stage stage;

    private Image img_bg;
    private Image img_title;
    private Image img_selbutton;
    private Image img_deselbutton;
    private Texture sel;
    private Texture desel;

    private int currentoption;

    /**
     * Constructor
     * @param game draw object
     */
    public VictoryScreen (BomberManGame game) {
        this.game = game;
        this.batch = game.getSpriteBatch();

        final String path = "core/img/";
        Texture bg = new Texture(path + "end.png");
        img_bg = new Image(bg);
        img_bg.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 69);

        Texture title = new Texture(path + "victory.png");
        img_title = new Image(title);
        img_title.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 1.23f);
        img_title.setSize(img_title.getWidth() * 1.3f, img_title.getHeight() * 1.3f);

        Texture selbut = new Texture(path + "Selectbutton.png");
        img_selbutton = new Image(selbut);
        img_selbutton.setBounds(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 3f,
                img_selbutton.getWidth() * 1.5f, img_selbutton.getHeight() * 1.5f);

        Texture deselbut = new Texture(path + "Deselectbutton.png");
        img_deselbutton = new Image(deselbut);
        img_deselbutton.setBounds(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 4.5f,
                img_deselbutton.getWidth() * 1.5f, img_deselbutton.getHeight() * 1.5f);

        sel = new Texture(path + "Select.png");
        desel = new Texture(path + "Deselect.png");
    }

    /**
     * create a stage to conduct actions
     */
    @Override
    public void show() {
        Music_SoundManager.getInstance().playMusic("WTF.ogg", true);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, batch);

        stage.addActor(img_bg);
        stage.addActor(img_title);
        stage.addActor(img_selbutton);
        stage.addActor(img_deselbutton);

    }

    /**
     * input from keyboard
     */
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

    /**
     * Select choices
     */
    private void select()
    {
        if(currentoption == 0)
        {
            RunnableAction runnableAction = new RunnableAction();
            runnableAction.setRunnable(new Runnable() {
                /**
                 * Change Screen
                 */
                @Override
                public void run() {
                    game.setScreen(new PlayScreen(game, 1,1,1,1));
                }
            });
            stage.addAction(new SequenceAction(Actions.delay(0.1f), Actions.fadeOut(0.2f), runnableAction));
        }
        else if(currentoption == 1)
            Gdx.app.exit();
    }

    /**
     * render actions generated above
     * @param delta time throught game
     */
    @Override
    public void render(float delta) {
        handleinput();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act(delta);

        batch.begin();
        if(currentoption == 0)
            batch.draw(sel,Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2.45f,
                    sel.getWidth() * 1.5f, sel.getHeight() * 1.5f);
        else batch.draw(desel, Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2.45f,
                desel.getWidth() * 1.5f, desel.getHeight() * 1.5f);

        if(currentoption == 1)
            batch.draw(sel,Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 3.35f,
                    sel.getWidth() * 1.5f, sel.getHeight() *  1.5f);
        else batch.draw(desel, Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 3.35f,
                sel.getWidth() * 1.5f, sel.getHeight() *  1.5f);
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
