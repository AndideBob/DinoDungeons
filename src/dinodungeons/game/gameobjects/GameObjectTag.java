package dinodungeons.game.gameobjects;

import java.util.Arrays;
import java.util.Collection;

public enum GameObjectTag {
	NONE,
	PLAYER,
	WALL,
	TRANSPORT,
	COLLECTABLE_ITEM_CLUB,
	COLLECTABLE_ITEM_ITEM_1,
	COLLECTABLE_ITEM_ITEM_2,
	COLLECTABLE_ITEM_ITEM_3,
	COLLECTABLE_ITEM_ITEM_4,
	COLLECTABLE_ITEM_ITEM_5,
	COLLECTABLE_ITEM_ITEM_6,
	COLLECTABLE_ITEM_ITEM_7,
	COLLECTABLE_ITEM_ITEM_8,
	COLLECTABLE_ITEM_ITEM_9,
	COLLECTABLE_ITEM_ITEM_A,
	COLLECTABLE_ITEM_ITEM_B,
	COLLECTABLE_ITEM_MIRROR,
	COLLECTABLE_ITEM_ITEM_D,
	COLLECTABLE_ITEM_ITEM_E,
	COLLECTABLE_ITEM_ITEM_F,
	DAMAGING_IMMOVABLE;
	
	public static final Collection<GameObjectTag> movementBlockers = Arrays.asList(WALL, DAMAGING_IMMOVABLE);
	
	public static final Collection<GameObjectTag> collectableItems = Arrays.asList(COLLECTABLE_ITEM_CLUB, COLLECTABLE_ITEM_ITEM_1,
		COLLECTABLE_ITEM_ITEM_2, COLLECTABLE_ITEM_ITEM_3, COLLECTABLE_ITEM_ITEM_4, COLLECTABLE_ITEM_ITEM_5,
		COLLECTABLE_ITEM_ITEM_6, COLLECTABLE_ITEM_ITEM_7, COLLECTABLE_ITEM_ITEM_8, COLLECTABLE_ITEM_ITEM_9,
		COLLECTABLE_ITEM_ITEM_A, COLLECTABLE_ITEM_ITEM_B, COLLECTABLE_ITEM_MIRROR,
		COLLECTABLE_ITEM_ITEM_D, COLLECTABLE_ITEM_ITEM_E, COLLECTABLE_ITEM_ITEM_F);
}
