package packClient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Button extends JPanel implements MouseListener{
	Panel panel;
	Keyboard keyboard;
	String buttonID;
	
	public Button(Panel p, Keyboard k, String str) {
		panel = p;
		keyboard = k;
		buttonID = str;
		this.setDoubleBuffered(true);
		this.addMouseListener(this);
		this.setOpaque(false);
		this.panel.add(this);
	}
	
	
	
	public void mouseClicked(MouseEvent e) {			
	}

	public void mousePressed(MouseEvent e) {
		keyboard.addInput(buttonID);		
	}

	public void mouseReleased(MouseEvent e) {		
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {		
	}

}
