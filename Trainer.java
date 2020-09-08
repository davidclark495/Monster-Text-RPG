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

	private PokemonTracker team;
	private Bag bag;

	public Trainer() {
		team = new PokemonTracker();
		bag = new Bag();
	}

	public Pokemon getPokemon(int index) {
		return team.getPokemon(index);
	}

	public boolean addPokemon(Pokemon pokemon) {
		pokemon.setNewTrainer(this);
		return team.addPokemon(pokemon);
	}

	public Bag getBag() {
		return bag;
	}

	public String getAllPokemon() {
		return team.getAllPokemon();
	}

	public boolean swapPokemonToFront(int newFrontIndex) {
		return team.swapPokemonToFront(newFrontIndex);
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
		private String getAllPokemon() {
			String str = "Number of Pokemon: " + teamSize + "\n";
			for(int i = 0; i < teamSize; i++) {
				str += i + " - " + team[i];
			}
			return str;
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
