package pokemon;

import java.util.Random;

import audio.SoundPlayer;
import io.StandardIO;

public class Pokemon {

	private static final double expNextScaleFactor = 1.25;
	//private static final int defaultHPIncrease = 2, defaultATKIncrease = 1, defaultDEFIncrease = 1;

	// presentation
	private String name, nickname, cry;
	private String asciiFilePath;
	// code objects
	private PkType type;
	private MovesTracker movesTracker;
	private Trainer trainer;
	// stats
	private int maxHp, hp;
	private int level = 5, exp = 0, expNextLvl = 50;
	private int atk = 10, def = 10;
	private int hpMod = 2, atkMod = 1, defMod = 1;

	public Pokemon() {
		this("Pokemon", PkType.normal, 100);
	}

	public Pokemon(String nm, PkType type) {
		this(nm, type, 100);
	}

	//base constructor, called by all others (excludes clone-type constructors)
	public Pokemon(String nm, PkType type, int maxHp) {
		this.name = nm;
		this.nickname = nm;
		this.type = type;
		this.maxHp = this.hp = maxHp;
		movesTracker = new MovesTracker();
		// sets a default file path, may be wrong
		setAsciiFilePath("res/ascii_art/" + nm.toLowerCase() + "_80.txt");
		cry = "";
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

	//independently assigns many values
	public Pokemon(Pokemon poke) {
		this(poke.getName(), poke.getType(), poke.getMaxHp());
		this.movesTracker = new MovesTracker(poke.getMovesTracker());
		level = poke.level;
		expNextLvl = poke.expNextLvl;
		this.setStatBlock(poke.maxHp, poke.atk, poke.def, poke.hpMod, poke.atkMod, poke.defMod, poke.level);
		this.setAsciiFilePath(poke.getAsciiFilePath());
		this.cry = poke.cry;
	}

	public Pokemon(Pokemon poke, int level) {
		this(poke);
		setLevel(level);
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
	 * Uses the specified attack on the other pokemon. The other pokemon is
	 * updated accordingly.
	 * 
	 * Attacker:
	 * Uses the attack at moves[index] on the other pokemon. Calls the other pokemon's getAttacked() method.
	 * This pokemon cannot attack if fainted.
	 * "index" must be between 0 and 3 (inclusive).
	 * If no move exists at moves[index], an exception will be thrown.
	 * 
	 * Defender:
	 * Makes this pokemon respond to being hit. Updates hp based on the base damage of the attack.
	 * baseDamage is the damage defined by the attack
	 * netDamage is the damage multiplied by modifiers (i.e. type-relationships)
	 * order that modifiers are applied:
	 * 		base pwr & constants
	 * 		type-relation
	 * 		STAB
	 * 		attacker's atk stat
	 * 		defender's def stat
	 * 		random variance
	 * 
	 * @param defender The pokemon that this is attacking
	 */
	public String attack(Pokemon defender, int index) {
		
		// ATTACKER //
		if( isFainted() ) {// can't attack if fainted
			return String.format("* %s is fainted. %s couldn't attack.\n", this.nickname, this.nickname);
		}
		if(movesTracker.getMove(index).getCurrentPP() <= 0) {// can't attack if out of PP
			return String.format("* %s is out of PP for that move. They couldn't attack.\n", this.nickname);
		}
		
		Attack move = movesTracker.getMove(index);
		
		// gets a string summarizing the action
		String atkSummary = String.format("* %s used %s against %s.\n", this.nickname, move.getName(), defender.getNickname());

		// handle effects of using the move
//		if(move.getAudioPath() != null) 
//			SoundPlayer.playSound(move.getAudioPath());
		move.decrementPP();

		
		// DEFENDER //
		double typeAdvantageConst = 1.5;
		
		String typeMatchupSummary = "", dmgSummary = "", finalDefSummary = "";
		int netDamage;

		// set the base damage of the attack, apply constants
		netDamage = move.getDamage();
		netDamage = (int)Math.ceil(netDamage*0.15);

		// calculate type-relation modifiers on the net damage 
		if( PkType.pokeIsWeakTo(defender, move.getType()) ) {
			netDamage *= typeAdvantageConst;
			typeMatchupSummary = "* It was super effective!\n";
		}
		else if( PkType.pokeIsResistantTo(defender, move.getType()) ) {
			netDamage /= typeAdvantageConst;
			typeMatchupSummary = "* It wasn't very effective...\n";
		}
		
		// calculate STAB
		if( move.getType().equals(this.getType()) )
			netDamage *= 1.5;
		
		// calculate atk/def modifiers on net damage
		netDamage *= (this.atk + 20);
		netDamage /= (defender.def + 20);
		
		// calculate random variance
		Random rng = new Random();
		double randomProportion = (rng.nextInt(10) + 95) / (double)100;// value btwn .95 and 1.05
		netDamage = (int)Math.ceil( netDamage * randomProportion);
		
		// respond to the final damage dealt
		defender.takeDamage(netDamage);
		dmgSummary = String.format("* %s took %d damage.\n", defender.nickname, netDamage);

		// build a summary of what happened (dmg, typeMatchup)
		finalDefSummary = dmgSummary + typeMatchupSummary;

		
		// RETURN a text summary
		return atkSummary + finalDefSummary;
	}

	






	// string methods //
	/**
	 * @return a summary of this Pokemon
	 */
	public String toString() {
		String message = "";
		message += nickname + ":\t";
		message += "level " + level + " | ";
		message += hp + "/" + maxHp + " hp | ";
		message += "[" + type + "]\n";
		return message;
	}
	
	public String getStatisticsStr() {
		String message = "--Statistics--"
				+ "\nName: " + name + " (" + nickname + ")"
				+ "\nType: " + type
				+ "\nMax HP: " + maxHp
				+ "\nATK: " + atk
				+ "\nDEF: " + def
				+ "\nLevel: " + level;
		return message + "\n\n";
	}

	/**
	 * Gets the values that are likely to change over the course of a battle.
	 */
	public String getStatus() {
		String message = "Current Status:"
				+ "\nName: " + name
				+ "\nCurrent HP: " + hp
				+ "\n";
		return message;
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
	
	public String getHealthBar() {
		int numTenths = (int)Math.ceil(hp/(double)maxHp * 10);
		String message = "";
		for(int i = 0; i < numTenths; i++)
			message += "*";
		return String.format("%-10s", message);
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

	public void setName(String nm) {
		this.name = nm;
	}
	
	public void setType(PkType type) {
		this.type = type;
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

	public String getAsciiFilePath() {
		return asciiFilePath;
	}
	public void setAsciiFilePath(String filepath) {
		asciiFilePath = filepath;
	}
	
	public String getCry() {
		return cry;
	}

	public void setCry(String cry) {
		this.cry = cry;
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
		int hpInc = rng.nextInt(2) + hpMod;
		int atkInc = rng.nextInt(2) + atkMod;
		int defInc = rng.nextInt(2) + defMod;
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
		int levelDiff = level-this.level;
		
		this.level = level;
		this.exp = 0;		
		expNextLvl *= Math.pow(expNextScaleFactor, levelDiff );
		// scale relevant stats to match the appropriate level
		maxHp += levelDiff*hpMod;
		setHp(maxHp);
		atk += levelDiff*atkMod;
		def += levelDiff*defMod;
		
//		for(int i = 5; i < level; i++) {
//			levelUp();
//			
//		}
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

	/**
	 * Sets up the stats and base stats (i.e. stat growth-modifiers) for this pokemon.
	 * Stats are established for a level 5 pokemon.
	 * The "level" parameter scales up the stats to the level-x equivalents.
	 * 
	 * @param maxHP
	 * @param atk
	 * @param def
	 * @param hpMod
	 * @param atkMod
	 * @param defMod
	 * @param level
	 */
	public void setStatBlock(int maxHP, int atk, int def, int hpMod, int atkMod, int defMod, int level) {
		this.maxHp = maxHP;
		this.hp = maxHP;
		this.atk = atk;
		this.def = def;
		this.hpMod = hpMod;
		this.atkMod = atkMod;
		this.defMod = defMod;
		setLevel(level);
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
		 * @return a numbered list of available moves, starting at 1
		 */
		public String toString() {
			String str = "Available Moves: " + numMoves + "\n";
			for(int i = 1; i <= numMoves; i++) {
				str += i + " - " + moves[i-1] + "\n";
			}
//			for(int i = 0; i < numMoves; i++) {
//				str += i + " - " + moves[i] + "\n";
//			}
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
