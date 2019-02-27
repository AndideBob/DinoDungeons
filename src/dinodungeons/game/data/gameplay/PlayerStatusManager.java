package dinodungeons.game.data.gameplay;

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

	private PlayerStatusManager() {
		maxHealth = defaultHealth;
		currentHealth = maxHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
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
