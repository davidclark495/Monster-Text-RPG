package location;

import audio.SoundPlayer;
import io.StandardIO;
import io.StandardMenu;
import items.PokemonStorageBox;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class PokeCenter extends Location{

	// base constructor
	public PokeCenter(String nm, String mapDesc, String localDesc) {
		super(nm, mapDesc, localDesc);
		defineActivity();
	}

	/**
	 * a helper method that Location requires
	 * sets the location's Activity to a certain lambda function that represents the main function of the area
	 */
	protected void defineActivity() {
		this.setActivity( () -> {
			// the actual definition of this location's Activity
			runMenu();
			return true;
		});
	}

	/**
	 * helper for runActivity()
	 * choose between healing and using the box
	 */
	private void runMenu() {
		String prompt = "What would you like to do?";
		String[] options = {
				"heal",
				"box"
		};

		int choice = StandardMenu.promptSelectionEscapable(prompt, options);
		switch(choice) {		
		case 1:
			// "heal" 
			healPokemon();
			break;
		case 2:
			// "box"
			boxMenu();
			break;
		}
	}

	/**
	 * Helper for runMenu()
	 * loop until EscChar is input
	 */
	private void boxMenu() {
		String prompt = "What would you like to do?";
		String[] options = {
				"deposit",
				"withdraw",
				"summary"
		};

		int choice = StandardMenu.promptSelectionEscapable(prompt, options);
		switch(choice) {		
		case 1:
			// "deposit" 
			depositPokemon();
			break;
		case 2:
			// "withdraw"
			withdrawPokemon();
			break;
		case 3:
			// "summary"
			StandardIO.printDivider();
			StandardIO.println( getPlayer().getBox().getAllPokemonStr() );
			break;
		}

	}

	/**
	 * helper for boxMenu()
	 */
	private void depositPokemon() {
		Trainer playerTrainer = getPlayer().getTrainer();
		PokemonStorageBox box = getPlayer().getBox();

		// don't proceed if player only has 1 pokemon
		if(playerTrainer.getNumPokemon() == 1) {// bad state: player would be depositing last pokemon
			StandardIO.printDivider();
			StandardIO.println("You can't deposit your only pokemon.\n");
			return;
		}

		// choose which pokemon to deposit (from trainer's team)
		int choice;
		boolean loopAgain = false;
		do {
			// handle menu
			StandardIO.printDivider();
			StandardIO.println("Which pokemon will you deposit?\n");
			StandardIO.println(playerTrainer.getAllPokemonStr());// print menu, 1-6
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt() -1;// convert from display values to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -2 ){// account for -1 offset, EscChar is -2
				return;
			}
			// catch bad inputs
			if(playerTrainer.getPokemon(choice) == null) {// bad input: pokemon at index "choice" doesn't exist
				loopAgain = true;
				StandardIO.println("You don't have that pokemon.\n");
			}
			else {
				loopAgain = false;
			}
		}while(loopAgain);

		// make the deposit
		Pokemon depositedPokemon = playerTrainer.getPokemon(choice);
		box.addPokemon(depositedPokemon);
		playerTrainer.removePokemon(choice);
		StandardIO.printDivider();
		StandardIO.println("You deposited " + depositedPokemon.getNickname() + ".\n");
	}

	/**
	 * helper for boxMenu()
	 */
	private void withdrawPokemon() {
		Trainer playerTrainer = getPlayer().getTrainer();
		PokemonStorageBox box = getPlayer().getBox();

		// don't proceed if box is empty
		if(box.isEmpty()) {// bad state: box is empty
			StandardIO.printDivider();
			StandardIO.println("Your box contains no pokemon to withdraw.\n");
			return;
		}
		// don't proceed if player has no space
		if(playerTrainer.hasFullTeam()) {
			StandardIO.printDivider();
			StandardIO.println("You don't have any space on your team.\n");
			return;
		}

		// choose which pokemon to withdraw (from box)
		int choice;
		boolean loopAgain = false;
		do {
			// handle menu
			StandardIO.printDivider();
			StandardIO.println("Which pokemon will you withdraw?\n");
			StandardIO.println(box.getAllPokemonStr());// print menu, 1 through N
			StandardIO.printEscCharReminder();
			choice = StandardIO.promptInt() -1;// convert from display values to indexes
			StandardIO.printLineBreak();

			// handle escape request
			if( choice == -2 ){// account for -1 offset, EscChar is -2
				return;
			}
			// catch bad inputs
			if(box.getPokemon(choice) == null) {// bad input: pokemon at index "choice" doesn't exist
				loopAgain = true;
				StandardIO.println("You don't have that pokemon.\n");
			}
			else {
				loopAgain = false;
			}
		}while(loopAgain);

		// make the withdrawal
		Pokemon withdrawnPoke = box.removePokemon(choice);
		playerTrainer.addPokemon( withdrawnPoke );
		StandardIO.printDivider();
		StandardIO.println("You withdrew " + withdrawnPoke.getNickname() + ".\n");
	}

	/**
	 * Helper for healLoop
	 * Heal all pokemon of the known trainer
	 */
	private void healPokemon() {
		for(int i = 0; i < getPlayer().getTrainer().getNumPokemon(); i++) {
			Pokemon temp = getPlayer().getTrainer().getPokemon(i);
			temp.setHp(temp.getMaxHP());;
			temp.restoreAllPP();

			StandardIO.print(".");
			StandardIO.delayModerate();
			SoundPlayer.playSound("sounds/game_sounds/toggle.wav");
		}
		StandardIO.printLineBreak();
		StandardIO.printLineBreak();
		StandardIO.println("You have successfully healed your pokemon.\n");
	}
}
