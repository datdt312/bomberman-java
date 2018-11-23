package com.mygdx.game.Screens.SpriteMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BombApearSprite implements Disposable
{
    private final SpriteBatch batch;

    private TextureAtlas textureAtlas1;

    private float scale = 2f;

    private float stateTime;
    private Sprite apearsprite2;
    private Sprite apearsprite;
    private Animation<TextureRegion> apear2;
    private Animation<TextureRegion> apear;

    private final float SCALE = 16f;
    private Stage stage;


    private StringBuilder stringBuilder;


    public BombApearSprite(SpriteBatch batch, float width, float height) {
        this.batch = batch;

        textureAtlas1 = new TextureAtlas("core/assets/beginning.pack");

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        for (int i = 0; i < 9; i++) {
            keyFrames.add(new TextureRegion(textureAtlas1.findRegion("apear_sprite"), i * 24, 0, 24, 32));
        }
        apear2 = new Animation<TextureRegion>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        apearsprite2 = new Sprite(apear2.getKeyFrame(0));
        apearsprite2.setBounds(1366 / 1.5f, 768 / 2.8f, 24f * scale, 32f * scale);
        stateTime = 0;
        keyFrames.clear();
        for (int i = 0; i < 9; i++) {
            keyFrames.add(new TextureRegion(textureAtlas1.findRegion("apear_sprite"), i * 24, 0, 24, 32));
        }
        apear = new Animation<TextureRegion>(0.1f, keyFrames, Animation.PlayMode.LOOP);
        apearsprite = new Sprite(apear.getKeyFrame(0));
        apearsprite.setBounds(1366 / 3.4f, 768 / 2.8f, 24 * scale, 32 * scale);
        keyFrames.clear();

        FitViewport viewport = new FitViewport(width * SCALE, height * SCALE);
        stage = new Stage(viewport, batch);

        stringBuilder = new StringBuilder();

    }



    public void draw(float delta) {

        stateTime += delta;
        apearsprite2.setRegion(apear2.getKeyFrame(stateTime));
        apearsprite.setRegion(apear.getKeyFrame(stateTime));

        batch.begin();

        apearsprite2.draw(batch);

        apearsprite.draw(batch);
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            apearsprite.setPosition(2000,800);
            apearsprite2.setPosition(2000,800);
        }

        batch.end();

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}