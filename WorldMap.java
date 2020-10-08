package location;

import java.util.ArrayList;

public class WorldMap {

	private Location start;
	
	public WorldMap() {
		setUp();
		
		
	}
	
	public void setUp() {
		// make places
		Location starterField = new Field("Starter Field");
		Location pokeCenter = new PokeCenter("Hometown PokeCenter");
		Location nearbyCave = new Cave("Nearby Cave");
		Location highCliff = new Cliff("High Cliff");
		
		
		// make connections
		starterField.addMutualPath(pokeCenter);
		starterField.addMutualPath(nearbyCave);
		nearbyCave.addMutualPath(highCliff);
		
		
		// set start
		start = starterField;
	}
	
	public Location getStart() {
		return start;
	}
	
}
