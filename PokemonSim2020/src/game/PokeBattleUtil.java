package game;

import audio.SoundPlayer;
import io.StandardIO;
import pokemon.Pokemon;
import pokemon.PokemonUtil;

public class PokeBattleUtil {
	/**
	 * Helper for runPlayerWinsEnding()
	 * updates the player pokemon's exp / level / stats
	 * and prints a relevant summary
	 */
	public static void winningExpProcessor(Pokemon playerPokemon, Pokemon enemyPokemon) {
		StandardIO.println(playerPokemon.getNickname() + " gained " + PokemonUtil.getExpDropped(enemyPokemon) + " experience!\n");

		int prevLevel = playerPokemon.getLevel();
		int prevExp = playerPokemon.getCurrentExp();
		String levelMessage = "Level:\t" + prevLevel;
		String expMessage = "Exp:\t" + prevExp + "/" + playerPokemon.getExpHeldAtNextLevel();
		String hpMessage = "HP:\t" + playerPokemon.getHP() + "/" + playerPokemon.getMaxHP();
		String atkMessage = "ATK:\t" + playerPokemon.getATK();
		String defMessage = "DEF:\t" + playerPokemon.getDEF();
		
		playerPokemon.gainExp( PokemonUtil.getExpDropped(enemyPokemon) );

		int newLevel = playerPokemon.getLevel();
		int newExp = playerPokemon.getCurrentExp();

		if(newLevel > prevLevel) {// if a level up occurred, give a more detailed message w/ stats
			hpMessage += " --> " + playerPokemon.getHP() + "/" + playerPokemon.getMaxHP() + "\n";
			atkMessage += " --> " + playerPokemon.getATK() + "\n";
			defMessage += " --> " + playerPokemon.getDEF() + "\n";
		}else {
			hpMessage = "";
			atkMessage = "";
			defMessage = "";
		}

		levelMessage += " --> " + newLevel + "\n";
		expMessage += " --> " + newExp + "/" + playerPokemon.getExpHeldAtNextLevel() + "\n";

		StandardIO.println(levelMessage + expMessage + hpMessage + atkMessage + defMessage);

		// play audio if level up occurred
		if(newLevel > prevLevel)
			SoundPlayer.playSound("sounds/game_sounds/level_up.wav");

		StandardIO.printDivider();
	}
}
