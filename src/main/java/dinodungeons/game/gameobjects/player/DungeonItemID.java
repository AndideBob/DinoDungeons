package dinodungeons.game.gameobjects.player;

import dinodungeons.game.gameobjects.base.GameObjectTag;

public enum DungeonItemID {
	KEY_SMALL("k",16),
	KEY_BIG("K",17),
	MAP("M",18),
	UNDEFINED("3",19);
	
	private int spriteSheetPosition;
	
	private String saveRepresentation;
	
	private DungeonItemID(String saveRepresentation, int spriteSheetPosition){
		this.spriteSheetPosition = spriteSheetPosition;
		this.saveRepresentation = saveRepresentation;
	}

	public int getSpriteSheetPosition() {
		return spriteSheetPosition;
	}
	
	public String getSaveRepresentation() {
		return saveRepresentation;
	}

	public static DungeonItemID getDungeonItemIDBySaveRepresentation(String saveRepresentation){
		for(DungeonItemID iid : values()){
			if(iid.getSaveRepresentation().equals(saveRepresentation)){
				return iid;
			}
		}
		return DungeonItemID.KEY_SMALL;
	}
	
	public static DungeonItemID getItemIDByGameObjectTag(GameObjectTag tag){
		switch(tag){
		case COLLECTABLE_KEY_SMALL:
			return DungeonItemID.KEY_SMALL;
		case COLLECTABLE_KEY_BIG:
			return DungeonItemID.KEY_BIG;
		case COLLECTABLE_MAP:
			return DungeonItemID.MAP;
		default:
			return KEY_SMALL;
		}
	}
}
