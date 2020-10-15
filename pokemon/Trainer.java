package pokemon;

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
	public boolean swapPokemon(int index1, int index2) {
		return teamTracker.swapPokemon(index1, index2);
	}
	
	public void removePokemon(int index) {
		teamTracker.removePokemon(index);
	}

	public boolean hasFullTeam() {
		return teamTracker.hasFullTeam();
			
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
			if(index < 0 || index > team.length)
				return null;
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
		 * @return a numbered list of all pokemon in the team, starts at 1
		 */
		private String getAllPokemonString() {
			String str = "Number of Pokemon: " + teamSize + "\n";
			for(int i = 1; i <= teamSize; i++) {
				str += i + " - " + team[i-1];
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
		
		private boolean swapPokemon(int index1, int index2) {
			try {
				Pokemon temp = team[index1];
				team[index1] = team[index2];
				team[index2] = temp;
				return true;
			}catch(Exception e) {
				return false;
			}

		}
		
		private Pokemon removePokemon(int index) {
			if(index < 0 || index >= teamSize)
				return null;
			
			Pokemon removedPoke = team[index];
			for(int i = index; i < teamSize-1; i++) {// compress the array
				team[i] = team[i+1];
			}
			team[teamSize-1] = null;
			teamSize--;
			return removedPoke;
		}
		
		private boolean hasFullTeam() {
			return teamSize == MAX_HELD_POKEMON;
		}
	}




}
