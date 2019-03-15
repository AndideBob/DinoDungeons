package dinodungeons.gfx.tilesets;

import dinodungeons.gfx.GFXResourceID;

public enum TileSet {
	CAVE_GREEN(GFXResourceID.TILESET_CAVE_GREEN, "caveGrn", 0),
	CAVE_RED(GFXResourceID.TILESET_CAVE_RED, "caveRed", 1);
	
	private GFXResourceID resourceID;
	private String representationInFile;
	private int colorVariation;
	
	private TileSet(GFXResourceID resourceID, String representationInFile, int colorVariation){
		this.resourceID = resourceID;
		this.representationInFile = representationInFile.toLowerCase();
		this.colorVariation = colorVariation;
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

	public int getColorVariation() {
		return colorVariation;
	}
	
}
