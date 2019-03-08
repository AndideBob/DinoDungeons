package dinodungeons.gfx.tilesets;

import dinodungeons.gfx.GFXResourceID;

public enum TileSet {
	CAVE_GREEN(GFXResourceID.TILESET_CAVE_GREEN, "caveGrn"),
	CAVE_RED(GFXResourceID.TILESET_CAVE_RED, "caveRed");
	
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
		return CAVE_GREEN;
	}
	
	public GFXResourceID getResourceID(){
		return resourceID;
	}
	
	public String getRepresentationInFile(){
		return representationInFile.toLowerCase();
	}
}
