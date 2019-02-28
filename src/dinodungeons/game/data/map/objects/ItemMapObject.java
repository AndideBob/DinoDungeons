package dinodungeons.game.data.map.objects;

public class ItemMapObject extends MapObject {

	private int itemID;
	
	public ItemMapObject() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEditorInfo() {
		switch(itemID){
		default:
			return "Item: " + itemID;
		}
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

}
