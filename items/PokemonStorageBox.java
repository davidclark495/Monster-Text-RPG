package items;

import java.util.ArrayList;

import pokemon.Pokemon;

public class PokemonStorageBox {

	private ArrayList<Pokemon> box;
	
	public PokemonStorageBox() {
		box = new ArrayList<Pokemon>();
	}
	
	public void addPokemon(Pokemon entry) {
		box.add(entry);
	}
	public Pokemon getPokemon(int index) {
		if(index < 0 || index >= box.size())
			return null;
		return box.get(index);
	}
	public Pokemon removePokemon(int index) {
		if(index < 0 || index >= box.size())
			return null;
		return box.remove(index);
	}
	
	public String getAllPokemonStr() {
		if(box.size() == 0) {
			return "Empty box\n";
		}
		
		String message = "Box Contents:\n";
		for(int i = 1; i <= box.size(); i++) {
			message += i + " - " + box.get(i-1);
		}
		return message;
	}
	
	public boolean isEmpty() {
		return box.isEmpty();
	}
	
}
