package packpack;

import java.net.*;
import java.io.*;


public class Server {
	
	public static void main(String[] args) throws IOException{
		int gameIDs = 0;	

		ServerSocket ss = new ServerSocket(4999);
		System.out.println("server started");
		
		while(true) {
			Socket s = ss.accept();		
			System.out.println("client " + s + " connected");
	
			Game newGame = new Game();
			newGame.setGameID(gameIDs);
			gameIDs++;
			
			System.out.println("gameID : " + newGame.getGameID());
			PrintWriter pr = new PrintWriter(s.getOutputStream());

			while(true) {
				BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
				
				String recvd = bf.readLine();
				System.out.println("client : " + recvd);
					if(recvd.equals("exit")) {
						pr.println("Bye!");
						pr.flush();
						break;
					}
				
				pr.println("Allo?");
				pr.flush();
				}
		}
	}	
}
