package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.EnemyButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class EnemyPlacementSelectionButton extends SelectionButton {
	
	private Editor editorHandle;
	
	private EnemyButtonGroup belongingButtonGroup;

	public EnemyPlacementSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_ENEMY);
		belongingButtonGroup = new EnemyButtonGroup(editorHandle);
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
