package pokemon;

public class Pokemon {
	
	private String name, nickname;
	private PkType type;
	private int maxHp, hp;
	private boolean isFainted;
	private MovesTracker movesTracker;
	private Trainer trainer;


	public Pokemon() {
		this("Pokemon", PkType.normal, 100);
	}

	public Pokemon(String nm, PkType type) {
		this(nm, type, 100);
	}
	
	public Pokemon(String nm, PkType type, int maxHp) {
		this.name = nm;
		this.nickname = nm;
		this.type = type;
		this.maxHp = this.hp = maxHp;
		isFainted = false;
		movesTracker = new MovesTracker();
	}
	
	public Pokemon(String nm, PkType type, int maxHp, Attack[] moves) {
		this(nm, type, maxHp);
		this.teachAllMoves(moves);
	}
	
	public Pokemon(Pokemon poke) {
		this(poke.getName(), poke.getType(), poke.getMaxHp());
		this.movesTracker = new MovesTracker(poke.getMovesTracker());
	}

	
	
	
	
	
	/**
	 * 
	 * @param other
	 * @return true if both have the same name
	 */
	public boolean sameSpecies(Pokemon other) {
		return name.equals(other.name);
	}



	// battle methods //
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
			String message = "* " + nickname + " used " + move.getName() + " against " + other.getNickname() + ".";
			System.out.println(message);

			other.getAttacked(move);


		}
	}

	/**
	 * Makes this pokemon respond to being hit. Updates hp based on the base damage of the attack.
	 * baseDamage is the damage defined by the attack
	 * netDamage is the damage multiplied by modifiers (i.e. type-relationships)
	 * 
	 * @param attack The attack that this pokemon is being hit with.
	 */
	public void getAttacked(Attack attack) {
		String typeMatchupSummary = "", dmgSummary = "", finalSummary = "";
		int baseDamage, netDamage;
		
		// set the base damage of the attack
		baseDamage = attack.getDamage();
		
		// calculate modifiers on the net damage (type
		if( type.isWeakTo(attack.getType()) ) {
			netDamage = baseDamage * 2;
			typeMatchupSummary = "* It was super effective!\n";
		}
		else if( type.isResistantTo(attack.getType()) ) {
			netDamage = baseDamage / 2;
			typeMatchupSummary = "* It wasn't very effective...\n";
		}
		else {
			netDamage = baseDamage;
		}
		
		// respond to the final damage dealt
		takeDamage(netDamage);
		dmgSummary = "* " + nickname + " took " + netDamage + " damage.\n";
		
		// build a summary of what happened (dmg, typeMatchup)
		finalSummary = dmgSummary + typeMatchupSummary;
		System.out.println(finalSummary);
	}







	// print methods //
	/**
	 * @return a summary of this Pokemon
	 */
	public String toString() {
		String message = "";
		message += nickname + ": [" + type + "] " + hp + "/" + maxHp + " hp\n";
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
	/**
	 * 
	 * @return the pokemon's maximum health
	 */
	public int getMaxHp() {
		return maxHp;
	}

	/**
	 * Sets the pokemon's maxHp to a new value.
	 * If the new maxHp is lower than the current hp, sets hp to the maxHp
	 * 
	 * @param maxHp The pokemon's new maxHp
	 */
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
		if(hp > maxHp) {
			hp = maxHp;			
		}
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

	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	
	/**
	 * 
	 * @param move
	 * @return
	 */
	public boolean teachAllMoves(Attack[] moves) {
		return movesTracker.addAllMoves(moves);
	}

	public void printAllMoves() {
		System.out.println( movesTracker.toString() );
	}
	

	public int getNumMoves() {
		return movesTracker.getNumMoves();
	}

	public Attack[] getAllMoves() {
		return movesTracker.getAllMoves();
	}
	
	public MovesTracker getMovesTracker() {
		return movesTracker;
	}
	
	/**
	 * 
	 * @return the trainer that owns this pokemon
	 */
	public Trainer getTrainer() {
		return trainer;
	}

	/**
	 * Removes this pokemon's reference to a trainer, makes it "wild"
	 */
	public void releaseFromTrainer() {
		trainer = null;
	}
	
	/**
	 * Makes newTrainer the owner of this pokemon.
	 * Fails if this pokemon already has a trainer.
	 */
	public boolean setNewTrainer(Trainer newTrainer) {
		if( trainer == null ) {
			trainer = newTrainer;
			return true;
		}
		return false;
	}








	////////////////// Inner Classes ////////////////////////////////////

	private class MovesTracker{

		private static final int MAX_NUM_MOVES = 4;
		
		private Attack[] moves;
		private int numMoves;

		private MovesTracker() {
			moves = new Attack[MAX_NUM_MOVES];
			numMoves = 0;
		}
		
		private MovesTracker(Attack[] moves) {
			moves = new Attack[MAX_NUM_MOVES];
			numMoves = 0;
			this.addAllMoves(moves);
		}
		
		private MovesTracker(MovesTracker mvsTrckr) {
			moves = mvsTrckr.getAllMoves();
			numMoves = mvsTrckr.getNumMoves();
		}


		/**
		 * 
		 * @return a numbered list of available moves
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
		 * Returns null if the pokemon has no move at that position.
		 * If no such move exists, returns null.
		 * 
		 * @param index
		 * @return the attack at moves[index]
		 */
		private Attack getMove(int index) {
			try{
				return moves[index];
			}catch(Exception e) {
				return null;
			}
		}
		
		/**
		 * 
		 * @return an array of all this pokemon's moves.
		 */
		private Attack[] getAllMoves() {
			return moves;
		}

		/**
		 * Add a move to the end of the pokemon's move list.
		 * Will fail if the pokemon already knows the maximum number of moves.
		 * Updates numMoves to be the length of moves.
		 * 
		 * @param newMove
		 * @return true if move was added, false if move couldn't be added
		 */
		private boolean addMove(Attack newMove) {
			if(numMoves < MAX_NUM_MOVES) {
				moves[numMoves] = newMove;
				numMoves++;
				return true;
			}
			return false;
		}
		
		/**
		 * Add the moves in the array to the pokemon's move list.
		 * Will fail if the pokemon doesn't have enough space to learn each move.
		 * Updates numMoves to be the length of moves.
		 * 
		 * @param newMoves An array of Attacks to teach the pokemon
		 * @return true if moves were added, false if moves couldn't be added
		 */
		private boolean addAllMoves(Attack[] newMoves) {
			if(newMoves.length + numMoves <= MAX_NUM_MOVES) {
				for(int i = 0; i < newMoves.length; i++) {
					moves[numMoves] = newMoves[i];
					numMoves++;
				}
			}
			return false;
		}
		
		/**
		 * 
		 * @return the number of moves currently known
		 */
		private int getNumMoves() {
			return numMoves;
		}
	}


}
