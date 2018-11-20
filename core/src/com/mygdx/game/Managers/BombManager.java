package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Components.Bomb;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

/**
 * Manager Of Bombs
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
public class BombManager
{
    private ArrayList<Bomb> bomb_manage;
    private int maxBombs;

    /**
     * Constructor
     * @param player Player of the game
     */
    public BombManager(Boomber player)
    {
        bomb_manage = new ArrayList<Bomb>();
        maxBombs = player.getMaxBombs();
    }

    /**
     * Update Bombs
     * @param map map of the game
     * @param player Player of the game
     * @param dt deltaTime
     */
    public void update(MapCreator map, Boomber player, float dt)
    {
        for (Bomb b : bomb_manage)
        {
            b.update(dt);
        }
        deleteExplodedBomb();
    }

    /**
     * Create A New Bomb When Player Plan
     *
     * @param map    map of the game
     * @param player Player of the game
     * @return true if can create a new bomb at player's position; false if not
     */
    public boolean addNewBomb(MapCreator map, Boomber player)
    {
        maxBombs = player.getMaxBombs();

        Bomb b = new Bomb(map, player);
        boolean existed = false;
        for (Bomb bomb : bomb_manage)
        {
            if (bomb.compareTo(b) == 0)
                existed = true;
        }
        if (! existed && bomb_manage.size() < maxBombs)
        {
            bomb_manage.add(b);
            return true;
        }
        return false;
    }

    /**
     * Delete Bombs Have Exploded
     */
    public void deleteExplodedBomb()
    {
        for (int i = bomb_manage.size() - 1; i >= 0; i--)
        {
            if (bomb_manage.get(i).isDone())
                bomb_manage.remove(i);
        }
    }

    /**
     * Draw Bombs
     *
     * @param batch . . .
     * @param map   map of the game
     */
    public void draw(Batch batch, MapCreator map)
    {
        for (Bomb b : bomb_manage)
        {
            b.draw(batch, map);
        }
    }
}
