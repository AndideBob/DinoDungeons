package dinodungeons.game.data.map;

import java.io.File;

import lwjgladapter.GameWindowConstants;

public class ScreenMapConstants {
	
	public static final String mapDirectiory = GameWindowConstants.FILEPATH_DIRECTORY + File.separator + "data" + File.separator + "maps";
	public static final String mapFileExtension = ".ddm";

	public static final int minBaseLayerSelection = 0;
	public static final int maxBaseLayerSelection = 5;
	
	public static final int BASE_LAYER_WALL = 0;
	public static final int BASE_LAYER_BORDER = 1;
	public static final int BASE_LAYER_FLOOR_A = 2;
	public static final int BASE_LAYER_FLOOR_B = 3;
	public static final int BASE_LAYER_FLOOR_C = 4;
	public static final int BASE_LAYER_STAIRS = 5;

}
