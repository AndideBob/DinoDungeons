package dinodungeons.editor.ui.buttons;

public enum ButtonSprite {
	NEW_MAP(0),
	SAVE_MAP(1),
	LOAD_MAP(2),
	SET_DUNGEON_ID(3),
	SET_ROOM_UP(4),
	SET_ROOM_DOWN(5),
	SET_ROOM_LEFT(6),
	SET_ROOM_RIGHT(7),
	CONFIRM(8),
	CANCEL(9);
	
	private int positionOnSpriteSheet;
	
	private ButtonSprite(int positionOnSpriteSheet) {
		this.positionOnSpriteSheet = positionOnSpriteSheet;
	}
	
	public int getPositionOnSpriteSheet() {
		return positionOnSpriteSheet;
	}
}
