package location;

import io.StandardIO;
import game.PokeBattle;
import pokemon.Dex;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class PokeCenter extends Location{

	public PokeCenter() {
		this("PokeCenter", null);
	}
	public PokeCenter(String nm) {
		this(nm, null);
	}
	public PokeCenter(Player plyr) {
		this("PokeCenter", plyr);
	}
	public PokeCenter(String nm, Player plyr) {
		super(nm, plyr);
		this.setDescription("A place to heal your pokemon.");
	}

	@Override
	public void runActivity() {
		// prompts the user
		boolean doHeal = healPrompt();
		io.printLineBreak();
		if(doHeal) 
			healPokemon();
	}

	/**
	 * Helper for healLoop
	 * Loops until the player chooses "No" (false) or "Yes" (true)
	 */
	private boolean healPrompt() {
		int choice;
		while(true) {

			// handle menu
			System.out.println("Heal your pokemon?\n"
					+ "0 - No\n"
					+ "1 - Yes\n");
			choice = io.promptInt();

			// option 0: don't heal
			if( choice == 0 ){
				return false;
			}
			// option 1: heal pokemon
			else if(choice == 1) {
				return true;
			}
			// bad input: allow loop to repeat
			else {
				io.printInputNotRecognized();
			}
			io.printDivider();
		}
	}

	/**
	 * Helper for healLoop
	 * Heal all pokemon of the known trainer
	 */
	private void healPokemon() {
		for(int i = 0; i < getTrainer().getNumPokemon(); i++) {
			Pokemon temp = getTrainer().getPokemon(i);
			temp.setHp(temp.getMaxHp());;
		}
		System.out.println("You have successfully healed your pokemon.\n");
	}
}
