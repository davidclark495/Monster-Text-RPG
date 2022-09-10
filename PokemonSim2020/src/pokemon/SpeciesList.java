package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeciesList {

	private static Map<String, Species> allSpecies = new HashMap<>();
		
	static {
		readSpeciesData();
		readLearnsetData();		// req's SpeciesData, MovesData (Movelist)
		readEvolutionData();	// req's SpeciesData, ItemsData (ItemList)
	}
	
	public static Species getSpecies(String name) {		
		return allSpecies.get(name);
	}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// tester method, should not be public
	public static Map<String, Species> getAllSpecies(){
		return allSpecies;
	}


	// ///////// //
	// Read CSVs //
	// ///////// //

	/*
	 * Gathers data about all available pokemon Species from a CSV. 
	 */
	private static void readSpeciesData() {
		final String COMMA_DELIMITER = ",";
		
		// Read CSV into an ArrayList
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("csv/pokemon.csv"))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) { 
        	e.printStackTrace(); 
        }
        
        // Move data from ArrayList to purpose-built code
        List<Species> speciesList = new ArrayList<>();
        for(int i = 1; i < records.size(); i++) {
        	List<String> list = records.get(i);
        	
        	int dexNo = Integer.parseInt(list.get(1));
        	String species = list.get(2);
        	String t1Text = list.get(3);
        	PkType t1 = PkType.valueOf(t1Text);
        	String t2Text = list.get(4);
        	PkType t2;
        	if(t2Text.equals("")) {
        		t2 = null;
        	} else {
        		t2 = PkType.valueOf(t2Text);        		
        	}
//        	int baseTot = Integer.parseInt(list.get(5));
        	int baseHP = Integer.parseInt(list.get(6));
        	int baseATK = Integer.parseInt(list.get(7));
        	int baseDEF = Integer.parseInt(list.get(8));
        	int baseSpATK = Integer.parseInt(list.get(9));
        	int baseSpDEF = Integer.parseInt(list.get(10));
        	int baseSPD = Integer.parseInt(list.get(11));

        	speciesList.add(new Species(species, t1, t2, baseHP, 
        			baseATK, baseDEF, baseSpATK, baseSpDEF, baseSPD));
        }
        
        for(Species spec : speciesList) {
        	allSpecies.put(spec.getName(), spec);
        }
	}
	
	/*
	 * Gathers data about all species' learnsets from a CSV.
	 */
	private static void readLearnsetData() {
		final String COMMA_DELIMITER = ",";
		
		// Read CSV into an ArrayList
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("csv/pokemon_learnsets.csv"))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) { 
        	e.printStackTrace(); 
        }
        
        for(int i = 1; i < records.size(); i++) {
        	List<String> list = records.get(i);
        	String speciesStr = list.get(1);
        	int level = Integer.parseInt(list.get(2));
        	String moveStr = list.get(3);
        	
        	Species species = getSpecies(speciesStr);
        	Move move = MoveList.getMove(moveStr);
        	species.addMoveToLearnset(level, move);
        }
	}
	

	private static void readEvolutionData() {
		final String COMMA_DELIMITER = ",";
		
		// Read CSV into an ArrayList
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("csv/evolutions.csv"))){
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (Exception e) { 
        	e.printStackTrace(); 
        }
        
        for(int i = 1; i < records.size(); i++) {
        	List<String> list = records.get(i);
        	
        	Species specFrom = getSpecies(list.get(0));	
        	Species specInto = getSpecies(list.get(1));	
        	int evolvesAtLvl = Integer.parseInt(list.get(2));
//        	Item heldItem = ItemList.getItem(list.get(3));
        	boolean reqsFriendship = list.get(4).equals("YES");
        	boolean reqsTrade = list.get(5).equals("YES");
//        	Pokemon.Gender genderReqd = Pokemon.Gender.valueof(list.get(6));
        	
        	EvolutionLock evoLock = new EvolutionLock(specInto, evolvesAtLvl, 
        			null, reqsFriendship, reqsTrade);
        	
        	specFrom.addEvolution(evoLock);
        }
	}

	
}
