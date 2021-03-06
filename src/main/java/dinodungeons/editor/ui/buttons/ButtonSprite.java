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
	SELECTION_BASE_LAYER(10),
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
	ITEM_F(39),
	SELECTION_SWITCH(40),
	SWITCH_A(41),
	SWITCH_B(42),
	SWITCH_C(43),
	SWITCH_D(44),
	SWITCH_AB(45),
	SWITCH_ABC(46),
	SWITCH_ABCD(47),
	OBJECT_STONE_BLOCK(48),
	OBJECT_CANDLE(49),
	SELECTION_DOOR(56),
	DOOR_ENEMY(57),
	DOOR_SWITCH_A(58),
	DOOR_SWITCH_B(59),
	DOOR_SWITCH_C(60),
	DOOR_SWITCH_D(61),
	DOOR_SWITCH_AB(62),
	DOOR_SWITCH_ABC(63),
	DOOR_SWITCH_ABCD(64),
	DOOR_KEY_SMALL(65),
	DOOR_KEY_BIG(66),
	SELECTION_TILESET(72),
	TILESET_CAVE_RED(73),
	TILESET_CAVE_GREEN(74),
	TILESET_DUNGEON_ORANGE(75),
	TILESET_DUNGEON_PINK(76),
	SELECTION_EXIT(88),
	EXIT_INSTANT(89),
	EXIT_CAVE_ENTRANCE(88),
	EXIT_CAVE_EXIT(90),
	EXIT_STAIRS(91),
	EXIT_BLOCKED_CAVE_ENTRANCE(92),
	SELECTION_MISC(96),
	DESTRUTCTABLE_BUSH(97),
	DESTRUTCTABLE_BLOCK(98),
	SPIKES_WOOD(104),
	SPIKES_METAL(105),
	SELECTION_ENEMY(112),
	ENEMY_BAT_GREEN(113),
	ENEMY_TRICERABLOB(114),
	ENEMY_CROCDROP(115),
	SELECTION_ERASE(136),
	PAGE_ADD(137),
	PAGE_REMOVE(138),
	PAGE_FORWARD(139),
	PAGE_BACKWARDS(140),
	SIGN_WOODEN(144),
	SIGN_STONE(145),
	BUILDING_BASIC_HUT(152),
	BUILDING_STORE_A(153),
	NPC_OLD_MAN(168),
	NPC_TIKI_VILLAGER_A(169),
	NPC_TIKI_VILLAGER_B(170);
	
	private int positionOnSpriteSheet;
	
	private ButtonSprite(int positionOnSpriteSheet) {
		this.positionOnSpriteSheet = positionOnSpriteSheet;
	}
	
	public int getPositionOnSpriteSheet() {
		return positionOnSpriteSheet;
	}
}
