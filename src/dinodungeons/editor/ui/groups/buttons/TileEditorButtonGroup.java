package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.tileset.BaseLayerChangeButton;
import dinodungeons.game.data.map.ScreenMapConstants;

public class TileEditorButtonGroup extends UIButtonGroup {
	
	public TileEditorButtonGroup(final Editor editorHandle){
		super(editorHandle);
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new BaseLayerChangeButton(256, 208, editorHandle, ScreenMapConstants.BASE_LAYER_WALL));
		buttons.add(new BaseLayerChangeButton(272, 208, editorHandle, ScreenMapConstants.BASE_LAYER_BORDER));
		buttons.add(new BaseLayerChangeButton(288, 208, editorHandle, ScreenMapConstants.BASE_LAYER_FLOOR_A));
		buttons.add(new BaseLayerChangeButton(304, 208, editorHandle, ScreenMapConstants.BASE_LAYER_FLOOR_B));
		buttons.add(new BaseLayerChangeButton(256, 192, editorHandle, ScreenMapConstants.BASE_LAYER_FLOOR_C));
		buttons.add(new BaseLayerChangeButton(272, 192, editorHandle, ScreenMapConstants.BASE_LAYER_STAIRS));
		buttons.add(new BaseLayerChangeButton(288, 192, editorHandle, ScreenMapConstants.BASE_LAYER_ENTRANCE_LEFT));
		buttons.add(new BaseLayerChangeButton(304, 192, editorHandle, ScreenMapConstants.BASE_LAYER_ENTRANCE_RIGHT));
		buttons.add(new BaseLayerChangeButton(256, 176, editorHandle, ScreenMapConstants.BASE_LAYER_DOOR_DOWN));
		buttons.add(new BaseLayerChangeButton(272, 176, editorHandle, ScreenMapConstants.BASE_LAYER_DOOR_UP));
		buttons.add(new BaseLayerChangeButton(288, 176, editorHandle, ScreenMapConstants.BASE_LAYER_DOOR_RIGHT));
		buttons.add(new BaseLayerChangeButton(304, 176, editorHandle, ScreenMapConstants.BASE_LAYER_DOOR_LEFT));
		return buttons;
	}

}
