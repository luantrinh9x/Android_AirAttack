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

public class Rocket {

	private Vector2 position;
	private Vector2 size;
	private Rectangle rocketRec;
	static Rocket rocket;
	static Plane plane;
	public static Array<Rocket> arrayRocket;
	private TextureRegion tr1, tr2;
	private TextureRegion[] trArray;
	private Texture t1, t2;

	public static Texture normalRocketO, whiteRocketO, normalRocketB,
			whiteRocketB, normalRocketS, whiteRocketS, ran1, ran2;
	public static TextureRegion trNormalRocketO, trWhiteRocketO;
	private TextureRegion currRocketFrame;
	public static TextureRegion[] framesRocket;
	public Animation aniRocket;
	public static long lastRocketTime;
	private static int randomRocket = 0;

	public Rocket(Vector2 position, Vector2 size, Texture t1, Texture t2) {
		super();
		this.position = position;
		this.size = size;
		rocketRec = new Rectangle(position.x, position.y, size.x, size.y);
		this.t1 = t1;
		this.t2 = t2;

		tr1 = new TextureRegion(t1);
		tr2 = new TextureRegion(t2);

		trArray = new TextureRegion[2];

		trArray[0] = tr1;
		trArray[1] = tr2;

		aniRocket = new Animation(0.5f, trArray);
	}

	public static void rocketSprite() {
		normalRocketO = new Texture(Gdx.files.internal("o-rocket.png"));
		trNormalRocketO = new TextureRegion(normalRocketO);
		whiteRocketO = new Texture(Gdx.files.internal("o-rocket1.png"));
		trWhiteRocketO = new TextureRegion(whiteRocketO);

		normalRocketB = new Texture(Gdx.files.internal("b-rocket.png"));
		whiteRocketB = new Texture(Gdx.files.internal("b-rocket1.png"));

		normalRocketS = new Texture(Gdx.files.internal("s-rocket.png"));
		whiteRocketS = new Texture(Gdx.files.internal("s-rocket1.png"));
	}

	public static void rocketDrop() {
		float x = 0;
		plane = Play.getPlane();
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			x = MathUtils.random(70, 880);
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			x = plane.getPosition().x + MathUtils.random(-600, 600);
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			x = plane.getPosition().x + MathUtils.random(-300, 300);
		}

		randomRocket = MathUtils.random(1, 3);

		if (randomRocket == 1) {
			ran1 = Rocket.normalRocketO;
			ran2 = Rocket.whiteRocketO;
		} else if (randomRocket == 2) {
			ran1 = Rocket.normalRocketB;
			ran2 = Rocket.whiteRocketB;
		} else {
			ran1 = Rocket.normalRocketS;
			ran2 = Rocket.whiteRocketS;
		}

		rocket = new Rocket(new Vector2(x, Play.camera.position.y + 940),
				new Vector2(120, 146), ran1, ran2);

		arrayRocket.add(rocket);
		Rocket.lastRocketTime = TimeUtils.millis();
	}

	public static void batchRocket() {
		for (Rocket drop : Rocket.getArrayRocket()) {
			Play.batch.draw(drop.getCurrRocketFrame(), drop.getPosition().x,
					drop.getPosition().y, drop.getSize().x, drop.getSize().y);
		}
	}

	public TextureRegion getCurrRocketFrame() {
		return currRocketFrame;
	}

	public void setCurrRocketFrame(TextureRegion currRocketFrame) {
		this.currRocketFrame = currRocketFrame;
	}

	public static Array<Rocket> getArrayRocket() {
		return arrayRocket;
	}

	public static void setArrayRocket(Array<Rocket> arrayRocket) {
		Rocket.arrayRocket = arrayRocket;
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

	public Rectangle getRocketRec() {
		return rocketRec;
	}

	public void setRocketRec(Rectangle rocketRec) {
		this.rocketRec = rocketRec;
	}

	public static void disposeRocket() {
		normalRocketO.dispose();
		whiteRocketO.dispose();
		normalRocketB.dispose();
		whiteRocketB.dispose();
		normalRocketS.dispose();
		whiteRocketS.dispose();
		ran1.dispose();
		ran2.dispose();
	}

	public static TextureRegion[] getFramesRocket() {
		return framesRocket;
	}

	public static void setFramesRocket(TextureRegion[] framesRocket) {
		Rocket.framesRocket = framesRocket;
	}

	public Animation getAniRocket() {
		return aniRocket;
	}

	public void setAniRocket(Animation aniRocket) {
		this.aniRocket = aniRocket;
	}

}
