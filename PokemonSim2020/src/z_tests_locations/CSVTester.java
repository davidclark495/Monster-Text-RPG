package z_tests_locations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import io.StandardIO;
import location.WorldMap;
import pokemon.EvolutionLock;
import pokemon.Species;
import pokemon.SpeciesList;

import org.junit.jupiter.api.BeforeEach;


public class CSVTester {

	
	@Test
	void WorldMap_readLocationData_demo() {		
		System.out.println(WorldMap.getAllLocationsString());
	}

	@Test
	void WorldMap_readPathData_demo() {
		System.out.println(WorldMap.getAllPathsString());	
	}
	
	@Test 
	void WorldMap_readEncounterData_demo() {
		System.out.println(WorldMap.getAllEncountersString());
	}
	
	@Test
	void MoveList_readMoveData_demo() {
		
	}
	
	@Test
	void MoveList_readMoveDetailData_demo() {
		
	}
	
	@Test
	void SpeciesList_readSpeciesData_demo() {
		
	}
	
	@Test
	void SpeciesList_readLearnSetData_demo() {

	}
	
	@Test
	void SpeciesList_readEvolutionsData_demo() {
		for(Species spec : SpeciesList.getAllSpecies().values()) {
			for(EvolutionLock evoLock : spec.getEvolutions()) {
				System.out.printf("%s --> %s\n", spec.getName(), evoLock.getNewSpecies().getName());
				System.out.printf("\tlevel: %d\n", evoLock.getLevelReqd());
				System.out.printf("\theld-item: %s\n", evoLock.getHeldItemReqd());
				System.out.printf("\tneeds-friendship: %b\n", evoLock.isFriendshipReqd());
				System.out.printf("\tneeds-trade: %b\n", evoLock.isTradeReqd());
				System.out.printf("\tgender-reqd: %s\n", "(not yet implemented)");
			}
		}
	}
	
}
