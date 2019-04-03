package dinodungeons.editor.ui.buttons.filemanagement;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.input.InputUsage;

public class ButtonLoadMap extends BaseButton {
	
	private Editor editorHandle;

	public ButtonLoadMap(int positionX, int positionY, Editor editorHandle) {
		super(positionX, positionY, ButtonSprite.LOAD_MAP);
		this.editorHandle = editorHandle;
	}

	@Override
	protected void onClick() {
		editorHandle.waitForInput("Enter map id", InputUsage.LOAD);
	}

}
