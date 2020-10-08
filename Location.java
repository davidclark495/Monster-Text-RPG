package location;

import java.util.ArrayList;

import pokemon.Trainer;

public abstract class Location {

	private ArrayList<Location> pathsAway;
	private String name, description;
	private Trainer playerTrainer;
	
	public Location() {
		
	}
	public Location(String nm) {
		this(nm, null);
	}
	public Location(String nm, Trainer plyrTrnr) {
		this.name = nm;
		playerTrainer = plyrTrnr;
		pathsAway = new ArrayList<Location>();
	}
	
	
	
	/**
	 * 
	 */
	public abstract void runActivity();
	
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
	public void setPathsAway(ArrayList<Location> newPathsAway) {
		this.pathsAway = newPathsAway;
	}
	public Location getPathAway(int index) {
		return pathsAway.get(index);
	}
	
	public Trainer getTrainer() {
		return playerTrainer;
	}
	public void setTrainer(Trainer playerTrainer) {
		this.playerTrainer = playerTrainer;
	}

	
	
}
