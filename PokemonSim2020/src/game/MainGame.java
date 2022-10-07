package game;

import audio.SoundPlayer;
import io.SaveFileManager;
import io.StandardIO;
import io.StandardMenu;
import items.Bag;
import items.Pokeball;
import items.Potion;
import location.Location;
import location.LocationUtil;
import location.WorldMap;
import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.PokemonUtil;
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
	/**
	 * Helper for run().
	 * Prints options, then asks the player to make a choice, then handles that choice.
	 */
	private void gameLoopMenu() {
		boolean runLoop = true;
		while(runLoop) {
			// print options
			StandardIO.printDivider();

			String prompt = "What would you like to do?";
			String[] options = {
					"explore " + player.getLocation().getName(),
					"travel",
					"check status",
					"save/load (in development)",
					"options",
					"quit game"
			};

			int choice = StandardMenu.promptSelection(prompt, options);
			switch(choice) {		
			case 1:
				// "explore", i.e. current location activity
				player.getLocation().runActivity();
				break;
			case 2:
				// "travel"
				LocationUtil.travel(worldmap, player, player.getLocation());
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
		String prompt = "What would you like to see?";
		String[] options = {
				"current location",
				"bag info",
				"team info"
		};

		int choice = StandardMenu.promptSelectionEscapable(prompt, options);
		switch(choice) {		
		case 1:
			// print player location
			StandardIO.printDivider();
			StandardIO.println( player.getLocation().toString() + "\n");
			StandardIO.delayModerate();
			break;
		case 2:
			// print player bag info
			StandardIO.printDivider();
			StandardIO.println( player.getTrainer().getBag().getAllItemsSummary() );
			StandardIO.delayVeryLong();
			break;
		case 3:
			// print player team info
			pokemonStatusMenu();
			break;
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
		String prompt = "What would you like to do?";
		String[] options = {
				"view stats",
				"swap",
				"heal" 
		};

		int actionChoice = StandardMenu.promptSelectionEscapable(prompt, options);
		switch(actionChoice) {		
		case 1:
			// view stats
			StandardIO.printDivider();
			StandardIO.println( PokemonUtil.getStatistics(selectedPokemon) + selectedPokemon.getAllMovesString());
			StandardIO.delayVeryLong();
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
			StandardIO.delayLong();
			break;
		}
	}

	private int selectPokemonIndex(String question) {
		// build menu
		StandardIO.printDivider();
		Pokemon[] pokes = player.getTrainer().getAllPokemon();
		String[] options = new String[player.getTrainer().getNumPokemon()];
		for(int i = 0; i < options.length; i++)
			options[i] = PokemonUtil.getInlineSummary(pokes[i]);

		int pokeChoice = StandardMenu.promptIndexEscapable(question, options);
		return pokeChoice;
	}

	/**
	 * Helper for gameLoopMenu
	 * 
	 */
	private void saveOrLoadMenu() {
		// print options
		StandardIO.printDivider();
		String prompt = "What would you like to do?";
		String[] options = {
				"save",
				"load"
		};

		int choice = StandardMenu.promptSelectionEscapable(prompt, options);
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
			if(loadedTrainer != null) {
				player.setTrainer(loadedTrainer);
				StandardIO.println("The save file was correctly loaded.");
				StandardIO.println("(BETA - team/bag info restored, locations/box not recovered)");
			} else {
				StandardIO.println("Unfortunately, the save file could not be loaded.");
			}
			StandardIO.printLineBreak();
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
		String prompt = "What would you like to change?";
		String[] options = {
				"text-crawl delay",
				"audio"
		};

		int choice = StandardMenu.promptSelectionEscapable(prompt, options);
		switch(choice) {		
		case 1:
			// print current crawl speed
			StandardIO.printDivider();
			StandardIO.println("Current crawl delay: " + StandardIO.getCrawlDelay());
			StandardIO.print("New crawl delay: ");
			StandardIO.setCrawlDelay( StandardIO.getInt() );
			StandardIO.printLineBreak();
			break;
		case 2:
			// print current crawl speed
			StandardIO.printDivider();
			StandardIO.println("Audio is currently" 
					+ (!SoundPlayer.isAllowedSound() ? " not" : "") + " allowed.");

			prompt = "Allow audio?";
			boolean activateAudio = StandardMenu.promptYesOrNo(prompt);
			if(activateAudio) {
				SoundPlayer.setAllowSound(true);
				SoundPlayer.playSound("sounds/game_sounds/toggle.wav");
			}else{
				SoundPlayer.setAllowSound(false);
			}
			break;
		}
	}

	/**
	 * run on startup, sets up the trainer
	 */
	private void setUpPlayer() {
		// set up the player 
		player = new Player();
		player.setLocation(WorldMap.getStart());
		// set up the player's bag
		player.getTrainer().setBag(Bag.getBasicBag());

	}
}
