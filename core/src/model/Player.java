package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
    // we need to know its position on the screen
    // and its sprite sheet
    public Vector2 position; // x and y
    public Texture spriteSheet;
    public TextureRegion[] spriteFrames;

    public Player() {
        // default position
        position = new Vector2(5,5);
        //spriteSheet = new Texture(Gdx.files.internal("img/character_test.png"));
        spriteSheet = new Texture(new FileHandle("D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\resources\\img\\character_test2.png"));

        // 2d array
        // rows and columns
        // test 1
        // each character on the sprite sheet has width of 67 pixels and height of 70 pixels

        // test 2
        // each character on the sprite sheet has width of 112,5 pixels and height of 130,75 pixels

        // test 3
        // each character on the sprite sheet has width of 101 pixels and height of 105 pixels
        TextureRegion[][] spriteSheetFrames = TextureRegion.split(spriteSheet, 101, 105);

        // counting how many sprite we have:
        int counter = 0;
        // for loop to be able to access each and every element found
        // find how many rows we have in our spriteSheet
        for(int row = 0; row < spriteSheetFrames.length; row++ ){
            // access the columns
            // getting the amount of columns found here within a single row
            // find columns per row
            for(int column = 0; column < spriteSheetFrames[row].length; column++){
                counter++;
            }
        }

        // create a new array of a certain size and that size is going to be of size counter
        // how many sprites are in that spriteSheet:
        spriteFrames = new TextureRegion[counter]; // it is going to be of size 53 // and 32

        // reset counter
        // this for loop is very similar to the above one
        // we are accessing the rows, and the rows are in an array
        // but we're accessing them differently, telling it to take
        // the 2d array spriteSheetFrames and return every row as a
        // TextureRegion[] array
        // storing each spriteSheetFrames in the array as a sprite
        counter = 0;
        // access animationFrames
        for(TextureRegion[] row : spriteSheetFrames){
            // access sprite from rows
            for(TextureRegion sprite : row){
                // storing the sprite in the position of the counter (spriteFrames[0], etc)
                spriteFrames[counter++] = sprite;
            }
        }
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
        batch.draw(spriteFrames[0], position.x, position.y, 101 * (1/32f), 105 * (1/32f));
        //  Splitting the Sprite sheet:



    }

    // update its position and anything regarding the player's properties
    public void update(float deltaTime){

    }
}
