package dinodungeons.sfx;

import java.io.File;

import lwjgladapter.GameWindowConstants;

public enum SFXResourceID {
	SFX_EXPLOSION(File.separator + SFXResourceID.folderSFX + File.separator + SFXResourceID.folderEffects + File.separator + "Explosion.ogg"),
	SFX_HIT_CLUB(File.separator + SFXResourceID.folderSFX + File.separator + SFXResourceID.folderEffects + File.separator + "Hit_Club.ogg"),
	SFX_PICKUP_COIN(File.separator + SFXResourceID.folderSFX + File.separator + SFXResourceID.folderEffects + File.separator + "Pickup_Coin.ogg"),
	MUSIC_MAIN_THEME(File.separator + SFXResourceID.folderSFX + File.separator + SFXResourceID.folderMusic + File.separator + "LOZOverworld.ogg");
	
	
	private static final String folderSFX = "sfx";
	private static final String folderEffects = "effects";
	private static final String folderMusic = "music";

	private String filePath;
	
	private SFXResourceID(String filePath){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + filePath;
	}

	public String getFilePath() {
		return filePath;
	}
}
