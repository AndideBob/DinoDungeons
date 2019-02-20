package dinodungeons.gfx.tilesets;

import dinodungeons.gfx.GFXResourceID;

public enum TileSet {
	NONE(GFXResourceID.DUNGEON_TEST, "test");
	
	private GFXResourceID resourceID;
	private String representationInFile;
	
	private TileSet(GFXResourceID resourceID, String representationInFile){
		this.resourceID = resourceID;
		this.representationInFile = representationInFile.toLowerCase();
	}
	
	public static TileSet getTileSetFromRepesentation(String representation){
		for(TileSet ts : values()){
			if(ts.getRepresentationInFile().equals(representation)){
				return ts;
			}
		}
		return NONE;
	}
	
	public GFXResourceID getResourceID(){
		return resourceID;
	}
	
	public String getRepresentationInFile(){
		return representationInFile.toLowerCase();
	}
}
