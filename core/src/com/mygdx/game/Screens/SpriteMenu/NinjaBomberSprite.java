/**
 * Create Ninjabomber in menu
 * @author hndbp
 * @since 2018/11/20
 */
package com.mygdx.game.Screens.SpriteMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class NinjaBomberSprite implements Disposable
{
    private final SpriteBatch batch;

    private TextureAtlas textureAtlas1;

    private float scale = 2f;

    private float stateTime;
    private Sprite ninjabombsprite2;
    private Sprite ninjabombsprite;
    private Animation<TextureRegion> ninja2;
    private Animation<TextureRegion> ninja;

    private final float SCALE = 16f;
    private Stage stage;


    private StringBuilder stringBuilder;

    /**
     * Constructor
     * @param batch draw through Screen class
     */
    public NinjaBomberSprite(SpriteBatch batch) {
        this.batch = batch;

        stateTime = 0;

        textureAtlas1 = new TextureAtlas("core/assets/beginning.pack");

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        for (int i = 0; i < 9; i++) {
            keyFrames.add(new TextureRegion(textureAtlas1.findRegion("apear_sprite"), i * 24, 0, 24, 32));
        }
        ninja2 = new Animation<TextureRegion>(0.2f, keyFrames, Animation.PlayMode.LOOP);
        ninjabombsprite2 = new Sprite(ninja2.getKeyFrame(0));
        ninjabombsprite2.setBounds(Gdx.graphics.getWidth() / 1.5f, Gdx.graphics.getHeight() / 2.8f, 24f * scale, 32f * scale);
        keyFrames.clear();

        for (int i = 0; i < 9; i++) {
            keyFrames.add(new TextureRegion(textureAtlas1.findRegion("apear_sprite"), i * 24, 0, 24, 32));
        }
        ninja = new Animation<TextureRegion>(0.2f, keyFrames, Animation.PlayMode.LOOP);
        ninjabombsprite = new Sprite(ninja.getKeyFrame(0));
        ninjabombsprite.setBounds(Gdx.graphics.getWidth() / 3.4f, Gdx.graphics.getHeight() / 2.8f, 24 * scale, 32 * scale);
        keyFrames.clear();

        FitViewport viewport = new FitViewport(0, 0);
        stage = new Stage(viewport, batch);

    }

    /**
     * Draw ninjas
     * @param delta time
     */
    public void draw(float delta) {

        stateTime += delta;
        ninjabombsprite2.setRegion(ninja2.getKeyFrame(stateTime));
        ninjabombsprite.setRegion(ninja.getKeyFrame(stateTime));

        batch.begin();

        ninjabombsprite2.draw(batch);

        ninjabombsprite.draw(batch);
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            ninjabombsprite.setPosition(2000,800);
            ninjabombsprite2.setPosition(2000,800);
        }

        batch.end();

        stage.draw();
    }

    /**
     * Close a stage
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

}