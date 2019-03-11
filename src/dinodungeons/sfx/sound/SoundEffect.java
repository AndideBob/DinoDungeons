package dinodungeons.sfx.sound;

import dinodungeons.sfx.SFXResourceID;

public enum SoundEffect {
	EXPLOSION(SFXResourceID.SFX_EXPLOSION),
	HIT_CLUB(SFXResourceID.SFX_HIT_CLUB),
	PICKUP_COIN(SFXResourceID.SFX_PICKUP_COIN);
	
	private SFXResourceID sfxResourceID;
	
	private SoundEffect(SFXResourceID sfxResourceID) {
		this.sfxResourceID = sfxResourceID;
	}
	
	public SFXResourceID getSfxResourceID() {
		return sfxResourceID;
	}
}
