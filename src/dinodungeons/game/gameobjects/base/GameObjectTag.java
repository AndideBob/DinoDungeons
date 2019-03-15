package dinodungeons.game.gameobjects.base;

import java.util.Arrays;
import java.util.Collection;

class SortingLayers{
	public static final int sortingLayerUndrawn = -1;
	public static final int sortingLayerStatic = 0;
	public static final int sortingLayerMoving = 1;
	public static final int sortingLayerPlayer = 2;
	public static final int sortingLayerEffects = 3;
}

public enum GameObjectTag {
	NONE(SortingLayers.sortingLayerUndrawn),
	PLAYER(SortingLayers.sortingLayerPlayer),
	WALL(SortingLayers.sortingLayerUndrawn),
	TRANSPORT(SortingLayers.sortingLayerUndrawn),
	COLLECTABLE_ITEM_CLUB(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_1(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_2(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_3(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_4(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_5(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_6(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_7(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_8(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_9(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_A(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_B(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_MIRROR(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_D(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_E(SortingLayers.sortingLayerStatic),
	COLLECTABLE_ITEM_ITEM_F(SortingLayers.sortingLayerStatic),
	COLLECTABLE_MONEY_OBJECT_VALUE_ONE(SortingLayers.sortingLayerStatic),
	COLLECTABLE_MONEY_OBJECT_VALUE_FIVE(SortingLayers.sortingLayerStatic),
	COLLECTABLE_MONEY_OBJECT_VALUE_TEN(SortingLayers.sortingLayerStatic),
	COLLECTABLE_MONEY_OBJECT_VALUE_TWENTYFIVE(SortingLayers.sortingLayerStatic),
	ITEM_CLUB(SortingLayers.sortingLayerEffects),
	DAMAGING_IMMOVABLE(SortingLayers.sortingLayerStatic),
	PARTICLE(SortingLayers.sortingLayerEffects);
	
	public static final Collection<GameObjectTag> damagingObjects = Arrays.asList(DAMAGING_IMMOVABLE);
	
	public static final Collection<GameObjectTag> movementBlockers = Arrays.asList(WALL, DAMAGING_IMMOVABLE);
	
	public static final Collection<GameObjectTag> collectableItems = Arrays.asList(COLLECTABLE_ITEM_CLUB, COLLECTABLE_ITEM_ITEM_1,
		COLLECTABLE_ITEM_ITEM_2, COLLECTABLE_ITEM_ITEM_3, COLLECTABLE_ITEM_ITEM_4, COLLECTABLE_ITEM_ITEM_5,
		COLLECTABLE_ITEM_ITEM_6, COLLECTABLE_ITEM_ITEM_7, COLLECTABLE_ITEM_ITEM_8, COLLECTABLE_ITEM_ITEM_9,
		COLLECTABLE_ITEM_ITEM_A, COLLECTABLE_ITEM_ITEM_B, COLLECTABLE_ITEM_MIRROR,
		COLLECTABLE_ITEM_ITEM_D, COLLECTABLE_ITEM_ITEM_E, COLLECTABLE_ITEM_ITEM_F);
	
	private int sortingLayer;
	
	private GameObjectTag(int sortingLayer) {
		this.sortingLayer = sortingLayer;
	}
	
	public int getSortingLayer() {
		return sortingLayer;
	}
}
