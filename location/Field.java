package location;

import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public class Field extends WildEncounterArea{

	public Field() {
		this("Field");
	}
	// base constructor
	public Field(String nm) {
		super(nm);
		this.setMapDescription("A place with tall grass and wild pokemon.");
		this.setLocalDescription("There's a warm breeze.");
		this.setEncounterPrompt("Walk into the tall grass?");
		setFieldEncountersDefault();
	}
	public Field(String nm, int variant) {
		this(nm);
		setEncounterVariant(variant);
	}


	/**
	 * sets the encounter possibilities from a one of several options
	 * 0 is a default case
	 */
	public void setEncounterVariant(int variant) {
		switch(variant) {
		case 1:
			setFieldEncountersA();
			break;
		case 2:
			setFieldEncountersB();
			break;
		default:
			setFieldEncountersDefault();
		}
	}
	
	/**
	 * helper method for constructor
	 */
	private void setFieldEncountersA() {
		// used to generate a random encounter
		Pokemon[] possiblePokes = new Pokemon[]{
				Dex.missingno, 
				Dex.eevee, 
				Dex.sunkern,
				new Pokemon(Dex.cutiefly, 3), 
				Dex.vulpix};
		double[] encounterRates = new double[]{
				0.10, 
				0.35, 
				0.35, 
				0.10,
				0.10};
		super.setPossibleEncounters(possiblePokes, encounterRates);
	}
	
	/**
	 * helper method for constructor
	 */
	private void setFieldEncountersB() {
		// used to generate a random encounter
		Pokemon[] possiblePokes = new Pokemon[]{
				Dex.missingno, 
				Dex.eevee, 
				Dex.pikachu,
				Dex.carnivine, 
				Dex.sunkern};
		double[] encounterRates = new double[]{
				0.10, 
				0.30, 
				0.30, 
				0.15,
				0.15};
		super.setPossibleEncounters(possiblePokes, encounterRates);
	}
	
	/**
	 * helper method for constructor
	 */
	private void setFieldEncountersDefault() {
		// used to generate a random encounter
		Pokemon[] possiblePokes = new Pokemon[]{
				Dex.missingno, 
				Dex.eevee, 
				Dex.sunkern};
		double[] encounterRates = new double[]{
				0.10, 
				0.45, 
				0.45};
		super.setPossibleEncounters(possiblePokes, encounterRates);
	}
	
	

	
		
}
