package com.mygdx.game.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Iterator;

/**
 * Define A Map
 *
 * @author HaNoiDienBienPhu
 * @version 03.12.30.06
 * @since 2018-11-21
 */
public class MapCreator
{
    class DestroyedBrick
    {
        public DestroyedBrick(Rectangle rect)
        {
            this.rect = rect;
            elapsedTime = 0;
            countTime = 3;
            done = false;
        }
        public Rectangle rect;
        public float elapsedTime;
        public int countTime;
        public boolean done;
    }

    private static final float UNIT_SCALE = 3f;
    private TiledMap map;

    private Texture texture;
    private TextureRegion[] animation;
    private int animationLength;

    private OrthographicCamera mapCam;
    private OrthogonalTiledMapRenderer renderer;

    private int width_number;
    private int height_number;
    private int tileWidth;
    private int tileHeight;

    private Vector2 posPlayer;

    private ArrayList<MapObject> obj_ballooms;
    private ArrayList<MapObject> obj_items;

    private ArrayList<Rectangle> walls;
    private ArrayList<Rectangle> bricks;
    private ArrayList<DestroyedBrick> destroyed;


    /**
     * Constructor
     *
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
        MapObject objectPlayer = tmp.getObjects().get(0);
        float x = Float.parseFloat(objectPlayer.getProperties().get("x").toString());
        float y = Float.parseFloat(objectPlayer.getProperties().get("y").toString());
        posPlayer = new Vector2(x * UNIT_SCALE, y * UNIT_SCALE);

        createBallooms();
        createItems();
        setupCamera();
        setupAnimationDestroyedBricks();
    }

    private void setupAnimationDestroyedBricks()
    {
        texture = new Texture("core/assets/Block_Brick.png");
        TextureRegion[][] regions = TextureRegion.split(texture, 16, 16);
        animationLength = regions[0].length;
        animation = new TextureRegion[animationLength];
        for (int i=0; i<animationLength; i++)
            animation[i] = regions[0][i];

    }

    private void setupCamera()
    {
        mapCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mapCam.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        mapCam.update();
        renderer.setView(mapCam);
    }

    private void createBallooms()
    {
        obj_ballooms = new ArrayList<MapObject>();
        MapLayer tmp = map.getLayers().get("Ballooms");
        MapObjects objects = tmp.getObjects();
        for (MapObject mo : objects)
        {
            obj_ballooms.add(mo);
        }
    }

    private void createItems()
    {
        obj_items = new ArrayList<MapObject>();
        MapLayer tmp = map.getLayers().get("Items");
        MapObjects objects = tmp.getObjects();
        for (MapObject mo:objects)
        {
            obj_items.add(mo);
            System.out.println(mo.getName());
        }
    }

    /**
     * Create Bound Of Bricks, Walls
     */
    private void createMapRectangle()
    {
        walls = new ArrayList<Rectangle>();
        bricks = new ArrayList<Rectangle>();
        destroyed = new ArrayList<DestroyedBrick>();
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

    public void destroyBrick(Rectangle r, int countTime)
    {
        if (countTime == 0)
        {
            TiledMapTileLayer temp = (TiledMapTileLayer) map.getLayers().get("Bricks");
            temp.getCell((int) (r.getX() / tileWidth / UNIT_SCALE), (int) (r.getY() / tileHeight / UNIT_SCALE)).setTile(null);
            destroyed.add(new DestroyedBrick(r));
        }
    }

    private void deleteRectangleBrick(Rectangle r)
    {
        for (int i=bricks.size()-1; i>=0; i--)
        {
            if (bricks.get(i).equals(r))
            {
                bricks.remove(i);
            }
        }
    }

    public void update(float dt)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0))
        {
            for (Rectangle r : getBricks())
            {
                destroyBrick(r, 0);
            }
        }

        for (DestroyedBrick db:destroyed)
        {
            if (!db.done)
            db.elapsedTime+=10*dt;
            if (db.elapsedTime>1)
            {
                db.elapsedTime = 0;
                db.countTime++;
            }
        }
        deleteDestroyedBricks();
    }
    public void draw(Batch batch)
    {
        batch.begin();
        for (DestroyedBrick db : destroyed)
        {
            if (!db.done && db.countTime<animationLength)
                batch.draw(animation[db.countTime], db.rect.getX(), db.rect.getY(), db.rect.getWidth(), db.rect.getHeight());
            else
            {
                db.done = true;
            }
        }
        batch.end();
    }

    public void deleteDestroyedBricks()
    {
        for (int i = destroyed.size()-1; i>=0; i--)
        {
            if (destroyed.get(i).done)
            {
                deleteRectangleBrick(destroyed.get(i).rect);
                destroyed.remove(i);
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
     *
     * @return Width Size Of Map
     */
    public int getV_WIDTH()
    {
        return (int) (width_number * tileWidth * UNIT_SCALE);
    }

    /**
     * Get Height Size Of Map
     *
     * @return Height Size Of Map
     */
    public int getV_HEIGHT()
    {
        return (int) (height_number * tileHeight * UNIT_SCALE);
    }

    /**
     * Get Renderer Of Map
     *
     * @return Renderer Of Map
     */
    public OrthogonalTiledMapRenderer getRenderer()
    {
        return renderer;
    }

    /**
     * Get Map
     *
     * @return Map
     */
    public TiledMap getMap()
    {
        return map;
    }

    /**
     * Get Bounded Walls
     *
     * @return Bounded Walls
     */
    public ArrayList<Rectangle> getWalls()
    {
        return walls;
    }

    /**
     * Get Bounded Bricks
     *
     * @return Bounded Bricks
     */
    public ArrayList<Rectangle> getBricks()
    {
        return bricks;
    }

    /**
     * Get Scale Of Map
     *
     * @return Number Scale Of Map
     */
    public float getUNIT_SCALE()
    {
        return UNIT_SCALE;
    }

    /**
     * Get Number Of Tile In Width
     *
     * @return Number Of Tile In Width
     */
    public int getWidth_number()
    {
        return width_number;
    }

    /**
     * Get Number Of Tile In Height
     *
     * @return Number Of Tile In Height
     */
    public int getHeight_number()
    {
        return height_number;
    }

    /**
     * Get Width Of A Tile
     *
     * @return Width Of A Tile
     */
    public int getTileWidth()
    {
        return tileWidth;
    }

    /**
     * Get Height Of A Tile
     *
     * @return Height Of A Tile
     */
    public int getTileHeight()
    {
        return tileHeight;
    }

    /**
     * Get Spawn Position Of Player
     *
     * @return Spawn Position Of Player
     */
    public Vector2 getPosPlayer()
    {
        return posPlayer;
    }

    public ArrayList<Vector2> getPositionBallooms()
    {
        ArrayList<Vector2> res = new ArrayList<Vector2>();
        for (MapObject o : obj_ballooms)
        {
            float x = Float.parseFloat(o.getProperties().get("x").toString());
            float y = Float.parseFloat(o.getProperties().get("y").toString());
            res.add(new Vector2(x * UNIT_SCALE, y * UNIT_SCALE));
        }
        return res;
    }

    public ArrayList<MapObject> getObj_items()
    {
        return obj_items;
    }
}
