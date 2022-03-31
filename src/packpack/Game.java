package packpack;

import java.util.*;

public class Game {
	private String gameID;
	private int inning = 1;
	private int maxInnings = 1;
	private boolean top = true;
	private int scoreTop = 0;
	private int scoreBot = 0;
	private int balls =  0;
	private int strikes = 0;
	private int outs = 0;
	
	private void upInning() {
		if(!top) {
			inning++;
		}
		top = !top;
	}
	
	public String getInning() {
		return Integer.toString(inning) + " " + Boolean.toString(top);
	}
	
	public void setGameID(int i) {
		gameID = Integer.toString(i);
	}
	
	public String getGameID() {
		return gameID;
	}
	
	public void upScore() {
		if(top) {
			scoreTop++;
		} else {
			scoreBot++;
		}
	}
	
	public String getScore() {
		return Integer.toString(scoreTop) + Integer.toString(scoreBot);
	}
	
	public void setMaxInnings(int i) {
		maxInnings = i;
	}
	
	public int getMaxInnings() {
		return maxInnings;
	}
	
	public void upStrikes() {
		strikes++;
	}
	
	public int getStrikes() {
		return strikes;
	}
	
	public void upOuts() {
		outs++;
	}
	
	public void resetOuts() {
		outs = 0;
	}
	
	public int getOuts() {
		return outs;
	}
	
	public void upBalls() {
		balls++;
	}
	
	public int getBalls() {
		return balls;
	}
	
	public void resetTurn() {
		strikes = 0;
		balls = 0;
	}
}