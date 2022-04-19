package packClient;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class TeamClient {
	Panel panel;
	int[] benchCoords = {0,0};
	String sprite;
	
	ArrayList<PlayerClient> players = new ArrayList<>();

	public TeamClient(Panel p, String s, int i, int j) {
		panel = p;
		sprite = s;
		players.add(new PlayerClient(panel, sprite, i, j));
		players.add(new PlayerClient(panel, sprite, i+60, j));
		players.add(new PlayerClient(panel, sprite, i+120, j));
		players.add(new PlayerClient(panel, sprite, i+180, j));
		players.add(new PlayerClient(panel, sprite, i+240, j));
	}
	
	public void moveTeam() {
		for (PlayerClient player: players) {
			player.move();
		}
	}
	
	public void drawTeam(Graphics2D g2D) {
		for (PlayerClient player: players) {
			player.draw(g2D);
		}
	}


}
