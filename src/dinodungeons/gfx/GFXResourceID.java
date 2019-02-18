package dinodungeons.gfx;

import java.io.File;

import lwjgladapter.GameWindowConstants;

public enum GFXResourceID {
	DUNGEON_TEST(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTILESETS + File.separator + "dungeonTest.png"),
	CHARACTER(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderCHARACTERS + File.separator + "character.png");
	
	
	private static final String folderGFX = "gfx";
	private static final String folderCHARACTERS = "characters";
	private static final String folderTILESETS = "tilesets";

	private String filePath;
	
	private GFXResourceID(String filePath){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
