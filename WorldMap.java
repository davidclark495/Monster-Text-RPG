package location;

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
		Location honeyZone = new HoneyZone("Sticky Hive");
		
		
		// make connections
		starterField.addMutualPath(pokeCenter);
		starterField.addMutualPath(nearbyCave);
		starterField.addMutualPath(honeyZone);
		
		nearbyCave.addMutualPath(highCliff);
		
		starterField.addMutualPathSingleLock(highCliff);
		
		// set start
		start = starterField;
	}
	
	public Location getStart() {
		return start;
	}
	
}
