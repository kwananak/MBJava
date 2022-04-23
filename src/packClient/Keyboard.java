package packClient;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Keyboard{
	boolean keyOn = false;
	BufferedImage board;
	Panel panel;
	int[] coords = {0,230};
	ArrayList<String> answer = new ArrayList<>();
	Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonC, buttonE;
	String storedAnswer = "";

	
	public Keyboard(Panel p) {
		this.panel = p;
	}
	
	public void addInput(String str) {
		if (str.equals("C")) {
			if (answer.size() > 0) {
				answer.remove(answer.size() - 1);
			}
		} else if (str.equals("E")) {
			String answerString = "";
			for (int i = 0; i < answer.size(); i++) {
				answerString += answer.get(i);
			}
			storedAnswer = answerString;
			answer.clear();
		} else {		
			if (answer.size() == 5) {
				answer.remove(4);
			}
			answer.add(str);
		}
		
	}
	
		
	public void setColor(String str) {	
		if(str.equals("true")) {
			 try {
					this.board = ImageIO.read(ResourceLoader.load("Sprites/redKey.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			 coords[0] = 170;
		} else {
			 try {
					this.board = ImageIO.read(ResourceLoader.load("Sprites/blueKey.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			 coords[0] = 450;
		} 
		button1 = new Button(panel,this,"1");
		button1.setBounds(coords[0] + 132, coords[1] + 156, 36, 36);
		button2 = new Button(panel,this,"2");
		button2.setBounds(coords[0] + 174, coords[1] + 156, 36, 36);
		button3 = new Button(panel,this,"3");
		button3.setBounds(coords[0] + 216, coords[1] + 156, 36, 36);
		button4 = new Button(panel,this,"4");
		button4.setBounds(coords[0] + 132, coords[1] + 198, 36, 36);
		button5 = new Button(panel,this,"5");
		button5.setBounds(coords[0] + 174, coords[1] + 198, 36, 36);
		button6 = new Button(panel,this,"6");
		button6.setBounds(coords[0] + 216, coords[1] + 198, 36, 36);
		button7 = new Button(panel,this,"7");
		button7.setBounds(coords[0] + 132, coords[1] + 240, 36, 36);
		button8 = new Button(panel,this,"8");
		button8.setBounds(coords[0] + 174, coords[1] + 240, 36, 36);
		button9 = new Button(panel,this,"9");
		button9.setBounds(coords[0] + 216, coords[1] + 240, 36, 36);
		button0 = new Button(panel,this,"0");
		button0.setBounds(coords[0] + 132, coords[1] + 282, 36, 36);
		buttonC = new Button(panel,this,"C");
		buttonC.setBounds(coords[0] + 174, coords[1] + 282, 36, 36);
		buttonE = new Button(panel,this,"E");
		buttonE.setBounds(coords[0] + 216, coords[1] + 282, 36, 36);

	}
	
	public void draw(Graphics2D g2D) {
		if (keyOn) {
			String answerString = "";
			for (int i = 0; i < answer.size(); i++) {
				answerString += answer.get(i);
			}	
			g2D.drawImage(board, coords[0], coords[1], null);
			g2D.setFont(new Font("Fixedsys",Font.BOLD, 36));
			g2D.drawString(answerString, coords[0] + 142, coords[1] + 142);
		}
	}
	
	public void flipKey() {
		keyOn = !keyOn;
	}

	public void clearStoredAnswer() {
		storedAnswer = "";
	}
}
