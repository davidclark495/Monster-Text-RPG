package game;

import audio.SoundPlayer;
import io.SaveFileManager;
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
					+ "4 - save/load (in development)\n"
					+ "5 - options\n"
					+ "6 - quit game\n";
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
				// "save"
				saveOrLoadMenu();
				break;
			case 5:
				// "options"
				optionsMenu();
				break;
			case 6:
				// "quit"
				runLoop = false;
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
		switch(choice) {		
		case 1:
			// print player location
			StandardIO.printDivider();
			StandardIO.println( player.getLocation().toString() + "\n");
			StandardIO.delay(1000);
			break;
		case 2:
			// print player bag info
			StandardIO.printDivider();
			StandardIO.println( player.getTrainer().getBag().getAllItemsSummary() );
			StandardIO.delay(1000);
			break;
		case 3:
			// print player team info
			pokemonStatusMenu();
			break;
		case -1:
			// "escape character"
			break;
		default:
			StandardIO.printInputNotRecognized();
		}
	}

	/**
	 * Helper for statusMenu()
	 * Gives players options for interacting w/ or viewing their team
	 */
	private void pokemonStatusMenu() {
		// choose which pokemon to view (from trainer's team)
		int selectedPokeIndex = selectPokemonIndex("Which Pokemon would you like to inspect?");
		if(selectedPokeIndex == -1)
			return;
		Pokemon selectedPokemon = player.getTrainer().getPokemon(selectedPokeIndex);

		// ask what to do with selected pokemon 
		// print options
		StandardIO.printDivider();
		String options = "What would you like to do?\n"
				+ "1 - view stats\n"
				+ "2 - swap\n"
				+ "3 - heal\n";
		StandardIO.println(options);
		StandardIO.printEscCharReminder();

		// respond to user choice
		int actionChoice = StandardIO.promptInt();
		StandardIO.printLineBreak();
		switch(actionChoice) {		
		case 1:
			// view stats
			StandardIO.printDivider();
			StandardIO.println( selectedPokemon.getStatisticsStr() + selectedPokemon.getAllMovesString());
			StandardIO.delay(1000);
			break;
		case 2:
			// swap
			int swapPartnerIndex = selectPokemonIndex("Which Pokemon should " + selectedPokemon.getNickname() + " be swapped with?");
			if(swapPartnerIndex == -1)
				return;
			Pokemon swapPartner = player.getTrainer().getPokemon(swapPartnerIndex);
			player.getTrainer().swapPokemon(selectedPokeIndex, swapPartnerIndex);
			StandardIO.println(selectedPokemon.getNickname() + " traded places with " + swapPartner.getNickname() + ".\n" );
			break;
		case 3:
			// heal
			// heal(selectedPokemon);
			StandardIO.printDivider();
			StandardIO.println( "Not yet implemented.\n" );
			StandardIO.delay(1000);
			break;
		case -1:
			// "escape character"
			break;
		default:
			StandardIO.printInputNotRecognized();
		}
	}

	private int selectPokemonIndex(String question) {
		int pokeChoice;
		boolean loopAgain = false;
		do {
			// handle menu
			StandardIO.printDivider();
			StandardIO.println(question + "\n");
			StandardIO.println(player.getTrainer().getAllPokemonStr());// print menu, 1-6
			StandardIO.printEscCharReminder();
			pokeChoice = StandardIO.promptInt() -1;// convert from display values to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( pokeChoice == -2 ){// account for -1 offset, EscChar is -2
				return -1;
			}
			// catch bad inputs
			if(player.getTrainer().getPokemon(pokeChoice) == null) {// bad input: pokemon at index "choice" doesn't exist
				loopAgain = true;
				StandardIO.println("You don't have that pokemon.\n");
			}
			else {
				loopAgain = false;
			}
		}while(loopAgain);

		return pokeChoice;
	}

	/**
	 * Helper for gameLoopMenu
	 * 
	 */
	private void saveOrLoadMenu() {
		// print options
		StandardIO.printDivider();
		String options = "What would you like to do?\n"
				+ "1 - save\n"
				+ "2 - load\n";
		StandardIO.println(options);

		// respond to user choice
		int choice = StandardIO.promptInt();
		StandardIO.printLineBreak();
		switch(choice) {		
		case 1:
			// "save"
			StandardIO.printDivider();
			if(SaveFileManager.writeTeamToSave(player)) {
				StandardIO.println("Your team was successfully saved.\n");
			}else {
				StandardIO.println("Your team could not be saved.\n");
			}
			break;
		case 2:
			// "load"
			StandardIO.printDivider();
			Trainer loadedTrainer = SaveFileManager.loadTrainerFromSave();
			if(loadedTrainer != null)
				player.setTrainer(loadedTrainer);
			break;
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
				+ "1 - text-crawl delay\n"
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
			StandardIO.println("Current crawl delay: " + StandardIO.getCrawlDelay());
			StandardIO.print("New crawl delay: ");
			StandardIO.setCrawlDelay( StandardIO.getInt() );
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
		Trainer playerTrainer = player.getTrainer();
		playerTrainer = player.getTrainer();
		playerTrainer.addPokemon(new Pokemon(Dex.vulpix, 5));
		playerTrainer.addPokemon(new Pokemon(Dex.wooper));


		playerTrainer.getBag().addItem(Pokeball.POKEBALL, 99);
		playerTrainer.getBag().addItem(Pokeball.GREATBALL, 5);
		playerTrainer.getBag().addItem(Potion.HYPER_POTION, 2);

	}
}
