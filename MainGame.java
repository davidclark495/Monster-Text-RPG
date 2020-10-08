package game;

import java.util.Random;

import io.StandardIO;
import items.Pokeball;
import items.Potion;
import location.WorldMap;
import pokemon.Attack;
import pokemon.Dex;
import pokemon.PkType;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class MainGame {

	private WorldMap worldmap;
	private Player player;
	private Trainer playerTrainer;
	private StandardIO io;

	public MainGame() {
		worldmap = new WorldMap();
		setUpPlayer();
		io = new StandardIO();
	}

	/**
	 * 
	 */
	public void run() {
		gameLoopMenu();
	}

	/**
	 * Helper for run().
	 * Represents the Player's turn.
	 * Prints the battles status, then asks the player to make a choice, then handles that choice.
	 */
	private void gameLoopMenu() {
		boolean runLoop = true;
		while(runLoop) {
			// print options
			String options = "What would you like to do?\n"
					+ "1 - spend time in current location\n"
					+ "2 - travel\n"
					+ "3 - check status\n"
					+ "4 - quit game\n";
			System.out.println(options);

			// respond to user choice
			int choice = io.promptInt();
			io.printLineBreak();
			io.printDivider();
			switch(choice) {		
			case 1:
				// "current location activity" 
				player.getLocation().runActivity();
				break;
			case 2:
				// "travel"
				player.getLocation().runTravelActivity();
				break;
			case 3:
				// "status"
				statusMenu();
				break;
			case 4:
				// "quit"
				runLoop = false;
				break;
			default:
				System.out.println("Input not recognized. Please choose one of the given options.\n");
			}
			io.printDivider();
		} 
	}

	/**
	 * Helper for gameLoopMenu
	 * displays player info, team info
	 */
	private void statusMenu() {
		
		
		
		
		// print options
		String options = "What would you like to see?\n"
				+ "1 - current location\n"
				+ "2 - bag info\n"
				+ "3 - team info\n";
		System.out.println(options);
		io.printEscCharReminder();

		// respond to user choice
		int choice = io.promptInt();
		io.printLineBreak();
		io.printDivider();
		switch(choice) {		
		case 1:
			// print player location
			System.out.println( player.getLocation().toString() + "\n");
			break;
		case 2:
			// print player bag info
			System.out.println( player.getTrainer().getBag().getAllItemsSummary() );
			break;
		case 3:
			// print player team info
			System.out.println( player.getTrainer().getAllPokemonStr() );
			break;
		case -1:
			// "escape character"
			break;
		default:
			System.out.println("Input not recognized. Please choose one of the given options.\n");
		}
	}

	/**
	 * run on startup, sets up the trainer
	 */
	private void setUpPlayer() {
		// set up the player 
		player = new Player();
		player.setLocation(worldmap.getStart());
		// set up the player's team
		playerTrainer = player.getTrainer();
		//playerTrainer.addPokemon(new Pokemon(Dex.charmander));
		playerTrainer.addPokemon(new Pokemon(Dex.whooper));

		playerTrainer.getBag().addItem(Pokeball.POKEBALL, 99);
		playerTrainer.getBag().addItem(Pokeball.GREATBALL, 5);
		playerTrainer.getBag().addItem(Potion.HYPER_POTION, 99);
	}
}
