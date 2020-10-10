package game;

import audio.SoundPlayer;
import io.StandardIO;
import items.Pokeball;
import items.Potion;
import location.WorldMap;
import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class MainGame {

	private WorldMap worldmap;
	private Player player;
	private Trainer playerTrainer;

	public MainGame() {
		worldmap = new WorldMap();
		setUpPlayer();
	}

	/**
	 * 
	 */
	public void run() {
		gameLoopMenu();
	}

	/**
	 * Helper for run().
	 * Prints options, then asks the player to make a choice, then handles that choice.
	 */
	private void gameLoopMenu() {
		boolean runLoop = true;
		while(runLoop) {
			// print options
			StandardIO.printDivider();
			String options = "What would you like to do?\n"
					+ "1 - explore " + player.getLocation().getName() + "\n"
					+ "2 - travel\n"
					+ "3 - check status\n"
					+ "4 - quit game\n"
					+ "10 - options\n";
			StandardIO.println(options);

			// respond to user choice
			int choice = StandardIO.promptInt();
			StandardIO.printLineBreak();
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
			case 10:
				// "options"
				optionsMenu();
				break;
			default:
				StandardIO.println("Input not recognized. Please choose one of the given options.\n");
			}
		} 
	}

	/**
	 * Helper for gameLoopMenu
	 * displays player info, team info
	 */
	private void statusMenu() {
		
		
		
		
		// print options
		StandardIO.printDivider();
		String options = "What would you like to see?\n"
				+ "1 - current location\n"
				+ "2 - bag info\n"
				+ "3 - team info\n";
		StandardIO.println(options);
		StandardIO.printEscCharReminder();

		// respond to user choice
		int choice = StandardIO.promptInt();
		StandardIO.printLineBreak();
		StandardIO.printDivider();
		switch(choice) {		
		case 1:
			// print player location
			StandardIO.println( player.getLocation().toString() + "\n");
			break;
		case 2:
			// print player bag info
			StandardIO.println( player.getTrainer().getBag().getAllItemsSummary() );
			break;
		case 3:
			// print player team info
			StandardIO.println( player.getTrainer().getAllPokemonStr() );
			break;
		case -1:
			// "escape character"
			break;
		default:
			StandardIO.println("Input not recognized. Please choose one of the given options.\n");
		}
	}
	
	/**
	 * Helper for gameLoopMenu
	 * displays options, handles changes
	 */
	private void optionsMenu() {
		// print options
		StandardIO.printDivider();
		String options = "What would you like to change?\n"
				+ "1 - text-crawl speed\n"
				+ "2 - audio\n";
		StandardIO.println(options);
		StandardIO.printEscCharReminder();

		// respond to user choice
		int choice = StandardIO.promptInt();
		StandardIO.printLineBreak();
		StandardIO.printDivider();
		switch(choice) {		
		case 1:
			// print current crawl speed
			StandardIO.println("Current crawl speed: " + StandardIO.getCrawlSpeed());
			StandardIO.print("New crawl speed: ");
			StandardIO.setCrawlSpeed( StandardIO.getInt() );
			StandardIO.printLineBreak();
			break;
		case 2:
			// print current crawl speed
			StandardIO.println("Audio is currently" + (!SoundPlayer.isAllowedSound() ? " not" : "") + " allowed.");
			StandardIO.println("Allow audio? (y/n)\n");
			char response = StandardIO.promptChar();
			if(response == 'y') {
				SoundPlayer.setAllowSound(true);
				SoundPlayer.playSound("sounds/game_sounds/toggle.wav");
			}else if(response == 'n') {
				SoundPlayer.setAllowSound(false);
			}else {
				StandardIO.printInputNotRecognized();
			}
			StandardIO.printLineBreak();
			break;
		case -1:
			// "escape character"
			break;
		default:
			StandardIO.printInputNotRecognized();
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
		playerTrainer.addPokemon(new Pokemon(Dex.charmander));
		playerTrainer.addPokemon(new Pokemon(Dex.wooper));

		playerTrainer.getBag().addItem(Pokeball.POKEBALL, 99);
		playerTrainer.getBag().addItem(Pokeball.GREATBALL, 5);
		playerTrainer.getBag().addItem(Potion.HYPER_POTION, 2);
		
	}
}
