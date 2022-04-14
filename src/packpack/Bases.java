package packpack;

import java.util.*;

public class Bases {
	private ArrayList<ClientHandler> home = new ArrayList<>();
	private ArrayList<ClientHandler> first = new ArrayList<>();
	private ArrayList<ClientHandler> second = new ArrayList<>();
	private ArrayList<ClientHandler> third = new ArrayList<>();
	private ArrayList<ClientHandler> mount = new ArrayList<>();
	
	private ArrayList<ClientHandler> batOrderEvens;	
	private ArrayList<ClientHandler> batOrderOdds;

	public Bases (ArrayList<ClientHandler> a, ArrayList<ClientHandler> b) {
		batOrderEvens = new ArrayList<>(a);
		batOrderOdds = new ArrayList<>(b);
	}
	
	public void setFieldHome(ArrayList<ClientHandler> p) {
		switch (p.size()) {
			case 1:
				home.add(p.get(0));
				break;
			case 2: 
				mount.add(p.get(0));
				home.add(p.get(1));
				break;
			case 3: 
				mount.add(p.get(0));
				home.add(p.get(1));
				first.add(p.get(2));
				break;
			case 4: 
				mount.add(p.get(0));		
				home.add(p.get(1));
				first.add(p.get(2));
				third.add(p.get(3));
				break;
			case 5: 
				mount.add(p.get(0));
				home.add(p.get(1));
				first.add(p.get(2));
				second.add(p.get(3));
				third.add(p.get(4));
				break;
		}
	}
	
	public ClientHandler getPitcher() {
		if (mount.isEmpty()) {
			return home.get(0);
		} else {
			return mount.get(0);
		}
	}
	
	public void setHitter(boolean top) {
		if (home.size() == 2) {
			home.remove(1);
		}
		if (top) {
			home.add(batOrderEvens.get(0));
			ArrayList<ClientHandler> newOrder = new ArrayList<>();
			newOrder.addAll(batOrderEvens);			
			newOrder.add(newOrder.get(0));
			newOrder.remove(0);
			batOrderEvens = newOrder;
		} else {
			home.add(batOrderOdds.get(0));
			ArrayList<ClientHandler> newOrder = new ArrayList<>();
			newOrder.addAll(batOrderOdds);
			newOrder.add(newOrder.get(0));
			newOrder.remove(0);
			batOrderOdds = newOrder;
		}
	}
	
	public ClientHandler getHitter() {
		return home.get(1);
	}	
}
