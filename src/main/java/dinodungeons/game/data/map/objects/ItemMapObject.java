package dinodungeons.game.data.map.objects;

import dinodungeons.game.gameobjects.player.ItemID;

public class ItemMapObject extends MapObject {

	private ItemID itemID;
	
	public ItemMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		switch(itemID){
		default:
			return "Item: " + itemID.toString();
		}
	}

	public ItemID getItemID() {
		return itemID;
	}

	public void setItemID(ItemID itemID) {
		this.itemID = itemID;
	}

}
