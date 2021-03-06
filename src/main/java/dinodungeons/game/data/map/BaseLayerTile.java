package dinodungeons.game.data.map;

import java.util.Arrays;
import java.util.List;

public enum BaseLayerTile {
	NOTHING(0),
	FLOOR_NORMAL(1),
	FLOOR_ALT_A(4),
	FLOOR_ALT_B(5),
	WALL_DOWN(2),
	WALL_UP(3),
	WALL_RIGHT(6),
	WALL_LEFT(7),
	WALL_BOTTOM_RIGHT_IN(8),
	WALL_BOTTOM_LEFT_IN(9),
	WALL_TOP_RIGHT_IN(12),
	WALL_TOP_LEFT_IN(13),
	WALL_TOP_RIGHT_OUT(11),
	WALL_TOP_LEFT_OUT(10),
	WALL_BOTTOM_LEFT_OUT(14),
	WALL_BOTTOM_RIGHT_OUT(15),
	BORDER_DOWN(25),
	BORDER_UP(24),
	BORDER_RIGHT(29),
	BORDER_LEFT(28),
	BORDER_BOTTOM_RIGHT_IN(16),
	BORDER_BOTTOM_LEFT_IN(17),
	BORDER_TOP_RIGHT_IN(20),
	BORDER_TOP_LEFT_IN(21),
	BORDER_TOP_RIGHT_OUT(19),
	BORDER_TOP_LEFT_OUT(18),
	BORDER_BOTTOM_LEFT_OUT(22),
	BORDER_BOTTOM_RIGHT_OUT(23),
	BORDER_FULL(27),
	STAIRS(26),
	ENTRANCE_LEFT(30),
	ENTRANCE_RIGHT(31),
	DOOR_DOWN(32),
	DOOR_UP(33),
	DOOR_RIGHT(36),
	DOOR_LEFT(37),
	BLOCKED_DOOR_DOWN(34),
	BLOCKED_DOOR_UP(35),
	BLOCKED_DOOR_RIGHT(38),
	BLOCKED_DOOR_LEFT(39);
	
	public static final List<BaseLayerTile> floorTiles = Arrays.asList(FLOOR_NORMAL, FLOOR_ALT_A, FLOOR_ALT_B);
	
	private int tileSetPosition;
	
	private BaseLayerTile(int tileSetPosition){
		this.tileSetPosition = tileSetPosition;
	}
	
	public int getTileSetPosition(){
		return tileSetPosition;
	}
}
