package game;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import items.Bag;
import items.Pokeball;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

class Tester {

	private Trainer trainer;
	
	@BeforeEach
	void setUp() throws Exception{
		trainer = new Trainer();
		
		trainer.addPokemon(new Pokemon("Charmander", PkType.FIRE, 80));
		
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
		Pokemon poke = trainer.getPokemon(0);
		poke.getMove(0);
		//System.out.println(poke.getMove(0));
	}
	
	@Test 
	void pokemon_getMove_outOfBounds(){
		Pokemon poke = trainer.getPokemon(0);
		poke.getMove(10);
		//System.out.println(poke.getMove(10));
	}

}

