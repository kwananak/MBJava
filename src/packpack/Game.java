package packpack;

public class Game {
	private String gameID;
	private int inning = 1;
	private boolean top = true;
	
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
	
	
}
