package z_tests;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import pokemon.PkType;

public class PkTypeTester {

	
	// when comparing doubles, results will be off by no more than TOLERANCE
	private final double TOLERANCE = 0.00001;
	
	
	
	// single-type defender tests
	
	@Test
	void getDmgMultiplier_singleTypeDef_immune() {
		PkType atkr = PkType.NORMAL;
		PkType dfdr = PkType.GHOST;
		double result = PkType.getDmgMultiplier(atkr, dfdr);
		assertEquals(0, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_singleTypeDef_resist() {
		PkType atkr = PkType.NORMAL;
		PkType dfdr = PkType.ROCK;
		double result = PkType.getDmgMultiplier(atkr, dfdr);
		assertEquals(0.5, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_singleTypeDef_default() {
		PkType atkr = PkType.NORMAL;
		PkType dfdr = PkType.NORMAL;
		double result = PkType.getDmgMultiplier(atkr, dfdr);
		assertEquals(1, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_singleTypeDef_superEffective() {
		PkType atkr = PkType.FIGHTING;
		PkType dfdr = PkType.NORMAL;
		double result = PkType.getDmgMultiplier(atkr, dfdr);
		assertEquals(2, result, TOLERANCE);
	}
	
	// double-type defender tests
	
	/**
	 * A pokemon with types A and B should take the same damage as
	 * a pokemon with types B and A, all else being equal.
	 */
	@Test
	void getDmgMultiplier_doubleTypeDef_orderDoesNotMatter() {
		PkType atkr = PkType.GROUND;
		PkType dfdr1 = PkType.STEEL;
		PkType dfdr2 = PkType.FLYING;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		double result_flipped = PkType.getDmgMultiplier(atkr, dfdr2, dfdr1); 
		assertEquals(result, result_flipped, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_doubleTypeDef_immune() {
		PkType atkr = PkType.GROUND;
		PkType dfdr1 = PkType.STEEL;
		PkType dfdr2 = PkType.FLYING;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		assertEquals(0, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_doubleTypeDef_doubleResist() {
		PkType atkr = PkType.GRASS;
		PkType dfdr1 = PkType.FIRE;
		PkType dfdr2 = PkType.DRAGON;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		assertEquals(0.25, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_doubleTypeDef_singleResist() {
		PkType atkr = PkType.GRASS;
		PkType dfdr1 = PkType.ELECTRIC;
		PkType dfdr2 = PkType.FLYING;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		assertEquals(0.5, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_doubleTypeDef_default() {
		PkType atkr = PkType.WATER;
		PkType dfdr1 = PkType.POISON;
		PkType dfdr2 = PkType.FLYING;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		assertEquals(1, result, TOLERANCE);
	}
	
	/**
	 * If one of the defender's types resists the attack, 
	 * but its other type is weak to the attack, 
	 * the result is a 1x (default) multiplier.
	 */
	@Test
	void getDmgMultiplier_doubleTypeDef_defaultViaCancel() {
		PkType atkr = PkType.BUG;
		PkType dfdr1 = PkType.GRASS;
		PkType dfdr2 = PkType.POISON;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		assertEquals(1, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_doubleTypeDef_singleSuperEffective() {
		PkType atkr = PkType.WATER;
		PkType dfdr1 = PkType.NORMAL;
		PkType dfdr2 = PkType.FIRE;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		assertEquals(2, result, TOLERANCE);
	}
	
	@Test
	void getDmgMultiplier_doubleTypeDef_doubleSuperEffective() {
		PkType atkr = PkType.FIRE;
		PkType dfdr1 = PkType.STEEL;
		PkType dfdr2 = PkType.BUG;
		double result = PkType.getDmgMultiplier(atkr, dfdr1, dfdr2);
		assertEquals(4, result, TOLERANCE);
	}
}
