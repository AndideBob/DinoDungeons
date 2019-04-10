package dinodungeons.game.data.gameplay;

import dinodungeons.game.gameobjects.player.ItemID;

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
	
	private int maxHealth;
	
	private int currentHealth;
	
	private ItemID itemA;
	
	private ItemID itemB;

	private PlayerStatusManager() {
		maxHealth = defaultHealth;
		currentHealth = maxHealth;
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

	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int value){
		int actualValue = Math.max(1, Math.min(value, totalMaxHealth));
		maxHealth = actualValue;
	}
	
	public boolean isHurt() {
		return currentHealth < maxHealth;
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

}
