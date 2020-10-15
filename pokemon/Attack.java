package pokemon;

public class Attack {

	private String name;
	private int baseDamage;
	private PkType type;
	private int maxPP, currentPP;
	private String audiopath;

	public Attack() {
		this("Basic Attack", 10, PkType.normal);
	}

	public Attack(String name, int dmg, PkType type) {
		this(name, dmg, type, 10);

	}

	// base constructor, all others call this
	public Attack(String name, int dmg, PkType type, int maxPP) {
		this.name = name;
		this.baseDamage = dmg;
		this.type = type;
		this.maxPP = maxPP;
		this.currentPP = this.maxPP;
		audiopath = null;
	}

	public Attack(Attack move) {
		this(move.getName(), move.getDamage(), move.getType(), move.getMaxPP());
		this.audiopath = move.audiopath;
	}



	//	public String toString() {
	//		String str = name + ":\t" + baseDamage + " dmg\t(" + type + ")";
	//		return str;
	//	}
	public String toString() {
		String str = name + ":\t"
				+ baseDamage + " pwr | "
				+ String.format("%2d", currentPP) + "/" + String.format("%2d", maxPP) + " pp | "
				+ "(" + type + ")";
		return str;
	}


	// getters and setters //
	public String getName() {
		return name;
	}
	public int getDamage() {
		return baseDamage;
	}
	public PkType getType() {
		return type;
	}
	public int getMaxPP() {
		return maxPP;
	}
	public int getCurrentPP() {
		return currentPP;
	}
	public void setCurrentPP(int pp) {
		currentPP = pp;
	}
	public void decrementPP() {
		currentPP--;
	}
	public String getAudioPath() {
		return audiopath;
	}
	public void setAudioPath(String audiopath) {
		this.audiopath = audiopath;
	}



}
