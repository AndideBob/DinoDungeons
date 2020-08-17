package dinodungeons.game.data.map;

import java.io.File;

import lwjgladapter.GameWindowConstants;

public class ScreenMapConstants {
	
	public static final String mapDirectiory = GameWindowConstants.FILEPATH_DIRECTORY + File.separator + "data" + File.separator + "maps";
	public static final String mapFileExtension = ".ddm";

	public static final int minBaseLayerSelection = 0;
	public static final int maxBaseLayerSelection = 11;
	
	public static final int BASE_LAYER_WALL = 0;
	public static final int BASE_LAYER_BORDER = 1;
	public static final int BASE_LAYER_FLOOR_A = 2;
	public static final int BASE_LAYER_FLOOR_B = 3;
	public static final int BASE_LAYER_FLOOR_C = 4;
	public static final int BASE_LAYER_STAIRS = 5;
	public static final int BASE_LAYER_ENTRANCE_LEFT = 6;
	public static final int BASE_LAYER_ENTRANCE_RIGHT = 7;
	public static final int BASE_LAYER_DOOR_DOWN = 8;
	public static final int BASE_LAYER_DOOR_UP = 9;
	public static final int BASE_LAYER_DOOR_RIGHT = 10;
	public static final int BASE_LAYER_DOOR_LEFT = 11;

}
