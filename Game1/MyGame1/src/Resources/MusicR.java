package Resources;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicR {
	
	public static Music gameOverMusic;
	public static Music introMusic;
	public static Music victoryMusic;
	
	public static void declareMusic()
	{
		gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("gameOver.mp3"));
		gameOverMusic.setLooping(true);
		
		introMusic = Gdx.audio.newMusic(Gdx.files.internal("intro.mp3"));
		introMusic.setLooping(true);
		
		victoryMusic = Gdx.audio.newMusic(Gdx.files.internal("victory.mp3"));
		victoryMusic.setLooping(true);
	}

	public static Music getGameOverMusic() {
		return gameOverMusic;
	}

	public static void setGameOverMusic(Music gameOverMusic) {
		MusicR.gameOverMusic = gameOverMusic;
	}

	public static Music getIntroMusic() {
		return introMusic;
	}

	public static void setIntroMusic(Music introMusic) {
		MusicR.introMusic = introMusic;
	}

	public static Music getVictoryMusic() {
		return victoryMusic;
	}

	public static void setVictoryMusic(Music victoryMusic) {
		MusicR.victoryMusic = victoryMusic;
	}
}
