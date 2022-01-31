package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
    // we need to know its position on the screen
    // and its sprite sheet
    public Vector2 position; // x and y

    public Animation<TextureRegion> animation;  // Must declare frame type (TextureRegion)
    public Spritesheet spritesheet;

    //String playerSpritesAbsolutePath = "D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\resources\\img\\myPlayerTest.png";
    String player1SpritesAbsolutePath = "D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\resources\\img\\characterAnimationSheet.png";
    String player2SpritesAbsolutePath = "D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\resources\\img\\character2AnimationSheet.png";
    int widthEachPlayer = 48;
    int heightEachPlayer = 48;
    //int pixelsOfPlayer = 64;

    // A variable for tracking elapsed time for the animation
    private float stateTime;


    /* characterAnimationSheet.png
        Animations:
        0-5 = walking south
        6-13 = base
        14-19 = walking left
        20-25 = walking right
        26-30 = walking north
         */
        /* character2AnimationSheet.png
        Animations:
        0-3 = base
        4-6 = front idle
        7-9 = left idle
        10-11 = back idle
        12-14 = right idle
        15-19 = front walking
        20-24 = left walking
        25-29 = right walking
        30-34 = back walking
         */

    public Player() {
        // default position
        position = new Vector2(5,5);

        spritesheet = new Spritesheet(player1SpritesAbsolutePath, widthEachPlayer, heightEachPlayer);
        // returns the animation of the Spritesheet class
        animation = spritesheet.createAnimation(0, 5, 0.25f);

        // init the stateTime variable
        // it is like the game time
        stateTime = 0f;
    }

    // draw the player on the screen
    // SpriteBatch uses Batch as its parent
    public void draw(Batch batch){
        // tell it the sprite sheet to use and its position
        // putting width and height of one of the characters on the sprite sheet
        // each character on the sprite sheet has width of 101 pixels and height of 105 pixels
        // here we are saying to display the whole sprite sheet:
        //spriteBatch.draw(spriteSheet, 0, 0, 67, 70);

        // specify which sprite do we want using from spriteFrames[0] to spriteFrames[52] // and spriteFrames[31]

        // as it is batch now, this is not 101 pixels anymore, this is 101 units
        // my unit scale in TileMapHelper was 1/32f
        //batch.draw(spriteFrames[0], position.x, position.y, widthEachPlayer * (1/32f), heightEachPlayer * (1/32f));

        // draw the animation I've created
        // the true is that we want our animation to loop
        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y, widthEachPlayer * (1/32f), heightEachPlayer * (1/32f));

    }

    // update its position and anything regarding the player's properties
    public void update(float deltaTime){
        // position.x++; // will increases one unit per second
        //position.x -= deltaTime;
        position.y -= deltaTime;
        // modify the stateTime using deltaTime to not be stuck at 0
        stateTime += deltaTime;
    }
}
