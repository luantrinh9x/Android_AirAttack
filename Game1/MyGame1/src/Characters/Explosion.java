package Characters;

import Screens.Play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Explosion {
	private Vector2 position;
	private Vector2 size;
	private static Explosion explosion;
	public static Rectangle explosionRec;
	public static Array<Explosion> arrayExplosion;

	public static Texture mana0, mana1, mana2, mana3, mana4, mana5, mana6,
			mana7, mana8;
	public static TextureRegion trMana0, trMana1, trMana2, trMana3, trMana4,
			trMana5, trMana6, trMana7, trMana8, currManaFrame;
	public static TextureRegion[] framesMana;
	public static Animation aniMana;
	//
	public static Texture ex0, ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9,
			ex10, ex11;
	public static TextureRegion trEx0, trEx1, trEx2, trEx3, trEx4, trEx5,
			trEx6, trEx7, trEx8, trEx9, trEx10, trEx11, currExplosionFrame;
	public static TextureRegion[] framesExplosion;
	public static Animation aniExplosion;
	//
	public static Texture three, two, one, zero;
	public static TextureRegion trThree, trTwo, trOne, trZero, currReadyFrame;
	public static TextureRegion[] framesReady;
	public static Animation aniReady;

	public Explosion(Vector2 position, Vector2 size) {
		super();
		this.position = position;
		this.size = size;
		explosionRec = new Rectangle(position.x, position.y, size.x, size.y);
	}

	public static void readySprite() {
		three = new Texture(Gdx.files.internal("ready3.png"));
		trThree = new TextureRegion(three);
		two = new Texture(Gdx.files.internal("ready2.png"));
		trTwo = new TextureRegion(two);
		one = new Texture(Gdx.files.internal("ready1.png"));
		trOne = new TextureRegion(one);
		zero = new Texture(Gdx.files.internal("ready0.png"));
		trZero = new TextureRegion(zero);

		framesReady = new TextureRegion[4];
		framesReady[0] = trThree;
		framesReady[1] = trTwo;
		framesReady[2] = trOne;
		framesReady[3] = trZero;

		aniReady = new Animation(1f, framesReady);
	}

	public static void manaSprite() {
		mana0 = new Texture(Gdx.files.internal("power0.png"));
		trMana0 = new TextureRegion(mana0);
		mana1 = new Texture(Gdx.files.internal("power1.png"));
		trMana1 = new TextureRegion(mana1);
		mana2 = new Texture(Gdx.files.internal("power2.png"));
		trMana2 = new TextureRegion(mana2);
		mana3 = new Texture(Gdx.files.internal("power3.png"));
		trMana3 = new TextureRegion(mana3);
		mana4 = new Texture(Gdx.files.internal("power4.png"));
		trMana4 = new TextureRegion(mana4);
		mana5 = new Texture(Gdx.files.internal("power5.png"));
		trMana5 = new TextureRegion(mana5);
		mana6 = new Texture(Gdx.files.internal("power6.png"));
		trMana6 = new TextureRegion(mana6);
		mana7 = new Texture(Gdx.files.internal("power7.png"));
		trMana7 = new TextureRegion(mana7);
		mana8 = new Texture(Gdx.files.internal("powerfull.png"));
		trMana8 = new TextureRegion(mana8);

		framesMana = new TextureRegion[9];
		framesMana[0] = trMana0;
		framesMana[1] = trMana1;
		framesMana[2] = trMana2;
		framesMana[3] = trMana3;
		framesMana[4] = trMana4;
		framesMana[5] = trMana5;
		framesMana[6] = trMana6;
		framesMana[7] = trMana7;
		framesMana[8] = trMana8;

		aniMana = new Animation(1f, framesMana);
	}

	public static void explosionSprite() {
		ex0 = new Texture(Gdx.files.internal("docam0.png"));
		trEx0 = new TextureRegion(ex0);

		ex1 = new Texture(Gdx.files.internal("docam1.png"));
		trEx1 = new TextureRegion(ex1);

		ex2 = new Texture(Gdx.files.internal("docam2.png"));
		trEx2 = new TextureRegion(ex2);

		ex3 = new Texture(Gdx.files.internal("docam3.png"));
		trEx3 = new TextureRegion(ex3);

		ex4 = new Texture(Gdx.files.internal("docam4.png"));
		trEx4 = new TextureRegion(ex4);

		ex5 = new Texture(Gdx.files.internal("docam5.png"));
		trEx5 = new TextureRegion(ex5);

		ex6 = new Texture(Gdx.files.internal("docam6.png"));
		trEx6 = new TextureRegion(ex6);

		ex7 = new Texture(Gdx.files.internal("docam7.png"));
		trEx7 = new TextureRegion(ex7);

		ex8 = new Texture(Gdx.files.internal("docam8.png"));
		trEx8 = new TextureRegion(ex8);

		ex9 = new Texture(Gdx.files.internal("docam9.png"));
		trEx9 = new TextureRegion(ex9);

		ex10 = new Texture(Gdx.files.internal("docam10.png"));
		trEx10 = new TextureRegion(ex10);

		ex11 = new Texture(Gdx.files.internal("docam11.png"));
		trEx11 = new TextureRegion(ex11);

		framesExplosion = new TextureRegion[12];
		framesExplosion[0] = trEx0;
		framesExplosion[1] = trEx1;
		framesExplosion[2] = trEx2;
		framesExplosion[3] = trEx3;
		framesExplosion[4] = trEx4;
		framesExplosion[5] = trEx5;
		framesExplosion[6] = trEx6;
		framesExplosion[7] = trEx7;
		framesExplosion[8] = trEx8;
		framesExplosion[9] = trEx9;
		framesExplosion[10] = trEx10;
		framesExplosion[11] = trEx11;

		aniExplosion = new Animation(0.025f, framesExplosion);
	}

	public static void explosionDrop() {
		explosion = new Explosion(new Vector2(0, Play.camera.position.y - 960),
				new Vector2(1080, 1920));
		arrayExplosion.add(explosion);
	}

	public static void batchExplosion() {
		for (Explosion drop : Explosion.getArrayExplosion()) {
			Play.batch.draw(Explosion.getCurrExplosionFrame(),
					drop.getPosition().x, drop.getPosition().y,
					drop.getSize().x, drop.getSize().y);
		}
	}

	public static void batchMana() {
		Play.batch.draw(getCurrManaFrame(), 870, Play.camera.position.y - 100);
	}

	public static void batchReady() {
		Play.batch.draw(getCurrReadyFrame(), 0, Play.camera.position.y - 960);
	}

	public static TextureRegion getCurrManaFrame() {
		return currManaFrame;
	}

	public static void setCurrManaFrame(TextureRegion currManaFrame) {
		Explosion.currManaFrame = currManaFrame;
	}

	public static Array<Explosion> getArrayExplosion() {
		return arrayExplosion;
	}

	public static void setArrayExplosion(Array<Explosion> arrayExplosion) {
		Explosion.arrayExplosion = arrayExplosion;
	}

	public static TextureRegion getCurrExplosionFrame() {
		return currExplosionFrame;
	}

	public static void setCurrExplosionFrame(TextureRegion currExplosionFrame) {
		Explosion.currExplosionFrame = currExplosionFrame;
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

	public static TextureRegion getCurrReadyFrame() {
		return currReadyFrame;
	}

	public static void setCurrReadyFrame(TextureRegion currReadyFrame) {
		Explosion.currReadyFrame = currReadyFrame;
	}

	public static void disposeExplosion() {
		mana0.dispose();
		mana1.dispose();
		mana2.dispose();
		mana3.dispose();
		mana4.dispose();
		mana5.dispose();
		mana6.dispose();
		mana7.dispose();
		mana8.dispose();
		//
		ex0.dispose();
		ex1.dispose();
		ex2.dispose();
		ex3.dispose();
		ex4.dispose();
		ex5.dispose();
		ex6.dispose();
		ex7.dispose();
		ex8.dispose();
		ex9.dispose();
		ex10.dispose();
		ex11.dispose();
		//
		three.dispose();
		two.dispose();
		one.dispose();
		zero.dispose();
	}
}
