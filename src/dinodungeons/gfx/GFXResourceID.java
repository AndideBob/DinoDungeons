package dinodungeons.gfx;

import java.io.File;

import lwjgladapter.GameWindowConstants;

class FolderConstants{
	static final String folderGFX = File.separator + "gfx";
	
	static final String folderTEXT = folderGFX + File.separator + "text";
	static final String folderCHARACTERS = folderGFX + File.separator + "characters";
	static final String folderTILESETS = folderGFX + File.separator + "tilesets";
	static final String folderEDITOR = folderGFX + File.separator + "editor";
	static final String folderSPRITES = folderGFX + File.separator + "sprites";
	static final String folderUI = folderGFX + File.separator + "ui";
	
	static final String folderITEMS = folderSPRITES + File.separator + "items";
	static final String folderIMMOVABLE = folderSPRITES + File.separator + "immovable";
	static final String folderENVIRONMENT = folderSPRITES + File.separator + "environment";
	static final String folderPARTICLES = folderSPRITES + File.separator + "particles";
}

public enum GFXResourceID {
	//EDITOR
	EDITOR_SELECTOR(FolderConstants.folderEDITOR, "editorSelector.png"),
	//UI
	UI_BACKGROUND(FolderConstants.folderUI, "ui_background.png"),
	UI_HEALTH(FolderConstants.folderUI, "health.png"),
	UI_BORDERS(FolderConstants.folderUI, "ui_borders.png"),
	//TEXT
	TEXT_WHITE(FolderConstants.folderTEXT, "letters_white.png"),
	TEXT_BLACK(FolderConstants.folderTEXT, "letters_black.png"),
	//TILESETS
	TILESET_CAVE_GREEN(FolderConstants.folderTILESETS, "caveGreen.png"),
	TILESET_CAVE_RED(FolderConstants.folderTILESETS, "caveRed.png"),
	//CHARACTERS
	CHARACTER(FolderConstants.folderCHARACTERS, "character.png"),
	ENEMY_BAT_GREEN(FolderConstants.folderCHARACTERS, "bat_green.png"),
	//SPRITES-------------
	ITEMS(FolderConstants.folderSPRITES, "items.png"),
	COLLECTABLES(FolderConstants.folderSPRITES, "collectables.png"),
	PARTICLES_A(FolderConstants.folderPARTICLES, "particlesA.png"),
	PARTICLES_B(FolderConstants.folderPARTICLES, "particlesB.png"),
	EXPLOSION(FolderConstants.folderPARTICLES, "explosion.png"),
	//IMMOVABLES
	SPRITE_SPIKES(FolderConstants.folderIMMOVABLE, "spikes.png"),
	//ENVIRONMENT
	SPRITE_DESTRUCTABLES(FolderConstants.folderENVIRONMENT, "destructables.png"),
	//ITEMS
	ITEM_CLUB(FolderConstants.folderITEMS, "club.png");

	private String filePath;
	
	private GFXResourceID(String folderPath, String filename){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + folderPath + File.separator + filename;
	}

	public String getFilePath() {
		return filePath;
	}
}
