package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.SwitchObjectChangeButton;
import dinodungeons.editor.ui.groups.general.SwitchPlacementUIGroup;

public class SwitchObjectButtonGroup extends UIButtonGroup {
	
	private SwitchPlacementUIGroup belongingGroup;
	
	private int selectedSwitchObjectID;
	
	public SwitchObjectButtonGroup(final Editor editorHandle, final SwitchPlacementUIGroup belongingGroup){
		super(editorHandle);
		this.belongingGroup = belongingGroup;
		selectedSwitchObjectID = 0;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new SwitchObjectChangeButton(256, 208, this, 0));
		buttons.add(new SwitchObjectChangeButton(272, 208, this, 1));
		return buttons;
	}
	
	public void setObjectID(int switchObjectID) {
		selectedSwitchObjectID = switchObjectID;
		belongingGroup.setSwitchObject(switchObjectID);
	}
	
	@Override
	protected void onActivate() {
		belongingGroup.setSwitchObject(selectedSwitchObjectID);
		buttons.get(selectedSwitchObjectID).setPressed(true);
	}

}
