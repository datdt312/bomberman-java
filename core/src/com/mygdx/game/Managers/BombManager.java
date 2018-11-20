package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Components.Bomb;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

public class BombManager
{
    private ArrayList<Bomb> bomb_manage;
    private int maxBombs;

    public BombManager(Boomber player)
    {
        bomb_manage = new ArrayList<Bomb>();
        maxBombs = player.getMaxBombs();
    }

    public void update(MapCreator map, Boomber player, float dt)
    {
        for (Bomb b : bomb_manage)
        {
            b.update(dt);
        }
        deleteExplodedBomb();
    }

    public void addNewBomb(MapCreator map, Boomber player)
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
            bomb_manage.add(b);
    }

    public void deleteExplodedBomb()
    {
        for (int i = bomb_manage.size() - 1; i >= 0; i--)
        {
            if (bomb_manage.get(i).isDone())
                bomb_manage.remove(i);
        }
    }

    public void draw(Batch batch)
    {
        for (Bomb b : bomb_manage)
        {
            b.draw(batch);
        }
    }
}
