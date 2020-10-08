package location;

import java.util.ArrayList;

import io.StandardIO;
import pokemon.Player;
import pokemon.Trainer;

public abstract class Location {

	private ArrayList<Location> pathsAway;
	private String name, description;
	private Player player;
	private Trainer playerTrainer;
	protected StandardIO io;

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
		pathsAway = new ArrayList<Location>();
		io = new StandardIO();
	}



	/**
	 * 
	 */
	public abstract void runActivity();

	/**
	 * 
	 */
	public void runTravelActivity() {
		// move the trainer to a new area
		io.printDivider();
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
			System.out.println("Travel to a new area?\n"
					+ "0 - No\n"
					+ "1 - Yes\n");
			choice = io.promptInt();

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
				io.printInputNotRecognized();
			}
			io.printDivider();
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
			System.out.println("Current Location: " + name);
			printPathsAway();
			io.printEscCharReminder();
			choice = io.promptInt();
			io.printLineBreak();

			// handle escape request
			if( choice == -1 ){
				return;
			}
		} while (loopAgain);

		player.setLocation(getPathAway(choice));
		System.out.println("You travelled to '" + getPathAway(choice).getName() + "'.\n");

	}








	/**
	 * @return the name and description
	 */
	public String toString() {
		String message = this.name + ":\n\t" + description;
		return message;
	}

	public void printPathsAway() {
		String printMess = "Nearby Locations:\n\n";
		for(int i = 0; i < pathsAway.size(); i++) {
			printMess += i + " - " +  pathsAway.get(i).toString() + "\n";
		}
		System.out.println(printMess);
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

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public void setPathsAway(ArrayList<Location> newPathsAway) {
		this.pathsAway = newPathsAway;
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



}
