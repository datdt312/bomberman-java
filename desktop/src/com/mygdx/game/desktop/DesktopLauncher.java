package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BomberManGame;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.title = "HaNoiDienBienPhu";
        config.width = 1366;
        config.height = 768;
        //config.addIcon();
        //config.fullscreen = true;
        new LwjglApplication(new BomberManGame(), config);
    }
}
