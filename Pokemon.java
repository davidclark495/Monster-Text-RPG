package pokemon;

import java.util.Random;

import audio.SoundPlayer;
import io.StandardIO;

public class Pokemon {

	private static final double expNextScaleFactor = 1.25;
	private static final int defaultHPIncrease = 3, defaultATKIncrease = 1, defaultDEFIncrease = 1;

	private String name, nickname;
	private PkType type;
	private int maxHp, hp;
	private int level, exp, expNextLvl;// 5 is the starting level
	private int atk = 30, def = 30;// 30 is the default
	private MovesTracker movesTracker;
	private Trainer trainer;


	public Pokemon() {
		this("Pokemon", PkType.normal, 100);
	}

	public Pokemon(String nm, PkType type) {
		this(nm, type, 100);
	}

	//base constructor, called by all others
	public Pokemon(String nm, PkType type, int maxHp) {
		this.name = nm;
		this.nickname = nm;
		this.type = type;
		this.maxHp = this.hp = maxHp;
		movesTracker = new MovesTracker();
		level = 5;
		exp = 0;
		expNextLvl = 50;
	}

	public Pokemon(String nm, PkType type, int maxHp, Attack[] moves) {
		this(nm, type, maxHp);
		this.teachAllMoves(moves);
	}

	public Pokemon(String nm, PkType type, int maxHp, Attack[] moves, int level) {
		this(nm, type, maxHp);
		this.teachAllMoves(moves);
		setLevel(level);
	}

	public Pokemon(Pokemon poke) {
		this(poke.getName(), poke.getType(), poke.getMaxHp());
		this.movesTracker = new MovesTracker(poke.getMovesTracker());
		level = poke.level;
		expNextLvl = poke.expNextLvl;
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
		if( isFainted() ) {// can't attack if fainted
			StandardIO.println("* " + nickname + " is fainted. " + nickname + " couldn't attack.\n");
			return;
		}
		if(movesTracker.getMove(index).getCurrentPP() <= 0) {// can't attack if out of PP
			StandardIO.println("* " + nickname + " is out of PP for that move. They couldn't attack.\n");
			return;
		}
		
		// prints that the pokemon is attacking
		Attack move = movesTracker.getMove(index);
		String message = "* " + nickname + " used " + move.getName() + " against " + other.getNickname() + ".";
		StandardIO.println(message);

		// handle effects of using the move
		if(move.getAudioPath() != null) 
			SoundPlayer.playSound(move.getAudioPath());
		move.decrementPP();
		other.getAttacked(move, this);



	}

	/**
	 * Makes this pokemon respond to being hit. Updates hp based on the base damage of the attack.
	 * baseDamage is the damage defined by the attack
	 * netDamage is the damage multiplied by modifiers (i.e. type-relationships)
	 * order that modifiers are applied:
	 * 		type-relation
	 * 		STAB
	 * 		attacker's atk stat
	 * 		defender's def stat
	 * 
	 * @param move The attack that this pokemon is being hit with
	 * @param other The attacking pokemon
	 */
	public void getAttacked(Attack move, Pokemon other) {
		String typeMatchupSummary = "", dmgSummary = "", finalSummary = "";
		int baseDamage, netDamage;

		// set the base damage of the attack
		baseDamage = move.getDamage();
		netDamage = baseDamage;

		// calculate type-relation modifiers on the net damage 
		if( type.isWeakTo(move.getType()) ) {
			netDamage *= 2;
			typeMatchupSummary = "* It was super effective!\n";
		}
		else if( type.isResistantTo(move.getType()) ) {
			netDamage /= 2;
			typeMatchupSummary = "* It wasn't very effective...\n";
		}
		
		// calculate stab
		if( move.getType().equals(other.getType()))
			netDamage *= 1.5;
		
		// calculate atk/def modifiers on net damage
		netDamage *= other.atk;
		netDamage /= this.def;

		// respond to the final damage dealt
		takeDamage(netDamage);
		dmgSummary = "* " + nickname + " took " + netDamage + " damage.\n";

		// build a summary of what happened (dmg, typeMatchup)
		finalSummary = dmgSummary + typeMatchupSummary;
		StandardIO.println(finalSummary);
	}







	// print methods //
	/**
	 * @return a summary of this Pokemon
	 */
	public String toString() {
		String message = "";
		message += nickname + ":\t[" + type + "]\t" + hp + "/" + maxHp + " hp\n";
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
				+ "\nATK: " + atk
				+ "\nDEF: " + def
				+ "\nLevel: " + level;
		StandardIO.println(message);
	}

	/**
	 * Prints the values that are likely to change over the course of a battle.
	 */
	public void printStatus() {
		String message = "Current Status:"
				+ "\nName: " + name
				+ "\nCurrent HP: " + hp
				+ "\n";
		StandardIO.println(message);
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
	 * @return True if the pokemon has 0 hp
	 */
	public boolean isFainted() {
		if(this.hp == 0) {			
			return true;
		}
		
		return false;
	}


	// levels, exp, and stat increases
	/** 
	 * 
	 * @return the amount of exp this pokemon drops when defeated
	 */
	public int getExpDropped() {
		double lvlMult = 10;
		double maxHpMult = 0.2;

		int expDropped = (int)(level*lvlMult + maxHp*maxHpMult);
		return expDropped;
	}
	/**
	 * adds an amount of exp to this pokemon, levels up as many times as needed
	 * 
	 * @param expAmount The amount of exp that this pokemon gains
	 */
	public void addExp(int expAmount) {
		exp += expAmount;
		while(exp > expNextLvl) {
			exp -= expNextLvl;
			levelUp();
		}
	}
	private void levelUp() {
		// update level, increase exp until next level, do not modify current exp
		level++;
		expNextLvl *= expNextScaleFactor;
		// calculate stat increases
		Random rng = new Random();
		int hpInc = rng.nextInt(3) + defaultHPIncrease;
		int atkInc = rng.nextInt(3) + defaultATKIncrease;
		int defInc = rng.nextInt(3) + defaultDEFIncrease;
		// update stats
		maxHp += hpInc;
		hp += hpInc;
		atk += atkInc;
		def += defInc;
	}
	public int getCurrentExp() {
		return exp;
	}
	public int getExpNextLevel() {
		return expNextLvl;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
		this.exp = 0;		
		expNextLvl *= Math.pow(expNextScaleFactor, (level-5) );
		// scale relevant stats to match the appropriate level
		maxHp += (level-5)*defaultHPIncrease;
		setHp(maxHp);
		atk += (level-5)*defaultATKIncrease;
		def += (level-5)*defaultDEFIncrease;
	}


	public int getATK() {
		return atk;
	}

	public void setATK(int atk) {
		this.atk = atk;
	}

	public int getDEF() {
		return def;
	}

	public void setDEF(int def) {
		this.def = def;
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
		StandardIO.println( movesTracker.toString() );
	}
	public String getAllMovesString() {
		return movesTracker.toString();
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

	public void restoreAllPP() {
		movesTracker.restoreAllPP();
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
				moves[numMoves] = new Attack(newMove);
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
					moves[numMoves] = new Attack(newMoves[i]);
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
	
		private void restoreAllPP() {
			for(int i = 0; i < numMoves; i++) {
				moves[i].setCurrentPP(moves[i].getMaxPP());
			}
		}
	}
	
	


}
