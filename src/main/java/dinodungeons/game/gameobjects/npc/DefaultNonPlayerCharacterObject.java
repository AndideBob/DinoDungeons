package dinodungeons.game.gameobjects.npc;

import java.util.Collection;

import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.game.gameobjects.text.TextBoxContent;
import dinodungeons.gfx.sprites.SpriteManager;

public class DefaultNonPlayerCharacterObject extends BaseNonPlayerCharacterObject {
	
	private NPCType npcType;

	public DefaultNonPlayerCharacterObject(int positionX, int positionY, NPCType npcType, Collection<TextBoxContent> text) {
		super(positionX, positionY, text);
		this.npcType = npcType;
	}

	@Override
	public void draw(int anchorX, int anchorY) {
		SpriteManager.getInstance().getSprite(npcType.getSpriteID()).draw(direction, anchorX + positionX, anchorY + positionY);
	}

}
