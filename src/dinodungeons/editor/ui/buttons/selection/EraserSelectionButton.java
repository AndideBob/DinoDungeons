package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.game.data.gameplay.InputInformation;

public class EraserSelectionButton extends SelectionButton {
	
	private Editor editorHandle;

	public EraserSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_ERASE);
		this.editorHandle = editorHandle;
	}

	@Override
	public void select(boolean select) {
		editorHandle.setMapChange(MapChangeType.ERASE);
	}
	
	@Override
	protected void updateInternal(InputInformation inputInformation) {
	}

	@Override
	protected void drawInternal() {
	}

}
