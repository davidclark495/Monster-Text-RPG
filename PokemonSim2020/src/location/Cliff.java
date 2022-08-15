package location;

import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public class Cliff extends WildEncounterArea {
	public Cliff() {
		this("Cliff");
	}
	public Cliff(String nm) {
		super(nm);
		this.setMapDescription("A dangerous precipice. Home to a special pokemon.");
		this.setLocalDescription("There's a nest full of eggs by the edge.");
		this.setEncounterPrompt("Approach the edge?");
		setFieldEncounters();
	}


	/**
	 * helper method for constructor
	 */
	private void setFieldEncounters() {
		// used to generate a random encounter
		Pokemon[] possiblePokes = new Pokemon[]{Dex.skarmory};
		double[] encounterRates = new double[]{1.00};
		super.setPossibleEncounters(possiblePokes, encounterRates);
	}
}
