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
	Image top =  new ImageIcon("top.png").getImage();	
	Image bottom =  new ImageIcon("top.png").getImage();
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
			g2D.drawString("I " + inning, 635, 70);
			g2D.setFont(new Font("Fixedsys",Font.BOLD,100));
			g2D.drawString(score, 410, 110);
		}
	}
}
