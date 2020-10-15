package location;

public class WorldMap {

	private Location start;
	
	public WorldMap() {
		setUp();
		
		
	}
	
	public void setUp() {
		// make places
		Location starterFieldA = new Field("Sunny Field A", 1);
		Location starterFieldB = new Field("Sunny Field B", 2);
		Location pokeCenter = new PokeCenter("Hometown PokeCenter");
		Location nearbyCave = new Cave("Nearby Cave");
		Location highCliff = new Cliff("High Cliff");
		Location honeyZone = new HoneyZone("Sticky Hive");
		
		
		// make connections
		Location.addMutualPathSingleLock(starterFieldA, starterFieldB, 1);
		
		Location.addMutualPath(starterFieldB, pokeCenter);
		Location.addMutualPath(starterFieldB, nearbyCave);
		Location.addMutualPathSingleLock(starterFieldB, honeyZone, 1);
		
		Location.addMutualPathSingleLock(nearbyCave, highCliff, 1);
		
		
		// set start
		start = starterFieldA;
	}
	
	public Location getStart() {
		return start;
	}
	
}
