package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game {

    public static Boot instance;
    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    float tilesWidth = 14f;
    float tilesHeight = 14f;

    public Boot(){
        instance = this;
    }

    @Override
    public void create(){
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        // width and height of the camera means how many of pixel units I want to draw in my tiled map
        // to fix the Aspect Ratio:
        // we need to multiply the unit we want for the height by the height divided by the width
        // example: this.orthographicCamera = new OrthographicCamera(30, 30 * (heightScreen / widthScreen));
        // as the tileset have 32 pixels.
        // I want to see 23 width x 19 height units of it
        //this.orthographicCamera = new OrthographicCamera(23f, 19f * (heightScreen / widthScreen));
        // I want to see 14 width x 14 height units of it
        this.orthographicCamera = new OrthographicCamera(tilesWidth, tilesHeight * (heightScreen / widthScreen));
        //this.orthographicCamera = new OrthographicCamera();
        // comparing, even though it looks smaller, it seems more accurate
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);

        // passing the GameScreen
        setScreen(new GameScreen(orthographicCamera));
    }

}
