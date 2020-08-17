package dinodungeons.editor.map.factories;

import dinodungeons.editor.map.change.ItemPlacementMapChange;
import dinodungeons.game.gameobjects.player.ItemID;
import dinodungeons.editor.map.change.AbstractMapChange;

public class CollectableItemMapChangeFactory extends AbstractMapChangeFactory{

	private ItemID itemID;
	
	public CollectableItemMapChangeFactory() {
		this.itemID = ItemID.CLUB;
	}

	@Override
	public AbstractMapChange buildMapChange(int x, int y) {
		return new ItemPlacementMapChange(x, y, itemID);
	}
	
	@Override
	protected void handleParam(String param, int index) {
		if(index == 0){
			itemID = ItemID.getItemIDBySaveRepresentation(param);
		}
	}

}
