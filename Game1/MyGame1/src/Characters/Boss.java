package Characters;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import Screens.Menu;
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

public class Boss implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4647936967238419131L;
	private Vector2 position;
	private Vector2 size;
	private transient Rectangle bossRec;
	static Boss boss;
	public static Array<Boss> arrayBoss;
	public static long lastBossTime;

	public static Texture normalBoss, whiteBoss;
	public static TextureRegion trNormalBoss, trWhiteBoss, currBossFrame;
	public static TextureRegion[] framesBoss;
	public static Animation aniBoss;

	//
	public static Texture hundred, ninety, eighty, seventy, sixty, fifty,
			forty, thirty, twenty, ten;
	public static TextureRegion trHundred, trNinety, trEighty, trSeventy,
			trSixty, trFifty, trForty, trThirty, trTwenty, trTen,
			currHealthFrame;
	public static TextureRegion[] framesHealth;
	public static Animation aniHealth;

	//

	public Boss(Vector2 position, Vector2 size) {
		super();
		this.position = position;
		this.size = size;
		bossRec = new Rectangle(position.x, position.y, size.x, size.y);
	}

	public static void bossSprite() {
		normalBoss = new Texture(Gdx.files.internal("boss.png"));
		trNormalBoss = new TextureRegion(normalBoss);
		whiteBoss = new Texture(Gdx.files.internal("boss1.png"));
		trWhiteBoss = new TextureRegion(whiteBoss);

		framesBoss = new TextureRegion[2];
		framesBoss[0] = trNormalBoss;
		framesBoss[1] = trWhiteBoss;

		aniBoss = new Animation(1f, framesBoss);
		currBossFrame = aniBoss.getKeyFrame(0);
	}

	public static void bossHealthSprite() {
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

	public static void bossDrop() {
		if (Menu.isLoadGame() == true) {
			loadBoss();
		} else {
			boss = new Boss(new Vector2(MathUtils.random(0, 600),
					Play.camera.position.y + 940), new Vector2(484, 438));
			arrayBoss.add(boss);
		}

		lastBossTime = TimeUtils.millis();
	}
	
	private static void loadBoss() {
		Boss b = null;
		try {
			FileInputStream fileIn = new FileInputStream(
					"/sdcard/Android/boss.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			b = (Boss) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Boss class not found");
			c.printStackTrace();
			return;
		}
		boss = new Boss(new Vector2(b.getPosition().x, b.getPosition().y),
				new Vector2(b.getSize().x, b.getSize().y));
		arrayBoss.add(boss);
	}

	public static void batchBoss() {
		for (Boss drop : Boss.getArrayBoss()) {
			Play.batch.draw(Boss.currBossFrame.getTexture(),
					drop.getPosition().x, drop.getPosition().y,
					drop.getSize().x, drop.getSize().y);
		}
	}

	public static void batchBossHealth() {
		Play.batch.draw(getCurrHealthFrame(), 0, Play.camera.position.y + 955);
	}

	public static TextureRegion getCurrBossFrame() {
		return currBossFrame;
	}

	public static void setCurrBossFrame(TextureRegion currBossFrame) {
		Boss.currBossFrame = currBossFrame;
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

	public static Array<Boss> getArrayBoss() {
		return arrayBoss;
	}

	public static void setArrayBoss(Array<Boss> arrayBoss) {
		Boss.arrayBoss = arrayBoss;
	}

	public Rectangle getBossRec() {
		return bossRec;
	}

	public void setBossRec(Rectangle bossRec) {
		this.bossRec = bossRec;
	}

	public static TextureRegion getCurrHealthFrame() {
		return currHealthFrame;
	}

	public static void setCurrHealthFrame(TextureRegion currHealthFrame) {
		Boss.currHealthFrame = currHealthFrame;
	}

	public static void disposeBoss() {
		normalBoss.dispose();
		whiteBoss.dispose();
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
