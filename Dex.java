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
			TACKLE = new Attack("Tackle", 10, PkType.normal),
			SCRATCH = new Attack("Scratch", 10, PkType.normal),
			EMBER = new Attack("Ember", 20, PkType.fire),
			BUBBLE = new Attack("Bubble", 15, PkType.water),
			VINE_WHIP = new Attack("Vine Whip", 25, PkType.grass);
	
	public static Pokemon 
	eevee = new Pokemon("Eevee", PkType.normal, 60, new Attack[] {Dex.TACKLE}),
	charmander = new Pokemon("Charmander", PkType.fire, 80, new Attack[]{Dex.SCRATCH, Dex.EMBER}),
	whooper = new Pokemon("Whooper", PkType.water, 50, new Attack[] {Dex.TACKLE, Dex.BUBBLE}),
	carnivine = new Pokemon("Carnivine", PkType.grass, 90, new Attack[] {Dex.SCRATCH, Dex.VINE_WHIP});

}
