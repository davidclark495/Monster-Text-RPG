package pokemon;

import items.Item;

/**
 * Contains info about what a pokemon will evolve into and 
 * what conditions (e.g. level, item) are needed to trigger the evolution.
 * 
 * @author davidclark
 * Date: Sept 10, 2022
 */
public class EvolutionLock {

	private Species newForm;
	private int evolvesAtLevel;
	private Item heldItemReqd;
	private boolean isFriendshipReqd;
	private boolean isTradeReqd;
//	private Pokemon.Gender genderReqd;
	
	public EvolutionLock(Species newForm, int lvl, Item heldItem, 
			boolean reqFriendship, boolean reqTrade) 
	{
		this.newForm = newForm; 
		this.evolvesAtLevel = lvl;
		this.heldItemReqd = heldItem;
		this.isFriendshipReqd = reqFriendship;
		this.isTradeReqd = reqTrade;
	}
	
	// GETTERS
	public Species getNewSpecies() {
		return newForm;
	}

	public int getLevelReqd() {
		return evolvesAtLevel;
	}

	public Item getHeldItemReqd() {
		return heldItemReqd;
	}

	public boolean isFriendshipReqd() {
		return isFriendshipReqd;
	}

	public boolean isTradeReqd() {
		return isTradeReqd;
	}
	
}
