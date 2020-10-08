package pokemon;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import items.Bag;

/**
 * The Trainer class stands has a pool of pokemon and bag-items.
 *
 * @author  David Clark
 * @version 1.0
 * @since   2020-09-05 
 */
public class Trainer {

	private PokemonTracker teamTracker;
	private Bag bag;

	public Trainer() {
		teamTracker = new PokemonTracker();
		bag = new Bag();
	}

	public Pokemon getPokemon(int index) {
		return teamTracker.getPokemon(index);
	}

	public boolean addPokemon(Pokemon pokemon) {
		pokemon.setNewTrainer(this);
		return teamTracker.addPokemon(pokemon);
	}

	public Bag getBag() {
		return bag;
	}

	public String getAllPokemonStr() {
		return teamTracker.getAllPokemonString();
	}
	
	public Pokemon[] getAllPokemon() {
		return teamTracker.getAllPokemon();
	}
	
	/**
	 * 
	 * @return true if the trainer has non-fainted pokemon capable of fighting
	 */
	public boolean canFight() {
		for(int i = 0; i < teamTracker.teamSize; i++) 
			if(! teamTracker.getPokemon(i).isFainted())
				return true;
		
		return false;
	}
	
	public int getNumPokemon() {
		return teamTracker.getNumPokemon();
	}

	public boolean swapPokemonToFront(int newFrontIndex) {
		return teamTracker.swapPokemonToFront(newFrontIndex);
	}




	///////// inner classes /////////////////

	private class PokemonTracker{

		private static final int MAX_HELD_POKEMON = 6;

		private Pokemon[] team;
		private int teamSize;

		private PokemonTracker() {
			team = new Pokemon[MAX_HELD_POKEMON];
			teamSize = 0;
		}

		/**
		 * 
		 * @param index Must be between 0 and numHeldPokemon.
		 * @return the Pokemon at the specified index
		 */
		private Pokemon getPokemon(int index) {
			return team[index];
		}
		
		/**
		 * 
		 * @return the array of Pokemon, likely not full
		 */
		private Pokemon[] getAllPokemon() {
			return team;
		}

		/**
		 * 
		 * @param pokemon The pokemon to be added
		 * @return a boolean indicating successful/unsuccessful addition to the team
		 */
		private boolean addPokemon(Pokemon pokemon) {
			if(teamSize < MAX_HELD_POKEMON) {
				team[teamSize] = pokemon;
				teamSize++;
				return true;
			}

			return false;
		}

		/**
		 * @return a numbered list of all pokemon in the team
		 */
		private String getAllPokemonString() {
			String str = "Number of Pokemon: " + teamSize + "\n";
			for(int i = 0; i < teamSize; i++) {
				str += i + " - " + team[i];
			}
			return str;
		}
		
		private int getNumPokemon() {
			return teamSize;
		}

		private boolean swapPokemonToFront(int newFrontIndex) {
			try {
				Pokemon oldFrontPoke = team[0];
				Pokemon newFrontPoke = team[newFrontIndex];

				team[0] = newFrontPoke;
				team[newFrontIndex] = oldFrontPoke;
				return true;
			}catch(Exception e) {
				return false;
			}

		}
	}




}
