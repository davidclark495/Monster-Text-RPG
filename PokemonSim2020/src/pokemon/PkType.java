package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public enum PkType {
	
	NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE,
	FIGHTING, POISON, GROUND, FLYING, PSYCHIC,
	BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY;
	
	// DEFINE TYPE RELATIONSHIPS
	/* 
	 * For each type, what multiplier does it have 
	 * when attacking each other type?
	 * Could be articulated as: Map<Atkr, Map<Dfdr, Mult>> 
	 */
	private static EnumMap<PkType, EnumMap<PkType, Double>> allAtkrRelations;
	static {
		final String COMMA_DELIMITER = ",";
		
    	// Read CSV into an ArrayList
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("csv/typeRelations.csv"))){
			String line;
			while((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Store relationships in a map of maps
		allAtkrRelations = new EnumMap<PkType, EnumMap<PkType, Double>>(PkType.class);
		List<String> headerRow =  records.get(0);
		for(int atkr = 1; atkr < records.size(); atkr++) {
			EnumMap<PkType, Double> dmgMultipliers = new EnumMap<>(PkType.class);
			
			List<String> list = records.get(atkr);
			for(int dfdr = 1; dfdr < list.size(); dfdr++) {
				String dfdrStr = headerRow.get(dfdr);
				PkType dfdrType = PkType.valueOf(dfdrStr);
				double multiplier = Double.parseDouble(list.get(dfdr));
				dmgMultipliers.put(dfdrType, multiplier);
			}
			
			PkType atkrType = PkType.valueOf(records.get(atkr).get(0));
			allAtkrRelations.put(atkrType, dmgMultipliers);
		}
	}
	
	
	/**
	 * Returns the type-based damage multiplier btwn the attacking and defending types
	 * @param atkr
	 * @param dfdr
	 * @return
	 */
	public static double getDmgMultiplier(PkType atkr, PkType dfdr) {
		return allAtkrRelations.get(atkr).get(dfdr);
	}
	
	/**
	 * Returns the type-based damage multiplier btwn the attacking and defending types
	 * @param atkr
	 * @param dfdr1
	 * @param dfdr1
	 * @return
	 */
	public static double getDmgMultiplier(PkType atkr, PkType dfdr1, PkType dfdr2) {
		return allAtkrRelations.get(atkr).get(dfdr1) 
				* allAtkrRelations.get(atkr).get(dfdr2);
	}
	
	/**
	 * Returns true if this type is weak to the attacking type
	 * 
	 * @param atkrType 
	 * @return true if this type is weak to the attacking type
	 */
	public boolean isWeakTo(PkType atkrType) {
		PkType dfdrType = this;
		if(allAtkrRelations.get(atkrType).get(dfdrType) == 2)
			return true;
		
		return false;
	}
	
	
	/**
	 * Returns true if the attacking type exists in this type's resistances
	 * 
	 * @param atkrType 
	 * @return true if this type is resistant to the attacking type
	 */
	public boolean isResistantTo(PkType atkrType) {
		PkType dfdrType = this;
		if(allAtkrRelations.get(atkrType).get(dfdrType) == 0.5)
			return true;
		
		return false;
	}
}
