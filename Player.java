package location;

/**
 * 
 */
import pokemon.Trainer;

public class Player {

	private Trainer trainer;
	private Location location;
	
	public Player() {
		trainer =  new Trainer();
	}
	public Player(Trainer trainer) {
		this.trainer = trainer;
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location newLocation) {
		location = newLocation;
		location.setTrainer(this.trainer);
	}

}
