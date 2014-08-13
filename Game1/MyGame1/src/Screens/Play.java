package Screens;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import Characters.Boss;
import Characters.Bullet;
import Characters.Explosion;
import Characters.Fire;
import Characters.Health;
import Characters.Laser;
import Characters.Plane;
import Characters.Rocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Array;
import com.gdx.main.AirAttack;

public class Play implements Screen {
	public static Texture backgroundImage;
	private static Sound hitSound, bossSound, laserSound;
	private static Music backgroudMusic;
	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	private Sprite sprite;
	private float stateTime;
	private AirAttack aa;
	private Play play;
	//
	public static int health = 1000;
	private int loadHealth = 0;
	public static int score = 0;
	private int loadScore = 0;
	private static int AI = 0;
	public static int countHitBoss = 0;
	private int loadCountHitBoss = 0;
	public static int bossKilled = 0;
	public static int loadBossKilled = 0;
	public static int rocketKilled = 0;
	private int loadRocketKilled = 0;

	private Iterator<Rocket> iterRocket;
	private Iterator<Bullet> iterBullet;
	private Iterator<Boss> iterBoss;
	private Iterator<Laser> iterLaser;
	private Iterator<Health> iterHealth;
	//
	public static Plane plane;

	private Rocket roc;
	private Bullet bul;
	public static Boss bos;
	private Laser las;
	private static Health hea;

	// rocket speed
	private int rocketSpeedPauseEasy = 20;
	private int rocketSpeedPauseMedium = 40;
	private int rocketSpeedPauseHard = 60;
	private boolean callRocketPause = false;
	// bullet speed
	private int bulletSpeedPauseEasy = 700;
	private int bulletSpeedPauseMedium = 1400;
	private int bulletSpeedPauseHard = 2100;
	private boolean callBulletPause = false;
	// boss speed
	private float bossSpeedPauseEasy = 1f;
	private float bossSpeedPauseMedium = 2f;
	private float bossSpeedPauseHard = 3f;
	private int bossMoveLRPauseEasy = 10;
	private int bossMoveLRPauseMedium = 20;
	private int bossMoveLRPauseHard = 300;
	private boolean callBossPause = false;
	// laser
	private int laserSpeedPauseEasy = 1500;
	private int laserSpeedPauseMedium = 3000;
	private int laserSpeedPauseHard = 6000;
	// AI
	private int AIMediumPause = MathUtils.random(0, 100);
	private int AIHardPause = MathUtils.random(0, 200);
	// camera pause
	private float cameraPause = 0.075f;
	private float loadCamera = 0;
	// health
	private boolean planeHealth = false;
	private boolean bossHealth = false;
	private String showBossHealth = "";
	private String showBossHealthLeft = "";
	// boss move
	private String bossMoveLR = "left";
	// health
	private boolean callHealthPause = false;
	// timer
	private float startTime = System.nanoTime();
	public static float secondsTime = 0;
	private static float loadTime = 0;
	// victory distance
	private static int victoryDistance = 0;
	// status
	public static String status = "";
	// powerfull
	public static boolean checkPower = false;
	public static boolean checkPower2 = true;
	public static boolean pressPower = false;
	private float startPowerTime = System.nanoTime();
	private float powerTime = 0;

	public Play(AirAttack aa) {
		this.aa = aa;
		this.play = this;

		Explosion.readySprite();
		Plane.planeSprite();
		Boss.bossSprite();
		Rocket.rocketSprite();
		Fire.rocketFireSprite();
		Fire.bossFireSprite();
		Laser.laserSprite();
		Plane.planeHealthSprite();
		Boss.bossHealthSprite();
		Health.healthSprite();
		Explosion.manaSprite();
		Explosion.explosionSprite();

		batch = new SpriteBatch();
		stateTime = 0f;
		Gdx.input.setCatchBackKey(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1080, 1920);

		batch = new SpriteBatch();
		// background image
		backgroundImage();
		// sound
		sound();
		// music
		music();
		// create a Rectangle to logically represent the plane
		createPlaneRec();
		// hide fire at start
		createFireRec();

		if (Menu.loadGame == true) {
			try {
				loadCamera();
				camera.position.y = loadCamera;
				loadTime();
				secondsTime = loadTime;
				loadScore();
				score = loadScore;
				loadLevel();
				loadStyle();
				loadPlaneHealth();
				health = loadHealth;
				loadBossHealth();
				countHitBoss = loadCountHitBoss;
				loadRocketKilled();
				rocketKilled = loadRocketKilled;
				loadBossKilled();
				bossKilled = loadBossKilled;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// create array and spawn the first drop
		createFirstDrop();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (System.nanoTime() - startTime >= 1000000000) {
			if (victoryDistance == 2600) {
				secondsTime++;
			}
			startTime = System.nanoTime();
		}

		if (secondsTime == 1) {
			Explosion.currReadyFrame = Explosion.aniReady.getKeyFrame(0);
		} else if (secondsTime == 2) {
			Explosion.currReadyFrame = Explosion.aniReady.getKeyFrame(1);
		} else if (secondsTime == 3) {
			Explosion.currReadyFrame = Explosion.aniReady.getKeyFrame(2);
		} else if (secondsTime >= 4) {
			Explosion.currReadyFrame = Explosion.aniReady.getKeyFrame(3);
		}

		if (bossKilled == 1) {
			victoryDistance = 2880;
			setStatus("Win");
			callBulletPause = true;
			callHealthPause = true;
			callRocketPause = true;
			if (Play.getPlane().getPosition().y == -720) {
				dispose();
				aa.setScreen(new Victory(aa));
			}
		} else if (bossKilled == 0) {
			victoryDistance = 2600;
			setStatus("Lose");
		}

		if (camera.position.y <= victoryDistance) {
			if (victoryDistance == 2600) {
				camera.position.y += cameraPause;
			} else if (victoryDistance == 2880) {
				camera.position.y += 5f;
			}
		}

		// power key frame
		powerUp();

		// tell the camera to update its matrices.
		camera.update();
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		stateTimeAnimation();
		Plane.playStyle();

		batch.begin();
		sprite.draw(batch);
		Explosion.batchReady();
		Health.batchHealth();
		Plane.batchPlane();
		Fire.batchRocketFire();
		Fire.batchBossFire();
		Rocket.batchRocket();
		Bullet.batchBullet();
		Laser.batchLaser();
		Boss.batchBoss();
		Plane.batchPlaneHealth();
		Boss.batchBossHealth();
		Explosion.batchMana();
		Explosion.batchExplosion();
		showScoreAndHealth();

		// make sure the plane stays within the screen bounds
		Plane.planeInsideDisplay();

		// check to create new drop
		createNewDrop();

		// ///////////////////////////////////////
		iterBoss = Boss.getArrayBoss().iterator();
		while (iterBoss.hasNext()) {
			try {
				bos = iterBoss.next();
			} catch (Exception e) {
				continue;
			}
			waitBossCome();
			bos.getBossRec().setPosition(
					new Vector2(bos.getPosition().x, bos.getPosition().y));
			if (bos.getPosition().y < Play.camera.position.y - 1350) {
				minusScoreWhenBossPass();
				try {
					iterBoss.remove();
				} catch (Exception e) {
					continue;
				}
			}

			if (bos.getBossRec().overlaps(plane.getPlaneRec())) {
				try {
					health -= 5;
				} catch (Exception e) {
					continue;
				}
			}
		}
		// //////////////////////////////////////////////
		iterBullet = Bullet.getArrayBullet().iterator();
		while (iterBullet.hasNext()) {
			try {
				bul = iterBullet.next();
			} catch (Exception e) {
				continue;
			}
			bulletUpSpeed();
			bul.getBulletRec().setPosition(
					new Vector2(bul.getPosition().x, bul.getPosition().y));

			if (bul.getPosition().y > Play.camera.position.y + 940) {
				try {
					iterBullet.remove();
				} catch (Exception e) {
					continue;
				}
			}
			// ////////////////////////////////////
			iterRocket = Rocket.getArrayRocket().iterator();
			while (iterRocket.hasNext()) {
				try {
					roc = iterRocket.next();
				} catch (Exception e) {
					continue;
				}

				rocketDownSpeed();
				roc.getRocketRec().setPosition(
						new Vector2(roc.getPosition().x, roc.getPosition().y));

				AI++;
				AI();

				if (roc.getPosition().y < Play.camera.position.y - 1050) {
					score--;
					if (score < 1) {
						score = 0;
					}
					try {
						iterRocket.remove();
					} catch (Exception e) {
						continue;
					}
				}

				if (plane.getPlaneRec().overlaps(roc.getRocketRec())) {
					minusHealth();
					try {
						hitSound.play();
						iterRocket.remove();
					} catch (Exception e) {
						continue;
					}
				}

				// //////////////////////////////
				if (bul.getBulletRec().overlaps(roc.getRocketRec())) {
					Fire.getRocketFireRec().x = roc.getRocketRec().getX();
					Fire.getRocketFireRec().y = roc.getRocketRec().getY();
					plusScore();
					try {
						hitSound.play();
						iterBullet.remove();
						iterRocket.remove();
						rocketKilled++;
					} catch (Exception e) {
						continue;
					}
				}
				// ///////////////////////////
			}

			// //////////////////////////////////
			if (bos.getBossRec().overlaps(bul.getBulletRec())) {
				countHitBoss++;
				minusBossHealth();
				try {
					iterBullet.remove();
				} catch (Exception e) {
					continue;
				}
				if (Setting.getDifficult().equalsIgnoreCase("easy")) {
					if (countHitBoss >= 1000) {
						try {
							bossSound.play();
							iterBoss.remove();
							bossKilled++;
						} catch (Exception e) {
							continue;
						}
						score += 30;
						Fire.getBossFireRec().x = bos.getBossRec().getX();
						Fire.getBossFireRec().y = bos.getBossRec().getY();
					}
				} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
					if (countHitBoss >= 2000) {
						try {
							bossSound.play();
							iterBoss.remove();
							bossKilled++;
						} catch (Exception e) {
							continue;
						}
						score += 20;
						Fire.getBossFireRec().x = bos.getBossRec().getX();
						Fire.getBossFireRec().y = bos.getBossRec().getY();
					}
				} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
					if (countHitBoss >= 3000) {
						try {
							bossSound.play();
							iterBoss.remove();
							bossKilled++;
						} catch (Exception e) {
							continue;
						}
						score += 10;
						Fire.getBossFireRec().x = bos.getBossRec().getX();
						Fire.getBossFireRec().y = bos.getBossRec().getY();
					}
				}
			}
			// ///////////////////////////////////
		}
		// //////////////////////////////////////////
		iterLaser = Laser.getArrayLaser().iterator();
		while (iterLaser.hasNext()) {
			try {
				las = iterLaser.next();
			} catch (Exception e) {
				continue;
			}
			laserDownSpeed();
			las.getLaserRec().setPosition(
					new Vector2(las.getPosition().x, las.getPosition().y));

			if (las.getPosition().y < Play.camera.position.y - 3000) {
				try {
					iterLaser.remove();
				} catch (Exception e) {
					continue;
				}
			}

			if (las.getLaserRec().overlaps(plane.getPlaneRec())) {
				try {
					health -= 5;
				} catch (Exception e) {
					continue;
				}
			}
		}
		// //////////////////////////////////////////
		iterHealth = Health.getArrayHealth().iterator();
		while (iterHealth.hasNext()) {
			try {
				hea = iterHealth.next();
			} catch (Exception e) {
				continue;
			}

			if (hea.getPosition().y < Play.camera.position.y - 1070) {
				try {
					iterHealth.remove();
				} catch (Exception e) {
					continue;
				}
			}
			if (bul.getBulletRec().overlaps(hea.getHealthRec())) {
				try {
					health += 1000;
					iterBullet.remove();
					iterHealth.remove();
				} catch (Exception e) {
					continue;
				}
			}
		}

		if (pressPower == true) {
			try {
				iterBullet.remove();
				iterRocket.remove();
				iterLaser.remove();
				if (Setting.getDifficult().equalsIgnoreCase("easy")) {
					countHitBoss += 100;
					if (countHitBoss >= 900) {
						countHitBoss = 900;
					}
				} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
					countHitBoss += 200;
					if (countHitBoss >= 1900) {
						countHitBoss = 1900;
					}
				} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
					countHitBoss += 300;
					if (countHitBoss >= 2900) {
						countHitBoss = 2900;
					}
				}
			} catch (Exception e) {
			}
			if (System.nanoTime() - startPowerTime >= 1000000000) {
				powerTime++;
				startPowerTime = System.nanoTime();
				if (powerTime >= 5f) {
					pressPower = false;
					checkPower2 = false;
				}
			}
		}

		// ////////////////////////////////////////////////
		if (health > 3000) {
			health = 3000;
		}

		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			if (countHitBoss >= 1000) {
				countHitBoss = 1000;
			}
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			if (countHitBoss >= 2000) {
				countHitBoss = 2000;
			}
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			if (countHitBoss >= 3000) {
				countHitBoss = 3000;
			}
		}

		if (health <= 0) {
			dispose();
			aa.setScreen(new GameOver(aa));
		}

		// //////////////////////////////
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// press back key
		pressBack();
		resumeFromPause();
	}

	private void resumeFromPause() {
		backgroudMusic.play();
		//
		rocketSpeedPauseEasy = 20;
		rocketSpeedPauseMedium = 40;
		rocketSpeedPauseHard = 60;
		callRocketPause = false;
		// bullet
		bulletSpeedPauseEasy = 700;
		bulletSpeedPauseMedium = 1400;
		bulletSpeedPauseHard = 2100;
		callBulletPause = false;
		// boss
		bossSpeedPauseEasy = 1f;
		bossSpeedPauseMedium = 2f;
		bossSpeedPauseHard = 3f;
		callBossPause = false;
		bossMoveLRPauseEasy = 10;
		bossMoveLRPauseMedium = 20;
		bossMoveLRPauseHard = 300;
		// laser
		laserSpeedPauseEasy = 1500;
		laserSpeedPauseMedium = 3000;
		laserSpeedPauseHard = 6000;
		//
		AIMediumPause = MathUtils.random(0, 100);
		AIHardPause = MathUtils.random(0, 200);
		//
		cameraPause = 0.075f;
		// health
		planeHealth = false;
		bossHealth = false;
		// health
		callHealthPause = false;
	}

	private void pressBack() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyUp(final int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					// pause background music
					backgroudMusic.pause();
					// pause rocket
					rocketSpeedPauseEasy = 0;
					rocketSpeedPauseMedium = 0;
					rocketSpeedPauseHard = 0;
					callRocketPause = true;
					// bullet
					bulletSpeedPauseEasy = 0;
					bulletSpeedPauseMedium = 0;
					bulletSpeedPauseHard = 0;
					callBulletPause = true;
					// boss
					bossSpeedPauseEasy = 0;
					bossSpeedPauseMedium = 0;
					bossSpeedPauseHard = 0;
					callBossPause = true;
					bossMoveLRPauseEasy = 0;
					bossMoveLRPauseMedium = 0;
					bossMoveLRPauseHard = 0;
					// laser
					laserSpeedPauseEasy = 0;
					laserSpeedPauseMedium = 0;
					laserSpeedPauseHard = 0;
					// AI
					AIMediumPause = 0;
					AIHardPause = 0;
					// camera
					cameraPause = 0f;
					// health
					planeHealth = true;
					bossHealth = true;
					// health
					callHealthPause = true;
					//
					aa.setScreen(new GamePause(aa));
					aa.play = play;
				}
				return super.keyDown(keycode);
			}
		});
	}

	private void stateTimeAnimation() {
		stateTime += Gdx.graphics.getDeltaTime();

		Fire.currBossFireFrame = Fire.bossFireAnimation.getKeyFrame(stateTime,
				true);

		Fire.currRocketFireFrame = Fire.fireAnimation.getKeyFrame(stateTime,
				true);

		for (Rocket r : Rocket.getArrayRocket()) {
			r.setCurrRocketFrame(r.getAniRocket().getKeyFrame(stateTime, true));
		}

		Laser.currLaserFrame = Laser.aniLaser.getKeyFrame(stateTime, true);

		Health.currHealthFrame = Health.aniHealth.getKeyFrame(stateTime, true);

		Boss.currBossFrame = Boss.aniBoss.getKeyFrame(stateTime, true);

		Explosion.currExplosionFrame = Explosion.aniExplosion.getKeyFrame(
				stateTime, pressPower);

	}

	private void showScoreAndHealth() {
		//
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			if ((1000 - countHitBoss) <= 400) {
				AirAttack.font.setColor(Color.RED);
			} else if ((1000 - countHitBoss) <= 700) {
				AirAttack.font.setColor(Color.YELLOW);
			} else if ((1000 - countHitBoss) <= 1000) {
				AirAttack.font.setColor(Color.GREEN);
			}
			showBossHealth = "/1000";
			showBossHealthLeft = Integer.toString(1000 - countHitBoss);
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			if ((2000 - countHitBoss) <= 800) {
				AirAttack.font.setColor(Color.RED);
			} else if ((2000 - countHitBoss) <= 1400) {
				AirAttack.font.setColor(Color.YELLOW);
			} else if ((2000 - countHitBoss) <= 2000) {
				AirAttack.font.setColor(Color.GREEN);
			}
			showBossHealth = "/2000";
			showBossHealthLeft = Integer.toString(2000 - countHitBoss);
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			if ((3000 - countHitBoss) <= 1200) {
				AirAttack.font.setColor(Color.RED);
			} else if ((3000 - countHitBoss) <= 2100) {
				AirAttack.font.setColor(Color.YELLOW);
			} else if ((3000 - countHitBoss) <= 3000) {
				AirAttack.font.setColor(Color.GREEN);
			}
			showBossHealth = "/3000";
			showBossHealthLeft = Integer.toString(3000 - countHitBoss);
		}
		AirAttack.font.draw(batch, showBossHealthLeft, 15,
				Play.camera.position.y + 945);
		AirAttack.font.setColor(Color.GREEN);
		AirAttack.font.draw(batch, showBossHealth, 100,
				Play.camera.position.y + 945);
		// score
		AirAttack.font.setColor(Color.WHITE);
		AirAttack.font.draw(batch, "Score:", 860, Play.camera.position.y - 925);
		AirAttack.font.setColor(Color.ORANGE);
		AirAttack.font.draw(batch, Integer.toString(score), 985,
				Play.camera.position.y - 925);
		// timer
		AirAttack.font.setColor(Color.CYAN);
		AirAttack.font.draw(batch, getScreenTime(), 960,
				Play.camera.position.y + 945);

		if (health <= 1500) {
			AirAttack.font.setColor(Color.RED);
		} else if (health <= 2400) {
			AirAttack.font.setColor(Color.YELLOW);
		} else if (health <= 3000) {
			AirAttack.font.setColor(Color.GREEN);
		}
		AirAttack.font.draw(batch, Integer.toString(health), 15,
				Play.camera.position.y - 925);
		AirAttack.font.setColor(Color.GREEN);
		AirAttack.font.draw(batch, "/3000", 100, Play.camera.position.y - 925);

		if (health >= 3000 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(0);
		} else if (health >= 2700 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(1);
		} else if (health >= 2400 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(2);
		} else if (health >= 2100 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(3);
		} else if (health >= 1800 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(4);
		} else if (health >= 1500 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(5);
		} else if (health >= 1200 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(6);
		} else if (health >= 900 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(7);
		} else if (health >= 600 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(8);
		} else if (health >= 300 && planeHealth == false) {
			Plane.currHealthFrame = Plane.aniHealth.getKeyFrame(9);
		}
	}

	private void createNewDrop() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			if (TimeUtils.millis() - Rocket.lastRocketTime > 1500
					&& callRocketPause == false)
				Rocket.rocketDrop();
			if (TimeUtils.millis() - Bullet.lastBulletTime > 200
					&& callBulletPause == false)
				Bullet.bulletHit();
			if (TimeUtils.millis() - Health.lastHealthTime > 45000
					&& callHealthPause == false)
				Health.healthDrop();
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			if (TimeUtils.millis() - Rocket.lastRocketTime > 1000
					&& callRocketPause == false)
				Rocket.rocketDrop();
			if (TimeUtils.millis() - Bullet.lastBulletTime > 150
					&& callBulletPause == false)
				Bullet.bulletHit();
			if (TimeUtils.millis() - Health.lastHealthTime > 30000
					&& callHealthPause == false)
				Health.healthDrop();
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			if (TimeUtils.millis() - Rocket.lastRocketTime > 500
					&& callRocketPause == false)
				Rocket.rocketDrop();
			if (TimeUtils.millis() - Bullet.lastBulletTime > 100
					&& callBulletPause == false)
				Bullet.bulletHit();
			if (TimeUtils.millis() - Health.lastHealthTime > 15000
					&& callHealthPause == false)
				Health.healthDrop();
		}
	}

	private void createFirstDrop() {
		Rocket.arrayRocket = new Array<Rocket>();
		Bullet.arrayBullet = new Array<Bullet>();
		Boss.arrayBoss = new Array<Boss>();
		Laser.arrayLaser = new Array<Laser>();
		Health.arrayHealth = new Array<Health>();
		Explosion.arrayExplosion = new Array<Explosion>();
		if (bossKilled <= 0) {
			Boss.bossDrop();
		}
	}

	private void createPlaneRec() {
		if (Menu.isLoadGame() == true) {
			loadPlane();
		} else {
			float x = 0;
			if (Setting.getPlayStyle().equalsIgnoreCase("touch")) {
				x = 278;
			} else if (Setting.getPlayStyle().equalsIgnoreCase("Accelerometer")) {
				x = 235;
			}
			plane = new Plane(new Vector2(450, 10), new Vector2(x, 202));
		}
	}

	private void createFireRec() {
		Fire.rocketFireRec = new Rectangle();
		Fire.rocketFireRec.x = -70;
		Fire.rocketFireRec.y = -70;

		Fire.bossFireRec = new Rectangle();
		Fire.bossFireRec.x = -150;
		Fire.bossFireRec.y = -250;

		Bullet.bulletImage = new Texture(Gdx.files.internal("bullet.png"));

		Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(0);
		Explosion.currReadyFrame = Explosion.aniReady.getKeyFrame(0);
	}

	private void sound() {
		hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.mp3"));
		bossSound = Gdx.audio.newSound(Gdx.files.internal("boss.mp3"));
		laserSound = Gdx.audio.newSound(Gdx.files.internal("laser.mp3"));
	}

	private void music() {
		backgroudMusic = Gdx.audio.newMusic(Gdx.files
				.internal("backgroundMusic.mp3"));
		backgroudMusic.setLooping(true);
		backgroudMusic.play();
	}

	private void backgroundImage() {
		backgroundImage = new Texture(Gdx.files.internal("backgroundImage.png"));
		backgroundImage.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		sprite = new Sprite(backgroundImage);
		sprite.setSize(1080, 3840);
	}

	private void AI() {
		if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			if (AI % 2 == 0) {
				roc.getPosition().x += AIMediumPause
						* Gdx.graphics.getDeltaTime();
			} else {
				roc.getPosition().x -= AIMediumPause
						* Gdx.graphics.getDeltaTime();
			}
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			if (AI % 2 == 0) {
				roc.getPosition().x += AIHardPause
						* Gdx.graphics.getDeltaTime();
			} else {
				roc.getPosition().x -= AIHardPause
						* Gdx.graphics.getDeltaTime();
			}
		}
	}

	private void minusHealth() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			health -= 5;
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			health -= 10;
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			health -= 15;
		}
	}

	private void bulletUpSpeed() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			bul.getPosition().y += bulletSpeedPauseEasy
					* Gdx.graphics.getDeltaTime();
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			bul.getPosition().y += bulletSpeedPauseMedium
					* Gdx.graphics.getDeltaTime();
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			bul.getPosition().y += bulletSpeedPauseHard
					* Gdx.graphics.getDeltaTime();
		}
	}

	private void rocketDownSpeed() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			roc.getPosition().y -= rocketSpeedPauseEasy
					* Gdx.graphics.getDeltaTime();
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			roc.getPosition().y -= rocketSpeedPauseMedium
					* Gdx.graphics.getDeltaTime();
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			roc.getPosition().y -= rocketSpeedPauseHard
					* Gdx.graphics.getDeltaTime();
		}
	}

	private void plusScore() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			score += 6;
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			score += 4;
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			score += 2;
		}
	}

	private void waitBossCome() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			bos.getPosition().y -= bossSpeedPauseEasy
					* Gdx.graphics.getDeltaTime();
			if (TimeUtils.millis() - Laser.lastLaserTime > 600
					&& bos.getPosition().y <= Play.camera.position.y + 550
					&& callBossPause == false) {
				laserSound.play(0.05f);
				Laser.laserDrop();
			}
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			bos.getPosition().y -= bossSpeedPauseMedium
					* Gdx.graphics.getDeltaTime();
			if (TimeUtils.millis() - Laser.lastLaserTime > 300
					&& bos.getPosition().y <= Play.camera.position.y + 550
					&& callBossPause == false) {
				laserSound.play(0.05f);
				Laser.laserDrop();
			}
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			bos.getPosition().y -= bossSpeedPauseHard
					* Gdx.graphics.getDeltaTime();
			if (bos.getPosition().y <= Play.camera.position.y + 550) {
				bossSpeedPauseHard = 0;
				if (bossMoveLR.equalsIgnoreCase("left")) {
					bos.getPosition().x -= bossMoveLRPauseHard
							* Gdx.graphics.getDeltaTime();
					if (bos.getPosition().x < 0) {
						bossMoveLR = "right";
					}
				}
				if (bossMoveLR.equalsIgnoreCase("right")) {
					bos.getPosition().x += bossMoveLRPauseHard
							* Gdx.graphics.getDeltaTime();
					if (bos.getPosition().x > 600) {
						bossMoveLR = "left";
					}
				}
			}
			if (TimeUtils.millis() - Laser.lastLaserTime > 100
					&& bos.getPosition().y <= Play.camera.position.y + 550
					&& callBossPause == false) {
				laserSound.play(0.05f);
				Laser.laserDrop();
			}
		}
	}

	private void minusScoreWhenBossPass() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			score -= 150;
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			score -= 100;
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			score -= 50;
		}
	}

	private void laserDownSpeed() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")) {
			las.getPosition().y -= laserSpeedPauseEasy
					* Gdx.graphics.getDeltaTime();
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")) {
			las.getPosition().y -= laserSpeedPauseMedium
					* Gdx.graphics.getDeltaTime();
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")) {
			las.getPosition().y -= laserSpeedPauseHard
					* Gdx.graphics.getDeltaTime();
		}
	}

	private void minusBossHealth() {
		if (Setting.getDifficult().equalsIgnoreCase("easy")
				&& bossHealth == false) {
			if (countHitBoss < 100) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(0);
			} else if (countHitBoss < 200) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(1);
			} else if (countHitBoss < 300) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(2);
			} else if (countHitBoss < 400) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(3);
			} else if (countHitBoss < 500) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(4);
			} else if (countHitBoss < 600) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(5);
			} else if (countHitBoss < 700) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(6);
			} else if (countHitBoss < 800) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(7);
			} else if (countHitBoss < 900) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(8);
			} else if (countHitBoss < 1000) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(9);
			}
		} else if (Setting.getDifficult().equalsIgnoreCase("medium")
				&& bossHealth == false) {
			if (countHitBoss < 200) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(0);
			} else if (countHitBoss < 400) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(1);
			} else if (countHitBoss < 600) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(2);
			} else if (countHitBoss < 800) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(3);
			} else if (countHitBoss < 1000) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(4);
			} else if (countHitBoss < 1200) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(5);
			} else if (countHitBoss < 1400) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(6);
			} else if (countHitBoss < 1600) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(7);
			} else if (countHitBoss < 1800) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(8);
			} else if (countHitBoss < 2000) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(9);
			}
		} else if (Setting.getDifficult().equalsIgnoreCase("hard")
				&& bossHealth == false) {
			if (countHitBoss < 300) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(0);
			} else if (countHitBoss < 600) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(1);
			} else if (countHitBoss < 900) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(2);
			} else if (countHitBoss < 1200) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(3);
			} else if (countHitBoss < 1500) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(4);
			} else if (countHitBoss < 1800) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(5);
			} else if (countHitBoss < 2100) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(6);
			} else if (countHitBoss < 2400) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(7);
			} else if (countHitBoss < 2700) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(8);
			} else if (countHitBoss < 3000) {
				Boss.currHealthFrame = Boss.aniHealth.getKeyFrame(9);
			}
		}
	}

	private String getScreenTime() {
		int seconds = (int) (secondsTime % 60);
		int minutes = (int) ((secondsTime / 60) % 60);
		String secondsStr = (seconds < 10 ? "0" : "") + seconds;
		String minutesStr = (minutes < 10 ? "0" : "") + minutes;
		return new String(minutesStr + ":" + secondsStr);
	}

	private void powerUp() {
		if (rocketKilled <= 10) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(0);
			checkPower = false;
		} else if (rocketKilled <= 20) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(1);
			checkPower = false;
		} else if (rocketKilled <= 30) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(2);
			checkPower = false;
		} else if (rocketKilled <= 40) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(3);
			checkPower = false;
		} else if (rocketKilled <= 50) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(4);
			checkPower = false;
		} else if (rocketKilled <= 60) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(5);
			checkPower = false;
		} else if (rocketKilled <= 70) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(6);
			checkPower = false;
		} else if (rocketKilled <= 80) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(7);
			checkPower = false;
		} else if (rocketKilled <= 90) {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(8);
			checkPower = true;
		} else {
			Explosion.currManaFrame = Explosion.aniMana.getKeyFrame(8);
		}
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
		disposeAll();
	}

	public static void disposeAll() {
		backgroundImage.dispose();
		hitSound.dispose();
		bossSound.dispose();
		laserSound.dispose();
		backgroudMusic.dispose();
		health = 1000;
		AI = 0;
		countHitBoss = 0;
		victoryDistance = 0;
		bossKilled = 0;
		checkPower = false;
		checkPower2 = true;
		pressPower = false;
		Menu.loadGame = false;
		// batch.dispose();
		Boss.disposeBoss();
		Bullet.disposeBullet();
		Explosion.disposeExplosion();
		Fire.disposeFire();
		Health.disposeHealth();
		Laser.disposeLaser();
		Plane.disposePlane();
		Rocket.disposeRocket();
	}

	public static Plane getPlane() {
		return plane;
	}

	public static Boss getBos() {
		return bos;
	}

	public static void setBos(Boss bos) {
		Play.bos = bos;
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		Play.score = score;
	}

	public static int getRocketKilled() {
		return rocketKilled;
	}

	public static void setRocketKilled(int rocketKilled) {
		Play.rocketKilled = rocketKilled;
	}

	public static float getSecondsTime() {
		return secondsTime;
	}

	public static void setSecondsTime(float secondsTime) {
		Play.secondsTime = secondsTime;
	}

	public static String getStatus() {
		return status;
	}

	public static void setStatus(String status) {
		Play.status = status;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int health) {
		Play.health = health;
	}

	public static int getCountHitBoss() {
		return countHitBoss;
	}

	public static void setCountHitBoss(int countHitBoss) {
		Play.countHitBoss = countHitBoss;
	}

	public static int getBossKilled() {
		return bossKilled;
	}

	public static void setBossKilled(int bossKilled) {
		Play.bossKilled = bossKilled;
	}

	private void loadPlane() {
		Plane p = null;
		try {
			FileInputStream fileIn = new FileInputStream(
					"/mnt/sdcard/plane.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			p = (Plane) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Plane class not found");
			c.printStackTrace();
			return;
		}
		plane = new Plane(new Vector2(p.getPosition().x, p.getPosition().y),
				new Vector2(p.getSize().x, p.getSize().y));
	}

	private void loadCamera() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/camera.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			loadCamera = Float.parseFloat(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadTime() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/time.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			loadTime = Float.parseFloat(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadScore() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/score.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			loadScore = Integer.parseInt(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadLevel() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/level.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			Setting.setDifficult(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadStyle() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/style.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			Setting.setPlayStyle(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadPlaneHealth() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/planeHealth.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			loadHealth = Integer.parseInt(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadBossHealth() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/bossHealth.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			loadCountHitBoss = Integer.parseInt(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadRocketKilled() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/rocketKilled.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			loadRocketKilled = Integer.parseInt(sb.toString().trim());
		} finally {
			br.close();
		}
	}

	private void loadBossKilled() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"/sdcard/Android/bossKilled.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			loadBossKilled = Integer.parseInt(sb.toString().trim());
		} finally {
			br.close();
		}
	}
}
