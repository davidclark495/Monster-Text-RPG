package pokemon;

public class Attack {

	private String name;
	private int baseDamage;
	private PkType type;
	
	public Attack() {
		this("Basic Attack", 10, PkType.NORMAL);
	}
	
	public Attack(String name, int dmg, PkType type) {
		this.name = name;
		this.baseDamage = dmg;
		this.type = type;
	}

	
	
	public String toString() {
		String str = name + ": " + baseDamage + " dmg (" + type + ")";
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
	
	

}
