package items;

import pokemon.Pokemon;

/**
 * 
 * @author davidclark
 *
 */
public class Potion extends Item{

	public static final Potion
			POTION = new Potion("Potion", 20),
			SUPER_POTION = new Potion("Super Potion", 50),
			HYPER_POTION = new Potion("Hyper Potion", 200);
	
	private int healAmount;
	
	public Potion(String nm, int healAmt) {
		super(nm);
		this.healAmount = healAmt;
	}
	
	/**
	 * 
	 */
	public void healPokemon(Pokemon poke) {
		poke.heal(healAmount);
	}
	
	public int getHealAmt() {
		return healAmount;
	}

}
