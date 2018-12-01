/**
 * Oneal Enemy
 * @author HNDBP
 * @since 2018/11/28
 */
package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Components.Oneal;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

public class OnealManager
{
    private ArrayList<Oneal> oneals;

    /**
     * Constructor
     * @param map a map
     */
    public OnealManager(MapCreator map)
    {
        oneals = new ArrayList<Oneal>();
        for (Vector2 v : map.getPositionOneals())
        {
            oneals.add(new Oneal(map, v.x, v.y));
        }
    }

    /**
     * Update map, player move with time increasing
     * @param map a map
     * @param player bomber
     * @param dt time
     */
    public void update(MapCreator map, Boomber player, float dt)
    {
        for (Oneal o:oneals)
        {
            o.update(map, player, dt);
        }
        deleteDeadOneal();
    }

    /**
     * Draw game
     * @param batch draw objects
     */
    public void draw(Batch batch)
    {
        for (Oneal o:oneals)
        {
            o.draw(batch);
        }
    }

    /**
     * Delete Oneal
     */
    private void deleteDeadOneal()
    {
        for (int i = oneals.size()-1; i>=0; i--)
        {
            if (oneals.get(i).isDone())
            {
                oneals.remove(i);

            }
        }
    }

    /**
     * getter
     * @return list of oneals
     */
    public ArrayList<Oneal> getOneals()
    {
        return oneals;
    }
}
