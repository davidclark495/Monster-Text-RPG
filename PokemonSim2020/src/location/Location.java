package location;

import java.util.ArrayList;

import io.StandardIO;
import pokemon.Player;
import pokemon.Trainer;

public abstract class Location {

	// about self, own description
	private String name, mapDescription, localDescription;
	// about world, player, other entities
	private Player player;
	// administrative details about activities
	private Activity activity; // a functor that represents the main activity of this location
	protected int runActivityIterations = 0; // used for various encounters in same place

	
	/**
	 * 
	 * @param nm		the location's name
	 * @param mapDesc	a high-level description of the place, as viewed on a map
	 * @param localDesc	a closer description of the place, as from the location itself
	 */
	public Location(String nm, String mapDesc, String localDesc) {
		this.name = nm;
		this.mapDescription = mapDesc;
		this.localDescription = localDesc;
		defineActivity();
	}



	/**
	 * 
	 */
	public void runActivity() {
		runActivityIterations++;
		LocationUtil.printLocalDescription();
		activity.runActivity();
//		boolean pathUnlocked = checkUnlockPaths(); // updates pathsAway, prints, and stores true if a new path was found
	}

	/**
	 * a helper method
	 * sets the location's Activity to a certain lambda function that represents the main function of the area
	 */
	protected abstract void defineActivity();

	protected void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * 
	 */
	public void runTravelActivity() {
		// move the trainer to a new area
		LocationUtil.travel();
	}















	public boolean equals(Location other) {
		return this.name.equals(other.name); 
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
	public String getLocalDescription() {
		return localDescription;
	}
	public void setLocalDescription(String localDescription) {
		this.localDescription = localDescription;
	}

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}


	// --------------- inner class -----------------
	protected interface Activity{
		/**
		 *
		 * @return whether or not the activity was completely run
		 */
		public boolean runActivity();
	}

}
