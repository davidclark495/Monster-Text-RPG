package pokemon;

import java.util.Map;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

/**
 * Holds info. pertaining to all pokemon of a species, e.g. all Pikachu.
 * Species with the same name are assumed to be identical.
 * 
 * @author davidclark
 *
 */
public class Species{
	
	public enum BaseStat {
		BaseHP, BaseATK, BaseDEF, BaseSpATK, BaseSpDEF, BaseSPD;
	}
	
	private String name;
	private PkType type1, type2;
	private Map<BaseStat, Integer> baseStats = new EnumMap<>(BaseStat.class);;
	private Map<Integer, List<Move>> learnset = new HashMap<>();
	private List<EvolutionLock> evolutions = new ArrayList<>();

	
	public Species(String name, PkType type1, PkType type2, int baseHP, 
			int baseATK, int baseDEF, int baseSpATK, int baseSpDEF, int baseSPD) 
	{
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;		
		this.baseStats.put(BaseStat.BaseHP, baseHP);
		this.baseStats.put(BaseStat.BaseATK, baseATK);
		this.baseStats.put(BaseStat.BaseDEF, baseDEF);
		this.baseStats.put(BaseStat.BaseSpATK, baseSpATK);
		this.baseStats.put(BaseStat.BaseSpDEF, baseSpDEF);
		this.baseStats.put(BaseStat.BaseSPD, baseSPD);
	}
	
	// MUTATORS
	// needs thorough testing
	public void addMoveToLearnset(int level, Move moveLearned) {
		if(learnset.get(level) == null) {
			ArrayList<Move> movesAtLevelX = new ArrayList<>();
			movesAtLevelX.add(moveLearned);
			learnset.put(level, movesAtLevelX);
		} else {
			learnset.get(level).add(moveLearned);
		}
	}
	
	public void addEvolution(EvolutionLock evoLock) {
		evolutions.add(evoLock);
	}
	
	// ACCESSORS
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Species))
			return false;
		else
			return this.name == ((Species)other).name;
	}
	
	public String getName() {
		return name;
	}

	public PkType getType1() {
		return type1;
	}

	public PkType getType2() {
		return type2;
	}
	
	public int getBaseStat(BaseStat stat){
		return baseStats.get(stat);
	}
	
	public List<Move> getMovesLearnedAtLevel(int level){
		return learnset.get(level);
	}
	
	public List<EvolutionLock> getEvolutions(){
		return evolutions;
	}
	
}