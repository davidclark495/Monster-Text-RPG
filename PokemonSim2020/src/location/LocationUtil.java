package location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.StandardIO;
import pokemon.Player;

/**
 * Contains utility functions for displaying and interacting with Location data.
 * @author davidclark
 * date: 09/03/2022
 */
public class LocationUtil {

	////////////////////
	// STRING METHODS //
	////////////////////
	// 
	// output or return strings/data
	// 
	
	/**
	 * 
	 */
	public static void travel(WorldMap world, Player player, Location currLoc) {
		List<Map.Entry<Location, PathLock>> outPaths = world.getOutboundPaths(currLoc);
		
		int choice;
		boolean loopAgain = false;
		do {
			// handle menu
			StandardIO.printDivider();
			StandardIO.println("Current Location: " + currLoc.getName());
			printPathsAway(world, currLoc);// print a menu, 1 to N
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt() -1;// convert from display values to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -2 ){// account for offset of -1, EscChar is -2
				return;
			}
			

			// handle good/bad inputs
			if( choice < 0  || choice > outPaths.size()) {// catch out-of-bounds inputs
				loopAgain = true;
				StandardIO.printInputNotRecognized();
			}else if( !(outPaths.get(choice).getValue().isUnlocked()) ){// attempt to access locked paths
				loopAgain = true;
				StandardIO.println("That path is currently inaccessible.\n");
			}else {// catch good inputs
				loopAgain = false;				
			}
		} while (loopAgain);

		Location dst = outPaths.get(choice).getKey();
		player.setLocation(dst);
		StandardIO.printDivider();
		StandardIO.println("You travelled to '" + dst.getName() + "'.\n");
		
	}
	
	/**
	 * @return the name and description
	 */
	public static String getLocationAsString(Location loc) {
		String message = loc.getName() + ":\n\t" + loc.getMapDescription();
		return message;
	}

	public static void printPathsAway(WorldMap world, Location currLoc) {
		// find open, closed paths
		List<Map.Entry<Location, PathLock>> pathsAway = world.getOutboundPaths(currLoc);
		List<Location> pathsAwayOpen = new ArrayList<Location>();
		List<Location> pathsAwayLocked = new ArrayList<Location>();
		for(Map.Entry<Location,PathLock> entry : pathsAway) {
			Location dst = entry.getKey();
			PathLock lock = entry.getValue();
			if(lock.isUnlocked())
				pathsAwayOpen.add(dst);
			else
				pathsAwayLocked.add(dst);
		}
		// print in a nice format
		String printMess = "Nearby Locations:\n\n";
		int pathNumber = 1;
		for(int i = 0; i < pathsAwayOpen.size(); i++) {
			printMess += String.format("%d - %s\n", pathNumber, getLocationAsString(pathsAwayOpen.get(i)));
			pathNumber++;
		}
		for(int i = 0; i < pathsAwayLocked.size(); i++) {
			printMess += String.format("%d - [LOCKED]\n", pathNumber);
			pathNumber++;
		}

		StandardIO.println(printMess);
	}
	
	
	/**
	 * 
	 */
	public static void printLocalDescription(Location loc) {
		StandardIO.printDivider();
		StandardIO.println(loc.getLocalDescription());
		StandardIO.printLineBreak();
	}


	
	//////////////////
	// PATH METHODS //
	//////////////////
	
	
//	/**
//	 * Moves paths (i.e. a location) from the lockedPathsAway list to the pathsAway list
//	 * if the required number of iterations has been met.
//	 * 
//	 * @return whether or not a path was unlocked
//	 */
//	public boolean checkUnlockPaths() {
//
//		boolean pathUnlocked = false;
//		
//		for(int i = 0; i < lockedPathsAway.size(); i++) {
//			HiddenPathData data = lockedPathsAway.get(i);
//			if(data.getUnlockIteration() == this.runActivityIterations) {
//				this.addPathAway(data.getLockedPath());
//				this.lockedPathsAway.remove(i);
//				StandardIO.printDivider();
//				StandardIO.println("...a new path has appeared.\n");
//				pathUnlocked = true;
//			}
//		}
//		
//		return pathUnlocked;
//	}

	
}
