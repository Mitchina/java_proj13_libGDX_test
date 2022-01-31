package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Boot;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "TiledMapGame";
		//config.resizable = false;
		config.width = 480;
		config.height = 480;

		//new LwjglApplication(new MyGdxGame(), config);
		new LwjglApplication(new Boot(), config);
	}
}
