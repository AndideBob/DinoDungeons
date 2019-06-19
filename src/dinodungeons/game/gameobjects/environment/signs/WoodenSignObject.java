package dinodungeons.game.gameobjects.environment.signs;

import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class WoodenSignObject extends BaseSignObject {

	private int colorVariant;
	
	public WoodenSignObject(int positionX, int positionY, int colorVariant, TextBoxContent text) {
		super(positionX, positionY, colorVariant, text);
	}
	
	@Override
	public void draw(int anchorX, int anchorY) {
		int positionOnSpriteSheet = 8 + colorVariant;
		SpriteManager.getInstance().getSprite(SpriteID.SIGNS).draw(positionOnSpriteSheet, anchorX + positionX, anchorY + positionY);
	}

}
