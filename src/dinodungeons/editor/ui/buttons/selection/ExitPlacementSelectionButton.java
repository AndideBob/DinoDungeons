package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.exits.ExitPlacementUIGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class ExitPlacementSelectionButton extends SelectionButton {
	
	private ExitPlacementUIGroup belongingUIGroup;

	public ExitPlacementSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_EXIT);
		belongingUIGroup = new ExitPlacementUIGroup(editorHandle);
	}

	@Override
	public void select(boolean select) {
		belongingUIGroup.setActive(select);
	}
	
	@Override
	protected void updateInternal(InputInformation inputInformation) {
		belongingUIGroup.update(inputInformation);
	}

	@Override
	protected void drawInternal() {
		belongingUIGroup.draw();
	}

}
