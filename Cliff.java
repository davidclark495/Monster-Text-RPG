package location;

import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public class Cliff extends WildEncounterArea {
	public Cliff() {
		this("Cliff", null);
	}
	public Cliff(String nm) {
		this(nm, null);
	}
	public Cliff(Player plyr) {
		this("Cliff", plyr);
	}
	public Cliff(String nm, Player plyr) {
		super(nm, plyr);
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
