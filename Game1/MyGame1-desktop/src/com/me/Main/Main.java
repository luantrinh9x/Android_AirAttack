package com.me.Main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.main.AirAttack;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Air Attack v" + AirAttack.VERSION;
		cfg.useGL20 = true;
		cfg.width = 720;
		cfg.height = 1280;
		
		new LwjglApplication(new AirAttack(), cfg);
	}
}
