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

    private TextureAtlas textureAtlas;
    private TextureAtlas textureAtlas1;

    private float scale = 2f;

    private float stateTime;
    private Sprite apearsprite2;
    private Sprite apearsprite;
    private Animation apear2;
    private Animation apear;

    private final float SCALE = 16f;
    private Stage stage;
    private BitmapFont font;
    private Label fpsLabel;
    private boolean showFPS = false;

    private StringBuilder stringBuilder;


    public BombApearSprite(SpriteBatch batch, float width, float height) {
        this.batch = batch;

        textureAtlas1 = new TextureAtlas("core/assets/beginning.pack");

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        for (int i = 0; i < 9; i++) {
            keyFrames.add(new TextureRegion(textureAtlas1.findRegion("apear_sprite"), i * 24, 0, 24, 32));
        }
        apear2 = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP_PINGPONG);
        apearsprite2 = new Sprite((TextureRegion) apear2.getKeyFrame(0));
        apearsprite2.setBounds(1366 / 1.5f, 768 / 2.8f, 24f * scale, 32f * scale);
        stateTime = 0;
        keyFrames.clear();
        for (int i = 0; i < 9; i++) {
            keyFrames.add(new TextureRegion(textureAtlas1.findRegion("apear_sprite"), i * 24, 0, 24, 32));
        }
        apear = new Animation(0.1f, keyFrames, Animation.PlayMode.LOOP_PINGPONG);
        apearsprite = new Sprite((TextureRegion) apear.getKeyFrame(0));
        apearsprite.setBounds(1366 / 3.4f, 768 / 2.8f, 24 * scale, 32 * scale);
        keyFrames.clear();

        FitViewport viewport = new FitViewport(width * SCALE, height * SCALE);
        stage = new Stage(viewport, batch);
        font = new BitmapFont(Gdx.files.internal("core/font/foo.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        fpsLabel = new Label("FPS:", labelStyle);
        fpsLabel.setFontScale(0.3f);
        fpsLabel.setPosition(16 * SCALE, -0.8f * SCALE);
        fpsLabel.setVisible(showFPS);

        stage.addActor(fpsLabel);

        stringBuilder = new StringBuilder();

    }


    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            showFPS = !showFPS;
            fpsLabel.setVisible(showFPS);
        }
    }

    public void draw(float delta) {
        handleInput();

        stateTime += delta;
        apearsprite2.setRegion((TextureRegion) apear2.getKeyFrame(stateTime));
        apearsprite.setRegion((TextureRegion) apear.getKeyFrame(stateTime));

        batch.begin();

        apearsprite2.draw(batch);

        apearsprite.draw(batch);
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER))
        {
            apearsprite.setPosition(2000,800);
            apearsprite2.setPosition(2000,800);
        }

        batch.end();


        if (showFPS) {
            stringBuilder.setLength(1);
            stringBuilder.append("FPS:").append(Gdx.graphics.getFramesPerSecond());
            fpsLabel.setText(stringBuilder.toString());
        }
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
    }

}