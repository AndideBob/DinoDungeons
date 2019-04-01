package dinodungeons.game.data.gameplay.inventory;

public class CollectableContainer {

	private int minValue;
	
	private int maxValue;
	
	private int currentValue;
	
	public CollectableContainer(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		currentValue = minValue;
	}
	
	public void setMinValue(int minValue) {
		if(minValue < maxValue){
			this.minValue = minValue;
			if(currentValue < minValue){
				currentValue = minValue;
			}
		}
	}

	public void setMaxValue(int maxValue) {
		if(maxValue > minValue){
			this.maxValue = maxValue;
			if(currentValue > maxValue){
				currentValue = maxValue;
			}
		}
	}
	
	public void increase(int amount){
		if(amount > 0){
			currentValue = Math.min(currentValue + amount, maxValue);
		}
	}
	
	public void decrease(int amount){
		if(amount > 0){
			currentValue = Math.max(currentValue - amount, minValue);
		}
	}
	
	public int getValue() {
		return currentValue;
	}
	
	public boolean isMin(){
		return currentValue <= minValue;
	}
	
	public boolean isMax(){
		return currentValue >= maxValue;
	}

}
