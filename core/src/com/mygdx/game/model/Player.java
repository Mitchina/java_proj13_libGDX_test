package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.controller.LevelController;

import java.util.HashMap;

public class Player {
    // we need to know its position on the screen
    // and its sprite sheet
    public Vector2 position; // x and y

    //public Animation<TextureRegion> animation;  // Must declare frame type (TextureRegion)

    public Spritesheet spritesheet;

    // just one animation:
    public Animation<TextureRegion> animation;

    private HashMap<String, Animation> animations;
    public String currentAnimation;

    //String playerSpritesAbsolutePath = "D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\resources\\img\\myPlayerTest.png";
    String player1SpritesAbsolutePath = "D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\com\\mygdx\\game\\resources\\img\\characterAnimationSheet.png";
    String player2SpritesAbsolutePath = "D:\\ju_javaPrograms\\java_proj13_test\\core\\src\\com\\mygdx\\game\\resources\\img\\character2AnimationSheet.png";
    float widthEachPlayer;
    float heightEachPlayer;
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

    public Player(int widthEachPlayer, int heightEachPlayer) {
        // default position
        position = new Vector2(5,5);
        this.widthEachPlayer = widthEachPlayer * (1/32f); // to give it the correct scale -> to use the right scale later // I'm using the tile scale
        this.heightEachPlayer = heightEachPlayer * (1/32f); // to give it the correct scale -> to use the right scale later

        spritesheet = new Spritesheet(player2SpritesAbsolutePath, widthEachPlayer, heightEachPlayer); // it will be still in int


        animations = new HashMap<String, Animation>();

        // put, first value-string
        animations.put("idleFront", spritesheet.createAnimation(4, 6, 0.25f));
        animations.put("idleLeft", spritesheet.createAnimation(7, 9, 0.25f));
        animations.put("idleBack", spritesheet.createAnimation(10, 11, 0.25f));
        animations.put("idleRight", spritesheet.createAnimation(12, 14, 0.25f));
        animations.put("walkFront", spritesheet.createAnimation(15, 19, 0.25f));
        animations.put("walkLeft", spritesheet.createAnimation(20, 24, 0.25f));
        animations.put("walkRight", spritesheet.createAnimation(25, 29, 0.25f));

        // testing the flip animation with 12-14 = right idle
        // it should show us the left idle
        //animation = spritesheet.flitAnimation(animation, true, false); // it is working

        // example of flipped animations inside the animations array:
        //animations.put("idleRight", spritesheet.flitAnimation(animations.get("idleLeft"), true, false)); // it is working

        // type the same name you gave above, so it can access the right animation
        currentAnimation = "idleFront";

        // testing with one animation only to see if I can see the box 2d
        //animation = spritesheet.createAnimation(4,6,0.25f);

        createBox2d();

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

        /* just one animation
        // draw the animation I've created
        // the true is that we want our animation to loop
        // Get current frame of animation for the current stateTime
        */
        //TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        // tell what animation to draw - animations:
        TextureRegion currentFrame = (TextureRegion) animations.get(currentAnimation).getKeyFrame(stateTime, true);


        // we are already doing the * (1/32f) calculation on the constructor
        //batch.draw(currentFrame, position.x, position.y, widthEachPlayer * (1/32f), heightEachPlayer * (1/32f));
        batch.draw(currentFrame, position.x, position.y, widthEachPlayer, heightEachPlayer);

        // now we need to draw multiple animations
        // creating an array of animations

    }

    // update its position and anything regarding the player's properties
    public void update(float deltaTime){
        // position.x++; // will increases one unit per second
        //position.x += deltaTime;
        //position.y -= deltaTime;
        // now we are going to pass the input for the positions

        // modify the stateTime using deltaTime to not be stuck at 0
        stateTime += deltaTime;
    }

    public void createBox2d(){
        // creating a rigid body for the player // attacking a shape to it // it is like the body properties
        BodyDef bodyDefinition = new BodyDef();
        //DynamicBody because we wanna apply forces, also be affected by gravity, collisions, etc
        //bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        // modify its position on the screen -> we already have a Vector2 here
        bodyDefinition.position.set(position);
        //bodyDefinition.position.set(this.widthEachPlayer/2, this.heightEachPlayer/2);
        // now create a mass in our game world, with will have the same position as our player does

        /*
        creating this body to the physic world
        passing the bodyDefinition inside it
        1. Now we have this player body with all its properties
        2. this body is being created inside the world
        */
        Body playerBody = LevelController.world.createBody(bodyDefinition);
        // set this class and pass all those information to the player class
        playerBody.setUserData(this); // this to refer this class and this obj

        // how it looks in the real world? // the class is called PolygonShape, and the form will be of rectangle
        PolygonShape rectangleShape = new PolygonShape();
        // get half width and half height, the center of our box, the angle = 0 cause we are not gonna rotate it
        rectangleShape.setAsBox(this.widthEachPlayer/2-1.1f, this.heightEachPlayer/2-1.3f, new Vector2(this.widthEachPlayer/2, this.heightEachPlayer/2+.05f), 0f);
        //rectangleShape.setAsBox(this.widthEachPlayer/2, this.heightEachPlayer/2);

        // to attach the shape to our body, we need first attach it using fixtures
        FixtureDef fixtureDefinition = new FixtureDef();
        // set shape property of the fixture
        fixtureDefinition.shape = rectangleShape;

        // now we can attach the fixture to the Body
        playerBody.createFixture(fixtureDefinition);

        // and now we can delete the shape that we created before (rectangleShape)
        //rectangleShape.dispose(); // shape no longer used
    }
}
