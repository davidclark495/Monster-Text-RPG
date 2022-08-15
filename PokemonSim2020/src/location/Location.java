package location;

import java.util.ArrayList;

import io.StandardIO;
import pokemon.Player;
import pokemon.Trainer;

public abstract class Location {

	// about self, own description
	private String name, mapDescription, localDescription;
	// about world, player, other entities
	private ArrayList<Location> pathsAway;
	private ArrayList<HiddenPathData> lockedPathsAway;
	private Player player;
	private Trainer playerTrainer;
	// administrative details about activities
	private int runActivityIterations = 0; //FEATURE - used to lock paths until a certain threshold is met, used for various encounters in same place
	private Activity activity; // a functor that represents the main activity of this location

	public Location() {
		this(null);
	}
	// base constructor
	public Location(String nm) {
		this.name = nm;
		pathsAway = new ArrayList<>();
		lockedPathsAway = new ArrayList<>();
		mapDescription = "";
		localDescription = "";
		defineActivity();
	}
//	public Location(String nm, Player plyr) {
//		this(nm);
//		this.setPlayer(plyr);
//	}



	/**
	 * 
	 */
	public void runActivity() {
		printLocalDescription();
		activity.runActivity();
		runActivityIterations++;
		boolean pathUnlocked = checkUnlockPaths(); // updates pathsAway, prints, and stores true if a new path was found
	}

	/**
	 * a helper method
	 * sets the location's Activity to a certain lambda function that represents the main function of the area
	 */
	protected abstract void defineActivity();
	
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	/**
	 * 
	 */
	public void printLocalDescription() {
		StandardIO.printDivider();
		StandardIO.println(localDescription);
		StandardIO.printLineBreak();
	}

	/**
	 * 
	 */
	public void runTravelActivity() {
		// move the trainer to a new area
		travel();
	}




	/**
	 * 
	 */
	public void travel() {
		int choice;
		boolean loopAgain = false;
		do {
			// handle menu
			StandardIO.printDivider();
			StandardIO.println("Current Location: " + name);
			printPathsAway();// print a menu, 1 to N
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt() -1;// convert from display values to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -2 ){// account for offset of -1, EscChar is -2
				return;
			}

			// handle good/bad inputs
			if( choice >= 0  && choice < pathsAway.size()) {// catch good inputs
				loopAgain = false;				
			}else if(choice >= pathsAway.size() && choice < pathsAway.size() + lockedPathsAway.size()){// attempt to access locked paths
				loopAgain = true;
				StandardIO.println("That path is currently inaccessible.\n");
			}else {// catch bad inputs
				loopAgain = true;
				StandardIO.printInputNotRecognized();
			}
		} while (loopAgain);

		player.setLocation(getPathAway(choice));
		StandardIO.printDivider();
		StandardIO.println("You travelled to '" + getPathAway(choice).getName() + "'.\n");

	}








	/**
	 * @return the name and description
	 */
	public String toString() {
		String message = this.name + ":\n\t" + mapDescription;
		return message;
	}

	public void printPathsAway() {
		String printMess = "Nearby Locations:\n\n";
		int i;
		for(i = 1; i <= pathsAway.size(); i++) {
			printMess += i + " - " +  pathsAway.get(i-1).toString() + "\n";
		}
		int pathNumber = i;
		for(i = 0; i < lockedPathsAway.size(); i++) {
			printMess += pathNumber++ + " - " + "[LOCKED]" + "\n";
		}

		StandardIO.println(printMess);
	}

	public boolean equals(Location other) {
		return (this.pathsAway.equals(other.pathsAway) && this.name.equals(other.name)); 
	}

	// getters and setters //
	public String getName() {
		return name;
	}
	public void setName(String nm) {
		this.name = nm;
	}

	public String getMapDescription() {
		return mapDescription;
	}
	public void setMapDescription(String description) {
		this.mapDescription = description;
	}

	public ArrayList<Location> getPathsAway() {
		return pathsAway;
	}
	public Location getPathAway(int index) {
		return pathsAway.get(index);
	}
	public void addPathAway(Location loc) {
		this.pathsAway.add(loc);
	}

	/**
	 * associates two places using an unlocked path
	 * 
	 * @param loc1
	 * @param loc2
	 */
	public static void addMutualPath(Location loc1, Location loc2) {
		loc1.addPathAway(loc2);
		loc2.addPathAway(loc1);
	}
	/**
	 * Sets a path going to (and a path coming from) the provided location.
	 * The location #1 will have a locked path, and location #2 will have an unlocked path.
	 * 
	 * @param loc1 The location with the locked path
	 * @param loc2 The location with the unlocked path
	 * @param iterationsToUnlock The number of times to do an activity at loc1 before the path opens
	 */
	public static void addMutualPathSingleLock(Location loc1, Location loc2, int iterationsToUnlock) {
		loc1.lockedPathsAway.add(new HiddenPathData(loc2, iterationsToUnlock));
		loc2.addPathAway(loc1);
	}
	
	public void addLockedPathAway(Location loc, int iterationsToUnlock) {
		this.lockedPathsAway.add(new HiddenPathData(loc, iterationsToUnlock));
	}


	public void setPathsAway(ArrayList<Location> newPathsAway) {
		this.pathsAway = newPathsAway;
	}
	/**
	 * Moves paths (i.e. a location) from the lockedPathsAway list to the pathsAway list
	 * if the required number of iterations has been met.
	 * 
	 * @return whether or not a path was unlocked
	 */
	public boolean checkUnlockPaths() {

		boolean pathUnlocked = false;
		
		for(int i = 0; i < lockedPathsAway.size(); i++) {
			HiddenPathData data = lockedPathsAway.get(i);
			if(data.getUnlockIteration() == this.runActivityIterations) {
				this.addPathAway(data.getLockedPath());
				this.lockedPathsAway.remove(i);
				StandardIO.printDivider();
				StandardIO.println("...a new path has appeared.\n");
				pathUnlocked = true;
			}
		}
		
		return pathUnlocked;
	}


	public Trainer getTrainer() {
		return playerTrainer;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
		this.playerTrainer = player.getTrainer();
	}
	public String getLocalDescription() {
		return localDescription;
	}
	public void setLocalDescription(String localDescription) {
		this.localDescription = localDescription;
	}
	public int getRunActivityIterations() {
		return runActivityIterations;
	}


	// --------------- inner class -----------------
	private static class HiddenPathData {
		private Location lockedPath;
		private int unlockIteration;

		public HiddenPathData(Location lockedPath, int unlockIteration) {
			this.lockedPath = lockedPath;
			this.unlockIteration = unlockIteration;
		}

		public Location getLockedPath() {
			return lockedPath;
		}
		public void setLockedPath(Location lockedPath) {
			this.lockedPath = lockedPath;
		}
		public int getUnlockIteration() {
			return unlockIteration;
		}
		public void setUnlockIteration(int unlockIteration) {
			this.unlockIteration = unlockIteration;
		}


	}

	protected interface Activity{
		/**
		 *
		 * @return whether or not the activity was completely run
		 */
		public boolean runActivity();
	}

}
