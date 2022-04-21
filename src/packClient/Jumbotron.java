package packClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Jumbotron extends JPanel{
	String strikes, balls, outs, inning, topBot;
	String fullJumbo = "true";
	String score = "0 - 0"; 
	String mainDisplay = "Mathball";
	Panel panel;
	
	public Jumbotron(Panel p) {
		panel = p;
	}
	
	public void updateJumbotron(String str) {
		String[] arrStr = str.split(",");
		strikes = arrStr[0];
		balls = arrStr[1];
		outs = arrStr[2];
		inning = arrStr[3];
		score = arrStr[4];
		topBot = arrStr[5];
		fullJumbo = arrStr[6];
	}
	
	public void draw(Graphics2D g2D) {
		g2D.setPaint(Color.white);
		
		if (fullJumbo.equals("true")) {
			g2D.setFont(new Font("Fixedsys",Font.BOLD,85));
			g2D.drawString(mainDisplay, 328, 105);
		} else {
			g2D.setFont(new Font("Fixedsys",Font.BOLD,30));
			g2D.drawString("S " + strikes, 331, 55);
			g2D.drawString("B " + balls, 330, 85);
			g2D.drawString("O " + outs, 328, 115);
			g2D.drawString(inning, 642, 70);
			g2D.setFont(new Font("Fixedsys",Font.BOLD,100));
			g2D.drawString(score, 410, 110);
			if (topBot.equals("true")) {
				int[] x = {640, 650, 660};
				int[] y = {100, 90, 100};
				g2D.fillPolygon(x, y, 3);
			} else {
				int[] x = {640, 650, 660};
				int[] y = {90, 100, 90};
				g2D.fillPolygon(x, y, 3);
			}			
		}
	}
	
	public void setMainDisplay(String str){
		switch (str) {
			case "1":
				mainDisplay = "Single!";
				fullJumbo = "true";
				break;
			case "2":
				mainDisplay = "DDouble!!";
				fullJumbo = "true";
				break;
			case "3":
				mainDisplay = "TTTriple!!!";
				fullJumbo = "true";
				break;
		}
	}
}
