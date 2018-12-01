/**
 * Run the application
 * @author HNDBP
 * @since 2018/11/14
 */
package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BomberManGame;

public class DesktopLauncher
{
    /**
     * Main function
     * @param arg no uses
     */
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.title = "HaNoiDienBienPhu";
        config.width = 1488;
        //config.width = 1366;
        config.height = 768;
        config.addIcon("core/assets/bomb.png", Files.FileType.Internal);
        config.x = config.y = -1;
        new LwjglApplication(new BomberManGame(), config);
    }
}
