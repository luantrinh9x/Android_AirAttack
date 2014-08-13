package Characters;

import Screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Health {

	private Vector2 position;
	private Vector2 size;
	public static Rectangle healthRec;
	static Health health;
	public static Array<Health> arrayHealth;

	public static Texture normalHealth, whiteHealth;
	public static TextureRegion trNormalHealth, trWhiteHealth, currHealthFrame;
	public static TextureRegion[] framesHealth;
	public static Animation aniHealth;
	public static long lastHealthTime;

	public Health(Vector2 position, Vector2 size) {
		super();
		this.position = position;
		this.size = size;
		healthRec = new Rectangle(position.x, position.y, size.x, size.y);
	}

	public static void healthSprite() {
		normalHealth = new Texture(Gdx.files.internal("health.png"));
		trNormalHealth = new TextureRegion(normalHealth);
		whiteHealth = new Texture(Gdx.files.internal("health1.png"));
		trWhiteHealth = new TextureRegion(whiteHealth);

		framesHealth = new TextureRegion[2];
		framesHealth[0] = trNormalHealth;
		framesHealth[1] = trWhiteHealth;

		aniHealth = new Animation(0.5f, framesHealth);
	}

	public static void healthDrop() {
		health = new Health(new Vector2(MathUtils.random(50, 850),
				Play.camera.position.y + MathUtils.random(-700, 600)),
				new Vector2(160, 173));
		arrayHealth.add(health);
		lastHealthTime = TimeUtils.millis();
	}

	public static void batchHealth() {
		for (Health drop : Health.getArrayHealth()) {
			Play.batch.draw(getCurrHealthFrame(), drop.getPosition().x,
					drop.getPosition().y, drop.getSize().x, drop.getSize().y);
		}
	}

	public static TextureRegion getCurrHealthFrame() {
		return currHealthFrame;
	}

	public static void setCurrHealthFrame(TextureRegion currHealthFrame) {
		Health.currHealthFrame = currHealthFrame;
	}

	public static Array<Health> getArrayHealth() {
		return arrayHealth;
	}

	public static void setArrayHealth(Array<Health> arrayHealth) {
		Health.arrayHealth = arrayHealth;
	}

	public static Rectangle getHealthRec() {
		return healthRec;
	}

	public static void setHealthRec(Rectangle healthRec) {
		Health.healthRec = healthRec;
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

	public static void disposeHealth() {
		normalHealth.dispose();
		whiteHealth.dispose();
	}
}
