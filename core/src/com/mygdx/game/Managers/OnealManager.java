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

    public OnealManager(MapCreator map)
    {
        oneals = new ArrayList<Oneal>();
        for (Vector2 v : map.getPositionOneals())
        {
            oneals.add(new Oneal(map, v.x, v.y));
        }
    }

    public void update(MapCreator map, Boomber player, float dt)
    {
        for (Oneal o:oneals)
        {
            o.update(map, player, dt);
        }
        deleteDeadOneal();
    }

    public void draw(Batch batch)
    {
        for (Oneal o:oneals)
        {
            o.draw(batch);
        }
    }

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

    public ArrayList<Oneal> getOneals()
    {
        return oneals;
    }
}
