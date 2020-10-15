package items;

public class Item implements Comparable<Item>{
	
	private String name;
	
	public Item(String nm) {
		this.name = nm;
	}
	
	
	
	
	
	
	// getters and setters
	public String getName() {
		return name;
	}



	/**
	 * returns the name of the item
	 */
	public String toString() {
		return name;
	}

	/**
	 * compares items based on their names
	 */
	@Override
	public int compareTo(Item other) {
		return this.name.compareTo(other.name);
	}
	

}
