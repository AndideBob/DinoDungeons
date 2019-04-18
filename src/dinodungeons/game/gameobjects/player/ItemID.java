package dinodungeons.game.gameobjects.player;

import dinodungeons.game.gameobjects.base.GameObjectTag;

public enum ItemID {
	CLUB("0",0),
	BOOMERANG("1",1),
	TORCH("2",2),
	BOMB("3",3),
	ITEM_4("4",4),
	ITEM_5("5",5),
	ITEM_6("6",6),
	ITEM_7("7",7),
	ITEM_8("8",8),
	ITEM_9("9",9),
	ITEM_A("A",10),
	ITEM_B("B",11),
	MIRROR("C",12),
	ITEM_D("D",13),
	ITEM_E("E",14),
	ITEM_F("F",15);
	
	private int spriteSheetPosition;
	
	private String saveRepresentation;
	
	private ItemID(String saveRepresentation, int spriteSheetPosition){
		this.spriteSheetPosition = spriteSheetPosition;
		this.saveRepresentation = saveRepresentation;
	}

	public int getSpriteSheetPosition() {
		return spriteSheetPosition;
	}
	
	public String getSaveRepresentation() {
		return saveRepresentation;
	}
	
	public static ItemID getItemIDBySpriteSheetPosition(int spriteSheetPosition){
		for(ItemID iid : values()){
			if(iid.getSpriteSheetPosition() == spriteSheetPosition){
				return iid;
			}
		}
		return CLUB;
	}

	public static ItemID getItemIDBySaveRepresentation(String saveRepresentation){
		for(ItemID iid : values()){
			if(iid.getSaveRepresentation().equals(saveRepresentation)){
				return iid;
			}
		}
		return CLUB;
	}
	
	public static ItemID getItemIDByGameObjectTag(GameObjectTag tag){
		switch(tag){
		case COLLECTABLE_ITEM_CLUB:
			return ItemID.CLUB;
		case COLLECTABLE_ITEM_BOOMERANG:
			return ItemID.BOOMERANG;
		case COLLECTABLE_ITEM_ITEM_2:
			return ItemID.TORCH;
		case COLLECTABLE_ITEM_BOMB:
			return ItemID.BOMB;
		case COLLECTABLE_ITEM_ITEM_4:
			return ItemID.ITEM_4;
		case COLLECTABLE_ITEM_ITEM_5:
			return ItemID.ITEM_5;
		case COLLECTABLE_ITEM_ITEM_6:
			return ItemID.ITEM_6;
		case COLLECTABLE_ITEM_ITEM_7:
			return ItemID.ITEM_7;
		case COLLECTABLE_ITEM_ITEM_8:
			return ItemID.ITEM_8;
		case COLLECTABLE_ITEM_ITEM_9:
			return ItemID.ITEM_9;
		case COLLECTABLE_ITEM_ITEM_A:
			return ItemID.ITEM_A;
		case COLLECTABLE_ITEM_ITEM_B:
			return ItemID.ITEM_B;
		case COLLECTABLE_ITEM_MIRROR:
			return ItemID.MIRROR;
		case COLLECTABLE_ITEM_ITEM_D:
			return ItemID.ITEM_D;
		case COLLECTABLE_ITEM_ITEM_E:
			return ItemID.ITEM_E;
		case COLLECTABLE_ITEM_ITEM_F:
			return ItemID.ITEM_F;
		default:
			return CLUB;
		}
	}
}
