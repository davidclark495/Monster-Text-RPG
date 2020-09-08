package pokemon;

/**
 * This class holds static references to pre-made Pokemon and Attacks
 * 
 * @author David Clark
 *
 */
public class Dex {


	public static final Attack BASIC_ATTACK = new Attack();
	public static final Attack 
			TACKLE = new Attack("Tackle", 10, PkType.NORMAL),
			SCRATCH = new Attack("Scratch", 10, PkType.NORMAL),
			EMBER = new Attack("Ember", 20, PkType.FIRE),
			BUBBLE = new Attack("Bubble", 15, PkType.WATER),
			VINE_WHIP = new Attack("Vine Whip", 25, PkType.GRASS);
	
	public static Pokemon 
	eevee = new Pokemon("Eevee", PkType.NORMAL, 60, new Attack[] {Dex.TACKLE}),
	charmander = new Pokemon("Charmander", PkType.FIRE, 80, new Attack[]{Dex.SCRATCH, Dex.EMBER}),
	whooper = new Pokemon("Whooper", PkType.WATER, 50, new Attack[] {Dex.TACKLE, Dex.BUBBLE}),
	carnivine = new Pokemon("Carnivine", PkType.GRASS, 90, new Attack[] {Dex.SCRATCH, Dex.VINE_WHIP});

}
