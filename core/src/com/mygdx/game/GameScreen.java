package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;
import model.Player;

import static helper.Constants.PPM;

// the GameScreen will load our level - displaying it
// first we need to load it
// tell how to draw itself on the screen
// point the camera
public class GameScreen extends ScreenAdapter {
    // SpriteBatch to render the sprites
    // to render the tiledmap
    //private SpriteBatch batch;

    // world obj to start the 2d boxes
    private World world;
    // see the bodies textures of the boxes
    private Box2DDebugRenderer box2DDebugRenderer;

    // displaying in a 2d plane
    private OrthogonalTiledMapRenderer renderer;
    // tilemap = load our map. take it and draw it to the 2d plane
    private TileMapHelper tileMapHelper;
    private OrthographicCamera camera;

    // draw our player sprites on the screen
    public Batch spriteBatch;
    public Player player;

    float tilesWidth = 14f;
    float tilesHeight = 14f;


    public GameScreen(OrthographicCamera camera){
        this.camera = camera;

        // passing vector2d with x and y, later add gravity in the y axis
        this.world = new World(new Vector2(0, 0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this); // see later the load function

        // call our img
        // Instantiation of the render for the map object
        this.renderer = tileMapHelper.setupMap();
        //this.renderer = new OrthogonalTiledMapRenderer(tileMapHelper.tiledMap, 1/32f);


        //this.spriteBatch = new SpriteBatch();

        spriteBatch = renderer.getBatch();
        player = new Player();
    }

    private void update(){
        // creating a step for our world
        //60 fps
        world.step(1/60f, 6, 2);

        // implement camera update. camera position needs to be updated
        cameraUpdate();

        spriteBatch.setProjectionMatrix(camera.combined);

        // update our img
        // setting the camera to be the view of the renderer
        // the renderer will only displays what the camera sees
        renderer.setView(camera);

        // close the game when pressed Esc button
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        // depending on the user input, move the camera position
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            camera.position.x -= 3; // move the camera back
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            camera.position.x += 3; // move the camera forward
        }


    }

    private void cameraUpdate(){
        // set new position for the camera
        // later on, set the position of the player for the camera follows it
        // for now at 0 - the center of the screen
        //camera.position.set(new Vector3(0, 0, 0));
        // setting the camera's initial position to the bottom left of the map
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
        // This camera coordinates can be set up the first time you use it,
        // and you can do it with the method .translate()
        //camera.translate(camera.viewportWidth / 2, camera.viewportHeight);
        camera.update();
    }

    // creating a render method
    // draws and update the screen of our game constantly
    // every circle of the cpu
    // move character on the screen, enemies, play level map, camera movement
    @Override
    public void render(float delta){
        // clear the screen - removing graphics and animations from the previous frame
        // calling a black screen to clear the screen
        //rgb(234,182,118) all colors need to be divided by 255 = (0.91f, 0.71f, 0.46f)
        //Gdx.gl.glClearColor(0,0,0,1); // black screen
        Gdx.gl.glClearColor(0.91f, 0.71f, 0.46f,1);
        // and this below will clear my game screen
        // passing a mask as argument
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.update();

        // before rendering the objs, render img
        // rendering our render - drawing things themselves up on the screen
        // what to display and where to display

        //renderer.render(tileMapHelper.returnLayersArray());

        // rendering background
        renderer.render(tileMapHelper.groundLayerIndices);
        renderer.render(tileMapHelper.belowCharLayerIndices);

        // update the player before drawing it
        // passing the deltaTime
        player.update(delta);

        // draw our player using the batch
        // telling it where it needs to begin and end
        spriteBatch.begin();
        // render all the objs

        //renderer.renderTileLayer(tileMapHelper.terrainLayer);

        // here we need to draw our player
        player.draw(spriteBatch);

        // stop drawing
        spriteBatch.end();

        // rendering foreground
        renderer.render(tileMapHelper.decorationLayersIndices);



        // ppm = Pixels Per Meter
        // amount of potential image detail that a camera offers at a given distance.
        // creating a final constant for it
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void resize(int width, int height){
        // the camera is using the old width and height of the original screen
        // but when it gets resized, either smaller or bigger
        // it changes the window and the camera remains the same
        // looking the actual town map a bit differently - fix:
        // set the viewport of the camera
        //camera.viewportWidth = 23f; // display 23 units
        //camera.viewportHeight = 19f * height / width; // without parenthesis
        // testing 14 f
        camera.viewportWidth = tilesWidth; // display 14 units
        camera.viewportHeight = tilesHeight * height / width; // without parenthesis
        // update the camera
        camera.update();

    }



    public World getWorld(){
        return world;
    }


}
