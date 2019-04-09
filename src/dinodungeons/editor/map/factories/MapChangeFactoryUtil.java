package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.MapChangeType;
import lwjgladapter.logging.Logger;

public class MapChangeFactoryUtil {

	public static MapChangeFactory getMapChangeFactory(MapChangeType mapChangeType) {
		switch (mapChangeType) {
		case BASE_LAYER:
			return new BaseLayerMapChangeFactory();
		case COLLECTABLE_ITEM:
			return new CollectableItemMapChangeFactory();
		case SWITCH_PLACEMENT:
			return new SwitchMapChangeFactory();
		}
		Logger.logError("Could not retrieve MapChangeFactory for MapChangeType: " + mapChangeType.toString());
		return new BaseLayerMapChangeFactory();
	}

}
