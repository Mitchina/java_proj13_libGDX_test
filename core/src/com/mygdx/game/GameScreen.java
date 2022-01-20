package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;

import static helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    // SpriteBatch to render the sprites
    private SpriteBatch batch;
    // world obj to start the 2d boxes
    private World world;
    // see the bodies textures of the boxes
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    public GameScreen(OrthographicCamera camera){
        this.camera = camera;
        this.batch = new SpriteBatch();
        // passing vector2d with x and y, later add gravity in the y axis
        this.world = new World(new Vector2(0, 0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        // call our img
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
    }

    private void update(){
        // creating a step for our world
        //60 fps
        world.step(1/60f, 6, 2);

        // implement camera update
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);

        // update our img
        orthogonalTiledMapRenderer.setView(camera);

        // close the game when pressed Esc button
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    private void cameraUpdate(){
        // set new position for the camera
        // for now at 0
        camera.position.set(new Vector3(0, 0, 0));
        camera.update();
    }

    // creating a render method
    @Override
    public void render(float delta){
        this.update();

        // clear the screen - removing graphics and animations from the previous frame
        // calling a black screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // before rendering the objs, render img
        orthogonalTiledMapRenderer.render();

        batch.begin();
        // render all the objs


        batch.end();
        // ppm = Pixels Per Meter
        // amount of potential image detail that a camera offers at a given distance.
        // creating a final constant for it
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public World getWorld(){
        return world;
    }


}
