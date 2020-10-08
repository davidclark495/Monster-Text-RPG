package pokemon;

public class PkType {

	public static PkType 
			normal = new PkType("NORMAL"), 
			grass = new PkType("GRASS"), 
			fire = new PkType("FIRE"), 
			water = new PkType("WATER"),
			electric = new PkType("ELECTRIC"),
			flying = new PkType("FLYING"),
			rock = new PkType("ROCK");
	
	static {
			normal.setWeaknesses(null);
			normal.setResistances(null);
			grass.setWeaknesses(new PkType[] {PkType.fire, PkType.flying});
			grass.setResistances(new PkType[] {PkType.water, PkType.grass});
			fire.setWeaknesses(new PkType[] {PkType.water});
			fire.setResistances(new PkType[] {PkType.grass, PkType.fire});
			water.setWeaknesses(new PkType[] {PkType.grass, PkType.electric});
			water.setResistances(new PkType[] {PkType.fire, PkType.water});
			electric.setWeaknesses(null);
			electric.setResistances(new PkType[] {PkType.water});
			flying.setWeaknesses(new PkType[] {PkType.electric, PkType.rock});
			flying.setResistances(new PkType[] {PkType.grass});
			rock.setWeaknesses(new PkType[] {PkType.grass, PkType.water});
			rock.setResistances(new PkType[] {PkType.normal, PkType.fire, PkType.flying});
	}
	
	private String typeName;
	private PkType[] weaknesses;
	private PkType[] resistances;
	
	private PkType(String typeName) {
		this.typeName = typeName;
	}
	
	private PkType(String typeName, PkType[] weak, PkType[] resist) {
		this(typeName);
		this.weaknesses = weak;
		this.resistances = resist;
	}

	/**
	 * Returns true if the attacking type exists in this type's weaknesses
	 * 
	 * @param atkType 
	 * @return true if this type is weak to the attacking type
	 */
	public boolean isWeakTo(PkType atkType) {
		if(this.weaknesses == null)
			return false;
		for(int i = 0; i < this.weaknesses.length; i++) {
			if(weaknesses[i].equals(atkType)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if the attacking type exists in this type's resistances
	 * 
	 * @param atkType 
	 * @return true if this type is resistant to the attacking type
	 */
	public boolean isResistantTo(PkType atkType) {
		if(this.resistances == null)
			return false;
		for(int i = 0; i < this.resistances.length; i++) {
			if(resistances[i].equals(atkType)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If the types have the same name, then they are equal
	 * @return 
	 */
	public boolean equals(PkType other) {
		return this.typeName == other.typeName;
	}
	
	/**
	 * @return the name of the type
	 */
	public String toString() {
		return typeName;
	}
	
	/**
	 * @return the name of the type
	 */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * Sets this type's weaknesses to the specified values
	 * (for use only in setting-up this class's static Types)
	 * @param weaknesses An array of PkTypes that this type is weak to
	 */
	private void setWeaknesses(PkType[] weaknesses) {
		this.weaknesses = weaknesses;
	}
	
	/**
	 * Sets this type's resistances to the specified values
	 * (for use only in setting-up this class's static Types)
	 * @param resistances An array of PkTypes that this type is resistant to
	 */
	private void setResistances(PkType[] resistances) {
		this.resistances = resistances;
	}
	
	
}
