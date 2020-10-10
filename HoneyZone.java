package location;

import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public class HoneyZone extends WildEncounterArea {

	public HoneyZone() {
		this("Honey Zone", null);
	}
	public HoneyZone(String nm) {
		this(nm, null);
	}
	public HoneyZone(Player plyr) {
		this("Honey Zone", plyr);
	}
	public HoneyZone(String nm, Player plyr) {
		super(nm, plyr);
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
				Dex.swirlix,
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
