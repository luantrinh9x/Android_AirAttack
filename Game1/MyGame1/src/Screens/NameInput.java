package Screens;

import com.badlogic.gdx.Input.TextInputListener;
import com.gdx.main.AirAttack;
import com.parse.ParseObject;

public class NameInput implements TextInputListener {

	private AirAttack aa;

	public NameInput(AirAttack aa) {
		this.aa = aa;
	}

	@Override
	public void input(String text) {
		ParseObject gameScore = new ParseObject("GameScore");
		gameScore.put("Name", text);
		gameScore.put("Score", Play.getScore());
		gameScore.put("Status", Play.getStatus());
		gameScore.put("Duration", Play.getSecondsTime());
		gameScore.put("Rocket", Play.getRocketKilled());
		gameScore.put("Level", Setting.getDifficult());
		gameScore.put("Style", Setting.getPlayStyle());
		gameScore.saveEventually();
		
		GameOver.resetCount();
	}

	@Override
	public void canceled() {
	}

}
