package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.TileEditorButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class BaseLayerSelectionButton extends SelectionButton {
	
	private Editor editorHandle;
	
	private TileEditorButtonGroup belongingButtonGroup;

	public BaseLayerSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_BASE_LAYER);
		this.editorHandle = editorHandle;
		belongingButtonGroup = new TileEditorButtonGroup(editorHandle);
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
