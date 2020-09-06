package pokemon;

import java.util.Random;
import java.util.Scanner;

public class BattleGame {

	private Pokemon p1, p2;
	private boolean continueBattle;

	public BattleGame(Pokemon p1, Pokemon p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public static void main(String[] args) {
		Pokemon p1 = new Pokemon("Charmander", PkType.FIRE, 80);
		p1.teachMove(Attack.BASIC_ATTACK);
		p1.teachMove(Attack.EMBER);
		p1.teachMove(new Attack("Hyper Beam", 50, PkType.NORMAL));
		p1.teachMove(new Attack("Punch in the Face", 100, PkType.NORMAL));
		p1.teachMove(new Attack("Flamethrower", 40, PkType.FIRE));
		Pokemon p2 = new Pokemon("Eevee", PkType.NORMAL, 60);
		p2.teachMove(Attack.BASIC_ATTACK);
		p2.teachMove(Attack.TACKLE);

		BattleGame game = new BattleGame(p1, p2);
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
			if( p1.isFainted() ) {
				continueBattle = false;
				runPlayerLosesEnding();
			}
			else if( p2.isFainted() ) {
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
		System.out.println("A wild " + p2.getName() + " jumped out of the tall grass!");
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
		String status = p1.getName() + ": " + p1.getHp() + " HP\n"
				+ p2.getName() + ": " + p2.getHp() + " HP\n";
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
	 * If the player chooses an invalid move, their pokemon will execute a basic attack
	 */
	public void choiceMovesMenu() {
		p1.getAllMoves();
		int choice = promptInt();
		printLineBreak();
		try {
			p1.attack(p2, choice);
		}catch(Exception e) {
			System.out.println("That isn't a valid move.\n");
			p1.attackBasic(p2);
		}
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
		System.out.println("Your bag is empty.\n");
	}

	/**
	 * Helper for run().
	 * Makes the opposing pokemon attack the player's pokemon.
	 */
	public void runEnemyTurn() {
		p2.attackBasic(p1);
	}

	/**
	 * Helper for run().
	 * Prints a victory message, 
	 * NEW FEATURE? updates important values (e.g. experience, level, stats)
	 */
	public void runPlayerWinsEnding() {
		// print message
		printDivider();
		String message = p2.getName() + " fainted! " + p1.getName() + " won the battle!";
		System.out.println(message);
	}

	public void runPlayerLosesEnding() {
		printDivider();
		String message = p1.getName() + " fainted. " + p1.getName() + " lost the battle.";
		System.out.println(message);
	}

	
	


	// get input //
	public String promptInput() {
		System.out.print("Your Input: ");
		Scanner reader = new Scanner(System.in);
		return reader.next();
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
		try {
			Thread.sleep(1000);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
}
