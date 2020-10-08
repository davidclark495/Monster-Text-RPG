package pokemon;

import location.Location;

public class Player {

	private Trainer trainer;
	private Location location;
	
	public Player() {
		trainer =  new Trainer();
	}
	public Player(Trainer trainer) {
		this.trainer = trainer;
	}
	
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trnr) {
		this.trainer = trnr;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location newLocation) {
		location = newLocation;
		location.setPlayer(this);
	}

}
