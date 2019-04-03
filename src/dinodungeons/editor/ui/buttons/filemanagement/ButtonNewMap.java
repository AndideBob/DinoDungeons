package dinodungeons.editor.ui.buttons.filemanagement;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.ButtonSprite;

public class ButtonNewMap extends BaseButton {
	
	private Editor editorHandle;

	public ButtonNewMap(int positionX, int positionY, Editor editorHandle) {
		super(positionX, positionY, ButtonSprite.NEW_MAP);
		this.editorHandle = editorHandle;
	}

	@Override
	protected void onClick() {
		editorHandle.openNewMap();
	}

}
