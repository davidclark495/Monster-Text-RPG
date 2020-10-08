package game;

import java.util.Random;

import io.StandardIO;
import items.Pokeball;
import items.Potion;
import location.Player;
import pokemon.Attack;
import pokemon.Dex;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

public class MainGame {

	private Player player;
	private Trainer playerTrainer;
	private StandardIO io;

	public MainGame() {
		setUpPlayer();
		io = new StandardIO();
	}

	/**
	 * 
	 */
	public void run() {
		walkInGrassLoop();
	}
	
	/**
	 * NEEDS REVIEW
	 * helper for run()
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
		
		boolean runLoop;
		runLoop = walkInGrassPrompt();
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
			PokeBattle battle = new PokeBattle(playerTrainer, wildPoke);
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

	/**
	 * run on startup, sets up the trainer
	 */
	private void setUpPlayer() {
		// set up the player's team
		playerTrainer = new Trainer();
		playerTrainer.addPokemon(new Pokemon(Dex.charmander));
		playerTrainer.addPokemon(new Pokemon(Dex.whooper));

		playerTrainer.getBag().addItem(Pokeball.POKEBALL, 99);
		playerTrainer.getBag().addItem(Pokeball.GREATBALL, 5);
		playerTrainer.getBag().addItem(Potion.HYPER_POTION, 99);
	}
}
