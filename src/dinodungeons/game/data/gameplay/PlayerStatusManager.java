package dinodungeons.game.data.gameplay;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import dinodungeons.game.gameobjects.player.ItemID;
import lwjgladapter.logging.Logger;

public class PlayerStatusManager {
	
	private static PlayerStatusManager instance;
	
	public static PlayerStatusManager getInstance(){
		if(instance == null){
			instance = new PlayerStatusManager();
		}
		return instance;
	}
	
	private static final int defaultHealth = 6;
	private static final int totalMaxHealth = 48;
	private static final int maxMoney = 999;
	
	private int maxHealth;
	
	private int currentHealth;
	
	private int currentMoney;
	
	private Set<ItemID> collectedItems;
	
	private ItemID itemA;
	
	private ItemID itemB;

	private PlayerStatusManager() {
		maxHealth = defaultHealth;
		currentHealth = maxHealth;
		collectedItems = new HashSet<>();
		itemA = null;
		itemB = null;
	}
	
	public void setItemA(ItemID item){
		itemA = item;
	}
	
	public void setItemB(ItemID item){
		itemB = item;
	}
	
	public ItemID getItemA(){
		return itemA;
	}
	
	public ItemID getItemB(){
		return itemB;
	}
	
	public Collection<ItemID> getCollectedItems(){
		return Collections.unmodifiableSet(collectedItems);
	}
	
	public void collectItem(ItemID itemID){
		Logger.logDebug("Item collected: " + itemID.toString());
		collectedItems.add(itemID);
		if(itemA == null) {
			itemA = itemID;
		}
		else if(itemB == null) {
			itemB = itemID;
		}
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int value){
		int actualValue = Math.max(1, Math.min(value, totalMaxHealth));
		maxHealth = actualValue;
	}

	public void heal(int amount) {
		if(amount > 0){
			currentHealth = Math.min(maxHealth, currentHealth + amount);
		}
	}
	
	public void damage(int amount) {
		if(amount > 0){
			currentHealth = Math.max(0, currentHealth - amount);
		}
	}
	
	public boolean isDead(){
		return currentHealth <= 0;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public void addMoney(int amount){
		if(amount > 0){
			currentMoney += amount;
			if(currentMoney > maxMoney){
				currentMoney = maxMoney;
			}
		}
		else{
			Logger.logDebug("Tried to add negative or zero amount of money! No change made!");
		}
	}
	
	public void removeMoney(int amount){
		if(amount > 0){
			currentMoney -= amount;
			if(currentMoney < 0){
				currentMoney = 0;
			}
		}
		else{
			Logger.logDebug("Tried to remove negative or zero amount of money! No change made!");
		}
	}
	
	public boolean isMoneyMaxedOut(){
		return currentMoney >= maxMoney;
	}
	
	public boolean isMoneyEmpty(){
		return currentMoney <= 0;
	}

	public int getCurrentMoney() {
		return currentMoney;
	}

}
