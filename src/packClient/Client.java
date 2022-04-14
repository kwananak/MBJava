package packClient;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
	private static ArrayList<String> inputLog = new ArrayList<>();	
	
	public static void main(String[] args) throws IOException {
		System.out.println("main started");
		Socket server = new Socket("localhost", 7777);
		System.out.println("connected to server");
		
		Receiver receiver = new Receiver(server);
		Sender sender = new Sender(server);
		
		receiver.start();
		sender.start();		
	}
	
	public static class Receiver extends Thread{
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
					if (serverResponse == null) break;
					System.out.println("server says: " + inputLog.get(inputLog.size()-1));
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
	
	public static class Sender extends Thread{
		private Socket server;
		private PrintWriter out;
		private BufferedReader keyboard;
		
		public Sender(Socket s) throws IOException {
			server = s;
			keyboard = new BufferedReader(new InputStreamReader(System.in));;
			out = new PrintWriter(server.getOutputStream(), true);
		}
		
		public void run() {
			System.out.println("Sender started");
			try{
				while(true) {
					System.out.println("ready for input->  ");
					String command = keyboard.readLine();
					
					if (command.equals("quit")) break;
					out.println(command);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
					out.close();
				}
		}		
	}
}

