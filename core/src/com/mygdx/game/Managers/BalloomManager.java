package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Balloom;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

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

    public void update(MapCreator map, float delta)
    {
        for (Balloom b:ballooms)
        {
            b.update(map,delta);
        }
    }

    public void draw(Batch batch)
    {
        for (Balloom b:ballooms)
        {
            b.draw(batch);
        }
    }


}
