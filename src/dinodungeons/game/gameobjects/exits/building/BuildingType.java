package dinodungeons.game.gameobjects.exits.building;

import dinodungeons.gfx.sprites.SpriteID;

public enum BuildingType {
	BASIC_HUT(SpriteID.BUILDING_BASIC_HUT, "BH"),
	STORE_A(SpriteID.BUILDING_STORE_A, "SA");
	
	private SpriteID spriteID;
	
	private String stringRepresentation;
	
	private BuildingType(SpriteID spriteID, String stringRepresentation){
		this.spriteID = spriteID;
		this.stringRepresentation = stringRepresentation;
	}
	
	public SpriteID getSpriteID(){
		return spriteID;
	}
	
	public String getStringRepresentation(){
		return stringRepresentation;
	}
	
	public static BuildingType getByStringRepresentation(String representation){
		for(BuildingType t : values()){
			if(t.getStringRepresentation().equalsIgnoreCase(representation)){
				return t;
			}
		}
		return BASIC_HUT;
	}
}
