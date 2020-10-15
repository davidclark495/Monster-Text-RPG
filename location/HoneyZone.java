package location;

import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public class HoneyZone extends WildEncounterArea {

	public HoneyZone() {
		this("Honey Zone");
	}
	// base constructor
	public HoneyZone(String nm) {
		super(nm);
		this.setMapDescription("A sticky-sappy paradise for bug pokemon.");
		this.setLocalDescription("There's a sickly sweet aroma.");
		this.setEncounterPrompt("Try the honey?");
		setFieldEncounters();
	}


	/**
	 * helper method for constructor
	 */
	private void setFieldEncounters() {
		// used to generate a random encounter
		Pokemon[] possiblePokes = new Pokemon[]{
				Dex.combee,
				Dex.cutiefly,
				Dex.milcery,
				Dex.grimer
				};
		double[] encounterRates = new double[]{
				0.30,
				0.30,
				0.30,
				0.10
				};
		super.setPossibleEncounters(possiblePokes, encounterRates);
	}
	
}
