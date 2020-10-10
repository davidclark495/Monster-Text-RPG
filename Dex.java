package pokemon;

import java.util.Random;

/**
 * This class holds static references to pre-made Pokemon and Attacks
 * 
 * @author David Clark
 *
 */
public class Dex {
	
	public static Attack 
		tackle = 	new Attack("Tackle", 	10, PkType.normal,	20),
		scratch = 	new Attack("Scratch", 	10, PkType.normal,	20),
		ember = 	new Attack("Ember", 	20, PkType.fire,	10),
		bubble = 	new Attack("Bubble", 	15, PkType.water,	15),
		vine_whip = new Attack("Vine Whip", 25, PkType.grass,	10),
		spark = 	new Attack("Spark", 	15, PkType.electric,15),
		gust = 		new Attack("Gust", 		20, PkType.flying,	10),
		rock_throw = new Attack("Rock Throw", 25, PkType.rock,	10),
		bug_bite = 	new Attack("Bug Bite",	15,	PkType.bug,		15),
		fairy_wind = new Attack("Fairy Wind", 15, PkType.fairy, 15),
		poison_sting = new Attack("Poison Sting", 5, PkType.poison, 25),
		sludge = new Attack("Sludge",	25,	PkType.poison,	10);

	static {// add sounds to attacks here
		String path = "sounds/attack_sounds/";
		
		tackle.setAudioPath(path + "whoosh.wav");
		scratch.setAudioPath(path + "metal_slash.wav");
		ember.setAudioPath(path + "weak_fire_sound.wav");
		bubble.setAudioPath(path + "bubbles.wav");
		vine_whip.setAudioPath(path + "whip_sound.wav");
		spark.setAudioPath(path + "spark.wav");
		gust.setAudioPath(path + "wind_woosh.wav");
		rock_throw.setAudioPath(path + "rock_smash.wav");
	}
	
	public static Pokemon 
		eevee = new Pokemon("Eevee", 			PkType.normal, 	60, 	new Attack[] {Dex.tackle}),
		charmander = new Pokemon("Charmander", 	PkType.fire, 	80, 	new Attack[] {Dex.scratch, Dex.ember}),
		wooper = new Pokemon("Wooper", 			PkType.water, 	50, 	new Attack[] {Dex.tackle, Dex.bubble}),
		carnivine = new Pokemon("Carnivine", 	PkType.grass, 	90, 	new Attack[] {Dex.scratch, Dex.vine_whip}),
		pikachu = new Pokemon("Pikachu", 		PkType.electric,80, 	new Attack[] {Dex.tackle, Dex.spark}),
		swoobat = new Pokemon("Swoobat", 		PkType.flying, 	50, 	new Attack[] {Dex.scratch, Dex.gust}),
		rolycoly = new Pokemon("Rolycoly", 		PkType.rock, 	40, 	new Attack[] {Dex.tackle, Dex.rock_throw}),
		skarmory = new Pokemon("Skarmory",		PkType.flying, 	120, 	new Attack[] {Dex.scratch, Dex.gust, Dex.rock_throw}),
		combee = new Pokemon("Combee",			PkType.bug,		50,		new Attack[] {Dex.bug_bite, Dex.gust}),
		cutiefly = new Pokemon("Cutiefly",		PkType.bug,		30,		new Attack[] {Dex.bug_bite, Dex.fairy_wind}),
		swirlix = new Pokemon("Swirlix",		PkType.fairy,	60,		new Attack[] {Dex.fairy_wind, Dex.tackle}),
		grimer = new Pokemon("Grimer",			PkType.poison,	80,		new Attack[] {Dex.poison_sting, Dex.sludge}),	
		missingno = new Pokemon("glitch", 		PkType.normal, 	0);
	
	static {// level up pokemon here, might not be useful
		//charmander.setLevel(10);
	}
	
	
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
