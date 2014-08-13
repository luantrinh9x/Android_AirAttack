package Characters;

import Screens.Play;
import Screens.Setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Laser {

	private Vector2 position;
	private Vector2 size;
	private Rectangle laserRec;
	private static Laser laser;
	public static Array<Laser> arrayLaser;

	public static long lastLaserTime;

	public static Texture greenLaser, blueLaser, pinkLaser;
	public static TextureRegion trGreenLaser, trBlueRocket, trPinkLaser,
			currLaserFrame;
	public static TextureRegion[] framesLaser;
	public static Animation aniLaser;

	public Laser(Vector2 position, Vector2 size) {
		super();
		this.position = position;
		this.size = size;
		laserRec = new Rectangle(position.x, position.y, size.x, size.y);
	}

	public static void laserSprite() {
		greenLaser = new Texture(Gdx.files.internal("green.png"));
		trGreenLaser = new TextureRegion(greenLaser);
		blueLaser = new Texture(Gdx.files.internal("blue.png"));
		trBlueRocket = new TextureRegion(blueLaser);
		pinkLaser = new Texture(Gdx.files.internal("pink.png"));
		trPinkLaser = new TextureRegion(pinkLaser);

		framesLaser = new TextureRegion[3];
		framesLaser[0] = trGreenLaser;
		framesLaser[1] = trBlueRocket;
		framesLaser[2] = trPinkLaser;

		aniLaser = new Animation(0.25f, framesLaser);
	}

	public static void laserDrop() {
		float x = 0;
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			x = Play.getBos().getPosition().x + 213;
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			x = Play.getBos().getPosition().x + MathUtils.random(63, 363);
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			x = Play.getBos().getPosition().x + MathUtils.random(23, 403);
		}
		laser = new Laser(new Vector2(x, Play.getBos().getPosition().y - 1650),
				new Vector2(59, 2000));
		arrayLaser.add(laser);
		Laser.lastLaserTime = TimeUtils.millis();
	}

	public static void batchLaser() {
		for (Laser drop : Laser.getArrayLaser()) {
			Play.batch.draw(Laser.getCurrLaserFrame(), drop.getPosition().x,
					drop.getPosition().y, drop.getSize().x, drop.getSize().y);
		}
	}

	public static TextureRegion getCurrLaserFrame() {
		return currLaserFrame;
	}

	public static void setCurrLaserFrame(TextureRegion currLaserFrame) {
		Laser.currLaserFrame = currLaserFrame;
	}

	public static Array<Laser> getArrayLaser() {
		return arrayLaser;
	}

	public static void setArrayLaser(Array<Laser> arrayLaser) {
		Laser.arrayLaser = arrayLaser;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}

	public Rectangle getLaserRec() {
		return laserRec;
	}

	public void setLaserRec(Rectangle laserRec) {
		this.laserRec = laserRec;
	}

	public static void disposeLaser() {
		greenLaser.dispose();
		blueLaser.dispose();
		pinkLaser.dispose();
	}
}
