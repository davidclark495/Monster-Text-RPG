package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import audio.SoundPlayer;
import io.StandardIO;

public class Pokemon {

	public enum Stat {
		MaxHP, ATK, DEF, SpATK, SpDEF, SPD;
	}

	private static final double EXPNEXT_SCALEFACTOR = 1.25;

	// presentation
	private String nickname;
	//	private String cry;
	//	private String asciiFilePath;
	// code objects
	private Species species; 			// contains info. shared by all similar pokemon (e.g. all Pikachu)
	private MovesTracker movesTracker;
	private Trainer trainer;
	// stats
	private int currHP;
	private int level = 5, totalExp = 0, totalExpNextLvl = 50;
	private Map<Stat, Double> stats = new EnumMap<>(Stat.class);



	public Pokemon(String species, int level) {
		this.species = SpeciesList.getSpeciesList().getSpecies(species);
		setLevel(level);
		recalculateStats();
	}
	
	public Pokemon(Species species, int level) {
		this.species = species;
		setLevel(level);
		recalculateStats();
	}




	/**
	 * 
	 * @param other
	 * @return true if both have the same name
	 */
	public boolean sameSpecies(Pokemon other) {
		return species.equals(other.species);
	}



	








	










	// getters and setters //
	public String getName() {
		return species.getName();
	}

	public String getNickname() {
		return nickname;
	}

	public PkType getType1() {
		return species.getType1();
	}

	public PkType getType2() {
		return species.getType2();
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
		

	//	public String getAsciiFilePath() {
	//		return asciiFilePath;
	//	}

	//	public String getCry() {
	//		return cry;
	//	}

	/**
	 * 
	 * @return True if the pokemon has 0 currHP
	 */
	public boolean isFainted() {
		if(this.currHP == 0) {			
			return true;
		}

		return false;
	}


	// LEVELS, EXP, and STATS //
	/**
	 * adds an amount of totalExp to this pokemon, levels up as many times as needed
	 * 
	 * @param expAmount The amount of totalExp that this pokemon gains
	 */
	public void gainExp(int expAmount) {
		totalExp += expAmount;
		while(totalExp > totalExpNextLvl) {
			levelUp();
		}
	}
	private void levelUp() {
		// update level, increase totalExp until next level, do not modify current totalExp
		level++;
		totalExpNextLvl = (int)(Math.pow(level, 3));
		// learn moves, if applicable
		// TODO: get user input on which moves should be forgotten
		this.teachAllMoves(species.getMovesLearnedAtLevel(level).toArray(new Move[0]));
		// recalculate stats w/ the higher level
		recalculateStats();
	}
	public int getCurrentExp() {
		return totalExp;
	}
	public int getExpNextLevel() {
		return totalExpNextLvl;
	}
	public int getLevel() {
		return level;
	}
	/**
	 * 
	 * @param level
	 * @return the min. amount of total exp the pokemon must have to reach the given level
	 */
	private int getExpHeldAtLevel(int level) {
		return (int) Math.pow(EXPNEXT_SCALEFACTOR, level);
	}
	
	private void setLevel(int level) {
		this.gainExp(getExpHeldAtLevel(level) - getCurrentExp());
	}

	// called after level-up
	private void recalculateStats() {
		int baseHP = species.getBaseStat(Species.BaseStat.BaseHP);
		int baseATK = species.getBaseStat(Species.BaseStat.BaseATK);
		int baseDEF = species.getBaseStat(Species.BaseStat.BaseDEF);
		int baseSpATK = species.getBaseStat(Species.BaseStat.BaseSpATK);
		int baseSpDEF = species.getBaseStat(Species.BaseStat.BaseSpDEF);
		int baseSPD = species.getBaseStat(Species.BaseStat.BaseSPD);

		double maxHP 	= (2*baseHP*level)/100.0 + level + 10;
		double atk 		= (2*baseATK*level) + 5;
		double def 		= (2*baseDEF*level) + 5;
		double spAtk 	= (2*baseSpATK*level) + 5;
		double spDef 	= (2*baseSpDEF*level) + 5;
		double spd 		= (2*baseSPD*level) + 5;

		stats.put(Stat.MaxHP, maxHP);
		stats.put(Stat.ATK, atk);
		stats.put(Stat.DEF, def);
		stats.put(Stat.SpATK, spAtk);
		stats.put(Stat.SpDEF, spDef);
		stats.put(Stat.SPD, spd);
	}
	
	public int getHp() {
		return currHP;
	}
	
	/**
	 * Sets the current currHP value to the given value.
	 * If the new value is greater than the max currHP, the current currHP value will be set to the max currHP.
	 * If the new value is less than 0, the current currHP value will be set to 0.
	 * 
	 * @param currHP The new currHP value
	 */
	public void setHp(int hp) {
		this.currHP = hp;

		if(this.currHP > getMaxHP()) {	
			this.currHP = getMaxHP();
		}

		if(this.currHP <= 0) {			
			this.currHP = 0;
		}
	}

	/**
	 * Decreases the current currHP by the specified amount. 
	 * 
	 * @param deltaHp
	 */
	public void takeDamage(int deltaHp) {
		setHp(this.currHP - deltaHp);
	}

	/**
	 * Increases the current currHP by the specified amount. 
	 * 
	 * @param deltaHp
	 */
	public void heal(int deltaHp) {
		setHp(this.currHP + deltaHp);
	}



	public int getMaxHP() {
		return (int)Math.floor(stats.get(Stat.MaxHP));
	}
	public int getATK() {
		return (int)Math.floor(stats.get(Stat.ATK));
	}
	public int getDEF() {
		return (int)Math.floor(stats.get(Stat.DEF));
	}
	public int getSpATK() {
		return (int)Math.floor(stats.get(Stat.SpATK));
	}
	public int getSpDEF() {
		return (int)Math.floor(stats.get(Stat.SpDEF));
	}
	public int getSPD() {
		return (int)Math.floor(stats.get(Stat.SPD));
	}





	// MOVES //
	/**
	 * 
	 * @param index
	 * @return the attack at moves[index]
	 */
	public Move getMove(int index) {
		return movesTracker.getMove(index);
	}

	/**
	 * 
	 * @param move
	 * @return
	 */
	public boolean teachMove(Move move) {
		return movesTracker.addMove(move);
	}

	/**
	 * 
	 * @param move
	 * @return
	 */
	public boolean teachAllMoves(Move[] moves) {
		return movesTracker.addAllMoves(moves);
	}

	public String getAllMovesString() {
		return movesTracker.toString();
	}


	public int getNumMoves() {
		return movesTracker.getNumMoves();
	}

	public Move[] getAllMoves() {
		return movesTracker.getAllMoves();
	}

	
	/**
	 * 
	 * @param index
	 * @return the amount of pp remaining for the attack at moves[index]
	 */
	public int getPP(int index) {
		return movesTracker.getPP(index);
	}
	public void decrementPP(int index) {
		movesTracker.decrementPP(index);
	}
	public void restoreAllPP() {
		movesTracker.restoreAllPP();
	}


	// TRAINER //
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

		private Move[] moves;	
		private int[] ppLeft;	
		private int numMoves;

		private MovesTracker() {
			moves = new Move[MAX_NUM_MOVES];
			ppLeft = new int[MAX_NUM_MOVES];
			numMoves = 0;
		}

		private MovesTracker(Move[] moves) {
			moves = new Move[MAX_NUM_MOVES];
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
				str += String.format("%d - %s\n", i, moves[i-1]);
			}
			return str;
		}



		// getters and setters //
		/**
		 * Returns the move at the given index.
		 * Returns null if the pokemon has no move at that position.
		 * 
		 * @param index
		 * @return the move at the given index
		 */
		private Move getMove(int index) {
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
		private Move[] getAllMoves() {
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
		private boolean addMove(Move newMove) {
			if(numMoves < MAX_NUM_MOVES) {
				moves[numMoves] = newMove;
				ppLeft[numMoves] = newMove.getMaxPP();
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
		private boolean addAllMoves(Move[] newMoves) {
			if(newMoves.length + numMoves > MAX_NUM_MOVES) 
				return false;

			for(int i = 0; i < newMoves.length; i++) {
				moves[numMoves] = newMoves[i];
				numMoves++;
			}
			return true;
		}

		/**
		 * 
		 * @return the number of moves currently known
		 */
		private int getNumMoves() {
			return numMoves;
		}
		
		/**
		 * Returns the PP of the move at the given index.
		 * 
		 * @param index
		 * @return the PP of the move at the given index
		 */
		private int getPP(int index) {
			return ppLeft[index];
		}
		
		/**
		 * Decrements the PP of the move at the given index.
		 * 
		 * @param index
		 * @return the PP of the move at the given index
		 */
		private void decrementPP(int index) {
			ppLeft[index]--;
		}

		private void restoreAllPP() {
			for(int i = 0; i < numMoves; i++) {
				ppLeft[i] = moves[i].getMaxPP();
			}
		}
	}

}
