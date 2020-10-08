package location;

import io.StandardIO;
import game.PokeBattle;
import pokemon.Dex;
import pokemon.Pokemon;
import pokemon.Trainer;

public class Field extends Location{
	
	private StandardIO io;

	public Field() {
		this("Field", null);
	}
	public Field(String nm) {
		this(nm, null);
	}
	public Field(Trainer plyrTrnr) {
		this("Field", plyrTrnr);
	}
	public Field(String nm, Trainer plyrTrnr) {
		super(nm, plyrTrnr);
		this.setDescription("A place with tall grass and wild pokemon.");
		io = new StandardIO();
	}

	@Override
	public void runActivity() {
		walkInGrassLoop();
	}

	/**
	 * NEEDS REVIEW
	 * helper for runActivity()
	 * should prompt user w/ "Walk in the tall grass?"; 'yes' initiates battle, 'no' ends program
	 */
	private void walkInGrassLoop() {
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
		
		boolean runLoop = walkInGrassPrompt();
		while(runLoop) {
			// generate a new wild pokemon
			
			Pokemon wildPoke = Dex.generateEncounter(possiblePokes, encounterRates);
			if( wildPoke.sameSpecies(Dex.missingno) ) {
				io.printLineBreak();
				System.out.println("...nothing happened.\n");
				runLoop = walkInGrassPrompt();
				continue;
			}
			
			// run the battle
			PokeBattle battle = new PokeBattle(this.getTrainer(), wildPoke);
			battle.run();
			// repeat?
			runLoop = walkInGrassPrompt();
		}
	}
	
	/**
	 * Helper for walkInGrassLoop
	 * Loops until the player chooses "No" (false) or "Yes" (true)
	 */
	private boolean walkInGrassPrompt() {
		int choice;
		while(true) {

			// handle menu
			System.out.println("Walk in the tall grass?\n"
					+ "0 - No\n"
					+ "1 - Yes\n");
			choice = io.promptInt();

			// option 0: don't walk in grass
			if( choice == 0 ){
				return false;
			}
			// option 1: walk in grass 
			else if(choice == 1) {
				return true;
			}
			// bad input: allow loop to repeat
			else {
				io.printInputNotRecognized();
			}
			io.printDivider();
		}
	}
	
}
