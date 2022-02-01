package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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

    public static void initializeController(){
        tileMapHelper = new TileMapHelper(this); // see later the load function

        // call our img
        // Instantiation of the render for the map object
        renderer = tileMapHelper.setupMap();
        //this.renderer = new OrthogonalTiledMapRenderer(tileMapHelper.tiledMap, 1/32f);
        world = new World(new Vector2(0, 0), true); // in this game we don't need gravity

        spriteBatch = renderer.getBatch();
    }


}
