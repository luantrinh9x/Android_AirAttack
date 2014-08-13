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

public class Victory implements Screen {
	private AirAttack aa;
	private SpriteBatch batch;
	private Stage stage;

	//
	Texture victoryImage;
	TextureRegion victoryRegion;
	Image victoryActor;
	//
	Texture scoreImage;
	TextureRegion scoreRegion;
	Image scoreActor;
	//
	Texture menuImage;
	TextureRegion menuRegion;
	Image menuActor;
	//
	//
	Texture exitImage;
	TextureRegion exitRegion;
	Image exitActor;

	//
	public Victory(AirAttack aa) {
		this.aa = aa;
		Gdx.input.setCatchBackKey(true);
		MusicR.declareMusic();
		MusicR.getVictoryMusic().play();
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
		victoryImage = new Texture(Gdx.files.internal("victoryImage.png"));
		victoryImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		victoryRegion = new TextureRegion(victoryImage, 1080, 1920);
		victoryActor = new Image(victoryRegion);
		// load button
		scoreImage = new Texture(Gdx.files.internal("scoreBtn.png"));
		scoreImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		scoreRegion = new TextureRegion(scoreImage, 600, 150);
		scoreActor = new Image(scoreRegion);
		scoreActor.setPosition(
				(Gdx.graphics.getWidth() / 2) - (scoreActor.getWidth() / 2),
				650);
		// load listener
		scoreActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.input.vibrate(50);
				scoreActor.setColor(Color.CYAN);

				NameInput listener = new NameInput(aa);
				Gdx.input.getTextInput(listener,
						"Your Score: " + Play.getScore(), "");
				return false;
			}
		});
		// exit button
		menuImage = new Texture(Gdx.files.internal("menuBtn.png"));
		menuImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		menuRegion = new TextureRegion(menuImage, 600, 150);
		menuActor = new Image(menuRegion);
		menuActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (menuActor.getWidth() / 2), 450);
		// exit listener
		menuActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				resetCount();
				Gdx.input.vibrate(50);
				MusicR.getVictoryMusic().dispose();
				dispose();
				aa.setScreen(new Menu(aa));
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
				MusicR.getVictoryMusic().dispose();
				resetCount();
				Gdx.input.vibrate(50);
				dispose();
				Gdx.app.exit();
				return false;
			}
		});

		stage.addActor(victoryActor);
		stage.addActor(exitActor);
		stage.addActor(scoreActor);
		stage.addActor(menuActor);
		Gdx.input.setInputProcessor(stage);
	}

	private void resetCount() {
		Play.setScore(0);
		Play.setRocketKilled(0);
		Play.setSecondsTime(0);
		Play.setStatus("");
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
		victoryImage.dispose();
		scoreImage.dispose();
		menuImage.dispose();
		exitImage.dispose();
	}

}
