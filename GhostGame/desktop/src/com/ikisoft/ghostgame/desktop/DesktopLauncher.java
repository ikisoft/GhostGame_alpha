package com.ikisoft.ghostgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ikisoft.ghostgame.GhostGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 270 * 2;
		config.height = 480 * 2;
		//config.foregroundFPS = 30;

		new LwjglApplication(new GhostGame(), config);
	}
}
