package location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.StandardIO;
import io.StandardMenu;
import pokemon.Player;

/**
 * Contains utility functions for displaying and interacting with Location data.
 * @author davidclark
 * date: 09/03/2022
 */
public class LocationUtil {

	/**
	 * 
	 */
	public static void travel(WorldMap world, Player player, Location currLoc) {
		List<Map.Entry<Location, PathLock>> outPaths = world.getOutboundPaths(currLoc);
		
//		StandardIO.printDivider();
		String prompt = "Current Location: " + currLoc.getName();
		String[] options = new String[outPaths.size()];
		for(int i = 0; i < outPaths.size(); i++) {
			Location dst = outPaths.get(i).getKey();
			PathLock lock = outPaths.get(i).getValue();
			if(lock.isUnlocked())
				options[i] = getLocationAsString(dst);
			else
				options[i] = "[LOCKED]";
		}
		int choice = StandardMenu.promptIndexEscapable(prompt, options);
		
		// handle escape request
		if( choice == -1 ){
			return;
		}
		// handle 'locked path' selection
		if( !(outPaths.get(choice).getValue().isUnlocked()) ){
			StandardIO.println("That path is currently inaccessible.\n");
			return;
		}

		
		Location dst = outPaths.get(choice).getKey();
		player.setLocation(dst);
		StandardIO.println("You travelled to '" + dst.getName() + "'.\n");
	}
	
	public static int getNumUnlockedPathsOut(WorldMap world, Location loc) {
		List<Map.Entry<Location, PathLock>> outPaths = world.getOutboundPaths(loc);
		int numUnlockedPathsOut = 0;
		for(int i = 0; i < outPaths.size(); i++) {
			PathLock lock = outPaths.get(i).getValue();
			if(lock.isUnlocked())
				numUnlockedPathsOut++;
		}
		return numUnlockedPathsOut;
	}
	
	/**
	 * @return the name and description
	 */
	public static String getLocationAsString(Location loc) {
		String message = loc.getName() + ":\n\t" + loc.getMapDescription();
		return message;
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
