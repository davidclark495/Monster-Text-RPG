package game;

import io.StandardIO;
import items.Pokeball;
import items.Potion;
import pokemon.Attack;
import pokemon.Dex;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

public class MainGame {

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
		int choice;
		boolean loopAgain = false;
		do {

			// handle menu
			System.out.println("Walk in the tall grass?\n"
					+ "0 - No\n"
					+ "1 - Yes\n");
			choice = io.promptInt();

			// end the game
			if( choice == 0 ){
				return;
			}
			// handle bad inputs
			if(choice == 1) {
				loopAgain = true;
				
			}
			// start a battle w/ a wild pokemon
			PokeBattle battle = new PokeBattle(playerTrainer, Dex.eevee);
			battle.run();
			io.printDivider();
		}while(loopAgain);
	}

	/**
	 * run on startup, sets up the trainer
	 */
	private void setUpPlayer() {
		// set up the player's team
		playerTrainer = new Trainer();
		playerTrainer.addPokemon(Dex.charmander);
		playerTrainer.addPokemon(Dex.whooper);

		playerTrainer.getBag().addItem(Pokeball.POKEBALL, 3);
		playerTrainer.getBag().addItem(Pokeball.GREATBALL);
		playerTrainer.getBag().addItem(Potion.POTION);
	}
}
