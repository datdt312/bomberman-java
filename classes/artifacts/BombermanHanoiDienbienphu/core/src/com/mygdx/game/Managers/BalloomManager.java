/**
 * Generate Balloom
 * @author HNDBP
 * @since 2018/11/23
 */
package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Balloom;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;
import java.util.Iterator;

public class BalloomManager
{
    private ArrayList<Balloom> ballooms;

    /**
     * Constructor
     * @param map  a map
     */
    public BalloomManager(MapCreator map)
    {
        ballooms = new ArrayList<Balloom>();
        ArrayList<Vector2> pos = map.getPositionBallooms();
        for (Vector2 v : pos)
        {
            ballooms.add(new Balloom(map, v.x, v.y));
        }
    }

    /**
     * Update
     * @param map map
     * @param player bomber
     * @param delta time
     */
    public void update(MapCreator map, Boomber player, float delta)
    {
        for (Balloom b : ballooms)
        {
            b.update(map,player, delta);
        }
        deleteDeadBalloom();
    }

    /**
     * Draw
     * @param batch
     */
    public void draw(Batch batch)
    {
        for (Balloom b : ballooms)
        {
            b.draw(batch);
        }
    }

    /**
     * Kill Balloom
     */
    private void deleteDeadBalloom()
    {
        for (int i=ballooms.size()-1; i>=0; i--)
        {
            if (ballooms.get(i).isDone())
            {
                ballooms.remove(i);
            }
        }
    }

    /**
     * getter
     * @return balloom
     */
    public ArrayList<Balloom> getBallooms()
    {
        return ballooms;
    }
}
