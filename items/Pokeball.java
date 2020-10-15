package items;

import java.util.Random;

import io.StandardIO;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class Pokeball extends Item{
	
	public static final Pokeball 
	POKEBALL = new Pokeball("Pokeball", 1),
	GREATBALL = new Pokeball("Greatball", 1.5),
	ULTRABALL = new Pokeball("Ultraball", 2);

	private double ballBonus;

	public Pokeball(String nm, double bonus) {
		super(nm);
		this.ballBonus = bonus;
	}

	
	
	
	
	
//	/**
//	 * Attempts to catch the pokemon, does not print results
//	 * The chance of success is a product of the pokemon's current Hp, etc.
//	 * Pokemon must have trainer == null, trainer.addPokemon() must be successful (otherwise poke is added to box)
//	 * 
//	 * @param player The player
//	 * @param poke The pokemon this ball is thrown at
//	 * @return whether the catch succeeded (the pokemon was caught) or failed
//	 */
//	public boolean catchAttemptSilent(Player player, Pokemon poke) {
//		Trainer thrower = player.getTrainer();
//		Random generator = new Random();
//		double successThreshold = 0.5 + 0.5*(poke.getHp())/poke.getMaxHp() ;// math, can be tweaked
//		double value = generator.nextDouble() * ballBonus;// math, can be tweaked
//		
//		// pokemon belongs to another trainer, cannot be caught
//		if(poke.getTrainer() != null) {
//			return false;
//		}
//		// capture fails
//		if( (successThreshold > value) ) {
//			return false;
//		}
//		
//		// success!
//		poke.setNewTrainer(thrower);
//		if( thrower.addPokemon(poke))
//			return true;
//		else {// trainer has no space on team, send to box
//			poke.setNewTrainer(thrower);
//			player.getBox().addPokemon(poke);
//			return true;
//		}
//	}

	/**
	 * Attempts to catch the pokemon, prints the results.
	 * The chance of success is a product of the pokemon's current Hp, etc.
	 * Pokemon must have trainer == null, trainer.addPokemon() must be successful (otherwise poke is added to box)
	 * 
	 * @param player The player
	 * @param poke The pokemon this ball is thrown at
	 * @return whether the catch succeeded (the pokemon was caught) or failed
	 */
	public boolean catchAttemptPrint(Player player, Pokemon poke) {
		Trainer thrower = player.getTrainer();
		StandardIO.println("You threw the " + this.getName() + ".\n");

		Random generator = new Random();
		double successThreshold = 0.5 ;// math, can be tweaked
		successThreshold += 0.5*(poke.getHp())/poke.getMaxHp() ;// factor in poke hp/maxHP
		//successThreshold += poke.getLevel() ;// factor in poke level
		double value = generator.nextDouble() * ballBonus;// math, can be tweaked

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
