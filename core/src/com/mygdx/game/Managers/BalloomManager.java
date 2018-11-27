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

    public BalloomManager(MapCreator map)
    {
        ballooms = new ArrayList<Balloom>();
        ArrayList<Vector2> pos = map.getPositionBallooms();
        for (Vector2 v : pos)
        {
            ballooms.add(new Balloom(map, v.x, v.y));
        }
    }

    public void update(MapCreator map, Boomber player, float delta)
    {
        for (Balloom b : ballooms)
        {
            b.update(map,player, delta);
        }
        deleteDeadBalloom();
    }

    public void draw(Batch batch)
    {
        for (Balloom b : ballooms)
        {
            b.draw(batch);
        }
    }

    private void deleteDeadBalloom()
    {
        Iterator<Balloom> iter = ballooms.iterator();

        while (iter.hasNext())
        {
            Balloom b = iter.next();

            if (b.isDone())
                iter.remove();
        }
    }

    public ArrayList<Balloom> getBallooms()
    {
        return ballooms;
    }
}
