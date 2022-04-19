package packClient;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private static ArrayList<String> inputLog = new ArrayList<>();	
	static Window window = new Window();
	static Panel panel = new Panel();
	
	
	public static void main(String[] args) throws IOException {
		Socket server = new Socket("localhost", 7777);
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
						panel.teams[0].players.get(0).setDestination(panel.bases.homeCoords);
						panel.teams[1].players.get(0).setDestination(panel.bases.firstCoords);						panel.teams[1].players.get(0).setDestination(panel.bases.firstCoords);
						panel.teams[0].players.get(3).setDestination(panel.bases.secondCoords);
						panel.teams[1].players.get(3).setDestination(panel.bases.thirdCoords);
						panel.teams[1].players.get(4).setDestination(panel.bases.mountCoords);
						String[] arrResp = serverResponse.split(":");
						switch (arrResp[1]) {
							case "sender": sender(server, arrResp[2]); 
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
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(str);
		String command;
		try {
			command = keyboard.readLine();
			PrintWriter out = new PrintWriter(sendSock.getOutputStream(), true);
			out.println(command);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
}

