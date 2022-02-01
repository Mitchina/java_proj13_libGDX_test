package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;

public class LevelController {
    // displaying in a 2d plane
    private static OrthogonalTiledMapRenderer renderer;
    // tilemap = load our map. take it and draw it to the 2d plane
    private static TileMapHelper tileMapHelper;

    // draw our player sprites on the screen
    public static Batch spriteBatch;

    // world obj to start the 2d boxes
    public static World world; // static to refers it in player
    // see the bodies textures of the boxes
    private static Box2DDebugRenderer box2DDebugRenderer;

    public static void initializeController(){
        tileMapHelper = new TileMapHelper(this); // see later the load function

        // call our img
        // Instantiation of the render for the map object
        renderer = tileMapHelper.setupMap();
        //this.renderer = new OrthogonalTiledMapRenderer(tileMapHelper.tiledMap, 1/32f);
        world = new World(new Vector2(0, 0), true); // in this game we don't need gravity

        box2DDebugRenderer = new Box2DDebugRenderer();
        box2DDebugRenderer.setDrawBodies(true); //This method set the body lines to visible

        spriteBatch = renderer.getBatch();
    }

    // creating an update function and a draw function

    public static void draw(float deltaTime){
        // rendering background
        renderer.render(tileMapHelper.groundLayerIndices);
        renderer.render(tileMapHelper.belowCharLayerIndices);

        // update the player before drawing it
        // passing the deltaTime
        player.update(deltaTime);

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
        //box2DDebugRenderer.render(world, camera.combined.scl(PPM)); // it was that way before!
        box2DDebugRenderer.render(world, camera.combined);

    }

    public static void update(float deltaTime){
        // creating a step for our world
        //60 fps
        world.step(1/60f, 6, 2);

        spriteBatch.setProjectionMatrix(camera.combined);
        // update our img
        // setting the camera to be the view of the renderer
        // the renderer will only displays what the camera sees
        renderer.setView(camera);

    }



}
