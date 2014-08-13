package com.gdx.main;

import Screens.Play;
import Screens.Splash;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AirAttack extends Game {
	public static final String TITLE = "Air Attack", VERSION = "1.0";
	public static BitmapFont font;
	
	public Play play;

	@Override
	public void create() {
		font = new BitmapFont(Gdx.files.internal("font/white.fnt"),
				Gdx.files.internal("font/white_0.png"), false);
		
		setScreen(new Splash(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
