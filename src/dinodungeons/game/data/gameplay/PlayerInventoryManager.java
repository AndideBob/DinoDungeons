package dinodungeons.game.data.gameplay;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import dinodungeons.game.data.DinoDungeonsConstants;
import dinodungeons.game.data.gameplay.inventory.CollectableContainer;
import dinodungeons.game.data.gameplay.inventory.CollectableType;
import dinodungeons.game.gameobjects.player.DungeonItemID;
import dinodungeons.game.gameobjects.player.ItemID;
import lwjgladapter.logging.Logger;

public class PlayerInventoryManager {

	private static PlayerInventoryManager instance;
	
	public static PlayerInventoryManager getInstance(){
		if(instance == null){
			instance = new PlayerInventoryManager();
		}
		return instance;
	}
	
	private Set<ItemID> collectedItems;
	
	private int currentDungeon;
	
	//Collectables
	private HashMap<CollectableType, CollectableContainer> collectables;
	
	private PlayerInventoryManager(){
		currentDungeon = 0;
		collectedItems = new HashSet<>();
		collectables = new HashMap<>();
		for(CollectableType collectable : CollectableType.values()){
			int minValue = 0;
			int maxValue = 0;
			switch(collectable){
			case NONE:
			case BOMBS:
				break;
			case KEYS_DUNGEON_01:
			case KEYS_DUNGEON_02:
			case KEYS_DUNGEON_03:
			case KEYS_DUNGEON_04:
				maxValue = DinoDungeonsConstants.maxKeyAmount;
				break;
			case MONEY:
				maxValue = DinoDungeonsConstants.maxMoneyAmount;
				break;	
			}
			collectables.put(collectable, new CollectableContainer(minValue, maxValue));
		}
	}
	
	public Collection<ItemID> getCollectedItems(){
		return Collections.unmodifiableSet(collectedItems);
	}
	
	public void collectItem(ItemID itemID){
		if(!collectedItems.contains(itemID)) {
			Logger.logDebug("Item collected: " + itemID.toString());
			collectedItems.add(itemID);
			if(PlayerStatusManager.getInstance().getItemA() == null) {
				PlayerStatusManager.getInstance().setItemA(itemID);
			}
			else if(PlayerStatusManager.getInstance().getItemB() == null) {
				PlayerStatusManager.getInstance().setItemB(itemID);
			}
			increaseLimitsOnItemCollection(itemID);
		}
	}
	
	private void increaseLimitsOnItemCollection(ItemID itemID) {
		switch(itemID) {
		case BOMB:
			collectables.get(CollectableType.BOMBS).setMaxValue(DinoDungeonsConstants.maxBombAmountBasic);
			collectables.get(CollectableType.BOMBS).increase(DinoDungeonsConstants.maxBombAmountBasic);
			break;
		}
	}
	
	public void increase(CollectableType collectable, int amount){
		collectables.get(collectable).increase(amount);
	}
	
	public void decrease(CollectableType collectable, int amount){
		collectables.get(collectable).increase(amount);
	}
	
	public boolean hasKeysForCurrentDungeon(){
		return !isEmpty(CollectableType.getKeyTypeForDungeonID(currentDungeon));
	}
	
	public int getKeysForCurrentDungeon(){
		return getCurrent(CollectableType.getKeyTypeForDungeonID(currentDungeon));
	}
	
	public void increaseKeysForCurrentDungeon(int amount){
		increase(CollectableType.getKeyTypeForDungeonID(currentDungeon), amount);
	}
	
	public void decreaseKeysForCurrentDungeon(int amount){
		decrease(CollectableType.getKeyTypeForDungeonID(currentDungeon), amount);
	}
	
	public int getCurrent(CollectableType collectable){
		return collectables.get(collectable).getValue();
	}
	
	public boolean isMaxed(CollectableType collectable){
		return collectables.get(collectable).isMax();
	}
	
	public boolean isEmpty(CollectableType collectable){
		return collectables.get(collectable).isMin();
	}
	
	public void setCurrentDungeon(int dungeonID){
		currentDungeon = dungeonID;
	}

	public void collectDungeonItem(DungeonItemID itemID) {
		switch (itemID) {
		case KEY_SMALL:
			increaseKeysForCurrentDungeon(1);
			break;
		default:
			Logger.logError("Collection of " + itemID.toString() + " not supported yet!");
			break;
		}
	}

}
