package dinodungeons.editor.map.factories;

import dinodungeons.game.data.map.objects.DoorMapObject.DoorType;
import dinodungeons.editor.map.change.AbstractMapChange;
import dinodungeons.editor.map.change.DoorPlacementMapChange;

public class DoorMapChangeFactory extends AbstractMapChangeFactory{

	private DoorType doorType;
	
	public DoorMapChangeFactory() {
		this.doorType = DoorType.ENEMIES;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new DoorPlacementMapChange(x, y, doorType);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			doorType = DoorType.getDoorTypeBySaveRepresentation(param);
		}
	}

}
