package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.MapChangeType;
import lwjgladapter.logging.Logger;

public class MapChangeFactoryUtil {

	public static MapChangeFactory getMapChangeFactory(MapChangeType mapChangeType) {
		switch (mapChangeType) {
		case NONE:
			return null;
		case ERASE:
			return new EraseMapChangeFactory();
		case BASE_LAYER:
			return new BaseLayerMapChangeFactory();
		case COLLECTABLE_ITEM:
			return new CollectableItemMapChangeFactory();
		case SWITCH_PLACEMENT:
			return new SwitchMapChangeFactory();
		case DOOR_PLACEMENT:
			return new DoorMapChangeFactory();
		case EXIT_PLACEMENT:
			return new ExitMapChangeFactory();
		case DESTRUCTIBLE_PLACEMENT:
			return new DestructapleMapChangeFactory();
		case SPIKE_PLACEMENT:
			return new SpikeMapChangeFactory();
		case IMMOVABLE_PLACEMENT:
			return new ImmovableMapChangeFactory();
		case ENEMY_PLACEMENT:
			return new EnemyMapChangeFactory();
		case SIGN_CHANGE:
			return new SignMapChangeFactory();
		case BUILDING_PLACEMENT:
			return new BuildingMapChangeFactory();
		case NPC_PLACEMENT:
			return new NPCMapChangeFactory();
		}
		Logger.logError("Could not retrieve MapChangeFactory for MapChangeType: " + mapChangeType.toString());
		return null;
	}

}
