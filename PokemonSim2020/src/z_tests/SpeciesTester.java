package z_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import pokemon.PkType;
import pokemon.Species;

public class SpeciesTester {

	// returns a new Species object called "Pikachu", populated w/ correct data
	private Species getPikachu() {
		return new Species("Pikachu", PkType.ELECTRIC, null, 35, 55, 40, 50, 50, 90);
	}

	// returns a new Species object called "Salamence", populated w/ correct data
	private Species getSalamence() {
		return new Species("Salamence", PkType.DRAGON, PkType.FLYING, 95, 135, 80, 110, 80, 100);
	}
	
	
	

	@Test
	void equals_self() {
		Species pikachu = getPikachu();
		assertTrue(pikachu.equals(pikachu));
	}
	
	@Test
	void equals_notEqualToOther() {
		assertFalse(getPikachu().equals(getSalamence()));
	}
	
	@Test
	void equals_sameData() {
		Species pikachu1 = getPikachu();
		Species pikachu2 = getPikachu();
		assertTrue(pikachu1.equals(pikachu2));
	}
	
	@Test
	void getName() {
		assertEquals("Pikachu", getPikachu().getName());
		assertEquals("Salamence", getSalamence().getName());
	}

	@Test
	void getType1() {
		assertEquals(PkType.ELECTRIC, getPikachu().getType1());
		assertEquals(PkType.DRAGON, getSalamence().getType1());
	}
	
	@Test
	void getType2() {
		assertEquals(PkType.FLYING, getSalamence().getType2());
	}
	
	@Test
	void getType2_null() {
		assertNull(getPikachu().getType2());
	}
	
	@Test
	void getBaseStat_maxHP() {
		assertEquals(35, getPikachu().getBaseStat(Species.BaseStat.BaseHP));
		assertEquals(95, getSalamence().getBaseStat(Species.BaseStat.BaseHP));
	}
}
