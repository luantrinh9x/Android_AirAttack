package Characters;

import Screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Fire {

	public static Rectangle rocketFireRec;
	public static Rectangle bossFireRec;

	private static final int FRAME_COLS = 5;
	private static final int FRAME_ROWS = 4;

	public static Animation fireAnimation;
	public static Texture fireSheet;
	public static TextureRegion[] fireFrames;
	public static TextureRegion currRocketFireFrame;

	private static final int bFRAME_COLS = 8;
	private static final int bFRAME_ROWS = 6;

	public static Animation bossFireAnimation;
	public static Texture bossFireSheet;
	public static TextureRegion[] bossFireFrames;
	public static TextureRegion currBossFireFrame;

	public static void rocketFireSprite() {
		fireSheet = new Texture(Gdx.files.internal("fire.png"));
		TextureRegion[][] tmp = TextureRegion.split(fireSheet,
				fireSheet.getWidth() / FRAME_COLS, fireSheet.getHeight()
						/ FRAME_ROWS);
		fireFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				fireFrames[index++] = tmp[i][j];
			}
		}
		fireAnimation = new Animation(0.05f, fireFrames);
	}

	public static void bossFireSprite() {
		bossFireSheet = new Texture(Gdx.files.internal("bossFire.png"));
		TextureRegion[][] tmp = TextureRegion.split(bossFireSheet,
				bossFireSheet.getWidth() / bFRAME_COLS,
				bossFireSheet.getHeight() / bFRAME_ROWS);
		bossFireFrames = new TextureRegion[bFRAME_COLS * bFRAME_ROWS];
		int index = 0;
		for (int i = 0; i < bFRAME_ROWS; i++) {
			for (int j = 0; j < bFRAME_COLS; j++) {
				bossFireFrames[index++] = tmp[i][j];
			}
		}
		bossFireAnimation = new Animation(0.05f, bossFireFrames);
	}

	public static void batchRocketFire() {
		Play.batch.draw(Fire.getCurrRocketFireFrame(),
				Fire.getRocketFireRec().x, Fire.getRocketFireRec().y);
	}

	public static void batchBossFire() {
		Play.batch.draw(Fire.getCurrBossFireFrame(),
				Fire.getBossFireRec().x + 100, Fire.getBossFireRec().y);
	}

	public static TextureRegion getCurrRocketFireFrame() {
		return currRocketFireFrame;
	}

	public static void setCurrRocketFireFrame(TextureRegion currRocketFireFrame) {
		Fire.currRocketFireFrame = currRocketFireFrame;
	}

	public static TextureRegion getCurrBossFireFrame() {
		return currBossFireFrame;
	}

	public static void setCurrBossFireFrame(TextureRegion currBossFireFrame) {
		Fire.currBossFireFrame = currBossFireFrame;
	}

	public static Rectangle getRocketFireRec() {
		return rocketFireRec;
	}

	public static void setRocketFireRec(Rectangle rocketFireRec) {
		Fire.rocketFireRec = rocketFireRec;
	}

	public static Rectangle getBossFireRec() {
		return bossFireRec;
	}

	public static void setBossFireRec(Rectangle bossFireRec) {
		Fire.bossFireRec = bossFireRec;
	}

	public static void disposeFire() {
		fireSheet.dispose();
		bossFireSheet.dispose();
	}
}
