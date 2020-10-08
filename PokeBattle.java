package game;

import java.util.Random;
import java.util.Scanner;

import io.StandardIO;
import items.Item;
import items.Pokeball;
import items.Potion;
import pokemon.Attack;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

public class PokeBattle {

	private Trainer playerTrainer, enemyTrainer;
	private Pokemon playerPokemon, enemyPokemon;
	private boolean continueBattle;
	private StandardIO io;

	/**
	 * The player (trainer) vs. an enemy (trainer)
	 * 
	 * @param t1
	 * @param t2
	 */
	public PokeBattle(Trainer t1, Trainer t2) {
		this(t1, t2.getPokemon(0));
		this.enemyTrainer = t2;
	}

	/**
	 * The player (trainer) vs. a wild pokemon
	 * PlayerTrainer must have a pokemon at index 0
	 * 
	 * @param playerTrainer
	 * @param enemyPokemon
	 */
	public PokeBattle(Trainer playerTrainer, Pokemon enemyPokemon) {
		io = new StandardIO();
		this.playerTrainer = playerTrainer;
		this.enemyPokemon = enemyPokemon;

		// find the trainer's first non-fainted pokemon
		for(int i = 0; i < playerTrainer.getNumPokemon(); i++){
			if( ! playerTrainer.getPokemon(i).isFainted() ) {
				this.playerPokemon = playerTrainer.getPokemon(i);
				break;
			}
		}
	}



	/**
	 * Plays through a battle.
	 * Prints initial statistics, then runs a battle-menu loop, then ends the game.
	 */
	public void run() {
		// if the player doesn't have a valid first pokemon, end battle
		if(playerPokemon == null) {
			System.out.println("You need a healthy Pokemon to do that.\n");
			return;
		}
		
		runStart();

		// main game loop, continue until otherwise stated
		continueBattle = true;
		while( continueBattle ) {
			// print menus, communicates with player
			runBattleMenu(); if(!continueBattle) break;// if player flees, exit the loop
			runEnemyTurn();
			io.delay();

			// check for end of battle
			if( playerPokemon.isFainted() ) {
				continueBattle = false;
				runPlayerLosesEnding();
				if (!playerTrainer.canFight())
					break;
			}
			else if( enemyPokemon.isFainted() ) {
				continueBattle = false;
				runPlayerWinsEnding();				
			}
		}
	}




	// helper methods //

	/**
	 * Helper for run().
	 * Prints an intro message.
	 */
	private void runStart() {
		System.out.println("\nA wild " + enemyPokemon.getName() + " appeared!");
		io.printLineBreak();
		io.delay();
	}

	/**
	 * Helper for run().
	 * Represents the Player's turn.
	 * Prints the battles status, then asks the player to make a choice, then handles that choice.
	 */
	private void runBattleMenu() {
		// print battle info
		io.printDivider();
		String status = playerPokemon.getNickname() + ": " + playerPokemon.getHp() + " HP\n"
				+ enemyPokemon.getName() + ": " + enemyPokemon.getHp() + " HP\n";
		System.out.println(status);

		// print options
		String options = "What would you like to do?\n"
				+ "1 - attack\n"
				+ "2 - run away\n"
				+ "3 - bag\n"
				+ "4 - pokemon\n";
		System.out.println(options);

		// respond to user choice
		int choice = io.promptInt();
		io.printLineBreak();
		io.printDivider();
		switch(choice) {		
		case 1:
			// "attack" 
			choiceMovesMenu();
			break;
		case 2:
			// "run away"
			choiceAttemptToEscape();
			break;
		case 3:
			// "bag"
			choiceBagMenu();
			break;
		case 4:
			// "pokemon"
			choicePokemonMenu();
			break;
		default:
			System.out.println("Input not recognized. Please choose one of the given options.\n");
		}
	}

	/**
	 * Helper for runBattleMenu()
	 * Prints the player's move list, then asks the player to choose a move, then attacks
	 * If the player chooses an invalid move, the menu will prompt for a new choice
	 */
	private void choiceMovesMenu() {
		int choice;
		boolean loopAgain;
		do {
			// handle menu
			playerPokemon.printAllMoves();
			io.printEscCharReminder();
			choice = io.promptInt();
			io.printLineBreak();

			// handle escape request
			if( choice == -1 ){
				runBattleMenu();
				return;
			}
			// handle bad inputs w/ loop
			loopAgain = playerPokemon.getMove(choice) == null ? true : false;
			if(loopAgain) {
				System.out.println(playerPokemon.getNickname() + " didn't understand you. "
						+ "\nPlease choose a valid move.\n");
			}
		}while(loopAgain);

		// execute the attack
		playerPokemon.attack(enemyPokemon, choice);		
	}

	/**
	 * Helper for runBattleMenu()
	 * Randomly decides if escape is successful, prints message.
	 * If the escape is successful, the battle will end.
	 */
	private void choiceAttemptToEscape() {
		Random generator = new Random();
		if(generator.nextInt(2) == 0) {
			//fails to escape
			System.out.println("You failed to get away.\n");
		}else {
			//succeeds in escaping
			System.out.println("You got away safely.\n");
			continueBattle = false;
		}
	}

	/**
	 * Helper for runBattleMenu()
	 */
	private void choiceBagMenu() {
		if( playerTrainer.getBag().isEmpty() ) {
			System.out.println("Your bag is empty.\n");
			runBattleMenu();
			return;
		}

		int choice;
		boolean loopAgain;
		do {
			// handle menu
			System.out.println( playerTrainer.getBag().getAllItemsSummary() );
			io.printEscCharReminder();
			choice = io.promptInt();
			io.printLineBreak();

			// handle escape request
			if( choice == -1 ){
				runBattleMenu();
				return;
			}

			// handle loop/repeats
			loopAgain = (playerTrainer.getBag().getItem(choice) == null);// loops again if bag fails to spend item
			if(loopAgain) {
				System.out.println("You don't have that item.\n");
			}
		}while(loopAgain);

		// use the item
		Item item = playerTrainer.getBag().spendItem(choice);
		useItem(item);
		//System.out.println("Your bag is empty.\n");
	}

	/**
	 * 
	 */
	private void choicePokemonMenu() {
		int choice;
		boolean loopAgain = false;
		do {
			// handle menu
			System.out.println(playerTrainer.getAllPokemonStr());
			io.printEscCharReminder();
			choice = io.promptInt();
			io.printLineBreak();

			// handle escape request
			if( choice == -1 ){
				runBattleMenu();
				return;
			}
			// catch bad inputs
			if(playerTrainer.getPokemon(choice) == playerPokemon) {// bad input: can't send out pokemon who is already out
				loopAgain = true;
				System.out.println(playerPokemon.getNickname() + " is already on the field!\n");
			}
			else if(playerTrainer.getPokemon(choice).isFainted()) {// bad input: pokemon at index "choice" is fainted
				loopAgain = true;
				System.out.println(playerTrainer.getPokemon(choice).getNickname() + " is resting. Give them a moment.\n");
			}
			else if(playerTrainer.getPokemon(choice) == null) {// bad input: pokemon at index "choice" doesn't exist
				loopAgain = true;
				System.out.println("You don't have that pokemon.\n");
			}
			else {
				loopAgain = false;
			}
		}while(loopAgain);

		// make the swap
		playerTrainer.swapPokemonToFront(choice);
		playerPokemon = playerTrainer.getPokemon(0);
		System.out.println("You sent out " + playerPokemon.getNickname() + ".\n");
		io.printDivider();
	}

	/**
	 * checks what kind of item is being used, uses appropriate method call
	 * may ask player for input
	 * 
	 * @param item
	 */
	private void useItem(Item item) {
		if(item instanceof Pokeball) {
			Pokeball ball = (Pokeball)(item);
			boolean catchSuccess = ball.catchAttemptPrint(playerTrainer, enemyPokemon);
			if(catchSuccess) {
				continueBattle = false;
				renameCaughtPokemon(enemyPokemon);
			}
		}
		else if(item instanceof Potion) {
			Potion pot = (Potion)(item);
			// get choice: which pokemon should be healed?
			int choice;
			boolean loopAgain;
			do {
				// handle menu
				System.out.println("Which pokemon should be healed?\n");
				System.out.println(playerTrainer.getAllPokemonStr());
				io.printEscCharReminder();
				choice = io.promptInt();
				io.printLineBreak();

				// handle escape request
				if( choice == -1 ){
					runBattleMenu();
					return;
				}
				// handle bad inputs w/ loop
				loopAgain = (playerTrainer.getPokemon(choice) == null);
				if(loopAgain) {
					System.out.println("You don't have that pokemon.");
				}
			}while(loopAgain);

			// heal the pokemon
			Pokemon pokeChoice = playerTrainer.getPokemon(choice);
			pot.healPokemon(pokeChoice);
			System.out.println("You healed " + pokeChoice.getNickname() + " by " + pot.getHealAmt() + ".\n");
		}
		else {
			System.out.println("unrecognized item");
		}
	}

	/**
	 * helper method for useItem()
	 */
	private void renameCaughtPokemon(Pokemon caughtPoke) {
		char choice;
		boolean loopAgain = false;
		do {
			// prompt input
			io.printDivider();
			System.out.println("Give the caught pokemon a nickname? (y/n) \n");
			choice = io.promptChar();
			io.printLineBreak();

			// handle each outcome
			if( choice == 'y' ) {
				System.out.println("Please type the new nickname for " + caughtPoke.getName() + ".\n");
				String newNickname = io.promptInput();
				caughtPoke.setNickname(newNickname);
				System.out.println("\n" + caughtPoke.getName() + " was renamed to " + caughtPoke.getNickname() + "!\n");
				io.printDivider();
				loopAgain = false;
			}else if( choice == 'n' ){
				loopAgain = false;
			}else {
				loopAgain = true;
				System.out.println("\"" + choice + "\" wasn't recognized. Please input \"y\" or \"n\".\n");
			}
		}while(loopAgain);
	}
	
	/**
	 * Helper for run().
	 * Makes the opposing pokemon attack the player's pokemon.
	 * Chooses a random attack from the enemy's move pool.
	 */
	private void runEnemyTurn() {
		Random generator = new Random();
		int enemyAttackChoice = generator.nextInt(enemyPokemon.getNumMoves());
		enemyPokemon.attack(playerPokemon, enemyAttackChoice);
	}

	/**
	 * Helper for run().
	 * Prints a victory message, 
	 * NEW FEATURE? updates important values (e.g. experience, level, stats)
	 */
	private void runPlayerWinsEnding() {
		// print message
		io.printDivider();
		String message = enemyPokemon.getName() + " fainted! " + playerPokemon.getNickname() + " won the battle!\n";
		System.out.println(message);
	}

	
	private void runPlayerLosesEnding() {
		io.printDivider();
		String message = playerPokemon.getNickname() + " fainted. " + playerPokemon.getNickname() + " lost the battle.\n";
		System.out.println(message);
		io.printDivider();
		if(! playerTrainer.canFight()) {
			System.out.println("You blacked out.\n");
			io.printDivider();
		}		
	}





	
}
