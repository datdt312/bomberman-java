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

    public EnemyManager(MapCreator map)
    {
        balloomManager = new BalloomManager(map);
        onealManager = new OnealManager(map);
    }

    public void update(MapCreator map, Boomber player, float dt)
    {
        balloomManager.update(map,player, dt);
        onealManager.update(map,player, dt);
    }

    public void draw(Batch batch)
    {
        balloomManager.draw(batch);
        onealManager.draw(batch);
    }

    public ArrayList<Balloom> getBallooms()
    {
        return balloomManager.getBallooms();
    }

    public ArrayList<Oneal> getOneals()
    {
        return onealManager.getOneals();
    }
}
