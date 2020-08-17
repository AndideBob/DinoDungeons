package dinodungeons.sfx;

import java.io.File;

import lwjgladapter.GameWindowConstants;

class FolderConstants{
	static final String folderSFX = File.separator + "sfx";
	static final String folderEffects = folderSFX + File.separator + "effects";
	static final String folderMusic = folderSFX + File.separator + "music";
}

public enum SFXResourceID {
	SFX_BOOMERANG_FLY(FolderConstants.folderEffects, "BoomerangFly.ogg"),
	SFX_BOOMERANG_HIT(FolderConstants.folderEffects, "BoomerangHit.ogg"),
	SFX_EXPLOSION(FolderConstants.folderEffects, "Explosion.ogg"),
	SFX_HIT_CLUB(FolderConstants.folderEffects, "Hit_Club.ogg"),
	SFX_PICKUP_COIN(FolderConstants.folderEffects, "Pickup_Coin.ogg"),
	SFX_PICKUP_HEALTH(FolderConstants.folderEffects, "Pickup_Health.ogg"),
	SFX_DESTROY_BUSH(FolderConstants.folderEffects, "Bush_Destroy.ogg"),
	SFX_DESTROY_ENEMY(FolderConstants.folderEffects, "Enemy_Destroyed.ogg"),
	SFX_PLAYER_DAMAGE(FolderConstants.folderEffects, "Damage_Taken.ogg"),
	MUSIC_MAIN_THEME(FolderConstants.folderMusic, "LOZOverworld.ogg");
	
	
	

	private String filePath;
	
	private SFXResourceID(String folder, String fileName){
		this.filePath = GameWindowConstants.FILEPATH_DIRECTORY + folder + File.separator + fileName;
	}

	public String getFilePath() {
		return filePath;
	}
}
