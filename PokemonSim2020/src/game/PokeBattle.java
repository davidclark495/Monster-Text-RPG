package game;

import java.util.Random;
import java.util.Set;

import audio.SoundPlayer;
import io.StandardIO;
import io.StandardMenu;
import items.Item;
import items.ItemUtil;
import items.Pokeball;
import items.Potion;
import pokemon.Move;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.PokemonUtil;
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
		StandardIO.println("A wild " + enemyPokemon.getName() + " appeared!");
		StandardIO.printLineBreak();
		StandardIO.delayModerate();
	}

	/**
	 * Helper for run().
	 * Represents the Player's turn.
	 * Prints the battles status, then asks the player to make a choice, then handles that choice.
	 * e.g.
	 * PlayerPoke: 20 HP	(Lvl 5)
	 * EnemyPoke: ********	(Lvl 4)
	 */
	private void runBattleMenu() {
		// print battle info
		StandardIO.printDivider();
		String status = String.format("%12s: %-3d HP\t\t(Lvl %2d)\n"
				+ "%12s: %10s \t(Lvl %2d)\n", 
				playerPokemon.getNickname(), playerPokemon.getHP(), playerPokemon.getLevel(),
				enemyPokemon.getName(), PokemonUtil.getHealthBar(enemyPokemon), enemyPokemon.getLevel());
		StandardIO.println(status);

		// define options
		String prompt = "What would you like to do?";
		String[] options = {
				"attack",
				"run away",
				"bag",
				"pokemon"
		};

		// respond to user choice
		int choice = StandardMenu.promptSelection(prompt, options);
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
		}
	}

	/**
	 * Helper for runBattleMenu()
	 * Prints the player's move list, then asks the player to choose a move, then attacks
	 * If the player chooses an invalid move, the menu will prompt for a new choice
	 */
	private void choiceMovesMenu() {
		// handle menu
		StandardIO.printDivider();
		String prompt = String.format("Which move should %s use?", playerPokemon.getNickname());
		Move[] moves = playerPokemon.getAllMoves();
		String[] options = new String[playerPokemon.getNumMoves()];
		for(int i = 0; i < options.length; i++) {
			options[i] = moves[i].toString();
		}

		int choice = StandardMenu.promptIndexEscapable(prompt, options);
		if(choice == -1) {
			runBattleMenu();
			return;
		}

		// execute the attack
		String atkSummary = PokemonUtil.attack(playerPokemon, choice, enemyPokemon);	
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
			StandardIO.printDivider();
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

		// handle menu
		StandardIO.printDivider();
		String prompt = "Which item will you use?";
		Item[] items = playerTrainer.getBag().getItemSet().toArray(new Item[0]);
		String[] options = new String[items.length];
		for(int i = 0; i < options.length; i++) {
			options[i] = items[i].toString();// + playerTrainer.getBag().getItemQuantity(items[i]);
		}
		int choice = StandardMenu.promptIndexEscapable(prompt, options);
		if(choice == -1) {
			runBattleMenu();
			return;
		}

		// use the item
		Item item = playerTrainer.getBag().spendItem(choice);
		useItem(item);
	}

	/**
	 * 
	 */
	private void choicePokemonMenu() {
		int choice;
		boolean loopAgain = false;
		do {
			// build menu
			StandardIO.printDivider();
			String prompt = "Which pokemon will you choose?";
			Pokemon[] pokes = player.getTrainer().getAllPokemon();
			String[] options = new String[player.getTrainer().getNumPokemon()];
			for(int i = 0; i < options.length; i++)
				options[i] = PokemonUtil.getInlineSummary(pokes[i]);

			choice = StandardMenu.promptIndexEscapable(prompt, options);
			if(choice == -1) {
				runBattleMenu();
				return;
			}

			// catch invalid options
			if(playerTrainer.getPokemon(choice) == playerPokemon) {// bad input: can't send out pokemon who is already out
				loopAgain = true;
				StandardIO.println(String.format("%s is already on the field!\n", playerPokemon.getNickname()));
			}
			else if(playerTrainer.getPokemon(choice).isFainted()) {// bad input: pokemon at index "choice" is fainted
				loopAgain = true;
				StandardIO.println(String.format("%s is resting. Give them a moment.\n", playerTrainer.getPokemon(choice).getNickname()));
			} 
		} while (loopAgain);

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
		StandardIO.printDivider();
		if(item instanceof Pokeball) {
			Pokeball ball = (Pokeball)(item);
			boolean catchSuccess = ItemUtil.catchAttemptPrint(player, enemyPokemon, ball);
			if(catchSuccess) {
				continueBattle = false;
				renameCaughtPokemon(enemyPokemon);
				StandardIO.printDivider();
				PokeBattleUtil.winningExpProcessor(playerPokemon, enemyPokemon);
			}else {
				StandardIO.printDivider();
			}
		} 
		else if(item instanceof Potion) {
			Potion pot = (Potion)(item);
			// get choice: which pokemon should be healed?
			Pokemon pokeChoice;
			boolean loopAgain = false;
			do {
				// build menu
				StandardIO.printDivider();
				String prompt = "Choose a pokemon to send out.";
				Pokemon[] pokes = player.getTrainer().getAllPokemon();
				String[] options = new String[player.getTrainer().getNumPokemon()];
				for(int i = 0; i < options.length; i++)
					options[i] = PokemonUtil.getInlineSummary(pokes[i]);

				int choice = StandardMenu.promptIndex(prompt, options);

				// catch invalid options
				pokeChoice = playerTrainer.getPokemon(choice);
				if(pokeChoice.getHP() == pokeChoice.getMaxHP()) {// bad input: pokemon at index "choice" has full health
					loopAgain = true;
					StandardIO.println(String.format("%s doesn't need healing.\n", pokeChoice.getNickname()));
				} 
			} while (loopAgain);

			// heal the pokemon
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
		boolean giveNickname = StandardMenu.promptYesOrNo("Give the caught pokemon a nickname?");
		if(giveNickname) {
			StandardIO.println("Please type the new nickname for " + caughtPoke.getName() + ".\n");
			String newNickname = StandardIO.promptInput();
			caughtPoke.setNickname(newNickname);
			StandardIO.println("\n" + caughtPoke.getName() + " was renamed to " + caughtPoke.getNickname() + "!\n");
		} 
	}

	/**
	 * Helper for run().
	 * Makes the opposing pokemon attack the player's pokemon.
	 * Chooses a random attack from the enemy's move pool.
	 */
	private void runEnemyTurn() {
		Random rng = new Random();
		int enemyAttackChoice = rng.nextInt(enemyPokemon.getNumMoves());

		String atkSummary = PokemonUtil.attack(enemyPokemon, enemyAttackChoice, playerPokemon);

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
		StandardIO.printDivider();
		PokeBattleUtil.winningExpProcessor(playerPokemon, enemyPokemon);
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
			// build menu
			StandardIO.printDivider();
			String prompt = "Choose a pokemon to send out.";
			Pokemon[] pokes = player.getTrainer().getAllPokemon();
			String[] options = new String[player.getTrainer().getNumPokemon()];
			for(int i = 0; i < options.length; i++)
				options[i] = PokemonUtil.getInlineSummary(pokes[i]);

			choice = StandardMenu.promptIndex(prompt, options);

			// catch invalid options
			if(playerTrainer.getPokemon(choice).isFainted()) {// bad input: pokemon at index "choice" is fainted
				loopAgain = true;
				StandardIO.println(String.format("%s is resting. Give them a moment.\n", playerTrainer.getPokemon(choice).getNickname()));
			} 
		} while (loopAgain);

		// make the swap
		playerTrainer.swapPokemonToFront(choice);
		playerPokemon = playerTrainer.getPokemon(0);
		StandardIO.println("You sent out " + playerPokemon.getNickname() + ".\n");
	}




}
