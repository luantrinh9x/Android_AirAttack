package Screens;

import Resources.MusicR;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class Setting implements Screen {

	private AirAttack aa;
	private SpriteBatch batch;
	private Stage stage;
	private boolean checkLevel = false;
	private boolean checkStyle = false;

	//
	Texture settingImage;
	TextureRegion settingRegion;
	Image settingActor;
	//
	Texture easyImage;
	TextureRegion easyRegion;
	Image easyActor;
	//
	Texture mediumImage;
	TextureRegion mediumRegion;
	Image mediumActor;
	//
	Texture hardImage;
	TextureRegion hardRegion;
	Image hardActor;
	//
	Texture touchImage;
	TextureRegion touchRegion;
	Image touchActor;
	//
	Texture acceImage;
	TextureRegion acceRegion;
	Image acceActor;
	//
	Texture goImage;
	TextureRegion goRegion;
	Image goActor;

	public static String difficult = "Hard";
	public static String playStyle = "Accelerometer";

	public Setting(AirAttack aa) {
		this.aa = aa;
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(checkLevel == true && checkStyle == true)
		{
			goActor.setColor(Color.ORANGE);
			stage.addActor(goActor);
		}
		batch.begin();
		stage.act(delta);
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);

		// setting image
		settingImage = new Texture(Gdx.files.internal("settingImage.png"));
		settingImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		settingRegion = new TextureRegion(settingImage, 1080, 1920);
		settingActor = new Image(settingRegion);

		// play button
		easyImage = new Texture(Gdx.files.internal("easyBtn.png"));
		easyImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		easyRegion = new TextureRegion(easyImage, 450, 113);
		easyActor = new Image(easyRegion);
		easyActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (easyActor.getWidth() / 2)+ 240, 1380);
		// play listener
		easyActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				checkLevel = true;
				Gdx.input.vibrate(50);
				easyActor.setColor(Color.GREEN);
				mediumActor.setColor(Color.WHITE);
				hardActor.setColor(Color.WHITE);
				Setting.setDifficult("Easy");
				return false;
			}
		});
		
		// load button
		mediumImage = new Texture(Gdx.files.internal("mediumBtn.png"));
		mediumImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		mediumRegion = new TextureRegion(mediumImage, 450, 113);
		mediumActor = new Image(mediumRegion);
		mediumActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (mediumActor.getWidth() / 2) + 240, 1230);
		// load listener
		mediumActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				checkLevel = true;
				Gdx.input.vibrate(50);
				easyActor.setColor(Color.WHITE);
				mediumActor.setColor(Color.YELLOW);
				hardActor.setColor(Color.WHITE);
				Setting.setDifficult("Medium");
				return false;
			}
		});
		// exit button
		hardImage = new Texture(Gdx.files.internal("hardBtn.png"));
		hardImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		hardRegion = new TextureRegion(hardImage, 450, 113);
		hardActor = new Image(hardRegion);
		hardActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (hardActor.getWidth() / 2) + 240, 1080);
		// exit listener
		hardActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				checkLevel = true;
				Gdx.input.vibrate(50);
				easyActor.setColor(Color.WHITE);
				mediumActor.setColor(Color.WHITE);
				hardActor.setColor(Color.RED);
				Setting.setDifficult("Hard");
				return false;
			}
		});
		
		// exit button
		touchImage = new Texture(Gdx.files.internal("touchBtn.png"));
		touchImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		touchRegion = new TextureRegion(touchImage, 450, 113);
		touchActor = new Image(touchRegion);
		touchActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (touchActor.getWidth() / 2) + 240,805);
		// exit listener
		touchActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				checkStyle = true;
				Gdx.input.vibrate(50);
				touchActor.setColor(Color.CYAN);
				acceActor.setColor(Color.WHITE);
				Setting.setPlayStyle("Touch");
				return false;
			}
		});
		
		// exit button
		acceImage = new Texture(Gdx.files.internal("acceBtn.png"));
		acceImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		acceRegion = new TextureRegion(acceImage, 450, 113);
		acceActor = new Image(acceRegion);
		acceActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (acceActor.getWidth() / 2) + 240, 655);
		// exit listener
		acceActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				checkStyle = true;
				Gdx.input.vibrate(50);
				touchActor.setColor(Color.WHITE);
				acceActor.setColor(Color.CYAN);
				Setting.setPlayStyle("Accelerometer");
				return false;
			}
		});
		
		// exit button
		goImage = new Texture(Gdx.files.internal("goBtn.png"));
		goImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		goRegion = new TextureRegion(goImage, 500, 167);
		goActor = new Image(goRegion);
		goActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (goActor.getWidth() / 2) + 245, 200);
		// exit listener
		goActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.input.vibrate(50);
				 MusicR.getIntroMusic().dispose();
				 dispose();
				 aa.setScreen(new Play(aa));
				return false;
			}
		});

		stage.addActor(settingActor);
		stage.addActor(easyActor);
		stage.addActor(mediumActor);
		stage.addActor(hardActor);
		stage.addActor(touchActor);
		stage.addActor(acceActor);
		Gdx.input.setInputProcessor(stage);
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
		settingImage.dispose();
		easyImage.dispose();
		mediumImage.dispose();
		hardImage.dispose();
		touchImage.dispose();
		acceImage.dispose();
		goImage.dispose();
	}

	public static String getDifficult() {
		return difficult;
	}

	public static void setDifficult(String difficult) {
		Setting.difficult = difficult;
	}

	public static String getPlayStyle() {
		return playStyle;
	}

	public static void setPlayStyle(String playStyle) {
		Setting.playStyle = playStyle;
	}
}
