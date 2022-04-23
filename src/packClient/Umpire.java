package packClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Umpire {
	int Velocity = 3;
	int[] coords = {0,0};
	int[] benchSpot = {0,0};
	Panel panel;	
	Image sprite;
	int[] destination = {0,0};
	String talk = " ";
	
	public Umpire(Panel panel, String str, int x, int y) {
		this.panel = panel;
		try {
			this.sprite = ImageIO.read(ResourceLoader.load(str));
		} catch (IOException e) {
			e.printStackTrace();
		}		this.coords[0] = x;
		this.coords[1] = y;
		this.benchSpot[0] = x;
		this.benchSpot[1] = y;
		this.destination[0] = x;
		this.destination[1] = y;
	}
	
	public void draw(Graphics2D g2D) {
		g2D.drawImage(sprite, coords[0], coords[1], null);
		g2D.setPaint(Color.white);
		g2D.setFont(new Font("Fixedsys",Font.BOLD,30));
		g2D.drawString(talk, coords[0] - 120, coords[1] - 15);
	}
	
	public void setTalk(String str) {
		talk = str;
	}
}
