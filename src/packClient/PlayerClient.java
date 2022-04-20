package packClient;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class PlayerClient {
	int Velocity = 3;
	int[] coords = {0,0};
	int[] benchSpot = {0,0};
	Panel panel;	
	Image sprite;
	int[] destination = {0,0};
	boolean field, home, onBase, bench, running = false;
	int base = 0;
	
	
	public PlayerClient(Panel panel, String str, int x, int y) {
		this.panel = panel;
		this.sprite =  new ImageIcon(str).getImage();
		this.coords[0] = x;
		this.coords[1] = y;
		this.benchSpot[0] = x;
		this.benchSpot[1] = y;
		this.destination[0] = x;
		this.destination[1] = y;
	}
	
	public void move() {
		if (coords[0] < destination[0] - Velocity + 1) {
			coords[0] += Velocity;
		}
		
		if (coords[0] > destination[0] + Velocity - 1) {
			coords[0] -= Velocity;
		}
		
		if (coords[1] < destination[1] - Velocity + 1) {
			coords[1] += Velocity;
		}
		
		if (coords[1] > destination[1] + Velocity - 1) {
			coords[1] -= Velocity;
		}
	}
	
	public void draw(Graphics2D g2D) {
		g2D.drawImage(sprite, this.coords[0], this.coords[1], null);
	}
	
	public void setDestination(int[] coords) {
		destination = coords;
	}
	
	public void setBase(int i) {
		base = i;
	}
	
	public int getBase() {
		return base;
	}
	
	public void returnBench() {
		destination = benchSpot;
	}
}
