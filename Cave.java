package location;

import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public class Cave extends WildEncounterArea{
	
	public Cave() {
		this("Cave", null);
	}
	public Cave(String nm) {
		this(nm, null);
	}
	public Cave(Player plyr) {
		this("Cave", plyr);
	}
	public Cave(String nm, Player plyr) {
		super(nm, plyr);
		this.setMapDescription("A dark place with wild pokemon.");
		this.setLocalDescription("Your footsteps echo through the space.");
		this.setEncounterPrompt("Step into the dark?");
		setFieldEncounters();
	}


	/**
	 * helper method for constructor
	 */
	private void setFieldEncounters() {
		// used to generate a random encounter
		Pokemon[] possiblePokes = new Pokemon[]{
				Dex.missingno, 
				Dex.swoobat, 
				Dex.rolycoly};
		double[] encounterRates = new double[]{
				0.10, 
				0.45, 
				0.45};
		super.setPossibleEncounters(possiblePokes, encounterRates);
	}
	
}
