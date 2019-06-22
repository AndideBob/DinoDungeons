package dinodungeons.game.gameobjects.environment.signs;

import java.util.Collection;

import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class StoneSignObject extends BaseSignObject {

	private int colorVariant;
	
	public StoneSignObject(int positionX, int positionY, int colorVariant, Collection<TextBoxContent> text) {
		super(positionX, positionY, colorVariant, text);
	}
	
	@Override
	public void draw(int anchorX, int anchorY) {
		int positionOnSpriteSheet = 0 + colorVariant;
		SpriteManager.getInstance().getSprite(SpriteID.SIGNS).draw(positionOnSpriteSheet, anchorX + positionX, anchorY + positionY);
	}

}
