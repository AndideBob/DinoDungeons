package dinodungeons.editor.ui.buttons.selection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.EditorMapManager;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.EditorUIHandler;
import dinodungeons.editor.ui.buttons.ButtonSprite;
import dinodungeons.editor.ui.groups.buttons.TilesetSelectionButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;

public class TileSetSelectionButton extends SelectionButton {
	
	private Editor editorHandle;
	
	private TilesetSelectionButtonGroup belongingButtonGroup;

	public TileSetSelectionButton(int positionX, int positionY, final EditorUIHandler uiHandler, final Editor editorHandle, final EditorMapManager mapManagerHandle) {
		super(positionX, positionY, uiHandler, ButtonSprite.SELECTION_TILESET);
		this.editorHandle = editorHandle;
		belongingButtonGroup = new TilesetSelectionButtonGroup(editorHandle, mapManagerHandle);
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
