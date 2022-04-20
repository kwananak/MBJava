package packClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Panel extends JPanel implements Runnable{
	Image background = new ImageIcon("back.png").getImage();
	TeamClient[] teams = {new TeamClient(this, "redSprite.png", 10, 150), new TeamClient(this, "blueSprite.png", 630, 150)};
	BasesClient bases = new BasesClient(teams);
	Umpire umpire = new Umpire(this, "umpiSprite.png", 530, 180);
	TeamClient teamField = null;
	TeamClient teamBat = null;
	int batter = 0;
	
	final int PANEL_WIDTH = 1000;
	final int PANEL_HEIGHT = 800;
	int FPS = 60;
	
	Thread gameThread;
	
	public Panel(){
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setDoubleBuffered(true);
	}
	
	public void startUIThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();			
			delta += (currentTime - lastTime) / drawInterval;			
			lastTime = currentTime;
			
			if(delta>=1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	
	public void update() {
		for (TeamClient team: teams) {
			team.moveTeam();
		}
	}
	
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);		
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawImage(background, 0, 0, null);		

		umpire.draw(g2D);
		teams[0].drawTeam(g2D);
		teams[1].drawTeam(g2D);
	}
	
	public void inningStart(String str) {	
		batter = 0;
		if (str.equals("top")) {
			teamField = teams[1];
			teamBat = teams[0];
		} else if (str.equals("bot")) {
			teamField = teams[0];
			teamBat = teams[1];
		}
		
		if (teamField != null) {
			teamField.players.get(0).setDestination(bases.mountCoords);
			teamField.players.get(1).setDestination(bases.homeCoordsField);
			teamField.players.get(2).setDestination(bases.firstCoordsField);
			teamField.players.get(3).setDestination(bases.secondCoordsField);
			teamField.players.get(4).setDestination(bases.thirdCoordsField);
		}
	}
	
	public void turnStart(String str) {
		
		if (str.equals("start")) {
			if(teamBat != null) {
				System.out.println("batter time");
				teamBat.players.get(batter).setDestination(bases.homeCoordsBat);
				batter++;
				if (batter == 5) {
					batter = 0;
				}
			}
		}
	}
}
