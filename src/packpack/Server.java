package packpack;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ArrayList<Game> games = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(7777);
		int clientID = 0;
		int gameID = 0;
		System.out.println("serverStarted");
		
		while(true) {
			clients.add(new ClientHandler(ss.accept(), clientID));
			System.out.println("accepted connection " + clients.get(clients.size()-1));
			new Thread(clients.get(clients.size()-1)).start();
			clientID++;
			if(clients.size() == 1 || games.get(games.size()-1).getFull()) {
				games.add(new Game(gameID));
				gameID++;
				games.get(games.size()-1).start();
			}
			games.get(games.size()-1).addPlayer(clients.get(clients.size()-1));
		}
	}	
}