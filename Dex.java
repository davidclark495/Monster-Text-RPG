package pokemon;

import java.util.Random;

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
		VINE_WHIP = new Attack("Vine Whip", 25, PkType.grass),
		SPARK = new Attack("Spark", 15, PkType.electric),
		GUST = new Attack("Gust", 20, PkType.flying),
		ROCK_THROW = new Attack("Rock Throw", 25, PkType.rock);

	public static Pokemon 
		eevee = new Pokemon("Eevee", PkType.normal, 60, new Attack[] {Dex.TACKLE}),
		charmander = new Pokemon("Charmander", PkType.fire, 80, new Attack[]{Dex.SCRATCH, Dex.EMBER}),
		whooper = new Pokemon("Whooper", PkType.water, 50, new Attack[] {Dex.TACKLE, Dex.BUBBLE}),
		carnivine = new Pokemon("Carnivine", PkType.grass, 90, new Attack[] {Dex.SCRATCH, Dex.VINE_WHIP}),
		pikachu = new Pokemon("Pikachu", PkType.electric, 80, new Attack[] {Dex.TACKLE, Dex.SPARK}),
		swoobat = new Pokemon("Swoobat", PkType.flying, 50, new Attack[] {Dex.SCRATCH, Dex.GUST}),
		rolycoly = new Pokemon("Rolycoly", PkType.rock, 40, new Attack[] {Dex.TACKLE, Dex.ROCK_THROW}),
		skarmory = new Pokemon("Skarmory", PkType.flying, 120, new Attack[] {Dex.SCRATCH, Dex.GUST, Dex.ROCK_THROW}),
		missingno = new Pokemon("glitch", PkType.normal, 0);
	
	/**
	 * Generates a random encounter from an array of pokemon (w/ an correlated array of encounter rates)
	 * Returns a random pokemon from the list
	 * 
	 * Throws exceptions if
	 * 		(currently commented out, problem with adding doubles) sum of rates[] != 1
	 * 		pokes.length != rates.length
	 * 
	 * returns a missingno Pokemon if no pokemon was selected (result of bad implementation, would need to be fixed)
	 * 
	 * Works by treating the rates as intervals, 
	 * then stacking them btwn 0 and 1, 
	 * then throwing a dart btwn 0 and 1, 
	 * then checking each interval to see if it caught the dart
	 * 
	 * @param pokes An array of all Pokemon that could appear
	 * @param rates A correlated array containing the relative frequency of encountering each Pokemon; 
	 * 				each value should be btwn 0 and 1; 
	 * 				the total value of all rates should be 1
	 * @return a random pokemon chosen from pokes
	 */
	public static Pokemon generateEncounter(Pokemon[] pokes, double[] rates) {
		// check: arrays are equal length
		if( pokes.length != rates.length ) {
			throw new IllegalArgumentException("Code error: pokes[].length must be equal to rates.length.");
		}
		// check: sum of rates[] is 1
		double sum = 0;
		for(int i = 0; i < rates.length; i++) {
			sum += rates[i];
		}
		if( sum < 0.999 || sum > 1.001 ) {
			throw new IllegalArgumentException("Code error: sum of rates[] must be 1.0.");
		}
		
		// generate encounter
		double selection = new Random().nextDouble();
		double lowerBound = 0;
		double upperBound = rates[0];
		for(int i = 0; i < pokes.length; i++) {
			// find which interval the selection landed in
			if( lowerBound < selection && selection < upperBound) {
				return new Pokemon(pokes[i]);
			}
			// set up the next loop
			lowerBound += rates[i];
			upperBound += rates[i+1];
		}
		return new Pokemon(Dex.missingno);
	}
	
	/**
	 * 
	 * @param pokes
	 * @return
	 */
	public static Pokemon generateEncounter(Pokemon[] pokes) {
		int selection = new Random().nextInt(pokes.length);
		return new Pokemon(pokes[selection]);
	}
	
}
