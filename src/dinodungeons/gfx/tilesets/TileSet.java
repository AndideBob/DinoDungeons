package dinodungeons.gfx.tilesets;

import dinodungeons.gfx.GFXResourceID;

public enum TileSet {
	TEST(GFXResourceID.DUNGEON_TEST);
	
	private GFXResourceID resourceID;
	
	private TileSet(GFXResourceID resourceID){
		this.resourceID = resourceID;
	}
	
	public GFXResourceID getResourceID(){
		return resourceID;
	}
}
