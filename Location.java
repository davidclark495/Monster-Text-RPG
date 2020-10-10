package location;

import java.util.ArrayList;

import io.StandardIO;
import pokemon.Player;
import pokemon.Trainer;

public abstract class Location {

	private ArrayList<Location> pathsAway;
	private ArrayList<Location> lockedPathsAway;
	private String name, mapDescription, localDescription;
	//private int runActivityIterations; FEATURE - used to lock paths until a certain threshold is met, used for various encounters in same place
	private Player player;
	private Trainer playerTrainer;

	public Location() {
		this(null, null);
	}
	public Location(String nm) {
		this(nm, null);
	}
	public Location(String nm, Player plyr) {
		this.name = nm;
		player = plyr;
		if(player != null)
			playerTrainer = plyr.getTrainer();
		pathsAway = new ArrayList<>();
		lockedPathsAway = new ArrayList<>();
		mapDescription = "";
		localDescription = "";
		//runActivityIterations = 0;
	}



	/**
	 * 
	 */
	public void runActivity() {
		printLocalDescription();
		//runActivityIterations++;
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
	 * Helper for travelLoop()
	 * Loops until the player chooses "No" (false) or "Yes" (true)
	 */
	private boolean travelPrompt() {
		int choice;
		while(true) {

			// handle menu
			StandardIO.println("Travel to a new area?\n"
					+ "0 - No\n"
					+ "1 - Yes\n");
			choice = StandardIO.promptInt();

			// option 0: don't travel
			if( choice == 0 ){
				return false;
			}
			// option 1: travel to a new area
			else if(choice == 1) {
				return true;
			}
			// bad input: allow loop to repeat
			else {
				StandardIO.printInputNotRecognized();
			}
		}
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
			printPathsAway();
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt();
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -1 ){
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
		for(i = 0; i < pathsAway.size(); i++) {
			printMess += i + " - " +  pathsAway.get(i).toString() + "\n";
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
	 * Sets a path going to (and a path coming from) the provided location
	 * 
	 * @param loc The new location to be connected
	 */
	public void addMutualPath(Location loc) {
		this.pathsAway.add(loc);
		loc.addPathAway(this);
	}
	/**
	 * Sets a path going to (and a path coming from) the provided location.
	 * The location that calls this method will have a locked path, and the location in the parameter 
	 * will have an unlocked path.
	 * 
	 * @param loc
	 */
	public void addMutualPathSingleLock(Location loc) {
		this.lockedPathsAway.add(loc);
		loc.addPathAway(this);
	}
	public void setPathsAway(ArrayList<Location> newPathsAway) {
		this.pathsAway = newPathsAway;
	}
	/**
	 * Moves a path (i.e. a location) from the lockedPathsAway list to the pathsAway list.
	 * The index is the value printed 
	 * 
	 * @param index
	 */
	public void unlockPath(int index) {
		
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



}
