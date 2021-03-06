package dinodungeons.editor.ui.buttons.mapsettings;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.input.InputUsage;
import dinodungeons.game.data.gameplay.InputInformation;

public class ButtonSetMapDungeon extends BaseButton {
	
	private Editor editorHandle;

	public ButtonSetMapDungeon(int positionX, int positionY, Editor editorHandle) {
		super(positionX, positionY, ButtonSprite.SET_DUNGEON_ID);
		this.editorHandle = editorHandle;
	}

	@Override
	protected void onClick() {
		editorHandle.waitForInput("dungeon id", InputUsage.DUNGEON_ID);		
	}

	@Override
	protected void updateInternal(InputInformation inputInformation) {
		//Do nothing
	}

	@Override
	protected void drawInternal() {
		//Do nothing
	}
}
