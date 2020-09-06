package game;

import java.util.Random;
import java.util.Scanner;

import items.Item;
import items.Pokeball;
import pokemon.Attack;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

public class BattleGameTrainers {

	private Trainer playerTrainer, enemyTrainer;
	private Pokemon playerPokemon, enemyPokemon;
	private boolean continueBattle;

	/**
	 * The player (trainer) vs. an enemy (trainer)
	 * 
	 * @param t1
	 * @param t2
	 */
	public BattleGameTrainers(Trainer t1, Trainer t2) {
		this.playerTrainer = t1;
		this.enemyTrainer = t2;

		this.playerPokemon = t1.getPokemon(0);
		this.enemyPokemon = t2.getPokemon(0);
	}

	/**
	 * The player (trainer) vs. a wild pokemon
	 * 
	 * @param playerTrainer
	 * @param enemyPokemon
	 */
	public BattleGameTrainers(Trainer playerTrainer, Pokemon enemyPokemon) {
		this.playerTrainer = playerTrainer;


		this.playerPokemon = playerTrainer.getPokemon(0);
		this.enemyPokemon = enemyPokemon;
	}


	public static void main(String[] args) {
		// set up the player's team
		Pokemon plyrPoke1 = new Pokemon("Charmander", PkType.FIRE, 80);
		plyrPoke1.teachMove(Attack.BASIC_ATTACK);
		plyrPoke1.teachMove(Attack.EMBER);

		Trainer plyrTrnr = new Trainer();
		plyrTrnr.addPokemon(plyrPoke1);

		plyrTrnr.getBag().addItem(Pokeball.POKEBALL, 3);

		// set up the enemy
		Pokemon enemyPoke = new Pokemon("Eevee", PkType.NORMAL, 60);
		enemyPoke.teachMove(Attack.BASIC_ATTACK);
		enemyPoke.teachMove(Attack.TACKLE);

		// run the game
		BattleGameTrainers game = new BattleGameTrainers(plyrTrnr, enemyPoke);
		game.run();
	}


	/**
	 * Plays through a battle.
	 * Prints initial statistics, then runs a battle-menu loop, then ends the game.
	 */
	public void run() {
		runStart();

		// main game loop, continue until otherwise stated
		continueBattle = true;
		while( continueBattle ) {
			// print menus, communicates with player
			runBattleMenu(); if(!continueBattle) break;// if player flees, exit the loop
			runEnemyTurn();
			delay();

			// check for end of battle
			if( playerPokemon.isFainted() ) {
				continueBattle = false;
				runPlayerLosesEnding();
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
	public void runStart() {
		System.out.println("A wild " + enemyPokemon.getName() + " jumped out of the tall grass!");
		printLineBreak();
		printDivider();
		delay();
	}

	/**
	 * Helper for run().
	 * Represents the Player's turn.
	 * Prints the battles status, then asks the player to make a choice, then handles that choice.
	 */
	public void runBattleMenu() {
		// print battle info
		printDivider();
		String status = playerPokemon.getName() + ": " + playerPokemon.getHp() + " HP\n"
				+ enemyPokemon.getName() + ": " + enemyPokemon.getHp() + " HP\n";
		System.out.println(status);

		// print options
		String options = "What would you like to do?\n"
				+ "1 - attack\n"
				+ "2 - run away\n"
				+ "3 - bag\n";
		System.out.println(options);

		// respond to user choice
		int choice = promptInt();
		printLineBreak();
		printDivider();
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
		default:
			System.out.println("Input not recognized. Please choose one of the given options.\n");
		}
	}

	/**
	 * Helper for runBattleMenu()
	 * Prints the player's move list, then asks the player to choose a move, then attacks
	 * If the player chooses an invalid move, the menu will prompt for a new choice
	 */
	public void choiceMovesMenu() {
		int choice;
		boolean loopAgain;
		do {
			// handle menu
			playerPokemon.getAllMoves();
			choice = promptInt();
			printLineBreak();
			
			// handle loop/repeats
			loopAgain = playerPokemon.getMove(choice) == null ? true : false;
			if(loopAgain) {
				System.out.println(playerPokemon.getName() + " didn't understand you. "
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
	public void choiceAttemptToEscape() {
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
	public void choiceBagMenu() {
		if( playerTrainer.getBag().isEmpty() ) {
			System.out.println("Your bag is empty.");
			runBattleMenu();
			return;
		}
		
		int choice;
		boolean loopAgain;
		do {
			// handle menu
			System.out.println( playerTrainer.getBag().getAllItemsSummary() );
			choice = promptInt();
			printLineBreak();
			
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
			}
		}
		else {
			System.out.println("unrecognized item");
		}
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
		printDivider();
		String message = enemyPokemon.getName() + " fainted! " + playerPokemon.getName() + " won the battle!";
		System.out.println(message);
	}

	private void runPlayerLosesEnding() {
		printDivider();
		String message = playerPokemon.getName() + " fainted. " + playerPokemon.getName() + " lost the battle.";
		System.out.println(message);
	}





	// get input //
	public String promptInput() {
		System.out.print("Your Input: ");
		Scanner reader = new Scanner(System.in);
		try{
			return reader.next();
		}catch(Exception e) {
			return "";
		}
	}
	public int promptInt() {
		System.out.print("Your Input: ");
		Scanner reader = new Scanner(System.in);
		try {
			return reader.nextInt();
		}catch(Exception e) {
			return -1;
		}
	}

	// standard output //
	public void printDivider() {
		System.out.println("--------------------\n");
	}
	public void printLineBreak() {
		System.out.println();
	}
	public void delay() {	
		//System.out.println("Press [enter] to continue.");
		//Scanner reader = new Scanner(System.in);
		//reader.nextLine();

		//try {
		//	Thread.sleep(1000);
		//}catch(Exception e) {
		//	e.printStackTrace();
		//}
	}
	public void printInputNotRecognized() {
		System.out.println("Input not recognized.\n");
	}
}
