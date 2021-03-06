package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	
	public static void main (String[] arg) { 
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
