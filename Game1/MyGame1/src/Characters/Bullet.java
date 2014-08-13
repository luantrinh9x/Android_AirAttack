package Characters;

import Screens.Play;
import Screens.Setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Bullet {

	private Vector2 position;
	private Vector2 size;
	private Rectangle bulletRec;
	static Bullet bullet;
	public static Array<Bullet> arrayBullet;

	public static Texture bulletImage;
	public static long lastBulletTime;

	public Bullet(Vector2 position, Vector2 size) {
		super();
		this.position = position;
		this.size = size;
		bulletRec = new Rectangle(position.x, position.y, size.x, size.y);
	}

	public static void bulletHit() {
		float x = 0;
		if (Setting.getPlayStyle().equalsIgnoreCase("Accelerometer")) {
			if (Gdx.input.getAccelerometerX() > 0) {
				x = Play.getPlane().getPlaneRec().x + 82;
			} else if (Gdx.input.getAccelerometerX() < 0) {
				x = Play.getPlane().getPlaneRec().x + 97;
			}
		} else if (Setting.getPlayStyle().equalsIgnoreCase("touch")) {
			x = Play.getPlane().getPlaneRec().x + 110;
		}

		bullet = new Bullet(new Vector2(x, Play.camera.position.y
				- Play.getPlane().getPlaneRec().y + 100), new Vector2(61, 93));

		arrayBullet.add(bullet);

		Bullet.lastBulletTime = TimeUtils.millis();
	}

	public static void batchBullet() {
		for (Bullet drop : Bullet.getArrayBullet()) {
			Play.batch.draw(Bullet.getBulletImage(), drop.getPosition().x,
					drop.getPosition().y, drop.getSize().x, drop.getSize().y);
		}
	}

	public static Texture getBulletImage() {
		return bulletImage;
	}

	public static void setBulletImage(Texture bulletImage) {
		Bullet.bulletImage = bulletImage;
	}

	public static Array<Bullet> getArrayBullet() {
		return arrayBullet;
	}

	public static void setArrayBullet(Array<Bullet> arrayBullet) {
		Bullet.arrayBullet = arrayBullet;
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

	public Rectangle getBulletRec() {
		return bulletRec;
	}

	public void setBulletRec(Rectangle bulletRec) {
		this.bulletRec = bulletRec;
	}

	public static void disposeBullet() {
		bulletImage.dispose();
	}
}
