package location;

import java.util.Random;

import game.PokeBattle;
import io.StandardIO;
import io.StandardMenu;
import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Species;

public class WildEncounterArea extends Location{

	private Species[] possibleSpecies;	// a list of all pokemon that can spawn
	private double[] encounterRates;	// parallel array, chance a given pokemon will spawn
	private int[] pokeLowerLevelBound;	// parallel array, lowest level a pokemon can spawn at
	private int[] pokeUpperLevelBound;	// parallel array, highest level a pokemon can spawn at
	private String encounterPrompt;

	public WildEncounterArea(String nm, String mapDesc, String localDesc, String encounterPrompt) {
		super(nm, mapDesc, localDesc);
		this.encounterPrompt = encounterPrompt;
		defineActivity();
	}


	/**
	 * a helper method that Location requires
	 * sets the location's Activity to a certain lambda function that represents the main function of the area
	 */
	protected void defineActivity() {
		this.setActivity( () -> {
			startEncounterLoop();
			return true;
		});
	}

	
	public void setPossibleEncounters(Species[] possiblePokes, double[] encounterRates,
			int[] pokeLowerLevelBound, int[] pokeUpperLevelBound) {
		// check: arrays are equal length
		if( possiblePokes.length != encounterRates.length ) {
			throw new IllegalArgumentException("Error: pokes[].length must be equal to rates[].length.");
		}
		
		this.possibleSpecies = possiblePokes;
		this.encounterRates = encounterRates;
		this.pokeLowerLevelBound = pokeLowerLevelBound;
		this.pokeUpperLevelBound = pokeUpperLevelBound;
	}

	/**
	 * NEEDS REVIEW
	 * helper for runActivity()
	 * should prompt user; 'yes' initiates battle, 'no' ends program
	 */
	private void startEncounterLoop() {
		boolean runLoop = startEncounterPrompt();
		while(runLoop) {


			// generate a new wild pokemon
			Pokemon wildPoke = generateEncounter();

			// print an image of the wildPoke, if possible 
			// (printTXTFile will fail if res/ascii_art/ does not contain an applicable file)
			//			StandardIO.printTXTFile(wildPoke.getAsciiFilePath());

			// run the battle
			PokeBattle battle = new PokeBattle(this.getPlayer(), wildPoke);
			battle.run();
			// repeat? 
			if(this.getPlayer().getTrainer().canFight()) {
				runLoop = startEncounterPrompt();
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
		boolean startEncounter = StandardMenu.promptYesOrNo(encounterPrompt);
		if(startEncounter)
			StandardIO.printDivider();
		
		return startEncounter;
	}


	/**
	 * Generates a random encounter from an array of pokemon (w/ a parallel array of encounter rates)
	 * Returns a random pokemon from the list
	 * 
	 * Works by treating the rates as intervals, 
	 * then stacking them btwn 0 and 1, 
	 * then throwing a dart btwn 0 and 1, 
	 * then checking each interval to see if it caught the dart
	 * 
	 * "pokes" is an array of all Pokemon that could appear
	 * "rates" is a correlated array containing the relative frequency of encountering each Pokemon; 
	 * 				each value should be btwn 0 and 1; 
	 * 				the total value of all rates should be 1
	 * @return a random pokemon chosen from pokes
	 */
	public Pokemon generateEncounter() {
		Random rng = new Random();
		
		// generate encounter
		double selection = rng.nextDouble();
		double lowerBound = 0;
		double upperBound = encounterRates[0];
		// find which interval the selection landed in
		for(int i = 0; i < possibleSpecies.length; i++) {
			if( lowerBound < selection && selection < upperBound) {
				int levelVariation = pokeUpperLevelBound[i] - pokeLowerLevelBound[i];
				int randLevel = rng.nextInt(levelVariation) + pokeLowerLevelBound[i];
				return new Pokemon(possibleSpecies[i], randLevel);
			}
			// set up the next loop
			lowerBound += encounterRates[i];
			upperBound += encounterRates[i+1];
		}

		// this line may execute if sum of encounterRates is not 1.0
		return new Pokemon(possibleSpecies[0], pokeLowerLevelBound[0]);
	}
	
	
	// TESTER METHODS
	public String getEncountersString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s:\n",this.getName()));
		for(int i = 0; i < possibleSpecies.length; i++) {
			sb.append(String.format("\t%.2f %12s %2d %2d\n", 
					encounterRates[i],
					possibleSpecies[i].getName(), 
					pokeLowerLevelBound[i],
					pokeUpperLevelBound[i]));
		}
		String temp = sb.toString();
		return sb.toString();
	}
}
