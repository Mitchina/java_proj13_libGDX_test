package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game {

    public static Boot instance;
    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    public Boot(){
        instance = this;
    }

    @Override
    public void create(){
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);

        // passing the GameScreen
        setScreen(new GameScreen(orthographicCamera));
    }

}
