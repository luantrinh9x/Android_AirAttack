package Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

import Screens.Play;
import Screens.Setting;

public class Plane implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7861580638034774765L;
	private Vector2 position;
	private Vector2 size;
	private static transient Rectangle planeRec;

	public static Texture normalPlane, leftPlane, rightPlane;
	public static TextureRegion trNormalPlane, trLeftPlane, trRightPlane,
			currPlaneFrame;
	public static TextureRegion[] normalFramesPlane;
	public static Animation aniPlane;

	//
	public static Texture hundred, ninety, eighty, seventy, sixty, fifty,
			forty, thirty, twenty, ten;
	public static TextureRegion trHundred, trNinety, trEighty, trSeventy,
			trSixty, trFifty, trForty, trThirty, trTwenty, trTen,
			currHealthFrame;
	public static TextureRegion[] framesHealth;
	public static Animation aniHealth;

	//

	public Plane(Vector2 position, Vector2 size) {
		super();
		this.position = position;
		this.size = size;
		planeRec = new Rectangle(position.x, position.y, size.x, size.y);
	}

	public static void planeSprite() {
		normalPlane = new Texture(Gdx.files.internal("p8n.png"));
		trNormalPlane = new TextureRegion(normalPlane);
		leftPlane = new Texture(Gdx.files.internal("p8l.png"));
		trLeftPlane = new TextureRegion(leftPlane);
		rightPlane = new Texture(Gdx.files.internal("p8r.png"));
		trRightPlane = new TextureRegion(rightPlane);

		normalFramesPlane = new TextureRegion[3];
		normalFramesPlane[0] = trNormalPlane;
		normalFramesPlane[1] = trLeftPlane;
		normalFramesPlane[2] = trRightPlane;

		aniPlane = new Animation(1f, normalFramesPlane);
		currPlaneFrame = aniPlane.getKeyFrame(0);
	}

	public static void planeHealthSprite() {
		hundred = new Texture(Gdx.files.internal("100.png"));
		trHundred = new TextureRegion(hundred);
		ninety = new Texture(Gdx.files.internal("90.png"));
		trNinety = new TextureRegion(ninety);
		eighty = new Texture(Gdx.files.internal("80.png"));
		trEighty = new TextureRegion(eighty);
		seventy = new Texture(Gdx.files.internal("70.png"));
		trSeventy = new TextureRegion(seventy);
		sixty = new Texture(Gdx.files.internal("60.png"));
		trSixty = new TextureRegion(sixty);
		fifty = new Texture(Gdx.files.internal("50.png"));
		trFifty = new TextureRegion(fifty);
		forty = new Texture(Gdx.files.internal("40.png"));
		trForty = new TextureRegion(forty);
		thirty = new Texture(Gdx.files.internal("30.png"));
		trThirty = new TextureRegion(thirty);
		twenty = new Texture(Gdx.files.internal("20.png"));
		trTwenty = new TextureRegion(twenty);
		ten = new Texture(Gdx.files.internal("10.png"));
		trTen = new TextureRegion(ten);

		framesHealth = new TextureRegion[10];
		framesHealth[0] = trHundred;
		framesHealth[1] = trNinety;
		framesHealth[2] = trEighty;
		framesHealth[3] = trSeventy;
		framesHealth[4] = trSixty;
		framesHealth[5] = trFifty;
		framesHealth[6] = trForty;
		framesHealth[7] = trThirty;
		framesHealth[8] = trTwenty;
		framesHealth[9] = trTen;

		aniHealth = new Animation(1f, framesHealth);
		currHealthFrame = aniHealth.getKeyFrame(0);
	}

	public static void planeInsideDisplay() {
		if (Play.getPlane().getPosition().x < 10)
			Play.getPlane().getPosition().x = 10;
		if (Setting.getPlayStyle().equalsIgnoreCase("touch")) {
			if (Play.getPlane().getPosition().x > 795)
				Play.getPlane().getPosition().x = 795;
		} else if (Setting.getPlayStyle().equalsIgnoreCase("Accelerometer")) {
			if (Play.getPlane().getPosition().x > 830)
				Play.getPlane().getPosition().x = 830;
		}
		if (Play.getPlane().getPosition().y > 940)
			Play.getPlane().getPosition().y = 940;
		if (Play.getPlane().getPosition().y < -720)
			Play.getPlane().getPosition().y = -720;
	}

	public static void playStyle() {
		if (Setting.getPlayStyle().equalsIgnoreCase("touch")) {
			// process user input
			if (Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				Play.camera.unproject(touchPos);

				if (touchPos.x >= 880 && touchPos.x <= 1080
						&& touchPos.y <= Play.camera.position.y + 140
						&& touchPos.y >= Play.camera.position.y - 140) {
					if (Play.checkPower == true && Play.checkPower2 == true) {
						Explosion.explosionDrop();
						Play.pressPower = true;
					}
				} else {
					Play.getPlane();
					Plane.currPlaneFrame = Plane.aniPlane.getKeyFrame(0);
					Play.getPlane().getPosition().x = touchPos.x - 150;
					Play.getPlane().getPosition().y = Play.camera.position.y
							- touchPos.y + 70;
					planeRec.setPosition(new Vector2(touchPos.x - 150,
							Play.camera.position.y - touchPos.y + 70));
				}
			}
		} else if (Setting.getPlayStyle().equalsIgnoreCase("Accelerometer")) {
			if (Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				Play.camera.unproject(touchPos);
				if (touchPos.x >= 880 && touchPos.x <= 1080
						&& touchPos.y <= Play.camera.position.y + 140
						&& touchPos.y >= Play.camera.position.y - 140) {
					if (Play.checkPower == true && Play.checkPower2 == true) {
						Explosion.explosionDrop();
						Play.pressPower = true;
					}
				}
			}
			if (Gdx.input.getAccelerometerX() > 0) {
				Play.getPlane().getPosition().x -= Gdx.input
						.getAccelerometerX() + 3 + Gdx.graphics.getDeltaTime();
				Plane.currPlaneFrame = Plane.aniPlane.getKeyFrame(1);
			} else if (Gdx.input.getAccelerometerX() < 0) {
				Play.getPlane().getPosition().x -= Gdx.input
						.getAccelerometerX() - 3 + Gdx.graphics.getDeltaTime();
				Plane.currPlaneFrame = Plane.aniPlane.getKeyFrame(2);
			} else if (Gdx.input.getAccelerometerX() == 0) {
				Plane.currPlaneFrame = Plane.aniPlane.getKeyFrame(0);
			}

			if (Gdx.input.getAccelerometerY() > 0) {
				Play.getPlane().getPosition().y += Gdx.input
						.getAccelerometerY() + 2 + Gdx.graphics.getDeltaTime();
			} else if (Gdx.input.getAccelerometerY() < 0) {
				Play.getPlane().getPosition().y += Gdx.input
						.getAccelerometerY() - 10 + Gdx.graphics.getDeltaTime();
			}
			planeRec.setPosition(new Vector2(Play.getPlane().getPosition().x,
					Play.getPlane().getPosition().y));
		}
	}

	public static void batchPlane() {
		Play.getPlane();
		Play.batch.draw(Plane.getCurrPlaneFrame(), Play.getPlane()
				.getPosition().x, (Play.camera.position.y - Play.getPlane()
				.getPosition().y), Play.getPlane().getSize().x, Play.getPlane()
				.getSize().y);
	}

	public static void batchPlaneHealth() {
		Play.batch.draw(getCurrHealthFrame(), 0, Play.camera.position.y - 960);
	}

	public static TextureRegion getCurrPlaneFrame() {
		return currPlaneFrame;
	}

	public static void setCurrPlaneFrame(TextureRegion currPlaneFrame) {
		Plane.currPlaneFrame = currPlaneFrame;
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

	public Rectangle getPlaneRec() {
		return planeRec;
	}

	public void setPlaneRec(Rectangle planeRec) {
		Plane.planeRec = planeRec;
	}

	public static TextureRegion getCurrHealthFrame() {
		return currHealthFrame;
	}

	public static void setCurrHealthFrame(TextureRegion currHealthFrame) {
		Plane.currHealthFrame = currHealthFrame;
	}

	public static void disposePlane() {
		normalPlane.dispose();
		leftPlane.dispose();
		rightPlane.dispose();
		//
		hundred.dispose();
		ninety.dispose();
		eighty.dispose();
		seventy.dispose();
		sixty.dispose();
		fifty.dispose();
		forty.dispose();
		thirty.dispose();
		twenty.dispose();
		ten.dispose();
	}

}
