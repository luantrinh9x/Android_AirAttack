package Screens;

import Resources.MusicR;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gdx.main.AirAttack;

public class Menu implements Screen {

	private AirAttack aa;
	private SpriteBatch batch;
	private Stage stage;
	//
	Texture menuImage;
	TextureRegion menuRegion;
	Image menuActor;
	//
	Texture playImage;
	TextureRegion playRegion;
	Image playActor;
	//
	Texture loadImage;
	TextureRegion loadRegion;
	Image loadActor;
	//
	Texture exitImage;
	TextureRegion exitRegion;
	Image exitActor;
	
	public static boolean loadGame = false;

	public Menu(AirAttack aa) {
		this.aa = aa;
		Gdx.input.setCatchBackKey(true);
		MusicR.declareMusic();
		MusicR.getIntroMusic().play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		stage.act(delta);
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);
		// menu image
		menuImage = new Texture(Gdx.files.internal("menuImage.png"));
		menuImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		menuRegion = new TextureRegion(menuImage, 1080, 1920);
		menuActor = new Image(menuRegion);
		// play button
		playImage = new Texture(Gdx.files.internal("playBtn.png"));
		playImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		playRegion = new TextureRegion(playImage, 600, 150);
		playActor = new Image(playRegion);
		playActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (playActor.getWidth() / 2), 650);
		// play listener
		playActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.input.vibrate(50);
				dispose();
				loadGame = false;
				aa.setScreen(new Setting(aa));
				return false;
			}
		});
		// load button
		loadImage = new Texture(Gdx.files.internal("loadBtn.png"));
		loadImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		loadRegion = new TextureRegion(loadImage, 600, 150);
		loadActor = new Image(loadRegion);
		loadActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (loadActor.getWidth() / 2), 450);
		// load listener
		loadActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.input.vibrate(50);
				MusicR.getIntroMusic().dispose();
				//
				loadGame = true;
				aa.setScreen(new Play(aa));
				return false;
			}
		});
		// exit button
		exitImage = new Texture(Gdx.files.internal("exitBtn.png"));
		exitImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		exitRegion = new TextureRegion(exitImage, 600, 150);
		exitActor = new Image(exitRegion);
		exitActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (exitActor.getWidth() / 2), 250);
		// exit listener
		exitActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.input.vibrate(50);
				MusicR.getIntroMusic().dispose();
				dispose();
				Gdx.app.exit();
				return false;
			}
		});
		stage.addActor(menuActor);
		stage.addActor(playActor);
		stage.addActor(loadActor);
		stage.addActor(exitActor);
		Gdx.input.setInputProcessor(stage);
	}

	public static boolean isLoadGame() {
		return loadGame;
	}

	public static void setLoadGame(boolean loadGame) {
		Menu.loadGame = loadGame;
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		menuImage.dispose();
		playImage.dispose();
		loadImage.dispose();
		exitImage.dispose();

		
	}
}
