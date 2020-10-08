package location;

import java.util.ArrayList;

public class WorldMap {

	private Location start;
	
	public WorldMap() {
		setUp();
		
		
	}
	
	public void setUp() {
		// list to foster connections
		ArrayList<Location> tempLocations = new ArrayList<>();

		// make places
		Location start = new Field("Starter Field");
		Location pokeCenter = new PokeCenter();
		
		// make connections
		tempLocations.add(pokeCenter);
		start.setPathsAway(tempLocations);
	}
	
}
