package items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

/**
 * Stores a TreeMap that associates Items w/ an Integer representing quantity in storage
 * 
 * @author davidclark
 *
 */
public class Bag{
	private TreeMap<Item, ItemData> items;// Item is correlated w/ Integer, which represents the quantity in storage.

	public Bag() {
		items = new TreeMap<>();
	}

	/**
	 * Adds the specified Item to the bag.
	 * If the bag already contains the item, it will lookup the current quantity and increment it.
	 * If the bag does not contain the item, it will add the item to the map w/ quantity of 1.
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		addItem(item, 1);
	}

	/**
	 * Adds the specified Item to the bag; repeats as many times as specified.
	 * If the bag already contains the item, it will lookup the current quantity and increment it.
	 * If the bag does not contain the item, it will add the item to the map w/ quantity of 1.
	 * 
	 * @param item
	 */
	public void addItem(Item item, int quantity) {
		ItemData iData = items.get(item);
		if(iData != null) {
			iData.increaseQuantity(quantity);				
		}
		else {
			items.put(item, new ItemData(item, quantity));
		}
	}

	/**
	 * If the bag contains the specified item, it decrements the count for it.
	 * Does not trigger the effect of the item.
	 * If the new count is 0 (or less), the item is removed from the table.
	 * @param item The item to be used.
	 */
	public Item spendItem(Item item) {
		ItemData iData = items.get(item);
		
		if(iData == null) {
			return null;
		}
		
		iData.increaseQuantity(-1);				
		if(iData.quantity <= 0) {
			items.remove(item);
		}
		return iData.getItem();

	}

	/**
	 * Decrements the count for the item at [index].
	 * Does not trigger the effect of the item.
	 * If the new count is 0 (or less), the item is removed from the table.
	 * Returns null if the index is out of bounds.
	 * Returns null if iData is null.
	 * 
	 * @param index The location of the item to be used.
	 * @return if successful, returns the Item; otherwise, returns null
	 */
	public Item spendItem(int index) {
		ItemData iData;

		// set iData
		// if index is out of bounds, return null
		try{
			iData = (ItemData) (items.values().toArray())[index];
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
		// if iData is null, return null
		if(iData == null) {
			return null;
		}

		// spend the item & return it
		iData.increaseQuantity(-1);				
		if(iData.quantity <= 0) {
			items.remove((items.keySet().toArray())[index]);
		}
		return iData.getItem();

	}

	/**
	 * Retrieves the item at [index]
	 * @param index The location of the item to be retrieved.
	 * @return if successful, returns the Item; otherwise, returns null
	 */
	public Item getItem(int index) {
		Item item;
		try{
			ItemData iData = (ItemData) (items.values().toArray())[index];
			item = iData.getItem();
		}catch(ArrayIndexOutOfBoundsException e) {
			item = null;
		}
		return item;
	}

	public boolean hasItem(Item item) {
		return items.containsKey(item);
	}

	public Set<Item> getItemSet() {
		return items.keySet();
	}


	public String getAllItemsSummary() {
		Collection<ItemData> itemCollection = items.values();
		String str = "Available Items: " + itemCollection.size() + "\n";
		int counter = 0;
		for(ItemData iData : itemCollection) {
			str += counter + " - " + iData.toString() + "\n";
			counter++;
		}
		return str;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}
}