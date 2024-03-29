package packpack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Game extends Thread {
	private String swing;
	private int gameID;
	private boolean top = true;
	private int strikes, balls, scoreEvens, scoreOdds, maxInnings, outs = 0;
	private int inning = 1;
	private ArrayList<ClientHandler> evens = new ArrayList<ClientHandler>();	
	private ArrayList<ClientHandler> odds = new ArrayList<ClientHandler>();
	private ArrayList<ClientHandler> players = new ArrayList<ClientHandler>();
	private ArrayList<ArrayList<Integer>> answers = new ArrayList<ArrayList<Integer>>();	
	private Bases bases;
	private ArrayList<Integer> pitch = new ArrayList<Integer>();
	private File log;
	
	
	private boolean full = false;
	
	public Game(int IDFromServer) {
		gameID = IDFromServer;
		log = new File("logs/logGame" + Integer.toString(IDFromServer) + ".txt");
	}
	
	public void run() {
		waitForPlayers();
		printLog("game " + Integer.toString(gameID) + " started with " + Integer.toString(players.size()) + " players");
		makeTeams();
		bases = new Bases(evens, odds);
		setMaxInnings();
		while(inning <= maxInnings) {
			startInning();
			while(outs < 2) {
				startTurn();
				endTurn();
			}
			endInning();
		}
		endGame();
	}
	
	public void waitForPlayers() {
		printLog("waiting on players for 3 secs");
		int i = 0;
		
		while (true) {
			i++;		
			if (players.size() > 9 || (players.size() > 1 && i > 3000)) {
				break;
			} else {
				try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}		
		full = !full;
	}
	
	public void makeTeams() {
		for(int j = 0; j < players.size(); j++) {
			if (j%2 == 0) {
				evens.add(players.get(j));
				players.get(j).sender("command:team:true");
			} else {
				odds.add(players.get(j));
				players.get(j).sender("command:team:false");
			}
		}
	}
	
	public void startInning() {
			String topStr;
			if(top) {
				topStr = "true";
			} else {
				topStr = "false";
			}
			massSend("command:jumbotron:" + strikes + "," + balls + "," + outs  + "," + inning + "," + scoreEvens + " - " + scoreOdds + "," + topStr + ",false");
			if (top) {
				bases.setFieldHome(odds);
				massSend("command:inningStart:top");
			} else {
				bases.setFieldHome(evens);
				massSend("command:inningStart:bot");
			}
	}
	
	public void setMaxInnings() {
		Integer numInns = 0;		
		massSend("command:umpire:How many Innings?");
		massSend("command:sender:How many Innings?");
		getAnswersFromHandlers();
		for(ArrayList<Integer> i : answers) {
			numInns += i.get(1);
		}
		maxInnings = numInns / answers.size();
		printLog("maxInnings : " + Integer.toString(maxInnings));
		massSend("command:umpire: " + Integer.toString(maxInnings) + " innings");				
		try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public String getAnswerFromMount(ClientHandler pitcher) {		
		pitcher.sender("command:pitch:choose a pitch");
		while (true) {
			if(!pitcher.getStoredIn().isBlank()) {
				String choice= pitcher.getStoredIn();
				pitcher.clearStoredIn();
				pitcher.sender("command:umpire: ");
				return choice;
			}
		}
	}
	
	public void getAnswersFromHandlers() {
		printLog("getAnswers game started");
		answers.clear();
		while (answers.size() < players.size()) {
			for (ClientHandler player : players) {
				if(!player.getStoredIn().isBlank()) {
					ArrayList<Integer> answer = new ArrayList<Integer>();
					answer.add(Integer.valueOf(player.getClientID()));
					answer.add(Integer.valueOf(player.getStoredIn()));
					answers.add(answer);
					player.clearStoredIn();
					printLog("got " + answers.get(answers.size()-1).get(1) + " from player " + player.getClientID());
				}
			}
		}
		printLog("closing getAnswers game " + gameID);
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
		String topStr;
		if(top) {
			topStr = "true";
		} else {
			topStr = "false";
		}
		try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		massSend("command:turnStart:" + strikes + "," + balls + "," + outs  + "," + inning + "," + scoreEvens + " - " + scoreOdds + "," + topStr + ",false");
		bases.setHitter(top);
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		while (true) {
			massSend("command:startLoop:" + strikes + "," + balls + "," + outs  + "," + inning + "," + scoreEvens + " - " + scoreOdds + "," + topStr + ",false");			
			pitch = getPitch(getAnswerFromMount(bases.getPitcher()));
			try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
			massSend("command:umpire:" + pitch.get(0) + " * " + pitch.get(1));
			printLog("sending pitch " + pitch.toString() + " to player " + bases.getHitter().getClientID() + " from player " + bases.getPitcher().getClientID());
			sendPitch(pitch);
			printLog("swing received : " + answers);
			swing = swingResult(pitch);
			printLog(swing);
			if (swing == "hit") {
				break;
			} else {
				strikes++;
				massSend("command:umpire:strike " + Integer.toString(strikes));
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}				
				if (strikes == 2) {
					outs++;
					break;
				}
			}
		}
	}
	
	private String swingResult(ArrayList<Integer> pitch) {
		if (pitch.get(0) * pitch.get(1) == answers.get(0).get(1)) {
			if (answers.get(0).get(0) == 0) {
				massSend("command:umpire:right answer from catcher");
				return "strike";
			} else {
				massSend("command:umpire:right answer from batter");
				return "hit";
			}
		} else {
			if (answers.get(0).get(0) == 0) {
				massSend("command:umpire:wrong answer from catcher");
				return "hit";
			} else {
				massSend("command:umpire:wrong answer from batter");
				return "strike";
			}
		}
	}
		
	
	private ArrayList<Integer> getPitch(String str) {
		Random rand = new Random();
		ArrayList<Integer> intPitch = new ArrayList<Integer>();
		
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
		bases.getHome().get(0).sender("command:sender:" + pitch.get(0) + " * " + pitch.get(1));	
		bases.getHome().get(1).sender("command:sender:" + pitch.get(0) + " * " + pitch.get(1));
		printLog("getAnswers game " + gameID + " started");
		
		answers.clear();
		while (answers.size() < bases.getHome().size()) {
			for (int i = 0 ; i < 2 ; i++) {
				if(!bases.getHome().get(i).getStoredIn().isBlank()) {
					ArrayList<Integer> answer = new ArrayList<Integer>();
					answer.add(i);
					answer.add(Integer.valueOf(bases.getHome().get(i).getStoredIn()));
					answers.add(answer);
					bases.getHome().get(i).clearStoredIn();
					printLog("got " + answers.get(answers.size()-1).get(1) + " from player " + bases.getHome().get(i).getClientID());
				}
			}
		}
	}
	
	private void endTurn() {
		strikes = 0;
		if (outs < 2){
			if (swing.equals("hit")) {
				upScore(bases.cycleBases(pitch.get(2)));
				massSend("command:cycleBases:" + pitch.get(2));
				try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
			} else {
				bases.clearBatter();
				massSend("command:clearBatter");
				try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
		printLog("outs " + outs + " ,score " + scoreEvens + " - " + scoreOdds);
	}
	
	private void endInning() {
		outs = 0;
		bases.clearBases();
		massSend("command:returnBench");
		upInning();
	}
	
	private void endGame() {
		printLog("final score " + scoreEvens + " - " + scoreOdds);
		massSend("command:umpire:final score " + scoreEvens + " - " + scoreOdds);
		//save stats
		//close game
	}
	
	public void massSend(String str) {
		for(ClientHandler client : players) {
			client.sender(str);
		}
	}
	
	public void printLog(String str) {
		FileWriter fw;
		try {
			fw = new FileWriter(log, true);
			PrintWriter pw = new PrintWriter(fw);
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		    LocalDateTime logTime = LocalDateTime.now();
		    String formattedTime = logTime.format(timeFormatter);	    
		    pw.println(formattedTime + " " + str);
			pw.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}