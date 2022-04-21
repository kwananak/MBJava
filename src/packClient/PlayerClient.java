package packClient;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class PlayerClient {
	int Velocity = 3;
	ArrayList<Integer> coords = new ArrayList<>();;
	ArrayList<Integer> benchSpot = new ArrayList<>();;
	Panel panel;	
	Image sprite;
	ArrayList<ArrayList<Integer>> destinations = new ArrayList<>();
	boolean field, home, onBase, bench, running = false;
	int base = 0;
	
	
	public PlayerClient(Panel panel, String str, int x, int y) {
		this.panel = panel;
		this.sprite =  new ImageIcon(str).getImage();
		this.coords.add(x);
		this.coords.add(y);
		this.benchSpot.add(x);
		this.benchSpot.add(y);
		ArrayList<Integer> dest = new ArrayList<>(); 
		dest.add(x);
		dest.add(y);
		this.destinations.add(dest);
	}
	
	public void move() {
		if (destinations.size() > 0) {
			if (coords.get(0) < destinations.get(0).get(0) - Velocity + 1) {
				coords.set(0, coords.get(0) + Velocity);
			}			
			if (coords.get(0) > destinations.get(0).get(0) + Velocity - 1) {
				coords.set(0, coords.get(0) - Velocity);
			}			
			if (coords.get(1) < destinations.get(0).get(1) - Velocity + 1) {
				coords.set(1, coords.get(1) + Velocity);
			}			
			if (coords.get(1) > destinations.get(0).get(1) + Velocity - 1) {
				coords.set(1, coords.get(1) - Velocity);
			}
			if (coords.equals(destinations.get(0))) {
				destinations.remove(0);
			}
		}		
	}
	
	public void draw(Graphics2D g2D) {
		g2D.drawImage(sprite, coords.get(0), coords.get(1), null);
	}
	
	public void setDestination(ArrayList<Integer> coords) {
		destinations.add(coords);
	}
	
	public void setBase(int i) {
		base = i;
	}
	
	public int getBase() {
		return base;
	}
	
	public void returnBench() {
		destinations.add(benchSpot);
	}
}
