package dinodungeons.editor.ui.buttons.mapchange;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.UIButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.map.objects.NonPlayerCharacterMapObject.NPCType;
import dinodungeons.game.gameobjects.text.TextBoxContent;

public class NPCChangeButton extends BaseButton {
	
	private UIButtonGroup belongingButtonGroup;
	
	private Editor editorHandle;
	
	private NPCType npcType;
	
	public NPCChangeButton(int positionX, int positionY, final Editor editorHandle, final UIButtonGroup belongingButtonGroup, NPCType npcType) {
		super(positionX, positionY, getButtonSpriteForNPCType(npcType));
		this.npcType = npcType;
		this.editorHandle = editorHandle;
		this.belongingButtonGroup = belongingButtonGroup;
	}

	@Override
	protected void onClick() {
		belongingButtonGroup.unpressAll();
		//setPressed(true);
		editorHandle.waitForPageInput(npcType, "NPC", new TextBoxContent());
	}
	
	private static ButtonSprite getButtonSpriteForNPCType(NPCType npcType){
		switch (npcType) {
		case DEFAULT_OLD_MAN:
			return ButtonSprite.NPC_OLD_MAN;
		case DEFAULT_TIKI_VILLAGER_A:
			return ButtonSprite.NPC_TIKI_VILLAGER_A;
		case DEFAULT_TIKI_VILLAGER_B:
			return ButtonSprite.NPC_TIKI_VILLAGER_B;
		}
		return ButtonSprite.CANCEL;
	}

	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		//Do nothing
	}
	
	public enum StaticObjectType{
		IMMOVABLE_BLOCK,
		SPIKES_METAL,
		SPIKES_WOOD,
		DESTRUCTABLE_GRASS,
		DESTRUCTABLE_STONE
	}

}
