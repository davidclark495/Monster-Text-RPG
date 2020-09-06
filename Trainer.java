package pokemon;

/**
* The Trainer class stands has a pool of pokemon and bag-items.
*
* @author  David Clark
* @version 1.0
* @since   2020-09-05 
*/
public class Trainer {

	private static final int MAX_HELD_POKEMON = 6;
	
	private PokemonTracker team;
	
	public Trainer() {
		team = new PokemonTracker(MAX_HELD_POKEMON);
	}
	
	public boolean addPokemon(Pokemon pokemon) {
		return team.addPokemon(pokemon);
	}
	
	
	
	
	
	
	
	///////// inner classes /////////////////
	
	private class PokemonTracker{
		private Pokemon[] team;
		private int teamSize;
		
		private PokemonTracker(int maxNumPokemon) {
			team = new Pokemon[maxNumPokemon];
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
				return true;
			}
			
			return false;
		}
	}

}
