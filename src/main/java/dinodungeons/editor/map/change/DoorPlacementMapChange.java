package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.DoorMapObject.DoorType;
import dinodungeons.game.data.map.objects.DoorMapObject;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;

public class DoorPlacementMapChange extends AbstractMapChange {
	
	DoorType doorType;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public DoorPlacementMapChange(int x, int y, DoorType doorType) {
		super(x, y);
		this.doorType = doorType;
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			DoorMapObject doorObject = new DoorMapObject();
			doorObject.setDoorType(doorType);
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), doorObject);
		}
		else {
			if(previousObject == null) {
				map.setMapObjectForPosition(getX(), getY(), new EmptyMapObject());
			}
			else {
				map.setMapObjectForPosition(getX(), getY(), previousObject);
			}
		}
	}

	@Override
	public void revert() {
		shouldRevert = !shouldRevert;
	}

}
