package pokemon;

import java.util.Random;

/**
 * Contains utility methods for accessing / altering Pokemon and their data.
 * 
 * @author davidclark
 * date: 09/02/2022
 */
public class PokemonUtil {
	
	// battle methods //
	/**
	 * Uses the attacker's specified attack on the defending pokemon. 
	 * The defender is updated accordingly.
	 * 
	 * Attacker:
	 * Uses the attack at moves[index] on the other pokemon. 
	 * This pokemon cannot attack if fainted.
	 * "index" must be between 0 and 3 (inclusive).
	 * If no move exists at moves[index], an exception will be thrown.
	 * 
	 * Defender:
	 * Makes this pokemon respond to being hit. Updates currHP based on the net damage of the attack.
	 * 
	 * @param atkr The pokemon launching the attack
	 * @param dfdr The pokemon being attacked
	 * @param mvIdx The index of the attacker's chosen move
	 * @return a String of narration describing attack
	 */
	public static String attack(Pokemon atkr, int mvIdx, Pokemon dfdr) {

		// ATTACKER //
		if( atkr.isFainted() ) {// can't attack if fainted
			return String.format("* %s is fainted. %s couldn't attack.\n", atkr.getNickname(), atkr.getNickname());
		}
		if(atkr.getPP(mvIdx) <= 0) {// can't attack if out of PP
			return String.format("* %s is out of PP for that move. They couldn't attack.\n", atkr.getNickname());
		}

		Move move = atkr.getMove(mvIdx);

		// gets a string summarizing the action
		String atkSummary = String.format("* %s used %s against %s.\n", atkr.getNickname(), move.getName(), dfdr.getNickname());

		// handle effects of using the move
		//		if(move.getAudioPath() != null) 
		//			SoundPlayer.playSound(move.getAudioPath());
		atkr.decrementPP(mvIdx);


		// DEFENDER //
		String typeMatchupSummary = "", dmgSummary = "", finalDefSummary = "";
		int netDamage;

		// set the base damage of the attack
		netDamage = move.getPower();

		// multiply by a function of the user's level
		netDamage *= (0.4 * atkr.getLevel()) + 2;

		// calculate type-relation modifiers on the net damage 
		double typeRelationMult = PkType.getDmgMultiplier(move.getType(), dfdr.getType1(), dfdr.getType2());
		netDamage *= typeRelationMult;
		if( typeRelationMult == 2 ) {
			typeMatchupSummary = "* It was super effective!\n";
		} else if( typeRelationMult == 0.5 ) {
			typeMatchupSummary = "* It wasn't very effective...\n";
		} else if( typeRelationMult == 0 ) {
			typeMatchupSummary = String.format("* It had no effect on %s\n", dfdr.getNickname());
		}

		// calculate atk/def modifiers on net damage
		if(move.getCategory() == Move.Category.PHYSICAL) {
			netDamage *= atkr.getATK();
			netDamage /= dfdr.getDEF();
		} else if(move.getCategory() == Move.Category.SPECIAL) {
			netDamage *= atkr.getSpATK();
			netDamage /= dfdr.getSpDEF();
		}

		// calculate STAB
		if( atkr.getType1().equals(move.getType()) 
				|| atkr.getType2().equals(move.getType())) {
			netDamage *= 1.5;			
		}

		// calculate random variance
		Random rng = new Random();
		double randomProportion = (rng.nextInt(15) + 85) / (double)100;// value btwn .85 and 1.00
		netDamage = (int)Math.floor(netDamage * randomProportion);

		// respond to the final damage dealt
		dfdr.takeDamage(netDamage);
		dmgSummary = String.format("* %s took %d damage.\n", dfdr.getNickname(), netDamage);

		// build a summary of what happened (dmg, typeMatchup)
		finalDefSummary = dmgSummary + typeMatchupSummary;


		// RETURN a text summary
		return atkSummary + finalDefSummary;
	}

	
	// string methods //
		/**
		 * @return a summary of this Pokemon
		 */
		public static String getInlineSummary(Pokemon poke) {
			String message = String.format("%s:\tlevel %d | %d/%d | [%s %s]\n", 
					poke.getNickname(), poke.getLevel(), poke.getHp(), 
					poke.getMaxHP(), poke.getType1(), poke.getType2());

			return message;
		}

		public String getStatistics(Pokemon poke) {
			String message = 
					String.format("--Statistics--\n"
							+ "Name: %s (%s)\n"
							+ "Type: %s %s\n"
							+ "HP: %3d / %3d\n"
							+ "ATK:   %3d DEF:   %3d\n"
							+ "SpATK: %3d SpDEF: %3d\n"
							+ "SPD:   %3d\n"
							+ "Level: %d\n", 
							poke.getNickname(), poke.getName(),
							poke.getType1(), poke.getType2(),
							poke.getHp(), poke.getMaxHP(),
							poke.getATK(), poke.getDEF(),
							poke.getSpATK(), poke.getSpDEF(),
							poke.getSPD(),
							poke.getLevel() );
			return message + "\n";
		}

		/**
		 * Gets the values that are likely to change over the course of a battle.
		 */
		public String getStatus(Pokemon poke) {
			String message = "Current Status:"
					+ "\nName: " + poke.getName()
					+ "\nCurrent HP: " + poke.getHp()
					+ "\n";
			return message;
		}
		
		/**
		 * Returns a visual representation of the pokemon's health.
		 * The precise HP value is obscured.
		 * @param poke
		 * @return
		 */
		public String getHealthBar(Pokemon poke) {
			int numTenths = (int)Math.ceil(poke.getHp()/(double)poke.getMaxHP() * 10);
			String message = "";
			for(int i = 0; i < numTenths; i++)
				message += "*";
			return String.format("%-10s", message);
		}
		
		
		// LEVELS, EXP, and STATS //
		/** 
		 * 
		 * @return the amount of exp the pokemon drops when defeated
		 */
		public int getExpDropped(Pokemon poke) {
			double lvlMult = 10;
			double maxHpMult = 0.2;

			int expDropped = (int)(poke.getLevel()*lvlMult + poke.getMaxHP()*maxHpMult);
			return expDropped;
		}

}
