package dinodungeons.gfx.tilesets;

import dinodungeons.gfx.GFXResourceID;

public enum DungeonTileSet {
	TEST(GFXResourceID.DUNGEON_TEST);
	
	private GFXResourceID resourceID;
	
	private DungeonTileSet(GFXResourceID resourceID){
		this.resourceID = resourceID;
	}
	
	public GFXResourceID getResourceID(){
		return resourceID;
	}
}
