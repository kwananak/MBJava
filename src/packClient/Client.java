package packClient;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private static ArrayList<String> inputLog = new ArrayList<>();	
	static Window window = new Window();
	static Panel panel = new Panel();
	
	public static void main(String[] args) throws IOException {
		Socket server = new Socket("192.168.0.196", 5565);
		System.out.println("connected to server");
		Receiver receiver = new Receiver(server);
		
		window.add(panel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		receiver.start();
		panel.startUIThread();	
	}
	
	private static class Receiver extends Thread{
		private Socket server;
		private BufferedReader in;	
		
		public Receiver(Socket s) throws IOException {
			server = s; 
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		}

		public void run() {
			System.out.println("Receiver started");
			try {
				while (true) {
					String serverResponse = in.readLine();
					inputLog.add(serverResponse);
					if (serverResponse.startsWith("command")) {
						String[] arrResp = serverResponse.split(":");
						switch (arrResp[1]) {
							case "sender": 
								sender(server, arrResp[2]); 
								break;
							case "inningStart": 
								panel.inningStart(arrResp[2]); 
								break;
							case "turnStart": 
								panel.turnStart();
								panel.jumbotron.updateJumbotron(arrResp[2]);
								panel.umpire.setTalk(" ");
								break;
							case "cycleBases":
								panel.cycleBases(arrResp[2]);
								panel.jumbotron.setMainDisplay(arrResp[2]);
								break;
							case "clearBatter":
								panel.clearBatter();
								panel.umpire.setTalk("Out!");
								break;
							case "returnBench":
								panel.returnBench();
								break;
							case "jumbotron":
								panel.jumbotron.updateJumbotron(arrResp[2]);
								break;
							case "umpire":
								panel.umpire.setTalk(arrResp[2]);
								break;
							case "pitch":
								panel.umpire.setTalk(arrResp[2]);
								sender(server, arrResp[2]);
								break;
							case "team":
								panel.keyboard.setColor(arrResp[2]);
								break;
							case "startLoop":
								panel.jumbotron.updateJumbotron(arrResp[2]);
								panel.umpire.setTalk(" ");
								break;
						}
					} else {
						System.out.println("server says: " + inputLog.get(inputLog.size()-1));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	private static void sender(Socket s, String str) {
		Socket sendSock = new Socket();
		sendSock = s;
		panel.keyboard.flipKey();
		while (true) {
			try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
			if (!panel.keyboard.storedAnswer.equals("")) {
				break;
			}
		}
		try {
			PrintWriter out = new PrintWriter(sendSock.getOutputStream(), true);
			out.println(panel.keyboard.storedAnswer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true)
			if (panel.keyboard.keyOn) {
				panel.keyboard.flipKey();
				break;
			}
		panel.keyboard.clearStoredAnswer();		
	}
}

