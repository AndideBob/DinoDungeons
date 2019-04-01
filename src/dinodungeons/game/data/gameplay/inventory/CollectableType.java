package dinodungeons.game.data.gameplay.inventory;

import lwjgladapter.logging.Logger;

public enum CollectableType {
	NONE,
	MONEY,
	BOMBS,
	KEYS_DUNGEON_01,
	KEYS_DUNGEON_02,
	KEYS_DUNGEON_03,
	KEYS_DUNGEON_04;
	
	public static CollectableType getKeyTypeForDungeonID(int dungeonID){
		switch(dungeonID){
		case 1:
			return CollectableType.KEYS_DUNGEON_01;
		case 2:
			return CollectableType.KEYS_DUNGEON_02;
		case 3:
			return CollectableType.KEYS_DUNGEON_03;
		case 4:
			return CollectableType.KEYS_DUNGEON_04;
		default:
			Logger.logError("Could not find key CollectableType for DungeonNumber: " + dungeonID);
			return NONE;
		}
	}
}
