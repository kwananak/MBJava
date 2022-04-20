package packClient;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Jumbotron {
	int strikes, balls, outs, inning = 0;
	String score, mainDisplay;
	boolean topBot, fullJumbo;
	Image top =  new ImageIcon("top.png").getImage();	
	Image bottom =  new ImageIcon("top.png").getImage();
	Panel panel;
	
	public Jumbotron(Panel p) {
		panel = p;
	}
	
	public void udpateJumbotron(int s, int b, int o, int i, String sc, boolean tb) {
		strikes = s;
		balls = b;
		outs = 0;
		inning = i;
		score = sc;
		topBot = tb;
	}
	
	
	
	
}
