package packClient;

import java.util.ArrayList;
import java.util.Arrays;

public class BasesClient {
	ArrayList<Integer> homeCoords = new ArrayList<Integer>(Arrays.asList(441, 201));
	ArrayList<Integer> firstCoords = new ArrayList<Integer>(Arrays.asList(237, 399));
	ArrayList<Integer> secondCoords = new ArrayList<Integer>(Arrays.asList(441, 600));
	ArrayList<Integer> thirdCoords = new ArrayList<Integer>(Arrays.asList(639, 399));
	ArrayList<Integer> mountCoords = new ArrayList<Integer>(Arrays.asList(441, 360));
	
	ArrayList<Integer> homeCoordsField = new ArrayList<Integer>(Arrays.asList(homeCoords.get(0) - 39, homeCoords.get(1)));
	ArrayList<Integer> firstCoordsField = new ArrayList<Integer>(Arrays.asList(firstCoords.get(0) - 39, firstCoords.get(1)));
	ArrayList<Integer> secondCoordsField = new ArrayList<Integer>(Arrays.asList(secondCoords.get(0) - 39, secondCoords.get(1)));
	ArrayList<Integer> thirdCoordsField = new ArrayList<Integer>(Arrays.asList(thirdCoords.get(0) - 39, thirdCoords.get(1)));
	
	ArrayList<Integer> homeCoordsBat = new ArrayList<Integer>(Arrays.asList(homeCoords.get(0) + 39, homeCoords.get(1)));
	ArrayList<Integer> firstCoordsBat = new ArrayList<Integer>(Arrays.asList(firstCoords.get(0) + 39, firstCoords.get(1)));
	ArrayList<Integer> secondCoordsBat = new ArrayList<Integer>(Arrays.asList(secondCoords.get(0) + 39, secondCoords.get(1)));
	ArrayList<Integer> thirdCoordsBat = new ArrayList<Integer>(Arrays.asList(thirdCoords.get(0) + 39, thirdCoords.get(1)));

	
	ArrayList<TeamClient> teams;
	
	public BasesClient(ArrayList<TeamClient> t) {
		teams = t;
	}
}
