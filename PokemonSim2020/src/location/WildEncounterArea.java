package location;

import game.PokeBattle;
import io.StandardIO;
import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public abstract class WildEncounterArea extends Location{

	private Pokemon[] possiblePokes;
	private double[] encounterRates;
	private String encounterPrompt;

	public WildEncounterArea() {
		this("Wild Encounter Area");
	}
	// base constructor
	public WildEncounterArea(String nm) {
		super(nm);
		this.setMapDescription("A dangerous place with wild pokemon.");
		this.setLocalDescription("You feel slightly on guard.");
		this.encounterPrompt = "Start encounter?";
		defineActivity();
	}
	
	
	/**
	 * a helper method that Location requires
	 * sets the location's Activity to a certain lambda function that represents the main function of the area
	 */
	protected void defineActivity() {
		this.setActivity( () -> {
			// the actual definition of this location's Activity
			if(possiblePokes == null)
				setPossibleEncounters(new Pokemon[]{Dex.missingno}, new double[]{1.00});
			startEncounterLoop();
			return true;
		});
	}
	
	// always called before running the encounter loop, should be called by subclasses to determine behavior
	public void setPossibleEncounters(Pokemon[] possiblePokes, double[] encounterRates) {
		this.possiblePokes = possiblePokes;
		this.encounterRates = encounterRates;
	}

	/**
	 * NEEDS REVIEW
	 * helper for runActivity()
	 * should prompt user; 'yes' initiates battle, 'no' ends program
	 */
	private void startEncounterLoop() {


		boolean runLoop = startEncounterPrompt();
		StandardIO.printLineBreak();
		while(runLoop) {
			
			
			// generate a new wild pokemon
			Pokemon wildPoke = Dex.generateEncounter(possiblePokes, encounterRates);
			if( wildPoke.sameSpecies(Dex.missingno) ) {
				StandardIO.printLineBreak();
				StandardIO.println("...nothing happened.\n");
				runLoop = startEncounterPrompt();
				continue;
			}
			
			// print an image of the wildPoke, if possible 
			// (printTXTFile will fail if res/ascii_art/ does not contain an applicable file)
			StandardIO.printTXTFile(wildPoke.getAsciiFilePath());
			
			// run the battle
			PokeBattle battle = new PokeBattle(this.getPlayer(), wildPoke);
			battle.run();
			// repeat? 
			if(this.getPlayer().getTrainer().canFight()) {
				runLoop = startEncounterPrompt();
				StandardIO.printLineBreak();
			}
			else {
				runLoop = false;
			}
		}
	}

	/**
	 * Helper for walkInGrassLoop
	 * Loops until the player chooses "No" (false) or "Yes" (true)
	 */
	private boolean startEncounterPrompt() {
		int choice;
		while(true) {

			// handle menu
			StandardIO.printDivider();
			StandardIO.println(encounterPrompt + " (y/n)");
			choice = StandardIO.promptChar();
			
			// proceess response
			if(choice == 'y')
				return true;
			else if(choice == 'n')
				return false;
			// bad input: allow loop to repeat
			else {
				StandardIO.printLineBreak();
				StandardIO.printInputNotRecognized();
			}
		}
	}

	// getters & setters
	public void setEncounterPrompt(String encounterPrompt) {
		this.encounterPrompt = encounterPrompt;
	}
	
}
