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
		this.basePower = power;
		this.type = type;
		this.maxPP = pp;
		this.accuracy = acc;
//		audiopath = null;
	}



	//	public String toString() {
	//		String str = name + ":\t" + basePower + " dmg\t(" + type + ")";
	//		return str;
	//	}
	public String toString() {
		String str = String.format("%10s: %3d pwr (%s) [%s]", name, basePower, type, category);
		return str;
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
