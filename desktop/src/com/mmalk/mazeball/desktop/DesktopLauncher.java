package com.mmalk.mazeball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mmalk.mazeball.MazeballGame;
import com.mmalk.mazeball.MazeballGameAppAdapter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		new LwjglApplication(new MazeballGameAppAdapter(), config);
		new LwjglApplication(new MazeballGame(), config);
	}
}
