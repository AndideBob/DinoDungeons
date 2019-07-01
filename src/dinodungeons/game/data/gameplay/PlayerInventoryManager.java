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
	
	private HashMap<Integer, Boolean> masterKeys;
	private HashMap<Integer, Boolean> maps;
	
	private PlayerInventoryManager(){
		currentDungeon = 0;
		collectedItems = new HashSet<>();
		masterKeys = new HashMap<>();
		maps = new HashMap<>();
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
			case KEYS_DUNGEON_05:
			case KEYS_DUNGEON_06:
			case KEYS_DUNGEON_07:
			case KEYS_DUNGEON_08:
			case KEYS_DUNGEON_09:
			case KEYS_DUNGEON_10:
			case KEYS_DUNGEON_11:
			case KEYS_DUNGEON_12:
				maxValue = DinoDungeonsConstants.maxKeyAmount;
				break;
			case MONEY:
				maxValue = DinoDungeonsConstants.maxMoneyAmount;
				break;
			}
			collectables.put(collectable, new CollectableContainer(minValue, maxValue));
		}
	}
	
	public void setCurrentDungeon(int dungeonID){
		currentDungeon = dungeonID;
	}
	
	//ITEMS
	
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
	
	//COLLECTABLE VALUES
	public int getCurrent(CollectableType collectable){
		return collectables.get(collectable).getValue();
	}
	
	public boolean isMaxed(CollectableType collectable){
		return collectables.get(collectable).isMax();
	}
	
	public boolean isEmpty(CollectableType collectable){
		return collectables.get(collectable).isMin();
	}
	
	public void increase(CollectableType collectable, int amount){
		collectables.get(collectable).increase(amount);
	}
	
	public void decrease(CollectableType collectable, int amount){
		collectables.get(collectable).decrease(amount);
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

	//DUNGEON ITEMS
	public void collectDungeonItem(DungeonItemID itemID) {
		switch (itemID) {
		case KEY_SMALL:
			increaseKeysForCurrentDungeon(1);
			break;
		case KEY_BIG:
			collectMasterKeyForCurrentDungeon();
			break;
		case MAP:
			collectMapForCurrentDungeon();
			break;
		default:
			Logger.logError("Collection of " + itemID.toString() + " not supported yet!");
			break;
		}
	}
	
	//MASTER KEY
	public void collectMasterKeyForCurrentDungeon(){
		masterKeys.put(currentDungeon, Boolean.TRUE);
	}
	
	public boolean hasMasterKeyForCurrentDungeon(){
		if(!masterKeys.containsKey(currentDungeon)){
			masterKeys.put(currentDungeon, Boolean.FALSE);
		}
		return masterKeys.get(currentDungeon);
	}
	
	//MAP
	public void collectMapForCurrentDungeon(){
		maps.put(currentDungeon, Boolean.TRUE);
	}
	
	public boolean hasMapForCurrentDungeon(){
		if(!maps.containsKey(currentDungeon)){
			maps.put(currentDungeon, Boolean.FALSE);
		}
		return maps.get(currentDungeon);
	}

	public String toString() {
		String result = "Player Inventory:";
		for(CollectableType ct : collectables.keySet()) {
			result += "\n" + ct.toString() + ":" + collectables.get(ct).getValue();
		}
		return result;
	}
}
