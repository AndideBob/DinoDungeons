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
	CANCEL(9),
	SELECTION_TILESET(10),
	SELECTION_COLLECTABLE_ITEMS(11),
	BASE_LAYER_WALL(12),
	BASE_LAYER_BORDER(13),
	BASE_LAYER_FLOOR_A(14),
	BASE_LAYER_FLOOR_B(15),
	BASE_LAYER_FLOOR_C(16),
	BASE_LAYER_STAIRS(17),
	BASE_LAYER_ENTRY_LEFT(18),
	BASE_LAYER_ENTRY_RIGHT(19),
	BASE_LAYER_DOOR_DOWN(20),
	BASE_LAYER_DOOR_UP(21),
	BASE_LAYER_DOOR_RIGHT(22),
	BASE_LAYER_DOOR_LEFT(23),
	ITEM_CLUB(24),
	ITEM_BOOMERANG(25),
	ITEM_2(26),
	ITEM_BOMB(27),
	ITEM_4(28),
	ITEM_5(29),
	ITEM_6(30),
	ITEM_7(31),
	ITEM_8(32),
	ITEM_9(33),
	ITEM_A(34),
	ITEM_B(35),
	ITEM_MIRROR(36),
	ITEM_D(37),
	ITEM_E(38),
	ITEM_F(39);
	
	private int positionOnSpriteSheet;
	
	private ButtonSprite(int positionOnSpriteSheet) {
		this.positionOnSpriteSheet = positionOnSpriteSheet;
	}
	
	public int getPositionOnSpriteSheet() {
		return positionOnSpriteSheet;
	}
}
