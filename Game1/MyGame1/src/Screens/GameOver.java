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

public class GameOver implements Screen {

	private AirAttack aa;
	private SpriteBatch batch;
	private Stage stage;

	//
	Texture gameOverImage;
	TextureRegion gameOverRegion;
	Image gameOverActor;
	//
	Texture retryImage;
	TextureRegion retryRegion;
	Image retryActor;
	//
	Texture scoreImage;
	TextureRegion scoreRegion;
	Image scoreActor;
	//
	Texture menuImage;
	TextureRegion menuRegion;
	Image menuActor;

	//

	public GameOver(AirAttack aa) {
		this.aa = aa;
		Gdx.input.setCatchBackKey(true);
		MusicR.declareMusic();
		MusicR.getGameOverMusic().play();
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
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);

		// menu image
		gameOverImage = new Texture(Gdx.files.internal("gameOverImage.png"));
		gameOverImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		gameOverRegion = new TextureRegion(gameOverImage, 1080, 1920);
		gameOverActor = new Image(gameOverRegion);
		// play button
		retryImage = new Texture(Gdx.files.internal("retryBtn.png"));
		retryImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		retryRegion = new TextureRegion(retryImage, 600, 150);
		retryActor = new Image(retryRegion);
		retryActor.setPosition(
				(Gdx.graphics.getWidth() / 2) - (retryActor.getWidth() / 2),
				650);
		// play listener
		retryActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				resetCount();
				Gdx.input.vibrate(50);
				MusicR.getGameOverMusic().dispose();
				dispose();
				aa.setScreen(new Play(aa));
				return false;
			}
		});
		// load button
		scoreImage = new Texture(Gdx.files.internal("scoreBtn.png"));
		scoreImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		scoreRegion = new TextureRegion(scoreImage, 600, 150);
		scoreActor = new Image(scoreRegion);
		scoreActor.setPosition(
				(Gdx.graphics.getWidth() / 2) - (scoreActor.getWidth() / 2),
				450);
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
								- (menuActor.getWidth() / 2), 250);
		// exit listener
		menuActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				resetCount();
				Gdx.input.vibrate(50);
				MusicR.getGameOverMusic().dispose();
				dispose();
				aa.setScreen(new Menu(aa));
				return false;
			}
		});

		stage.addActor(gameOverActor);
		stage.addActor(retryActor);
		stage.addActor(scoreActor);
		stage.addActor(menuActor);
		Gdx.input.setInputProcessor(stage);
	}

	public static void resetCount() {
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
		gameOverImage.dispose();
		retryImage.dispose();
		scoreImage.dispose();
		menuImage.dispose();
	}
}
