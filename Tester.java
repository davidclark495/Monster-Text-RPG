package game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import items.Bag;
import items.Pokeball;
import pokemon.Dex;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

class Tester {

	private Trainer trainer;
	
	@BeforeEach
	void setUp() throws Exception{
		trainer = new Trainer();
		
		trainer.addPokemon(new Pokemon(Dex.charmander));
		
		trainer.getBag().addItem(Pokeball.GREATBALL, 2);
		trainer.getBag().addItem(Pokeball.POKEBALL);
	}
	
	
	
	@Test
	void bag_spendItem_item() {
		Bag bag = trainer.getBag();
		assertNotNull(bag.spendItem(Pokeball.POKEBALL));
	}
	
	@Test
	void bag_spendItem_int() {
		Bag bag = trainer.getBag();
		for(int i = 0; i < 2; i++) {
			//System.out.println(bag.getAllItemsSummary());
			assertNotNull(bag.spendItem(0));			
		}
	}
	
	@Test 
	void pokemon_getMove_null(){
		Pokemon poke = new Pokemon();
		assertNull(poke.getMove(0));
	}
	
	@Test 
	void pokemon_getMove_outOfBounds(){
		Pokemon poke = new Pokemon();
		assertNull(poke.getMove(10));
	}
	
	@Test 
	void pkType_isWeakTo(){
		PkType fire = PkType.fire;
		PkType water = PkType.water;
		PkType normal = PkType.normal;
		System.out.println(fire.isWeakTo(water));
		assertTrue(fire.isWeakTo(water));
		assertFalse(fire.isWeakTo(normal));
	}
	
	@Test 
	void pkType_isResistantTo(){
		PkType fire = PkType.fire;
		PkType grass = PkType.grass;
		PkType normal = PkType.normal;
		assertTrue(fire.isResistantTo(grass));
		assertFalse(fire.isResistantTo(normal));
	}

}

