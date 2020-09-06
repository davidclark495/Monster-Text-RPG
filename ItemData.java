package items;

public class ItemData {

	Item item;
	int quantity;
	
	public ItemData(Item item) {
		this(item, 1);
	}
	public ItemData(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	
	
	/**
	 * returns the name of the item, along with the quantity
	 */
	public String toString() {
		return item + " (" + quantity + ")";
	}
	
	// getters and setters //
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void increaseQuantity(int delta) {
		this.quantity += delta;
	}
	public Item getItem() {
		return item;
	}
	
	
	

}
