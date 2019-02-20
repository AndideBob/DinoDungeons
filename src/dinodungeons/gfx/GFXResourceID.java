package dinodungeons.gfx;

import java.io.File;

import lwjgladapter.GameWindowConstants;

public enum GFXResourceID {
	EDITOR_SELECTOR(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderEDITOR + File.separator + "editorSelector.png"),
	TEXT(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTEXT + File.separator + "letters.png"),
	DUNGEON_TEST(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTILESETS + File.separator + "dungeonTest.png"),
	DUNGEON_RED(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTILESETS + File.separator + "dungeonRed.png"),
	CHARACTER(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderCHARACTERS + File.separator + "character.png");
	
	
	private static final String folderGFX = "gfx";
	private static final String folderTEXT = "text";
	private static final String folderCHARACTERS = "characters";
	private static final String folderTILESETS = "tilesets";
	private static final String folderEDITOR = "editor";

	private String filePath;
	
	private GFXResourceID(String filePath){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
