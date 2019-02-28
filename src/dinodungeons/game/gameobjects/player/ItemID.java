package dinodungeons.game.gameobjects.player;

public enum ItemID {
	CLUB("0",0),
	ITEM_1("1",1),
	ITEM_2("2",2),
	ITEM_3("3",3),
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

	public static ItemID getItemIDBySaveRepresentation(String saveRepresentation){
		for(ItemID iid : values()){
			if(iid.getSaveRepresentation().equals(saveRepresentation)){
				return iid;
			}
		}
		return CLUB;
	}
}
