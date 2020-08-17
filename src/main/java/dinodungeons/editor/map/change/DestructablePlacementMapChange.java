package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.DestructibleMapObject;
import dinodungeons.game.data.map.objects.DestructibleMapObject.DestructableType;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.MapObject;

public class DestructablePlacementMapChange extends AbstractMapChange {
	
	DestructableType destructableType;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public DestructablePlacementMapChange(int x, int y, DestructableType destructableType) {
		super(x, y);
		this.destructableType = destructableType;
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			DestructibleMapObject destructable = new DestructibleMapObject();
			destructable.setDestructableType(destructableType);
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), destructable);
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
