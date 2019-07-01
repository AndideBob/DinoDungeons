package dinodungeons.game.gameobjects.npc;

import java.util.Collection;

import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class TestNonPlayerCharacterObject extends BaseNonPlayerCharacterObject {

	public TestNonPlayerCharacterObject(int positionX, int positionY, Collection<TextBoxContent> text) {
		super(positionX, positionY, text);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(SpriteID.PLAYER).draw(direction, anchorX + positionX, anchorY + positionY);

	}

}
