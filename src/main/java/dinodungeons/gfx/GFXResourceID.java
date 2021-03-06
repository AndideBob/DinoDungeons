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
	static final String folderBUILDINGS = folderSPRITES + File.separator + "buildings";
}

public enum GFXResourceID {
	//EDITOR
	EDITOR_SELECTOR(FolderConstants.folderEDITOR, "editorSelector.png"),
	EDITOR_BUTTONS(FolderConstants.folderEDITOR, "buttons.png"),
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
	TILESET_DUNGEON_ORANGE(FolderConstants.folderTILESETS, "dungeonOrange.png"),
	TILESET_DUNGEON_PINK(FolderConstants.folderTILESETS, "dungeonPink.png"),
	TILESET_INTERIOR_HUT(FolderConstants.folderTILESETS, "interiorHut.png"),
	//CHARACTERS
	CHARACTER(FolderConstants.folderCHARACTERS, "character.png"),
	NPC_OLD_MAN(FolderConstants.folderCHARACTERS, "npc_OldMan.png"),
	NPC_TIKI_VILAGER_A(FolderConstants.folderCHARACTERS, "npc_TikiVillagerA.png"),
	NPC_TIKI_VILAGER_B(FolderConstants.folderCHARACTERS, "npc_TikiVillagerB.png"),
	ENEMY_BAT_GREEN(FolderConstants.folderCHARACTERS, "bat_green.png"),
	ENEMY_TRICERABLOB(FolderConstants.folderCHARACTERS, "tricerablob.png"),
	ENEMY_CROCDROP(FolderConstants.folderCHARACTERS, "crocdrop.png"),
	//SPRITES-------------
	ITEMS(FolderConstants.folderSPRITES, "items.png"),
	COLLECTABLES(FolderConstants.folderSPRITES, "collectables.png"),
	PARTICLES_A(FolderConstants.folderPARTICLES, "particlesA.png"),
	PARTICLES_B(FolderConstants.folderPARTICLES, "particlesB.png"),
	PARTICLES_C(FolderConstants.folderPARTICLES, "particlesC.png"),
	EXPLOSION(FolderConstants.folderPARTICLES, "explosion.png"),
	FIRE(FolderConstants.folderPARTICLES, "fire.png"),
	//BUILDINGS
	BUILDING_BASIC_HUT(FolderConstants.folderBUILDINGS, "basic_hut.png"),
	BUILDING_STORE_A(FolderConstants.folderBUILDINGS, "storeA.png"),
	//IMMOVABLES
	SPRITE_SPIKES(FolderConstants.folderIMMOVABLE, "spikes.png"),
	//ENVIRONMENT
	SPRITE_PUSHABLES(FolderConstants.folderENVIRONMENT, "pushables.png"),
	SPRITE_DESTRUCTABLES(FolderConstants.folderENVIRONMENT, "destructables.png"),
	SPRITE_DOORS(FolderConstants.folderENVIRONMENT, "doors.png"),
	SPRITE_CANDLE(FolderConstants.folderENVIRONMENT, "candle.png"),
	SPRITE_SIGNS(FolderConstants.folderENVIRONMENT, "signs.png"),
	//ITEMS
	ITEM_CLUB(FolderConstants.folderITEMS, "club.png"),
	ITEM_BOMB(FolderConstants.folderITEMS, "bomb.png"),
	ITEM_BOOMERANG(FolderConstants.folderITEMS, "boomerang.png");

	private String filePath;
	
	private GFXResourceID(String folderPath, String filename){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + folderPath + File.separator + filename;
	}

	public String getFilePath() {
		return filePath;
	}
}
