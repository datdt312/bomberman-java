package com.mygdx.game.Managers;

import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

public class MapManager
{
    private ArrayList<MapCreator> map;
    public MapManager()
    {
        map = new ArrayList<MapCreator>();
        MapCreator tmp;
        tmp = new MapCreator("core/maps/level1.tmx");
        this.map.add(tmp);
        tmp= new MapCreator("core/maps/map_1/map_1.tmx");
        this.map.add(tmp);
    }

    public MapCreator getMapLevel(int i)
    {
        return this.map.get(i);
    }

}
