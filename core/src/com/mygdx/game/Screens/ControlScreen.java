/**
 * Help player know how to play this game
 * @author hndbp
 * @since 2018/11/23
 */
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
import com.mygdx.game.Managers.Music_SoundManager;

public class ControlScreen implements Screen
{
    private float WIDTH_SCREEN;
    private float HEIGHT_SCREEN;
    private BomberManGame game;

    private Stage stage;
    private SpriteBatch batch;

    private Texture doorLeft;
    private Texture doorRight;

    private Animation<TextureRegion> helpAnim;
    private Sprite helpSpr;
    private Animation<Texture> indicate;
    private Sprite indication;

    private float timeLeft = - 980;
    private float timeRight = 910;
    private int count=0;
    private boolean goout = false;

    private final float scale = 2.4f;
    private final String path = "core/img/";

    private float statetime = 0;

    /**
     * Constructor
     * @param game draw objects
     */

    public ControlScreen(BomberManGame game)
    {
        WIDTH_SCREEN = Gdx.graphics.getWidth();
        HEIGHT_SCREEN = Gdx.graphics.getHeight();
        this.game = game;
        this.batch = game.getSpriteBatch();

        doorLeft = new Texture(path + "wl.png");
        doorRight = new Texture(path + "wr.png");


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i <= 10; i++)
            frames.add(new TextureRegion(new TextureAtlas(path + "help.pack").findRegion(i + ""),
                    0, 0, 479, 320));
        helpAnim = new Animation<TextureRegion>(0.33f, frames, Animation.PlayMode.LOOP_PINGPONG);
        helpSpr = new Sprite(helpAnim.getKeyFrame(0));
        helpSpr.setBounds(180, 50, 479 * 2, 320 * 2);
        frames.clear();

        Array<Texture> frams = new Array<Texture>();
        for(int i = 1; i <= 2; i++)
            frams.add(new Texture(path + "indicate" + i +".png"));
        indicate = new Animation<Texture>(0.5f, frams, Animation.PlayMode.LOOP_PINGPONG);
        indication = new Sprite(indicate.getKeyFrame(0));
        indication.setBounds(WIDTH_SCREEN / 4.8f,HEIGHT_SCREEN / 7, 419 * 2, 20 * 2);

    }

    /**
     * Tạo sân khấu để render ảnh
     */
    @Override
    public void show()
    {
        FitViewport viewport = new FitViewport(WIDTH_SCREEN, HEIGHT_SCREEN);
        stage = new Stage(viewport, batch);

        stage.addActor(new Image(new Texture(path + "null.png")));

    }

    /**
     * Render objects
     */
    @Override
    public void render(float delta)
    {
        statetime += delta;

        helpSpr.setRegion(helpAnim.getKeyFrame(statetime));
        indication.setRegion(indicate.getKeyFrame(statetime));

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || (timeLeft == -1000 ))
        {
            Music_SoundManager.getInstance().playSound("selectchoice.wav");
            RunnableAction runnableAction = new RunnableAction();
            runnableAction.setRunnable(new Runnable()
            {
                /**
                 * Change Screen
                 */
                @Override
                public void run()
                {
                    game.setScreen(new PlayScreen(game, 1,1.5f,1,1));
                }
            });
            stage.addAction(new SequenceAction(Actions.delay(0.2f), Actions.fadeOut(0.2f), runnableAction));
        }
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if ((timeLeft < -10 && !goout) || count <= 200 || (timeRight > -90 && !goout)) {

            //draw doorleft apear
            if(timeLeft != 0) {
                if(timeLeft >= -100)
                    timeLeft += 20;
                else timeLeft += 10;
                game.batch.draw(doorLeft, timeLeft, 0, doorLeft.getWidth() * scale + 355, doorLeft.getHeight() * scale);
            }

            //draw doorRight apear
            if(timeRight != -100){
                if(timeRight <= 0)
                    timeRight -=25;
                else timeRight -= 10;
                game.batch.draw(doorRight, timeRight, 0, doorRight.getWidth() * scale + 355, doorRight.getHeight() * scale);

            }
            count++;
            if(count == 201)
                goout = true;
            game.batch.draw(doorLeft, timeLeft, 0, doorLeft.getWidth() * scale + 355, doorLeft.getHeight() * scale);
            game.batch.draw(doorRight, timeRight, 0, doorRight.getWidth() * scale + 355, doorRight.getHeight() * scale);
        }
        if  ((timeLeft <= 0 && goout) || (timeRight >= -100 && goout))
        {
            //draw doorLeft go out
            if(timeLeft != -1000)
            {
                timeLeft -= 10;
                game.batch.draw(doorLeft, timeLeft, 0, doorLeft.getWidth() * scale + 355, doorLeft.getHeight() * scale);
            }
            //draw doorRight out
            if(timeRight != 920)
            {
                timeRight += 10;
                game.batch.draw(doorRight, timeRight, 0, doorRight.getWidth() * scale + 355, doorRight.getHeight() * scale);
            }

        }

        helpSpr.draw(batch);
        indication.draw(batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { stage.dispose(); }
}
