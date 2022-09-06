package pokemon;

/**
 * Defines a Move. Immutable.
 *
 * @author davidclark
 *
 */
public class Move {

	// presentation
//	private String audiopath;
	// function
	private String name;
	private PkType type;
	private Category category;
	private int basePower;
	private int maxPP;
	private double accuracy;

	public Move(String name, PkType type, Category category, int power, int pp, double acc) {
		this.name = name;
		this.type = type;
		this.category = category;
		this.basePower = power;
		this.maxPP = pp;
		this.accuracy = acc;
//		audiopath = null;
	}



	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Move))
			return false;
		return this.name.equals(((Move)other).name);
	}
	
	public String toString() {
		if(category == Category.STATUS)
			return String.format("%10s: \t\t (%s) \t [%s]", name, type, category);
		
		return String.format("%10s: %3d pwr \t (%s) \t [%s]", name, basePower, type, category);
	}
	


	// getters and setters //
	public String getName() {
		return name;
	}
	public PkType getType() {
		return type;
	}
	public Category getCategory() {
		return category;
	}
	public int getPower() {
		return basePower;
	}
	public int getMaxPP() {
		return maxPP;
	}
	public double getAccuracy() {
		return accuracy;
	}
	
//	public String getAudioPath() {
//		return audiopath;
//	}
//	public void setAudioPath(String audiopath) {
//		this.audiopath = audiopath;
//	}

	
	public enum Category {
		PHYSICAL, SPECIAL, STATUS;
	}

}
