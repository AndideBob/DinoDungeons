package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.StaticObjectButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class StaticObjectPlacementSelectionButton extends SelectionButton {
	
	private Editor editorHandle;
	
	private StaticObjectButtonGroup belongingButtonGroup;

	public StaticObjectPlacementSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_MISC);
		belongingButtonGroup = new StaticObjectButtonGroup(editorHandle);
		this.editorHandle = editorHandle;
	}

	@Override
	public void select(boolean select) {
		belongingButtonGroup.setActive(select);
		editorHandle.setMapChange(MapChangeType.NONE);
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
