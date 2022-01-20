package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.GameScreen;

import java.awt.*;

import static helper.Constants.PPM;

public class TileMapHelper {

    private TiledMap tiledMap;
    // access to world objs
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen){
        this.gameScreen = gameScreen;

    }

    public OrthogonalTiledMapRenderer setupMap(){
        // game.desktop.src
        // com.mygdx.game.desktop.DesktopLauncher.main
        // load our map
        //.core/main/resources/maps/tiles.tmx
        //tiledMap = new TmxMapLoader().load("main/resources/maps/tiles.tmx");
        //tiledMap = new TmxMapLoader().load("../maps/tiles.tmx");
        tiledMap = new TmxMapLoader().load("D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\resources\\maps\\tiles.tmx");

        // access the map Objects
        parseMapObjects(tiledMap.getLayers().get("Objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects){
        // iterate through objs
        for(MapObject mapObject : mapObjects){
            // if the objects == rectangle shapes
            if(mapObject instanceof PolygonMapObject){
                // create new body for this obj
                createStaticBody((PolygonMapObject) mapObject);

            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject){
        // define body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        // now we need a shape

        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture((com.badlogic.gdx.physics.box2d.Shape) shape, 1000);
        ((com.badlogic.gdx.physics.box2d.Shape) shape).dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        // store the vertices of the rectangle
        // get x, y, width and height and store it in a float[]
        /*
        float x = rectangleMapObject.getRectangle().getX();
        float y = rectangleMapObject.getRectangle().getY();
        float width = rectangleMapObject.getRectangle().getWidth();
        float height = rectangleMapObject.getRectangle().getHeight();
        float[] vertices = {x, y, width, height};*/

        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        // iterate over the half of this area
        for(int i = 0; i< vertices.length/2; i++){
            Vector2 current = new Vector2(vertices[i*2]/PPM, vertices[i*2 +1] / PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return (Shape) shape;
    }

}
