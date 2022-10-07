package z_tests_csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import io.StandardIO;
import location.WorldMap;
import pokemon.EvolutionLock;
import pokemon.Move;
import pokemon.MoveList;
import pokemon.Species;
import pokemon.SpeciesList;

import org.junit.jupiter.api.BeforeEach;


public class CSVTester {

	
//	@Test
	void WorldMap_readLocationData_demo() {		
		System.out.println("-------------");
		System.out.println("ALL LOCATIONS");
		System.out.println("-------------");
		System.out.println();
		System.out.println(WorldMap.getAllLocationsString());
	}

//	@Test
	void WorldMap_readPathData_demo() {
		System.out.println("---------");
		System.out.println("ALL PATHS");
		System.out.println("---------");
		System.out.println();
		System.out.println(WorldMap.getAllPathsString());	
	}
	
//	@Test 
	void WorldMap_readEncounterData_demo() {
		System.out.println("--------------");
		System.out.println("ALL ENCOUNTERS");
		System.out.println("--------------");
		System.out.println();
		System.out.println(WorldMap.getAllEncountersString());
	}
	
//	@Test
	void MoveList_readMoveData_demo() {
		System.out.println("--------------");
		System.out.println("ALL MOVES");
		System.out.println("--------------");
		System.out.println();
		for(Move m : MoveList.getAllMoves().values()) {
			System.out.printf("%16s -   Type: %8s   Cat: %.4s   Pow: %3d   PP: %2d   Acc: %3.2f\n", 
					m.getName(), m.getType(), m.getCategory(), 
					m.getPower(), 
					m.getMaxPP(), m.getAccuracy());
		}
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
	
//	@Test
	void SpeciesList_readEvolutionsData_demo() {
		System.out.println("--------------");
		System.out.println("ALL EVOLUTIONS");
		System.out.println("--------------");
		System.out.println();
		for(Species spec : SpeciesList.getAllSpecies().values()) {
			if(spec.getEvolutions().isEmpty())
				continue;
			
			for(EvolutionLock evoLock : spec.getEvolutions()) {
				System.out.printf("%s --> %s\n", spec.getName(), evoLock.getNewSpecies().getName());
				System.out.printf("\tlevel: %d\n", evoLock.getLevelReqd());
				System.out.printf("\theld-item: %s\n", evoLock.getHeldItemReqd());
				System.out.printf("\tneeds-friendship: %b\n", evoLock.isFriendshipReqd());
				System.out.printf("\tneeds-trade: %b\n", evoLock.isTradeReqd());
				System.out.printf("\tgender-reqd: %s\n", "(not yet implemented)");
			}
			System.out.println();
		}
	}
	
}
