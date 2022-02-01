package com.mygdx.game.model;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spritesheet {
    // handle animations
    public Texture spriteSheet;
    public TextureRegion[] spriteFrames;

    public Animation<TextureRegion> animation;  // Must declare frame type (TextureRegion)
    public Animation<TextureRegion> flippedAnimation;

    public Spritesheet(String pathToFile, int eachSpriteWidth, int eachSpriteHeight){
        //spriteSheet = new Texture(Gdx.files.internal("img/character_test.png"));
        spriteSheet = new Texture(new FileHandle(pathToFile));

        // 2d array
        // rows and columns
        // test 1
        // each character on the sprite sheet has width of 67 pixels and height of 70 pixels

        // test 2
        // each character on the sprite sheet has width of 112,5 pixels and height of 130,75 pixels

        // test 3
        // each character on the sprite sheet has width of 101 pixels and height of 105 pixels
        TextureRegion[][] spriteSheetFrames = TextureRegion.split(spriteSheet, eachSpriteWidth, eachSpriteHeight);

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

    // returning the animation to the player
    public Animation createAnimation(int startFrame, int lastFrame, float animationSpeed){

        // Creating an Animation by using the frames
        // tell the size of its array
        int counter = (lastFrame + 1) - startFrame;

        TextureRegion[] animationFrames = new TextureRegion[counter];

        // adding the sprite that we have in the animation
        // set a value for the first frame of the array
        // figure out which frame do you wanna use inside spriteFrames array

        // as we cannot create an animation spriteFrames[0] if we want the elem 0 of the array:
        // we're gonna start from the lastFrame and decrement it
        for(int index = lastFrame; index >= startFrame; index --){
            // access the animation frames
            // store the counter in the correct array
            // decrement the variable before using it
            animationFrames[--counter] = spriteFrames[index];
        }

        /*
        animationFrames[0] = spriteFrames[0];
        animationFrames[1] = spriteFrames[1];
        animationFrames[2] = spriteFrames[2];
        animationFrames[3] = spriteFrames[3];
        animationFrames[4] = spriteFrames[4];
        animationFrames[5] = spriteFrames[5];
        */

        // tell it the speed of the animation, for example 1f = 1 sec and then the array

        //animation = new Animation(1f, animationFrames);
        animation = new Animation<TextureRegion>(animationSpeed, animationFrames);
        return animation;
    }

    // flip the animation so it will face the other direction
    public Animation flitAnimation(Animation originalAnimation, boolean flipX, boolean flipY){
        // we need frames to flip
        // if we have the player sprites that only goes to the right we can flip it
        // get the length of the array
        int frameCount = originalAnimation.getKeyFrames().length;

        // creating a new array
        // we need it to be the exact size of the original array
        TextureRegion[] flippedFrames = new TextureRegion[frameCount];

        // we want to continue looping until is frameCount -1 - to get the truth number
        // because the frames start at 0, but the length count from 1
        for(int index = 0; index <=frameCount -1; index++){
            // access the correct elem
            // example:
            //TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
            flippedFrames[index] = (TextureRegion) (originalAnimation.getKeyFrames())[index];
            // boolean to flip horizontally and to flip vertically
            flippedFrames[index].flip(flipX, flipY);
        }
        flippedAnimation = new Animation<TextureRegion>(originalAnimation.getFrameDuration(), flippedFrames);
        return flippedAnimation;
    }
}
