package com.mygdx.game.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Define A Map
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
public class MapCreator
{
    private static final float UNIT_SCALE = 2f;
    private TiledMap map;

    private OrthographicCamera mapCam;
    private OrthogonalTiledMapRenderer renderer;

    private int width_number;
    private int height_number;
    private int tileWidth;
    private int tileHeight;

    private Vector2 posPlayer;

    private ArrayList<Rectangle> walls;
    private ArrayList<Rectangle> bricks;


    /**
     * Constructor
     * @param path link to file map
     */
    public MapCreator(String path)
    {
        map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);

        width_number = map.getProperties().get("width", Integer.class);
        height_number = map.getProperties().get("height", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);
        createMapRectangle();

        MapLayer tmp = map.getLayers().get("Player");
        MapObject objectPlayer = tmp.getObjects().get("Player");
        float x = Float.parseFloat(objectPlayer.getProperties().get("x").toString());
        float y = Float.parseFloat(objectPlayer.getProperties().get("y").toString());
        posPlayer = new Vector2(x*UNIT_SCALE, y*UNIT_SCALE);


        mapCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mapCam.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        mapCam.update();
        mapCam.lookAt(0, 0, 0);
        renderer.setView(mapCam);
    }

    /**
     * Create Bound Of Bricks, Walls
     */
    private void createMapRectangle()
    {
        walls = new ArrayList<Rectangle>();
        bricks = new ArrayList<Rectangle>();
        TiledMapTileLayer layers;

        // create Walls bodies/fixtures
        layers = (TiledMapTileLayer) map.getLayers().get("Walls");
        for (int i = 0; i < layers.getWidth(); i++)
        {
            for (int j = 0; j < layers.getHeight(); j++)
            {
                TiledMapTileLayer.Cell cell = layers.getCell(i, j);
                if (cell == null || cell.getTile() == null)
                    continue;
                float x = i * tileWidth * UNIT_SCALE;
                float y = j * tileHeight * UNIT_SCALE;
                float width = tileWidth * UNIT_SCALE;
                float height = tileHeight * UNIT_SCALE;
                Rectangle rect = new Rectangle(x, y, width, height);
                walls.add(rect);
            }
        }

        // create Bricks bodies/fixtures
        layers = (TiledMapTileLayer) map.getLayers().get("Bricks");
        for (int i = 0; i < layers.getWidth(); i++)
        {
            for (int j = 0; j < layers.getHeight(); j++)
            {
                TiledMapTileLayer.Cell cell = layers.getCell(i, j);
                if (cell == null || cell.getTile() == null)
                    continue;
                float x = i * tileWidth * UNIT_SCALE;
                float y = j * tileHeight * UNIT_SCALE;
                float width = tileWidth * UNIT_SCALE;
                float height = tileHeight * UNIT_SCALE;
                Rectangle rect = new Rectangle(x, y, width, height);
                bricks.add(rect);
            }
        }
    }

    /**
     * Render Map
     */
    public void render()
    {
        renderer.render();
    }

    /**
     * Dispose Map
     */
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
    }

    /**
     * Get Width Size Of Map
     * @return Width Size Of Map
     */
    public int getV_WIDTH()
    {
        return (int) (width_number * tileWidth * UNIT_SCALE);
    }

    /**
     * Get Height Size Of Map
     * @return Height Size Of Map
     */
    public int getV_HEIGHT()
    {
        return (int) (height_number * tileHeight * UNIT_SCALE);
    }

    /**
     * Get Renderer Of Map
     * @return Renderer Of Map
     */
    public OrthogonalTiledMapRenderer getRenderer()
    {
        return renderer;
    }

    /**
     * Get Map
     * @return Map
     */
    public TiledMap getMap()
    {
        return map;
    }

    /**
     * Get Bounded Walls
     * @return Bounded Walls
     */
    public ArrayList<Rectangle> getWalls()
    {
        return walls;
    }

    /**
     * Get Bounded Bricks
     * @return Bounded Bricks
     */
    public ArrayList<Rectangle> getBricks()
    {
        return bricks;
    }

    /**
     * Get Scale Of Map
     * @return Number Scale Of Map
     */
    public float getUNIT_SCALE()
    {
        return UNIT_SCALE;
    }

    /**
     * Get Number Of Tile In Width
     * @return Number Of Tile In Width
     */
    public int getWidth_number()
    {
        return width_number;
    }

    /**
     * Get Number Of Tile In Height
     * @return Number Of Tile In Height
     */
    public int getHeight_number()
    {
        return height_number;
    }

    /**
     * Get Width Of A Tile
     * @return Width Of A Tile
     */
    public int getTileWidth()
    {
        return tileWidth;
    }

    /**
     * Get Height Of A Tile
     * @return Height Of A Tile
     */
    public int getTileHeight()
    {
        return tileHeight;
    }

    /**
     * Get Spawn Position Of Player
     * @return Spawn Position Of Player
     */
    public Vector2 getPosPlayer()
    {
        return posPlayer;
    }
}
