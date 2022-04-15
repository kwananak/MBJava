package packpack;

import java.util.*;

public class Game extends Thread {
	private String swing;
	private int gameID;
	private boolean top = true;
	private int scoreEvens, scoreOdds, inning, maxInnings, outs = 0;
	private ArrayList<ClientHandler> evens = new ArrayList<>();	
	private ArrayList<ClientHandler> odds = new ArrayList<>();
	private ArrayList<ClientHandler> players = new ArrayList<>();
	private ArrayList<ArrayList<Integer>> answers = new ArrayList<>();	
	private Bases bases;
	private ArrayList<Integer> pitch = new ArrayList<>();

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
			while(outs < 1) {
				startTurn();
				endTurn();
			}
			System.out.println("out of endTurn");
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
	
	private void upScore(Integer i) {
		for (int j = 0; j < i; j++) {
			if(top) {
				scoreEvens++;
			} else {
				scoreOdds++;
			}
		}
	}
	
	public String getScore() {
		return Integer.toString(scoreEvens) + " " + Integer.toString(scoreOdds);
	}
	
	public void startTurn() {
		System.out.println("starting turn");
		bases.setHitter(top);
		int strikes = 0;
		while (true) {
			pitch = getPitch(getAnswerFromMount(bases.getPitcher()));
			System.out.println("sending pitch " + pitch.toString() + " to player " + bases.getHitter().getClientID() + " from player " + bases.getPitcher().getClientID());
			sendPitch(pitch);
			System.out.println("hit received : " + answers);
			swing = swingResult(pitch);
			System.out.println(swing);
			if (swing == "hit") {
				break;
			} else {
				strikes++;
				if (strikes == 1) {
					outs++;
					break;
				}
			}
		}
	}
	
	private String swingResult(ArrayList<Integer> pitch) {
		if (pitch.get(0) * pitch.get(1) == answers.get(0).get(1)) {
			if (answers.get(0).get(0) == 0) {
				System.out.println("right answer from catcher");
				return "strike";
			} else {
				System.out.println("right answer from batter");
				return "hit";
			}
		} else {
			if (answers.get(0).get(0) == 0) {
				System.out.println("wrong answer from catcher");
				return "hit";
			} else {
				System.out.println("wrong answer from batter");
				return "strike";
			}
		}
	}
		
	
	private ArrayList<Integer> getPitch(String str) {
		Random rand = new Random();
		ArrayList<Integer> intPitch = new ArrayList<>();
		
		if (str.equals("0")) {
			intPitch.add(rand.nextInt(5) + 3);
			intPitch.add(rand.nextInt(5) + 3);
			intPitch.add(1);
		} else {
			intPitch.add(rand.nextInt(5) + 8);
			intPitch.add(rand.nextInt(5) + 8);
			intPitch.add(2);
		}
		
		return intPitch; 
	}
	
	private void sendPitch(ArrayList<Integer> pitch) {
		bases.getHome().get(0).sender(pitch.toString());	
		bases.getHome().get(1).sender(pitch.toString());
		System.out.println("getAnswers game " + gameID + " started");
		answers.clear();
		while (answers.size() < bases.getHome().size()) {
			for (int i = 0 ; i < 2 ; i++) {
				if(!bases.getHome().get(i).getStoredIn().isBlank()) {
					ArrayList<Integer> answer = new ArrayList<>();
					answer.add(i);
					answer.add(Integer.valueOf(bases.getHome().get(i).getStoredIn()));
					answers.add(answer);
					bases.getHome().get(i).clearStoredIn();
					System.out.println("got " + answers.get(answers.size()-1).get(1) + " from player " + bases.getHome().get(i).getClientID());
				}
			}
		}
	}
	
	private void endTurn() {
		System.out.println("endTurn started");
		if (outs < 3){
			if (swing.equals("hit")) {
				upScore(bases.cycleBases(pitch.get(2)));
			} else {
				bases.clearBatter();
			}
		}
		System.out.println("outs " + outs + " ,score " + scoreEvens + " - " + scoreOdds);
	}
	
	private void endInning() {
		System.out.println("starting endInning");
		outs = 0;
		bases.clearBases();
		upInning();
	}
	
	private void endGame() {
		System.out.println("final score " + scoreEvens + " - " + scoreOdds);
		massSend("final score " + scoreEvens + " - " + scoreOdds);
		//save stats
		//close game
	}
	
	public void massSend(String str) {
		for(ClientHandler client : players) {
			client.sender(str);
		}
	}
	
}