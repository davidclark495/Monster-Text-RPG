package z_tests_locations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import io.StandardIO;
import location.WorldMap;
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
	
}
