package game;

import java.util.Random;
import audio.SoundPlayer;
import io.StandardIO;
import items.Item;
import items.Pokeball;
import items.Potion;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class PokeBattle {

	private Player player;
	private Trainer playerTrainer, enemyTrainer;
	private Pokemon playerPokemon, enemyPokemon;
	private boolean continueBattle;

	/**
	 * The player (trainer) vs. an enemy (trainer)
	 * 
	 * @param t1
	 * @param t2
	 */
	public PokeBattle(Player player, Trainer t2) {
		this(player, t2.getPokemon(0));
		this.enemyTrainer = t2;
	}

	/**
	 * The player (trainer) vs. a wild pokemon
	 * PlayerTrainer must have a pokemon at index 0
	 * 
	 * @param playerTrainer
	 * @param enemyPokemon
	 */
	public PokeBattle(Player player, Pokemon enemyPokemon) {
		this.player = player;
		this.playerTrainer = player.getTrainer();
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
			StandardIO.println("You need a healthy Pokemon to do that.\n");
			return;
		}

		runStart();

		// main game loop, continue until otherwise stated
		continueBattle = true;
		while( continueBattle ) {
			// print menus, communicates with player
			runBattleMenu(); if(!continueBattle) break;// if player flees, exit the loop
			runEnemyTurn();
			StandardIO.delayModerate();

			// check for end of battle
			if( playerPokemon.isFainted() ) {
				// print details
				runPlayerPokeFaints();

				// choose new poke if possible
				if(playerTrainer.canFight()) {
					replaceFaintedPlayerPoke();
				}

				// otherwise, blackout and end battle
				if(! playerTrainer.canFight()) {
					StandardIO.println("You blacked out.\n");
					if(player.getLastPokeCenter() != null) {
						StandardIO.printDivider();
						player.setLocation(player.getLastPokeCenter());
						StandardIO.println("You wake up in a PokeCenter.\n");
					}
					continueBattle = false;
				}	
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
		StandardIO.println("\nA wild " + enemyPokemon.getName() + " appeared!");
		StandardIO.printLineBreak();
		StandardIO.delayModerate();
	}

	/**
	 * Helper for run().
	 * Represents the Player's turn.
	 * Prints the battles status, then asks the player to make a choice, then handles that choice.
	 */
	private void runBattleMenu() {
		boolean loopAgain = false;
		do {
			// print battle info
			StandardIO.printDivider();
			// e.g.
			// PlayerPoke: 20 HP	(Lvl 5)
			// EnemyPoke: ********	(Lvl 4)
			String status = String.format("%s: %d HP\t\t(Lvl %d)\n"
					+ "%s: %s \t(Lvl %d)\n", 
					playerPokemon.getNickname(), playerPokemon.getHp(), playerPokemon.getLevel(),
					enemyPokemon.getName(), enemyPokemon.getHealthBar(), enemyPokemon.getLevel());
			StandardIO.println(status);

			// print options
			String options = "What would you like to do?\n"
					+ "1 - attack\n"
					+ "2 - run away\n"
					+ "3 - bag\n"
					+ "4 - pokemon\n";
			StandardIO.println(options);

			// respond to user choice
			int choice = StandardIO.promptInt();
			StandardIO.printLineBreak();
			switch(choice) {		
			case 1:
				// "attack" 
				choiceMovesMenu();
				loopAgain = false;
				break;
			case 2:
				// "run away"
				choiceAttemptToEscape();
				loopAgain = false;
				break;
			case 3:
				// "bag"
				choiceBagMenu();
				loopAgain = false;
				break;
			case 4:
				// "pokemon"
				choicePokemonMenu();
				loopAgain = false;
				break;
			default:
				StandardIO.printEscCharReminder();
				loopAgain = true;
			}
		} while(loopAgain);
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
			StandardIO.printDivider();
			StandardIO.println(playerPokemon.getAllMovesString());// displays a list, 1-4
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt() -1;// account for the conversion from display to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -2 ){// account for -1 offset, EscChar is -2
				runBattleMenu();
				return;
			}
			// handle bad inputs w/ loop
			loopAgain = playerPokemon.getMove(choice) == null;
			if(loopAgain) {
				StandardIO.println(playerPokemon.getNickname() + " didn't understand you. "
						+ "\nPlease choose a valid move.\n");
			}
		}while(loopAgain);

		// execute the attack
		String atkSummary = playerPokemon.attack(enemyPokemon, choice);	
		StandardIO.println(atkSummary);
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
			StandardIO.println("You failed to get away.\n");
		}else {
			//succeeds in escaping
			StandardIO.println("You got away safely.\n");
			continueBattle = false;
		}
	}

	/**
	 * Helper for runBattleMenu()
	 */
	private void choiceBagMenu() {
		if( playerTrainer.getBag().isEmpty() ) {
			StandardIO.printDivider();
			StandardIO.println("Your bag is empty.\n");
			runBattleMenu();
			return;
		}

		int choice;
		boolean loopAgain;
		do {
			// handle menu
			StandardIO.printDivider();
			StandardIO.println( playerTrainer.getBag().getAllItemsSummary() );// displays a list, 1-4
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt() -1;// account for the conversion from display to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -2 ){// account for -1 offset, EscChar is -2
				runBattleMenu();
				return;
			}

			// handle loop/repeats
			loopAgain = (playerTrainer.getBag().getItem(choice) == null);// loops again if bag fails to spend item
			if(loopAgain) {
				StandardIO.println("You don't have that item.\n");
			}
		}while(loopAgain);

		// use the item
		Item item = playerTrainer.getBag().spendItem(choice);
		useItem(item);
		//StandardIO.println("Your bag is empty.\n");
	}

	/**
	 * 
	 */
	private void choicePokemonMenu() {
		int choice;
		boolean loopAgain = false;
		do {
			// handle menu
			StandardIO.printDivider();
			StandardIO.println(playerTrainer.getAllPokemonStr());// print menu, 1-6
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt() -1;// convert from display values to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -2 ){// account for -1 offset, EscChar is -2
				runBattleMenu();
				return;
			}
			// catch bad inputs
			if(playerTrainer.getPokemon(choice) == null) {// bad input: pokemon at index "choice" doesn't exist
				loopAgain = true;
				StandardIO.println("You don't have that pokemon.\n");
			}
			else if(playerTrainer.getPokemon(choice) == playerPokemon) {// bad input: can't send out pokemon who is already out
				loopAgain = true;
				StandardIO.println(playerPokemon.getNickname() + " is already on the field!\n");
			}
			else if(playerTrainer.getPokemon(choice).isFainted()) {// bad input: pokemon at index "choice" is fainted
				loopAgain = true;
				StandardIO.println(playerTrainer.getPokemon(choice).getNickname() + " is resting. Give them a moment.\n");
			} 
			else {
				loopAgain = false;
			}
		}while(loopAgain);

		// make the swap
		playerTrainer.swapPokemonToFront(choice);
		playerPokemon = playerTrainer.getPokemon(0);
		StandardIO.println("You sent out " + playerPokemon.getNickname() + ".\n");
		StandardIO.printDivider();
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
			boolean catchSuccess = ball.catchAttemptPrint(player, enemyPokemon);
			if(catchSuccess) {
				continueBattle = false;
				renameCaughtPokemon(enemyPokemon);
				winningExpProcessor();
			}
		}
		else if(item instanceof Potion) {
			Potion pot = (Potion)(item);
			// get choice: which pokemon should be healed?
			int choice;
			boolean loopAgain;
			do {
				// handle menu
				StandardIO.println("Which pokemon should be healed?\n");
				StandardIO.println(playerTrainer.getAllPokemonStr());// print menu, 1-6
				StandardIO.printEscCharReminder();
				choice = StandardIO.promptInt() -1;// convert from display values to indexes
				StandardIO.printLineBreak();

				// handle escape request
				if( choice == -2 ){// account for -1 offset, EscChar is -2
					runBattleMenu();
					return;
				}
				// handle bad inputs w/ loop
				loopAgain = (playerTrainer.getPokemon(choice) == null);
				if(loopAgain) {
					StandardIO.println("You don't have that pokemon.");
				}
			}while(loopAgain);

			// heal the pokemon
			Pokemon pokeChoice = playerTrainer.getPokemon(choice);
			pot.healPokemon(pokeChoice);
			StandardIO.println("You healed " + pokeChoice.getNickname() + " by " + pot.getHealAmt() + ".\n");
		}
		else {
			StandardIO.println("unrecognized item");
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
			StandardIO.printDivider();
			StandardIO.println("Give the caught pokemon a nickname? (y/n) \n");
			choice = StandardIO.promptChar();
			StandardIO.printLineBreak();

			// handle each outcome
			if( choice == 'y' ) {
				StandardIO.println("Please type the new nickname for " + caughtPoke.getName() + ".\n");
				String newNickname = StandardIO.promptInput();
				caughtPoke.setNickname(newNickname);
				StandardIO.println("\n" + caughtPoke.getName() + " was renamed to " + caughtPoke.getNickname() + "!\n");
				StandardIO.printDivider();
				loopAgain = false;
			}else if( choice == 'n' ){
				loopAgain = false;
			}else {
				loopAgain = true;
				StandardIO.println("\"" + choice + "\" wasn't recognized. Please input \"y\" or \"n\".\n");
			}
		}while(loopAgain);
	}

	/**
	 * Helper for run().
	 * Makes the opposing pokemon attack the player's pokemon.
	 * Chooses a random attack from the enemy's move pool.
	 */
	private void runEnemyTurn() {
		Random rng = new Random();
		int enemyAttackChoice = rng.nextInt(enemyPokemon.getNumMoves());
		String atkSummary = enemyPokemon.attack(playerPokemon, enemyAttackChoice);
		StandardIO.println(atkSummary);
	}

	/**
	 * Helper for run().
	 * Prints a victory message, 
	 * updates important values (e.g. experience, level, stats)
	 */
	private void runPlayerWinsEnding() {
		// print victory message
		StandardIO.printDivider();
		StandardIO.println(enemyPokemon.getName() + " fainted! " + playerPokemon.getNickname() + " won the battle!\n");

		// handle exp, level ups
		winningExpProcessor();
	}

	/**
	 * Helper for runPlayerWinsEnding()
	 * updates the player pokemon's exp / level / stats
	 * and prints a relevant summary
	 */
	private void winningExpProcessor() {
		StandardIO.println(playerPokemon.getNickname() + " gained " + enemyPokemon.getExpDropped() + " experience!\n");

		int prevLevel = playerPokemon.getLevel();
		int prevExp = playerPokemon.getCurrentExp();
		String levelMessage = "Level:\t" + prevLevel;
		String expMessage = "Exp:\t" + prevExp + "/" + playerPokemon.getExpNextLevel();
		String hpMessage = "HP:\t" + playerPokemon.getHp() + "/" + playerPokemon.getMaxHp();
		String atkMessage = "ATK:\t" + playerPokemon.getATK();
		String defMessage = "DEF:\t" + playerPokemon.getDEF();
		
		playerPokemon.gainExp(enemyPokemon.getExpDropped());

		int newLevel = playerPokemon.getLevel();
		int newExp = playerPokemon.getCurrentExp();

		if(newLevel > prevLevel) {// if a level up occurred, give a more detailed message w/ stats
			hpMessage += " --> " + playerPokemon.getHp() + "/" + playerPokemon.getMaxHp() + "\n";
			atkMessage += " --> " + playerPokemon.getATK() + "\n";
			defMessage += " --> " + playerPokemon.getDEF() + "\n";
		}else {
			hpMessage = "";
			atkMessage = "";
			defMessage = "";
		}

		levelMessage += " --> " + newLevel + "\n";
		expMessage += " --> " + newExp + "/" + playerPokemon.getExpNextLevel() + "\n";

		StandardIO.println(levelMessage + expMessage + hpMessage + atkMessage + defMessage);

		// play audio if level up occurred
		if(newLevel > prevLevel)
			SoundPlayer.playSound("sounds/game_sounds/level_up.wav");

		StandardIO.printDivider();
	}

	/**
	 * Helper for run()
	 * prints details
	 */
	private void runPlayerPokeFaints() {
		StandardIO.printDivider();
		StandardIO.println(playerPokemon.getNickname() + " fainted. \n");
	}

	/**
	 * Helper for run()
	 * swaps a new pokemon to the front of the players team
	 * very similar to choicePokemonMenu()
	 */
	private void replaceFaintedPlayerPoke() {
		int choice;
		boolean loopAgain = false;
		do {
			//print context
			StandardIO.printDivider();
			StandardIO.println("Choose a pokemon to send out.\n");

			// handle menu
			StandardIO.println(playerTrainer.getAllPokemonStr());// print a menu, 1-6
			choice = StandardIO.promptInt() -1;// convert from display values to indexes
			StandardIO.printLineBreak();

			// catch bad inputs
			if(playerTrainer.getPokemon(choice) == null) {// bad input: pokemon at index "choice" doesn't exist
				loopAgain = true;
				StandardIO.println("You don't have that pokemon.\n");
			}
			else if(playerTrainer.getPokemon(choice).isFainted()) {// bad input: pokemon at index "choice" is fainted
				loopAgain = true;
				StandardIO.println(playerTrainer.getPokemon(choice).getNickname() + " is resting. Give them a moment.\n");
			}
			else {
				loopAgain = false;
			}
		}while(loopAgain);

		// make the swap
		playerTrainer.swapPokemonToFront(choice);
		playerPokemon = playerTrainer.getPokemon(0);
		StandardIO.println("You sent out " + playerPokemon.getNickname() + ".\n");
	}




}
