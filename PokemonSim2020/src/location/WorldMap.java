package location;

public class WorldMap {

	private Location start;
	
	public WorldMap() {
		setUp();
	}
	
	public void setUp() {
		// make places
		Location firstArea = new FirstArea("Sunny Clearing");
		Location starterFieldA = new Field("Sunny Field A", 1);
		Location starterFieldB = new Field("Sunny Field B", 2);
		Location pokeCenter = new PokeCenter("Hometown PokeCenter");
		Location nearbyCave = new Cave("Nearby Cave");
		Location highCliff = new Cliff("High Cliff");
		Location honeyZone = new HoneyZone("Sticky Hive");
		
		
		// make connections
		firstArea.addLockedPathAway(starterFieldA, 1);
		Location.addMutualPathSingleLock(starterFieldA, starterFieldB, 1);
		
		Location.addMutualPath(starterFieldB, pokeCenter);
		Location.addMutualPath(starterFieldB, nearbyCave);
		Location.addMutualPathSingleLock(starterFieldB, honeyZone, 3);
		
		Location.addMutualPathSingleLock(nearbyCave, highCliff, 2);
		
		
		// set start
		start = firstArea;
	}
	
	public Location getStart() {
		return start;
	}
	
}
