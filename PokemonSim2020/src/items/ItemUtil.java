package items;

import java.util.Random;

import io.StandardIO;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

/**
 * Contains methods for using Items.
 * 
 * @author davidclark
 * date: 09/05/2022
 */
public class ItemUtil {

	/**
	 * Attempts to catch the pokemon, prints the results.
	 * The chance of success is a product of the pokemon's current Hp, etc.
	 * Pokemon must have trainer == null, trainer.addPokemon() must be successful (otherwise poke is added to box)
	 * 
	 * @param player The player
	 * @param poke The pokemon this ball is thrown at
	 * @return whether the catch succeeded (the pokemon was caught) or failed
	 */
	public static boolean catchAttemptPrint(Player player, Pokemon poke, Pokeball ball) {
		Trainer thrower = player.getTrainer();
		StandardIO.println("You threw the " + ball.getName() + ".\n");

		Random generator = new Random();
		double successThreshold = 0.5 ;// math, can be tweaked
		successThreshold += 0.5*(poke.getHP())/poke.getMaxHP() ;// factor in poke hp/maxHP
		//successThreshold += poke.getLevel() ;// factor in poke level
		double value = generator.nextDouble() * ball.getBallBonus();// math, can be tweaked

		// pokemon belongs to another trainer, cannot be caught
		if(poke.getTrainer() != null) {
			StandardIO.println("Wait! That " + poke.getName() + " belongs to someone else!\n");
			return false;
		}
		
		// capture fails
		if( (value < successThreshold) ) {
			StandardIO.println("Oh no! The wild " + poke.getName() + " broke free!\n");
			return false;
		}
		
		// success!
		StandardIO.println(poke.getName() + " was caught!\n");
		poke.setNewTrainer(thrower);
		if( thrower.addPokemon(poke))
			return true;
		
		else {// trainer has no space on team, send to box
			StandardIO.println("...you don't have any space on your team."
					+ "\n" + poke.getName() + " was sent to your box.\n");
			poke.setNewTrainer(thrower);
			player.getBox().addPokemon(poke);
			return true;
		}

	}
}
