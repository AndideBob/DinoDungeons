package dinodungeons.game.data.gameplay.inventory;

import lwjgladapter.logging.Logger;

public enum CollectableType {
	NONE,
	MONEY,
	BOMBS,
	KEYS_DUNGEON_01,
	KEYS_DUNGEON_02,
	KEYS_DUNGEON_03,
	KEYS_DUNGEON_04,
	KEYS_DUNGEON_05,
	KEYS_DUNGEON_06,
	KEYS_DUNGEON_07,
	KEYS_DUNGEON_08,
	KEYS_DUNGEON_09,
	KEYS_DUNGEON_10,
	KEYS_DUNGEON_11,
	KEYS_DUNGEON_12;
	
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
		case 5:
			return CollectableType.KEYS_DUNGEON_05;
		case 6:
			return CollectableType.KEYS_DUNGEON_06;
		case 7:
			return CollectableType.KEYS_DUNGEON_07;
		case 8:
			return CollectableType.KEYS_DUNGEON_08;
		case 9:
			return CollectableType.KEYS_DUNGEON_09;
		case 10:
			return CollectableType.KEYS_DUNGEON_10;
		case 11:
			return CollectableType.KEYS_DUNGEON_11;
		case 12:
			return CollectableType.KEYS_DUNGEON_12;
		default:
			Logger.logError("Could not find key CollectableType for DungeonNumber: " + dungeonID);
			return NONE;
		}
	}
}
