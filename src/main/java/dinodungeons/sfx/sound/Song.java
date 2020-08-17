package dinodungeons.sfx.sound;

import dinodungeons.sfx.SFXResourceID;

public enum Song {
	MAIN_THEME(SFXResourceID.MUSIC_MAIN_THEME);
	
	private SFXResourceID sfxResourceID;
	
	private Song(SFXResourceID sfxResourceID) {
		this.sfxResourceID = sfxResourceID;
	}
	
	public SFXResourceID getSfxResourceID() {
		return sfxResourceID;
	}
}
