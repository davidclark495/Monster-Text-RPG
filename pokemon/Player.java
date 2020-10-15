package pokemon;

import items.PokemonStorageBox;
import location.Location;

public class Player {

	private Trainer trainer;
	private PokemonStorageBox box;
	private Location location;
	
	public Player() {
		this(new Trainer());
	}
	// base constructor
	public Player(Trainer trainer) {
		this.trainer = trainer;
		box = new PokemonStorageBox();
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
	public PokemonStorageBox getBox() {
		return box;
	}

}
