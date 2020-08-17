package dinodungeons.editor.ui.buttons.filemanagement;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.input.InputUsage;
import dinodungeons.game.data.gameplay.InputInformation;

public class ButtonSaveMap extends BaseButton {
	
	private Editor editorHandle;

	public ButtonSaveMap(int positionX, int positionY, Editor editorHandle) {
		super(positionX, positionY, ButtonSprite.SAVE_MAP);
		this.editorHandle = editorHandle;
	}

	@Override
	protected void onClick() {
		editorHandle.waitForInput("Enter map id", InputUsage.SAVING);
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
