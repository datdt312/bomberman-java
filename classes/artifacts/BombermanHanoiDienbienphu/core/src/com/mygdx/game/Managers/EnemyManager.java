/**
 * Manage oneal, balloom
 * @author HNDBP
 * @since 2018/11/23
 */
package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Components.Balloom;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Components.Oneal;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

public class EnemyManager
{
    BalloomManager balloomManager;
    OnealManager onealManager;

    /**
     * Constructor
     * @param map a map
     */
    public EnemyManager(MapCreator map)
    {
        balloomManager = new BalloomManager(map);
        onealManager = new OnealManager(map);
    }

    /**
     * Update
     * @param map a map
     * @param player bomber
     * @param dt time
     */
    public void update(MapCreator map, Boomber player, float dt)
    {
        balloomManager.update(map,player, dt);
        onealManager.update(map,player, dt);
    }

    /**
     * Draw enemy
     * @param batch
     */
    public void draw(Batch batch)
    {
        balloomManager.draw(batch);
        onealManager.draw(batch);
    }

    /**
     * getter
     * @return balloom
     */
    public ArrayList<Balloom> getBallooms()
    {
        return balloomManager.getBallooms();
    }

    /**
     * getter
     * @return oneal
     */
    public ArrayList<Oneal> getOneals()
    {
        return onealManager.getOneals();
    }
}
