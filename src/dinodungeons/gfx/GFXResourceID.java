package dinodungeons.gfx;

import java.io.File;

import lwjgladapter.GameWindowConstants;

public enum GFXResourceID {
	UI_BACKGROUND(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderUI + File.separator + "ui_background.png"),
	UI_HEALTH(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderUI + File.separator + "health.png"),
	UI_BORDERS(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderUI + File.separator + "ui_borders.png"),
	EDITOR_SELECTOR(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderEDITOR + File.separator + "editorSelector.png"),
	TEXT_WHITE(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTEXT + File.separator + "letters_white.png"),
	TEXT_BLACK(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTEXT + File.separator + "letters_black.png"),
	DUNGEON_TEST(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTILESETS + File.separator + "dungeonTest.png"),
	DUNGEON_RED(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderTILESETS + File.separator + "dungeonRed.png"),
	CHARACTER(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderCHARACTERS + File.separator + "character.png"),
	ITEMS(File.separator + GFXResourceID.folderGFX + File.separator + GFXResourceID.folderSPRITES + File.separator + "items.png");
	
	
	private static final String folderGFX = "gfx";
	private static final String folderTEXT = "text";
	private static final String folderCHARACTERS = "characters";
	private static final String folderTILESETS = "tilesets";
	private static final String folderEDITOR = "editor";
	private static final String folderSPRITES = "sprites";
	private static final String folderUI = "ui";

	private String filePath;
	
	private GFXResourceID(String filePath){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
