package location;

import io.StandardIO;
import game.PokeBattle;
import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class Field extends WildEncounterArea{

	public Field() {
		this("Field", null);
	}
	public Field(String nm) {
		this(nm, null);
	}
	public Field(Player plyr) {
		this("Field", plyr);
	}
	public Field(String nm, Player plyr) {
		super(nm, plyr);
		this.setDescription("A place with tall grass and wild pokemon.");
		this.setEncounterPrompt("Walk in the tall grass?");
		setFieldEncounters();
	}


	/**
	 * helper method for constructor
	 */
	private void setFieldEncounters() {
		// used to generate a random encounter
		Pokemon[] possiblePokes = new Pokemon[]{
				Dex.missingno, 
				Dex.eevee, 
				Dex.whooper, 
				Dex.carnivine,
				Dex.pikachu};
		double[] encounterRates = new double[]{
				0.10, 
				0.30, 
				0.30, 
				0.15,
				0.15};
		super.setPossibleEncounters(possiblePokes, encounterRates);
	}

	
		
}
