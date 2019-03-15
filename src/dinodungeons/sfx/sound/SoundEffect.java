package dinodungeons.sfx.sound;

import dinodungeons.sfx.SFXResourceID;

public enum SoundEffect {
	EXPLOSION(SFXResourceID.SFX_EXPLOSION),
	HIT_CLUB(SFXResourceID.SFX_HIT_CLUB),
	PICKUP_COIN(SFXResourceID.SFX_PICKUP_COIN),
	PICKUP_HEALTH(SFXResourceID.SFX_PICKUP_HEALTH),
	DESTROY_BUSH(SFXResourceID.SFX_DESTROY_BUSH),
	DESTROY_ENEMY(SFXResourceID.SFX_DESTROY_ENEMY),
	PLAYER_DAMAGE(SFXResourceID.SFX_PLAYER_DAMAGE);
	
	private SFXResourceID sfxResourceID;
	
	private SoundEffect(SFXResourceID sfxResourceID) {
		this.sfxResourceID = sfxResourceID;
	}
	
	public SFXResourceID getSfxResourceID() {
		return sfxResourceID;
	}
}
