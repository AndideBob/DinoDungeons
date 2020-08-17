package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;

public class EraseMapChange extends AbstractMapChange {
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public EraseMapChange(int x, int y) {
		super(x, y);
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), new EmptyMapObject());
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
