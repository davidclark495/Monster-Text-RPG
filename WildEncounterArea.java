package location;

import game.PokeBattle;
import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;

public class WildEncounterArea extends Location{

	private Pokemon[] possiblePokes;
	private double[] encounterRates;
	private String encounterPrompt;

	public WildEncounterArea() {
		this("Wild Encounter Area", null);
	}
	public WildEncounterArea(String nm) {
		this(nm, null);
	}
	public WildEncounterArea(Player plyr) {
		this("Wild Encounter Area", plyr);
	}
	public WildEncounterArea(String nm, Player plyr) {
		super(nm, plyr);
		this.setDescription("A dangerous place with wild pokemon.");
		this.encounterPrompt = "Start encounter?";
	}

	@Override
	public void runActivity() {
		if(possiblePokes == null)
			setPossibleEncounters(new Pokemon[]{Dex.missingno}, new double[]{1.00});
		startEncounterLoop();
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
		io.printLineBreak();
		while(runLoop) {
			// generate a new wild pokemon

			Pokemon wildPoke = Dex.generateEncounter(possiblePokes, encounterRates);
			if( wildPoke.sameSpecies(Dex.missingno) ) {
				io.printLineBreak();
				System.out.println("...nothing happened.\n");
				runLoop = startEncounterPrompt();
				continue;
			}

			// run the battle
			PokeBattle battle = new PokeBattle(this.getTrainer(), wildPoke);
			battle.run();
			// repeat?
			runLoop = startEncounterPrompt();
			io.printLineBreak();
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
			System.out.println(encounterPrompt + "\n"
					+ "0 - No\n"
					+ "1 - Yes\n");
			choice = io.promptInt();

			// option 0: don't start encounter
			if( choice == 0 ){
				return false;
			}
			// option 1: start encounter
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

	// getters & setters
	public void setEncounterPrompt(String encounterPrompt) {
		this.encounterPrompt = encounterPrompt;
	}
	
}
