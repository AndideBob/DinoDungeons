package dinodungeons.gfx.sprites;

import dinodungeons.gfx.GFXResourceID;

public enum SpriteID {
	BACKGROUNDS(GFXResourceID.UI_BACKGROUND, 1, 1),
	EDITOR_BUTTONS(GFXResourceID.EDITOR_BUTTONS, 16, 16),
	EDITOR_SELECTOR(GFXResourceID.EDITOR_SELECTOR, 16, 16),
	PLAYER(GFXResourceID.CHARACTER, 16, 16),
	ITEMS(GFXResourceID.ITEMS, 16, 16),
	COLLECTABLES(GFXResourceID.COLLECTABLES, 10, 10),
	SPIKES(GFXResourceID.SPRITE_SPIKES, 16, 16),
	WEAPON_CLUB(GFXResourceID.ITEM_CLUB, 16, 16),
	WEAPON_BOMB(GFXResourceID.ITEM_BOMB, 16, 16),
	WEAPON_BOOMERANG(GFXResourceID.ITEM_BOOMERANG, 12, 12),
	FIRE(GFXResourceID.FIRE, 16, 16),
	PUSHABLES(GFXResourceID.SPRITE_PUSHABLES, 16, 16),
	DESTRUCTABLES(GFXResourceID.SPRITE_DESTRUCTABLES, 16, 16),
	DOORS(GFXResourceID.SPRITE_DOORS, 16, 16),
	CANDLE(GFXResourceID.SPRITE_CANDLE, 16, 16),
	PARTICLES_A(GFXResourceID.PARTICLES_A, 8, 8),
	PARTICLES_B(GFXResourceID.PARTICLES_B, 16, 16),
	EXPLOSION(GFXResourceID.EXPLOSION, 32, 32),
	ENEMY_BAT_GREEN(GFXResourceID.ENEMY_BAT_GREEN, 24, 16);
	
	private GFXResourceID gfxResourceID;
	
	private int width;
	private int height;
	
	private SpriteID(GFXResourceID gfxResourceID, int width, int height){
		this.gfxResourceID = gfxResourceID;
		this.width = width;
		this.height = height;
	}

	public GFXResourceID getGfxResourceID() {
		return gfxResourceID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
