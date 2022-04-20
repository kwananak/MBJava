package packClient;

public class BasesClient {
	int[] homeCoords = {440, 200};
	int[] firstCoords = {190, 400};
	int[] secondCoords = {440, 600};
	int[] thirdCoords = {680, 400};
	int[] mountCoords  = {440, 360};
	
	int[] homeCoordsField = {homeCoords[0] - 40, homeCoords[1]};
	int[] firstCoordsField = {firstCoords[0] - 40, firstCoords[1]};
	int[] secondCoordsField = {secondCoords[0] - 40, secondCoords[1]};
	int[] thirdCoordsField = {thirdCoords[0] - 40, thirdCoords[1]};
	
	int[] homeCoordsBat = {homeCoords[0] + 40, homeCoords[1]};
	int[] firstCoordsBat = {firstCoords[0] + 40, firstCoords[1]};
	int[] secondCoordsBat = {secondCoords[0] + 40, secondCoords[1]};
	int[] thirdCoordsBat = {thirdCoords[0] + 40, thirdCoords[1]};
	
	TeamClient[] teams;
	
	public BasesClient(TeamClient[] t) {
		teams = t;
	}
}
