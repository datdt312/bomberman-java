package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class MapCreator
{
    private final float UNIT_SCALE = 2f;
    private TiledMap map;

    private OrthogonalTiledMapRenderer renderer;
    private int width_number;
    private int height_number;
    private int tileWidth;
    private int tileHeight;

    private ArrayList<Rectangle> border;
    private ArrayList<Rectangle> walls;
    private ArrayList<Rectangle> bricks;


    public MapCreator(String path)
    {
        map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
        width_number = map.getProperties().get("width", Integer.class);
        height_number = map.getProperties().get("height", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);
        createMapRectangle();
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

    private void createMapRectangle()
    {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        border = new ArrayList<Rectangle>();
        walls = new ArrayList<Rectangle>();
        bricks = new ArrayList<Rectangle>();

        // create Border bodies/fixtures
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Rectangle real_rect = rect.setSize(2f);
            System.out.print("RECT: ");
            System.out.println(rect.width + " " +rect.height);
            System.out.print("REAL_RECT: ");
            System.out.println(real_rect.width + " " +real_rect.height);
            border.add(rect);
        }

        // create Walls bodies/fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            walls.add(rect);
        }

        // create Bricks bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bricks.add(rect);

        }
    }
}
