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
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		keyboard.addInput(buttonID);	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
