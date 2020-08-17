package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.general.SwitchPlacementUIGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class SwitchPlacementSelectionButton extends SelectionButton {
	
	private SwitchPlacementUIGroup belongingButtonGroup;

	public SwitchPlacementSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_SWITCH);
		belongingButtonGroup = new SwitchPlacementUIGroup(editorHandle);
	}

	@Override
	public void select(boolean select) {
		belongingButtonGroup.setActive(select);
	}
	
	@Override
	protected void updateInternal(InputInformation inputInformation) {
		belongingButtonGroup.update(inputInformation);
	}

	@Override
	protected void drawInternal() {
		belongingButtonGroup.draw();
	}

}
