package packpack;

import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.*;

public class Server {
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ArrayList<Game> games = new ArrayList<>();
	static File log = new File("logs/log.txt");
	
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(5565);
		int clientID = 0;
		int gameID = 0;
		printLog("serverStarted");
		System.out.println("serverStarted");
		
		while(true) {
			clients.add(new ClientHandler(ss.accept(), clientID));
			printLog("accepted connection " + clients.get(clients.size()-1));
			System.out.println("accepted connection " + clients.get(clients.size()-1));
			new Thread(clients.get(clients.size()-1)).start();
			clientID++;
			if(clients.size() == 1 || games.get(games.size()-1).getFull()) {
				games.add(new Game(gameID));
				printLog("game " + gameID + " started");
				gameID++;
				games.get(games.size()-1).start();
			}
			games.get(games.size()-1).addPlayer(clients.get(clients.size()-1));
		}
	}	
	
	public static void printLog(String str) throws IOException {
		FileWriter fw = new FileWriter(log, true);
		PrintWriter pw = new PrintWriter(fw);
			  
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime logTime = LocalDateTime.now();
	    String formattedTime = logTime.format(timeFormatter);
	    
	    pw.println(formattedTime + " " + str);
		pw.close();	
	}
}