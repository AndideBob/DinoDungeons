package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.DoorChangeButton;
import dinodungeons.game.data.map.objects.DoorMapObject.DoorType;

public class DoorPlacementButtonGroup extends UIButtonGroup {
	
	public DoorPlacementButtonGroup(final Editor editorHandle){
		super(editorHandle);
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new DoorChangeButton(256, 208, editorHandle, this, DoorType.SWITCH_A));
		buttons.add(new DoorChangeButton(272, 208, editorHandle, this, DoorType.SWITCH_B));
		buttons.add(new DoorChangeButton(288, 208, editorHandle, this, DoorType.SWITCH_C));
		buttons.add(new DoorChangeButton(304, 208, editorHandle, this, DoorType.SWITCH_D));
		buttons.add(new DoorChangeButton(256, 192, editorHandle, this, DoorType.SWITCH_AB));
		buttons.add(new DoorChangeButton(272, 192, editorHandle, this, DoorType.SWITCH_ABC));
		buttons.add(new DoorChangeButton(288, 192, editorHandle, this, DoorType.SWITCH_ABCD));
		buttons.add(new DoorChangeButton(304, 192, editorHandle, this, DoorType.ENEMIES));
		buttons.add(new DoorChangeButton(256, 176, editorHandle, this, DoorType.KEY));
		buttons.add(new DoorChangeButton(272, 176, editorHandle, this, DoorType.MASTER_KEY));
		return buttons;
	}

}
