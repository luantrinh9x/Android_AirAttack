package Screens;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Characters.Plane;
import Characters.Boss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gdx.main.AirAttack;

public class GamePause implements Screen {
	private AirAttack aa;
	private SpriteBatch batch;
	private Stage stage;

	// Read text from file
	public static StringBuilder text = new StringBuilder();
	
	//
	Texture pauseImage;
	TextureRegion pauseRegion;
	Image pauseActor;
	//
	Texture menuImage;
	TextureRegion menuRegion;
	Image menuActor;
	//
	//
	Texture saveImage;
	TextureRegion saveRegion;
	Image saveActor;
	//
	//
	Texture resumeImage;
	TextureRegion resumeRegion;
	Image resumeActor;
	//

	public GamePause(AirAttack aa) {
		this.aa = aa;
		Gdx.input.setCatchBackKey(true);
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
		pauseImage = new Texture(Gdx.files.internal("pauseImage.png"));
		pauseImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		pauseRegion = new TextureRegion(pauseImage, 1080, 1920);
		pauseActor = new Image(pauseRegion);
		// exit button
		resumeImage = new Texture(Gdx.files.internal("resumeBtn.png"));
		resumeImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		resumeRegion = new TextureRegion(resumeImage, 600, 150);
		resumeActor = new Image(resumeRegion);
		resumeActor.setPosition(
				(Gdx.graphics.getWidth() / 2) - (resumeActor.getWidth() / 2),
				650);
		// exit listener
		resumeActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				dispose();
				aa.setScreen(aa.play);
				return false;
			}
		});
		// exit button
		saveImage = new Texture(Gdx.files.internal("saveBtn.png"));
		saveImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		saveRegion = new TextureRegion(saveImage, 600, 150);
		saveActor = new Image(saveRegion);
		saveActor
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (saveActor.getWidth() / 2), 450);
		// exit listener
		saveActor.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				saveActor.setColor(Color.CYAN);
				savePlane();
				saveBoss();
				saveCamera();
				saveTime();
				saveScore();
				saveLevel();
				saveStyle();
				savePlaneHealth();
				saveBossHealth();
				saveRocketKilled();
				saveBossKilled();
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
				dispose();
				Play.disposeAll();
				aa.setScreen(new Menu(aa));
				return false;
			}
		});

		stage.addActor(pauseActor);
		stage.addActor(resumeActor);
		stage.addActor(saveActor);
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
		pauseImage.dispose();
		menuImage.dispose();
		saveImage.dispose();
		resumeImage.dispose();
	}

	private void savePlane() {
		Plane plane = new Plane(new Vector2(Play.plane.getPosition().x,
				Play.plane.getPosition().y), new Vector2(
				Play.plane.getSize().x, Play.plane.getSize().y));
		try {
			FileOutputStream fileOut = new FileOutputStream(
					"/sdcard/Android/plane.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(plane);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private void saveBoss() {
		Boss boss = new Boss(new Vector2(Play.bos.getPosition().x,
				Play.bos.getPosition().y), new Vector2(Play.bos.getSize().x,
				Play.bos.getSize().y));
		try {
			FileOutputStream fileOut = new FileOutputStream(
					"/sdcard/Android/boss.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(boss);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private void saveCamera() {
		String mystring = Float.toString(Play.camera.position.y);
		try {
			File file = new File("/sdcard/Android/camera.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mystring);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveTime() {
		String mystring = Float.toString(Play.secondsTime);
		try {
			File file = new File("/sdcard/Android/time.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mystring);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveScore() {
		String mystring = Integer.toString(Play.getScore());
		try {
			File file = new File("/sdcard/Android/score.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mystring);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveLevel() {
		try {
			File file = new File("/sdcard/Android/level.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Setting.getDifficult());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveStyle() {
		try {
			File file = new File("/sdcard/Android/style.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Setting.getPlayStyle());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void savePlaneHealth() {
		String mystring = Integer.toString(Play.getHealth());
		try {
			File file = new File("/sdcard/Android/planeHealth.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mystring);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveBossHealth() {
		String mystring = Integer.toString(Play.getCountHitBoss());
		try {
			File file = new File("/sdcard/Android/bossHealth.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mystring);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveRocketKilled() {
		String mystring = Integer.toString(Play.getRocketKilled());
		try {
			File file = new File("/sdcard/Android/rocketKilled.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mystring);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveBossKilled() {
		String mystring = Integer.toString(Play.getBossKilled());
		try {
			File file = new File("/sdcard/Android/bossKilled.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(mystring);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
