package dinodungeons.gfx.sprites;

import dinodungeons.gfx.GFXResourceID;

public enum SpriteID {
	BACKGROUNDS(GFXResourceID.UI_BACKGROUND, 1, 1),
	PLAYER(GFXResourceID.CHARACTER, 16, 16),
	ITEMS(GFXResourceID.ITEMS, 16, 16),
	COLLECTABLES(GFXResourceID.COLLECTABLES, 10, 10),
	SPIKES(GFXResourceID.SPRITE_SPIKES, 16, 16),
	WEAPON_CLUB(GFXResourceID.ITEM_CLUB, 16, 16);
	
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
