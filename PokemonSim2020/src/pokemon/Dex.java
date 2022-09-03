package pokemon;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class holds static references to pre-made Pokemon and Attacks
 * 
 * @author David Clark
 *
 */
public class Dex {
	
	private ArrayList<Pokemon> allPokemon;
	private ArrayList<Move> allMoves;
	
	public Dex() {
		populateAllPokemon();
		populateAllMoves();
	}
	
	// main system for accessing pokemon and moves
	private void populateAllPokemon() {
//		allPokemon = new ArrayList<>();
//		allPokemon.add(eevee);
//		allPokemon.add(vulpix);
//		allPokemon.add(wooper);
//		allPokemon.add(sunkern);
//		allPokemon.add(carnivine);
//		allPokemon.add(pikachu);
//		allPokemon.add(woobat);
//		allPokemon.add(rolycoly);
//		allPokemon.add(skarmory);
//		allPokemon.add(combee);
//		allPokemon.add(cutiefly);
//		allPokemon.add(milcery);
//		allPokemon.add(grimer);
//		allPokemon.add(tentacool);	
	}
	
	private void populateAllMoves() {
//		allMoves = new ArrayList<>();
//		allMoves.add(tackle);
//		allMoves.add(scratch);
//		allMoves.add(ember);
//		allMoves.add(bubble);
//		allMoves.add(vine_whip);
//		allMoves.add(spark);
//		allMoves.add(gust);
//		allMoves.add(rock_throw);
//		allMoves.add(bug_bite);
//		allMoves.add(fairy_wind);
//		allMoves.add(poison_sting);
//		allMoves.add(sludge);
	}
	
	public Pokemon getPokemonFromString(String pokeName) {
		Pokemon foundPoke = null;
		for(Pokemon poke : allPokemon)
			if(pokeName.equals(poke.getName()))
				foundPoke = poke;
		return foundPoke;
	}
	
	public Move getAttackFromString(String atckName) {
		Move foundMove = null;
		for(Move move : allMoves)
			if(atckName.equals(move.getName()))
				foundMove = move;
		return foundMove;
	}
	
	// static blocks
	// attack info
	public static Move 
//		tackle = 		new Move("Tackle", 	35, PkType.NORMAL,	20),
//		scratch = 		new Move("Scratch", 	35, PkType.NORMAL,	20),
//		ember = 		new Move("Ember", 	40, PkType.FIRE,	10),
//		bubble = 		new Move("Bubble", 	40, PkType.WATER,	15),
//		vine_whip = 	new Move("Vine Whip", 50, PkType.GRASS,	10),
//		spark = 		new Move("Spark", 	40, PkType.ELECTRIC,15),
//		gust = 			new Move("Gust", 		40, PkType.FLYING,	10),
//		rock_throw =	new Move("Rock Throw",50, PkType.ROCK,	10),
//		bug_bite = 		new Move("Bug Bite",	60,	PkType.BUG,		15),
//		fairy_wind = 	new Move("Fairy Wind",40, PkType.FAIRY, 	15),
//		poison_sting = 	new Move("Poison Sting", 20, PkType.POISON,25),
//		sludge = 		new Move("Sludge",	65,	PkType.POISON,	10);

	static {// add sounds to attacks here
		String path = "sounds/attack_sounds/";
		
//		tackle.setAudioPath(path + "whoosh.wav");
//		scratch.setAudioPath(path + "metal_slash.wav");
//		ember.setAudioPath(path + "weak_fire_sound.wav");
//		bubble.setAudioPath(path + "bubbles.wav");
//		vine_whip.setAudioPath(path + "whip_sound.wav");
//		spark.setAudioPath(path + "spark.wav");
//		gust.setAudioPath(path + "wind_woosh.wav");
//		rock_throw.setAudioPath(path + "rock_smash.wav");
		//
		//
		//
		//
	}
	
	// pokemon info
	public static Pokemon 
//		eevee = new Pokemon("Eevee", 			PkType.NORMAL, 	20, 	new Move[] {Dex.tackle}),
//		vulpix = new Pokemon("Vulpix",			PkType.FIRE,	20,		new Move[] {Dex.tackle, Dex.ember}),
////		charmander = new Pokemon("Charmander", 	PkType.fire, 	20, 	new Move[] {Dex.scratch, Dex.ember}),
//		wooper = new Pokemon("Wooper", 			PkType.WATER, 	20, 	new Move[] {Dex.tackle, Dex.bubble}),
//		sunkern = new Pokemon("Sunkern", 		PkType.GRASS,	20,		new Move[] {Dex.tackle, Dex.vine_whip}),
//		carnivine = new Pokemon("Carnivine", 	PkType.GRASS, 	20, 	new Move[] {Dex.scratch, Dex.vine_whip}),
//		pikachu = new Pokemon("Pikachu", 		PkType.ELECTRIC,20, 	new Move[] {Dex.tackle, Dex.spark}),
//		woobat = new Pokemon("Woobat", 			PkType.FLYING, 	20, 	new Move[] {Dex.scratch, Dex.gust}),
//		rolycoly = new Pokemon("Rolycoly", 		PkType.ROCK, 	20, 	new Move[] {Dex.tackle, Dex.rock_throw}),
//		skarmory = new Pokemon("Skarmory",		PkType.FLYING, 	20, 	new Move[] {Dex.scratch, Dex.gust, Dex.rock_throw}),
//		combee = new Pokemon("Combee",			PkType.BUG,		20,		new Move[] {Dex.bug_bite, Dex.gust}),
//		cutiefly = new Pokemon("Cutiefly",		PkType.BUG,		20,		new Move[] {Dex.bug_bite, Dex.fairy_wind}),
//		milcery = new Pokemon("Milcery",		PkType.FAIRY,	20,		new Move[] {Dex.fairy_wind, Dex.tackle}),
//		grimer = new Pokemon("Grimer",			PkType.POISON,	20,		new Move[] {Dex.poison_sting, Dex.sludge}),	
//		tentacool = new Pokemon("Tentacool",	PkType.WATER,	20,		new Move[] {Dex.bubble, Dex.poison_sting}),
//		missingno = new Pokemon("glitch", 		PkType.NORMAL, 	20);
	
	static {// set stat blocks here
//		pokemon.setStatBlock(		hp, atk, def, hpMod, atkMod, defMod, level);
//		int a = 0;//baseline for atk
//		int d = 0;//baseline for def
//		eevee.setStatBlock(			20, a+10, d+10, 2, 1, 1,  4);
//		vulpix.setStatBlock(		21, a+12, d+ 9, 2, 2, 1,  4);
//		charmander.setStatBlock(	21, a+12, d+ 9, 2, 2, 1,  5);
//		wooper.setStatBlock(		18, a+10, d+12, 2, 1, 2,  4);
//		sunkern.setStatBlock(		24, a+ 8, d+13, 2, 1, 2,  4);
//		carnivine.setStatBlock(		23, a+13, d+ 7, 3, 2, 1,  9);
//		pikachu.setStatBlock(		22, a+11, d+ 9, 2, 1, 1,  5);
//		woobat.setStatBlock(		20, a+10, d+10, 2, 1, 1,  6);
//		rolycoly.setStatBlock(		15, a+12, d+13, 1, 2, 2,  8);
//		skarmory.setStatBlock(		24, a+10, d+15, 2, 1, 3, 11);
//		combee.setStatBlock(		18, a+ 9, d+13, 2, 1, 2,  6);
//		cutiefly.setStatBlock(		17, a+13, d+ 9, 2, 2, 1,  6);
//		milcery.setStatBlock(		22, a+ 9, d+12, 3, 1, 2,  8);
//		grimer.setStatBlock(		21, a+13, d+13, 1, 2, 2,  8);
//		tentacool.setStatBlock(		18, a+13, d+ 9, 2, 1, 1,  6);
	}
	
	static {// set cries here
//		vulpix.setCry("vuuulpix");
//		wooper.setCry("woooop");
//		pikachu.setCry("piikaaa");
	}
	
	
	// misc methods
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
