package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.CollectableItemPlacementButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class CollectableItemPlacementSelectionButton extends SelectionButton {
	
	private CollectableItemPlacementButtonGroup belongingButtonGroup;

	public CollectableItemPlacementSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_COLLECTABLE_ITEMS);
		belongingButtonGroup = new CollectableItemPlacementButtonGroup(editorHandle);
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
