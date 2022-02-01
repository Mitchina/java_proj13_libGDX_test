package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.controller.LevelController;
import com.mygdx.game.controller.PlayerController;
import com.mygdx.game.model.Player;

// the GameScreen will load our level - displaying it
// first we need to load it
// tell how to draw itself on the screen
// point the camera
public class GameScreen extends ScreenAdapter {
    private OrthographicCamera camera;

    /*
    public Player player;
    */

    float tilesWidth = 14f;
    float tilesHeight = 14f;


    public GameScreen(OrthographicCamera camera){
        this.camera = camera;

        LevelController.initializeController(this);

        PlayerController.initializeController();
        /*
        player = new Player(48, 48);
        */
    }

    private void update(){
        cameraUpdate();

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

        LevelController.update(delta, this.camera);

        // update the player before drawing it
        // passing the deltaTime
        PlayerController.update(delta);

        LevelController.draw(this.camera);
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


}
