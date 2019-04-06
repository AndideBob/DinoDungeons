package dinodungeons.editor.map.change;

import dinodungeons.game.data.map.ScreenMap;
import dinodungeons.game.data.map.objects.EmptyMapObject;
import dinodungeons.game.data.map.objects.ItemMapObject;
import dinodungeons.game.data.map.objects.MapObject;
import dinodungeons.game.gameobjects.player.ItemID;

public class ItemPlacementMapChange extends AbstractMapChange {
	
	ItemID itemId;
	
	MapObject previousObject;
	
	private boolean shouldRevert;

	public ItemPlacementMapChange(int x, int y, ItemID itemId) {
		super(x, y);
		this.itemId = itemId;
		previousObject = null;
		shouldRevert = false;
	}

	@Override
	public void applyTo(ScreenMap map) {
		if(!shouldRevert) {
			ItemMapObject item = new ItemMapObject();
			item.setItemID(itemId);
			previousObject = map.getMapObjectForPosition(getX(), getY());
			map.setMapObjectForPosition(getX(), getY(), item);
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
