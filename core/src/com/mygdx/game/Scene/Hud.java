package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Hud implements Disposable
{
    private final SpriteBatch batch;
    private TextureAtlas textureAtlas;

    private final float SCALE = 55f;
    private Stage stage;
    private BitmapFont font;
    private Label fpsLabel;

    private boolean timeUp; // true if timecount == 0
    private Integer liveCount;
    private Integer timeCount;
    private float timecountdown;
    private Label livecountLabel;
    private Label timecountLabel;
    private Label liveLabel;
    private Label timeLabel;
    private Image liveimg;
    private Image timeimg;

    private boolean showFPS = false;

    private StringBuilder stringBuilder;

    public Hud(SpriteBatch batch, float width, float height) {
        this.batch = batch;

        textureAtlas = new TextureAtlas("core/sprites/beginning.pack");

        //Hai Label
        timeCount = 200;
        timecountdown = 0;
        liveCount = 3;

        FitViewport viewport = new FitViewport(1366, 768, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        font = new BitmapFont(Gdx.files.internal("core/font/foo.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        livecountLabel = new Label("X" + String.format("%02d", liveCount), labelStyle);
        livecountLabel.setFontScale(1f);
        livecountLabel.setPosition(3f * SCALE, 12.4f * SCALE);

        timecountLabel = new Label("X" + String.format("%03d", timeCount), labelStyle);
        timecountLabel.setFontScale(1f);
        timecountLabel.setPosition(17f * SCALE, 12.4f * SCALE);

        liveLabel = new Label("LIVES", labelStyle);
        liveLabel.setFontScale(1.2f);
        liveLabel.setPosition(2.2f * SCALE, 13.2f * SCALE);

        timeLabel = new Label("TIME", labelStyle);
        timeLabel.setFontScale(1.2f);
        timeLabel.setPosition(16.3f * SCALE, 13.2f * SCALE);

        liveimg = new Image(new Texture("core/img/live.png"));
        liveimg.setSize(32,30);
        liveimg.setPosition(2.3f * SCALE, 12.5f * SCALE);

        timeimg = new Image(new Texture("core/img/time.png"));
        timeimg.setSize(30,30);
        timeimg.setPosition(16.3f * SCALE, 12.5f * SCALE);

        fpsLabel = new Label("FPS:", labelStyle);
        fpsLabel.setFontScale(0.3f);
        fpsLabel.setPosition(16 * SCALE, -0.8f * SCALE);
        fpsLabel.setVisible(showFPS);

        stage.addActor(liveimg);
        stage.addActor(timeimg);
        stage.addActor(liveLabel);
        stage.addActor(timeLabel);
        stage.addActor(livecountLabel);
        stage.addActor(timecountLabel);
        stage.addActor(fpsLabel);

        stringBuilder = new StringBuilder();

    }

    public void update(float dt){
        timecountdown += dt;
        if(timecountdown >= 1){
            if (timeCount > 0) {
                timeCount--;
            } else {
                timeUp = true;
            }
            timecountLabel.setText("X" + String.format("%03d", timeCount));
            timecountdown = 0;
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            showFPS = !showFPS;
            fpsLabel.setVisible(showFPS);
        }
    }

    public void draw(float delta) {
        handleInput();

        batch.begin();
        batch.end();

        if (showFPS) {
            stringBuilder.setLength(0);
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
    public boolean isTimeUp() { return timeUp; }

}
