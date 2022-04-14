package packpack;

import java.util.*;

public class Game extends Thread {
	private int gameID;
	private boolean top = true;
	private int scoreEvens, scoreOdds, inning, maxInnings, balls, strikes, outs = 0;
	private ArrayList<ClientHandler> evens = new ArrayList<>();	
	private ArrayList<ClientHandler> odds = new ArrayList<>();
	private ArrayList<ClientHandler> players = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> answers = new ArrayList<>();	
	private Bases bases;

	private boolean full = false;
	
	public Game(int IDFromServer) {
		gameID = IDFromServer;
	}
	
	public void run() {
		waitForPlayers();
		System.out.println("game " + gameID + " started with " + players.size() + " players");
		makeTeams();
		bases = new Bases(evens, odds);
		setMaxInnings();
		while(inning < maxInnings) {
			startInning();
			while(outs < 3) {
				startTurn();
				endTurn();
			}
			endInning();
		}
		endGame();
	}
	
	public void waitForPlayers() {
		System.out.println("waiting on players");
		int i = 0;
		
		while (true) {
			i++;		
			if (players.size() > 11 || (players.size() > 1 && i > 3000)) {
				break;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
		full = !full;
	}
	
	public void makeTeams() {
		for(int j = 0; j < players.size(); j++) {
			if (j%2 == 0) {
				evens.add(players.get(j));
				players.get(j).sender("You're in the Evens");
			} else {
				odds.add(players.get(j));
				players.get(j).sender("You're in the Odds");
			}
		}
	}
	
	public void startInning() {
			massSend("startInning " + inning + " " + top);
			if (top) {
				bases.setFieldHome(odds);
				massSend("Odds in the field");
			} else {
				bases.setFieldHome(evens);
				massSend("Evens in the field");
			}
	}
	
	public void setMaxInnings() {
		Integer numInns = 0;
		massSend("How many Innings?");
		getAnswersFromHandlers();
		for(ArrayList<Integer> i : answers) {
			numInns += i.get(1);
		}
		maxInnings = numInns / answers.size();
		System.out.println("game " + gameID + " maxInnings : " + maxInnings);
		massSend("maxInnings " + maxInnings);
	}
	
	public String getAnswerFromMount(ClientHandler pitcher) {
		pitcher.sender("choose a pitch ");
		while (true) {
			if(!pitcher.getStoredIn().isBlank()) {
				String choice= pitcher.getStoredIn();
				pitcher.clearStoredIn();
				return choice;
			}
		}
	}
	
	public void getAnswersFromHandlers() {
		System.out.println("getAnswers game " + gameID + " started");
		answers.clear();
		while (answers.size() < players.size()) {
			for (ClientHandler player : players) {
				if(!player.getStoredIn().isBlank()) {
					ArrayList<Integer> answer = new ArrayList<>();
					answer.add(Integer.valueOf(player.getClientID()));
					answer.add(Integer.valueOf(player.getStoredIn()));
					answers.add(answer);
					player.clearStoredIn();
					System.out.println("got " + answers.get(answers.size()-1).get(1) + " from player " + player.getClientID());
				}
			}
		}
		System.out.println("closing getAnswers game " + gameID);
	}
	
	public void addPlayer(ClientHandler newGuy) {
		players.add(newGuy);
	}
	
	public boolean getFull() {
		return full;
	}
	
	private void upInning() {
		if(!top) {
			inning++;
		}
		top = !top;
	}
	
	public String getInning() {
		return Integer.toString(inning) + " " + Boolean.toString(top);
	}
	
	private void upScore() {
		if(top) {
			scoreEvens++;
		} else {
			scoreOdds++;
		}
	}
	
	public String getScore() {
		return Integer.toString(scoreEvens) + " " + Integer.toString(scoreOdds);
	}
	
	public void startTurn() {
		bases.setHitter(top);
		while (strikes < 3) {
			getAnswerFromMount(bases.getPitcher());
			System.out.println("sending pitch to player " + bases.getHitter().getClientID() + " from player " + bases.getPitcher().getClientID());
			//send pitch
			//receive pitch
			//if hit or out: break
		}
	}
	
	private void endTurn() {
		//cycle runners & batter
		//if 3 outs : break
	}
	
	private void endInning() {
		//return players to bench
		upInning();
	}
	
	private void endGame() {
		//return score
		//save stats
		//close game
	}
	
	public void massSend(String str) {
		for(ClientHandler client : players) {
			client.sender(str);
		}
	}
	
}