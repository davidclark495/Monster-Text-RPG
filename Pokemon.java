package pokemon;

public class Pokemon {

	private String name;
	private PkType type;
	private int maxHp, hp;// hp represents the current health of the pokemon
	private boolean isFainted;
	//private int attack, defense;
	//private int level;
	private MovesTracker movesTracker;


	public Pokemon() {
		this("Pokemon", PkType.NORMAL, 100);
	}

	public Pokemon(String nm, PkType type, int maxHp) {
		this.name = nm;
		this.type = type;
		this.maxHp = this.hp = maxHp;
		isFainted = false;
		movesTracker = new MovesTracker();
	}

	//	public Pokemon(String nm, PkType type, int maxHp, int atk, int def) {
	//		this.name = nm;
	//		this.type = type;
	//		this.maxHp = maxHp;
	//		
	//	}








	// battle methods //
	/**
	 * Uses a basic attack on the other pokemon. Calls the other pokemon's getAttacked() method.
	 * This pokemon cannot attack if fainted.
	 * 
	 * @param other The pokemon that this is attacking
	 */
	public void attackBasic(Pokemon other) {
		if( ! isFainted ) {
			String message = "* " + name + " attacked " + other.getName() + ".\n";
			System.out.println(message);

			other.getAttacked(Attack.BASIC_ATTACK);
		}
	}

	/**
	 * Uses the attack at moves[index] on the other pokemon. Calls the other pokemon's getAttacked() method.
	 * This pokemon cannot attack if fainted.
	 * "index" must be between 0 and 3 (inclusive).
	 * If no move exists at moves[index], an exception will be thrown.
	 * 
	 * @param other The pokemon that this is attacking
	 */
	public void attack(Pokemon other, int index) {
		if( ! isFainted ) {

			Attack move = movesTracker.getMove(index);
			String message = "* " + name + " used " + move.getName() + " against " + other.getName() + ".\n";
			System.out.println(message);

			other.getAttacked(move);


		}
	}

	/**
	 * Makes this pokemon respond to being hit. Updates hp based on the base damage of the attack.
	 * 
	 * @param attack The attack that this pokemon is being hit with.
	 */
	public void getAttacked(Attack attack) {
		int netDamage = attack.getDamage();
		takeDamage(netDamage);

		String message = "* " + name + " took " + netDamage + " damage.\n";
		System.out.println(message);
	}







	// print methods //
	/**
	 * @return a summary of this Pokemon
	 */
	public String toString() {
		String message = "";
		message += name + " is a " + type + " type and has " + maxHp + " max HP. Current HP is " + hp + ".\n";
		return message;
	}

	/**
	 * Prints the (relatively) permanent values of this pokemon
	 */
	public void printStatistics() {
		String message = "Statistics:"
				+ "\nName: " + name 
				+ "\nType: " + type
				+ "\nMax HP: " + maxHp
				+ "\n";
		System.out.println(message);
	}

	/**
	 * Prints the values that are likely to change over the course of a battle.
	 */
	public void printStatus() {
		String message = "Current Status:"
				+ "\nName: " + name
				+ "\nCurrent HP: " + hp
				+ "\n";
		System.out.println(message);
	}








	// getters and setters //
	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getHp() {
		return hp;
	}

	/**
	 * Sets the current hp value to the given value.
	 * If the new value is greater than the max hp, the current hp value will be set to the max hp.
	 * If the new value is less than 0, the current hp value will be set to 0.
	 * Updates "isFainted" as needed.
	 * 
	 * @param hp The new hp value
	 */
	public void setHp(int hp) {
		this.hp = hp;

		if(this.hp > maxHp) {	
			this.hp = maxHp;
		}

		if(this.hp <= 0) {			
			this.hp = 0;
			isFainted = true;
		}
		else if(this.hp > 0) {
			isFainted = false;
		}

	}

	/**
	 * Decreases the current hp by the specified amount. 
	 * 
	 * @param deltaHp
	 */
	public void takeDamage(int deltaHp) {
		setHp(this.hp - deltaHp);
	}

	/**
	 * Increases the current hp by the specified amount. 
	 * 
	 * @param deltaHp
	 */
	public void heal(int deltaHp) {
		setHp(this.hp + deltaHp);
	}

	public String getName() {
		return name;
	}

	public PkType getType() {
		return type;
	}

	/**
	 * 
	 * @return True if the pokemon is fainted/incapacitated
	 */
	public boolean isFainted() {
		return isFainted;
	}





	// moves //
	/**
	 * 
	 * @param index
	 * @return the attack at moves[index]
	 */
	public Attack getMove(int index) {
		return movesTracker.getMove(index);
	}

	/**
	 * 
	 * @param move
	 * @return
	 */
	public boolean teachMove(Attack move) {
		return movesTracker.addMove(move);
	}

	public void getAllMoves() {
		System.out.println( movesTracker.toString() );
	}











	////////////////// Inner Classes ////////////////////////////////////

	private class MovesTracker{

		private Attack[] moves;
		//private int[] ppTracker;
		private int numMoves;

		private MovesTracker() {
			moves = new Attack[4];
			//ppTracker = new int[4];
			numMoves = 0;
		}


		/**
		 * returns a list of available moves
		 */
		public String toString() {
			String str = "Available Moves: " + numMoves + "\n";
			for(int i = 0; i < numMoves; i++) {
				str += i + " - " + moves[i] + "\n";
			}
			return str;
		}



		// getters and setters //
		/**
		 * Returns the move at moves[index].
		 * If no such move exists, returns a basic attack.
		 * 
		 * @param index
		 * @return the attack at moves[index]
		 */
		private Attack getMove(int index) {
			try{
				return moves[index];
			}catch(Exception e) {
				return Attack.BASIC_ATTACK;
			}
		}

		/**
		 * Add a move to the end of the pokemon's move list.
		 * Will fail if the pokemon already knows 4 moves.
		 * Updates numMoves to be the length of moves.
		 * 
		 * @param newMove
		 * @return
		 */
		private boolean addMove(Attack newMove) {
			if(numMoves < 4) {
				moves[numMoves] = newMove;
				numMoves++;
				return true;
			}

			return false;
		}
	}


}
