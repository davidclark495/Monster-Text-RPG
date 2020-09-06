package pokemon;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class Tester {

	private Trainer trainer;
	
	@BeforeEach
	void setUp() throws Exception{
		trainer = new Trainer();
		trainer.addPokemon(new Pokemon("Charmander", PkType.FIRE, 80));
	}
	
	@AfterEach
	void tearDown() throws Exception{
	}
	
	@Test
	void addPokemon() {
		
	}

}

