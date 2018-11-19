package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Components.Bomb;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

public class BombManager
{
    private ArrayList<Bomb> bomb_manage;

    public BombManager()
    {
        bomb_manage = new ArrayList<Bomb>();
    }

    public void update(float dt)
    {
        for (Bomb b : bomb_manage)
        {
            b.update(dt);
        }
        deleteExplodedBomb();
    }

    public void addNewBomb(MapCreator map, Boomber player)
    {
        Bomb b = new Bomb(map, player);
        bomb_manage.add(b);
    }

    public void deleteExplodedBomb()
    {
        for (Bomb b : bomb_manage)
        {
            if (b.isDone())
            {
                bomb_manage.remove(b);
            }
        }
    }

    public void draw(Batch batch, float dt)
    {
        for (Bomb b : bomb_manage)
        {
            b.draw(batch, dt);
        }
    }
}
