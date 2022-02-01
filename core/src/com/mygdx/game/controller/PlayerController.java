package com.mygdx.game.controller;

import com.mygdx.game.model.Player;

public class PlayerController {
    public static Player player;

    public static void initializeController(){
        player = new Player(48, 48);
    }

    public static void update(float deltaTime){
        player.update(deltaTime);
    }


}
