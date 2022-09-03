package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpeciesList {

	public static SpeciesList getSpeciesList() {
		return speciesList;
	}
	
	public Species getSpecies(String name) {
		return allSpecies.get(name);
	}
		
	

	private static SpeciesList speciesList = new SpeciesList();
	
	private Map<String, Species> allSpecies;
	
	private SpeciesList() {
		readSpeciesData();
		readLearnsetData();
	}
	

	/*
	 * Gathers data about all available pokemon Species from a CSV. 
	 */
	private void readSpeciesData() {
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
        List<Species> allSpecies = new ArrayList<>();
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
        	int baseHP = Integer.parseInt(list.get(5));
        	int baseATK = Integer.parseInt(list.get(6));
        	int baseDEF = Integer.parseInt(list.get(7));
        	int baseSpATK = Integer.parseInt(list.get(8));
        	int baseSpDEF = Integer.parseInt(list.get(9));
        	int baseSPD = Integer.parseInt(list.get(10));

        	allSpecies.add(new Species(species, t1, t2, baseHP, 
        			baseATK, baseDEF, baseSpATK, baseSpDEF, baseSPD));
        }
	}
	
	/*
	 * Gathers data about all species' learnsets from a CSV.
	 */
	private void readLearnsetData() {
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
        
        for(int i = 1; i < records.size(); i++) {
        	List<String> list = records.get(i);
        	String speciesStr = list.get(1);
        	int level = Integer.parseInt(list.get(2));
        	String moveStr = list.get(3);
        	
        	Species species = getSpeciesList().getSpecies(speciesStr);
        	Move move = MoveList.getMoveList().getMove(moveStr);
        	species.addMoveToLearnset(level, move);
        }
	}
	
}
