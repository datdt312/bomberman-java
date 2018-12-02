package com.mygdx.game.Managers;

import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

/**
 * Manager Of Maps
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
public class MapManager
{
    private ArrayList<MapCreator> map;

    /**
     * Constructor
     */
    public MapManager()
    {
        map = new ArrayList<MapCreator>();
        MapCreator tmp;
        //tmp = new MapCreator("core/maps/map_0/map_0.tmx");
        //this.map.add(tmp);
        tmp= new MapCreator("core/maps/map_1/map_1.tmx");
        this.map.add(tmp);
        tmp= new MapCreator("core/maps/map_2/map_2.tmx");
        this.map.add(tmp);
        tmp= new MapCreator("core/maps/map_3/map_3.tmx");
        this.map.add(tmp);
        tmp= new MapCreator("core/maps/map_4/map_4.tmx");
        this.map.add(tmp);
        //tmp= new MapCreator("core/maps/map_5/map_5.tmx");
        //this.map.add(tmp);

    }

    /**
     * Get Map By Index
     * @param i Index
     * @return Map On Index
     */
    public MapCreator getMapLevel(int i)
    {
        return this.map.get(i);
    }

}
