package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapCreator
{
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public MapCreator(String path)
    {
        map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map, 2f);
    }

    public OrthogonalTiledMapRenderer getRenderer()
    {
        return renderer;
    }

    public void setRenderer(OrthogonalTiledMapRenderer renderer)
    {
        this.renderer = renderer;
    }

    public TiledMap getMap()
    {
        return map;
    }

    public void setMap(TiledMap map)
    {
        this.map = map;
    }
}
