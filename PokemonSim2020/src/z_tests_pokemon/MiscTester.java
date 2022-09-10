package z_tests_pokemon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import items.Bag;
import items.Pokeball;
import location.Location;
import pokemon.Dex;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

class MiscTester {
//
//	private Trainer trainer;
//	
//	@BeforeEach
//	void setUp() throws Exception{
//		trainer = new Trainer();
//		
//		trainer.addPokemon(new Pokemon(Dex.eevee));
//		
//		trainer.getBag().addItem(Pokeball.GREATBALL, 2);
//		trainer.getBag().addItem(Pokeball.POKEBALL);
//	}
//	
//	
//	
//	@Test
//	void bag_spendItem_item() {
//		Bag bag = trainer.getBag();
//		assertNotNull(bag.spendItem(Pokeball.POKEBALL));
//	}
//	
//	@Test
//	void bag_spendItem_int() {
//		Bag bag = trainer.getBag();
//		for(int i = 0; i < 2; i++) {
//			//System.out.println(bag.getAllItemsSummary());
//			assertNotNull(bag.spendItem(0));			
//		}
//	}
//	
//	@Test 
//	void pokemon_getMove_null(){
//		Pokemon poke = new Pokemon();
//		assertNull(poke.getMove(0));
//	}
//	
//	@Test 
//	void pokemon_getMove_outOfBounds(){
//		Pokemon poke = new Pokemon();
//		assertNull(poke.getMove(10));
//	}
//	
//	@Test 
//	void pkType_isWeakTo(){
//		PkType fire = PkType.fire;
//		PkType water = PkType.water;
//		PkType normal = PkType.normal;
//		assertTrue(fire.isWeakTo(water));
//		assertFalse(fire.isWeakTo(normal));
//	}
//	
//	@Test 
//	void pkType_isResistantTo(){
//		PkType fire = PkType.fire;
//		PkType grass = PkType.grass;
//		PkType normal = PkType.normal;
//		assertTrue(fire.isResistantTo(grass));
//		assertFalse(fire.isResistantTo(normal));
//	}
//
//	@Test
//	void dex_generateEncounter_success() {
//		Pokemon resultPoke;
//		Pokemon[] possiblePokes = {new Pokemon(Dex.missingno), new Pokemon(Dex.eevee), new Pokemon(Dex.wooper), new Pokemon(Dex.carnivine)};
//		double[] encounterRates = {0.1, 0.5, 0.3, 0.1};
//		
//		int[] counter = {0, 0, 0, 0};
//		
//		for(int i = 0; i < 100; i++) {
//			resultPoke = Dex.generateEncounter(possiblePokes, encounterRates);
//			//System.out.println(resultPoke);
//			if(resultPoke == null)
//				counter[0]++;
//			else if(resultPoke.sameSpecies(Dex.eevee))
//				counter[1]++;
//			else if(resultPoke.sameSpecies(Dex.wooper))
//				counter[2]++;
//			else if(resultPoke.sameSpecies(Dex.carnivine))
//				counter[3]++;
//		}
//		
//		System.out.println(""
//				+ "0: " + counter[0] +"\n"
//				+ "1: " + counter[1] +"\n"
//				+ "2: " + counter[2] +"\n"
//				+ "3: " + counter[3] +"\n");
//		System.out.println(""
//				+ "0: " + counter[0]/100.0 +"\n"
//				+ "1: " + counter[1]/100.0 +"\n"
//				+ "2: " + counter[2]/100.0 +"\n"
//				+ "3: " + counter[3]/100.0 +"\n");
//	}
//	
//	@Test
//	void location_printPathsAway() {
//		Field field = new Field();
//		
//		Location[] tempPathsAway = new Location[3];
//		tempPathsAway[0] = new Field();
//		tempPathsAway[1] = new Field();
//		tempPathsAway[2] = new Field();
//		//field.setPathsAway(tempPathsAway);
//		
//		field.printPathsAway();
//	}
	
}

