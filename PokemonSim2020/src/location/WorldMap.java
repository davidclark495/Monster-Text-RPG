package location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import pokemon.Move;
import pokemon.PkType;
import pokemon.Species;
import pokemon.SpeciesList;

public class WorldMap {

	private Location start;
	
	// maps a string w/ the location's name to the actual location
	private static Map<String, Location> allLocations;
	
	/*
	 * Stores outbound paths (known/traversible if PathLock is "unlocked") from
	 * each location.
	 */
	private static Map<Location, List<Map.Entry<Location, PathLock>>> outboundPaths;
	
	
	
	public WorldMap() {
		readLocationData();
		readPathData();
		readEncounterData();
//		setUp();
	}
	
	public void setUp() {
		// make places
		
		
		
		// make connections
		
		
		// set start
//		start = firstArea;
	}
	
	public Location getStart() {
		return start;
	}
	
	public List<Map.Entry<Location, PathLock>> getOutboundPaths(Location loc){
		return outboundPaths.get(loc);
	}
	
	
	//////////////////// 
	// INIT. FROM CSV //
	////////////////////
	
	private static void readLocationData() {
		final String COMMA_DELIMITER = ",";
		
		// Read CSV into an ArrayList
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("csv/locations.csv"))){
			String line;
			while((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 1; i < records.size(); i++) {
			List<String> list = records.get(i);
			String name = list.get(0);
			String mapDesc = list.get(1);
			String localDesc = list.get(2);
			String type = list.get(3);
			Location loc = null;
			switch(type) {
			case "Story":
				loc = new StoryArea(name, mapDesc, localDesc);
				break;
			case "Encounter":
				String encounterPrompt = list.get(4);
				loc = new WildEncounterArea(name, mapDesc, localDesc, encounterPrompt);
				break;
			case "Boss":
				loc = new BossArea(name, mapDesc, localDesc);
				break;
			case "PokeCenter":
				loc = new PokeCenter(name, mapDesc, localDesc);
				break;
			}
			allLocations.put(name, loc);
		}
	}
	
	private static void readPathData() {
		final String COMMA_DELIMITER = ",";
		
		// Read CSV into an ArrayList
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("csv/locations_paths.csv"))){
			String line;
			while((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 1; i < records.size(); i++) {
			List<String> list = records.get(i);
			String srcStr = list.get(0);
			Location srcLoc = allLocations.get(srcStr);
			String dstStr = list.get(1);
			Location dstLoc = allLocations.get(dstStr);
			boolean defaultUnlocked = (list.get(2) == "YES");			
			// build an un-locked lock, 
			// or gather more data and build a real lock
			PathLock lock;
			if(defaultUnlocked) {
				lock = new PathLock(false, 0, null);
			} else {
				boolean needsActCompletion = (list.get(3) == "YES");
				int numActAttemptsNeeded = Integer.parseInt(list.get(4));
				String otherLocReqdStr = list.get(5);
				Location otherLocReqd = allLocations.get(otherLocReqdStr);	// will be null if str is ""
				lock = new PathLock(needsActCompletion,numActAttemptsNeeded, otherLocReqd);
			}	
			// store the path (src,dst) along with the lock
			if(outboundPaths.containsKey(srcLoc)) {
				outboundPaths.get(srcLoc).add(new AbstractMap.SimpleEntry(dstLoc, lock));
			} else {
				ArrayList<Map.Entry<Location, PathLock>> outPaths = new ArrayList<>();
				outPaths.add(new AbstractMap.SimpleEntry(dstLoc, lock));
				outboundPaths.put(srcLoc, outPaths);
			}
		}
	}
	
	private static void readEncounterData() {
		final String COMMA_DELIMITER = ",";
		
		// Read CSV into an ArrayList
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("csv/locations_encounters.csv"))){
			String line;
			while((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// records look like
		/*
		 * locA		prob1	spec1	lvlL	lvlU
		 * locA		prob2	spec2	...		...
		 * ...		...		...		...		...
		 * locB		prob1	spec1	lvlL	lvlU
		 */
		// gather lines until the location changes, then submit the lines & reset
		WildEncounterArea currLoc = null;
		ArrayList<Species> species = new ArrayList<>();
		ArrayList<Double> probabilities = new ArrayList<>();
		ArrayList<Integer> levelLowerBounds = new ArrayList<>();
		ArrayList<Integer> levelUpperBounds = new ArrayList<>();
		for(int i = 1; i < records.size(); i++) {
			List<String> list = records.get(i);
			String locStr = list.get(0);
			if(currLoc != null && !(locStr.equals(currLoc.getName()))) { // if the location has changed, submit lines & reset
				// convert from ArrayList<Num> to num[] (see "https://stackoverflow.com/questions/6018267/how-to-cast-from-listdouble-to-double-in-java")
				currLoc.setPossibleEncounters(
						species.toArray(new Species[0]), 
						probabilities.stream().mapToDouble(d -> d).toArray(), 
						levelLowerBounds.stream().mapToInt(x -> x).toArray(), 
						levelUpperBounds.stream().mapToInt(x -> x).toArray());
				
				species.clear();
				probabilities.clear();
				levelLowerBounds.clear();
				levelUpperBounds.clear();
			}
			probabilities.add( Double.parseDouble(list.get(1)));
			species.add( SpeciesList.getSpeciesList().getSpecies(list.get(2)));
			levelLowerBounds.add(Integer.parseInt(list.get(3)));
			levelUpperBounds.add(Integer.parseInt(list.get(4)));
		}
	}
	
}
