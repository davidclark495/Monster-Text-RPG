package items;

import java.util.Random;

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

	
	
	
	
	
	/**
	 * Attempts to catch the pokemon, does not print results
	 * The chance of success is a product of the pokemon's current Hp, etc.
	 * Pokemon must have trainer == null, trainer.addPokemon() must be successful 
	 * 
	 * @param thrower The trainer who would catch the pokemon
	 * @param poke The pokemon this ball is thrown at
	 * @return whether the catch succeeded (the pokemon was caught) or failed
	 */
	public boolean catchAttemptSilent(Trainer thrower, Pokemon poke) {
		Random generator = new Random();
		double successThreshold = 1 - (double)2/3*(poke.getHp())/poke.getMaxHp() ;// math, can be tweaked
		double value = generator.nextDouble() * ballBonus;// math, can be tweaked
		if(successThreshold < value && poke.getTrainer() == null) {
			if(thrower.addPokemon(poke)) {
				poke.setNewTrainer(thrower);
				return true;
			}
		}
		return false;
	}

	/**
	 * Attempts to catch the pokemon, prints the results.
	 * The chance of success is a product of the pokemon's current Hp, etc.
	 * Pokemon must have trainer == null, trainer.addPokemon() must be successful 
	 * 
	 * @param thrower The trainer who would catch the pokemon
	 * @param poke The pokemon this ball is thrown at
	 * @return whether the catch succeeded (the pokemon was caught) or failed
	 */
	public boolean catchAttemptPrint(Trainer thrower, Pokemon poke) {
		System.out.println("You threw the pokeball.\n");

		Random generator = new Random();
		double successThreshold = 0.5 + 0.5*(poke.getHp())/poke.getMaxHp() ;// math, can be tweaked
		double value = generator.nextDouble() * ballBonus;// math, can be tweaked

		// pokemon belongs to another trainer, cannot be caught
		if(poke.getTrainer() != null) {
			System.out.println("Wait! That " + poke.getName() + " belongs to someone else!\n");
			return false;
		}
		
		// capture fails
		if( (successThreshold > value) ) {
			System.out.println("Oh no! The wild " + poke.getName() + " broke free!\n");
			return false;
		}
		
		// trainer has no space on team
		if(! (thrower.addPokemon(poke)) ) {
			System.out.println("...you don't have any space on your team."
					+ "\nThe wild pokemon was released.\n");
			return false;
		}

		// success!
		System.out.println(poke.getName() + " was caught!\n");
		poke.setNewTrainer(thrower);
		return true;
	}



}
