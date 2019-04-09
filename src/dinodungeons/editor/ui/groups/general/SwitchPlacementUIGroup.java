package dinodungeons.editor.ui.groups.general;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.UIElement;
import dinodungeons.editor.ui.groups.UIGroup;
import dinodungeons.editor.ui.groups.buttons.SwitchSelectionButtonGroup;
import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.game.data.gameplay.RoomEvent;

public class SwitchPlacementUIGroup extends UIElement implements UIGroup{

	private Editor editorHandle;
	
	private SwitchSelectionButtonGroup switchSelectionButtonGroup;
	
	private RoomEvent selectedSwitch;
	
	private int switchObjectID;
	
	public SwitchPlacementUIGroup(final Editor editorHandle) {
		this.editorHandle = editorHandle;
		selectedSwitch = RoomEvent.NONE;
		switchObjectID = 0;
		switchSelectionButtonGroup = new SwitchSelectionButtonGroup(editorHandle, this);
	}

	@Override
	public void setActive(boolean active) {
		switchSelectionButtonGroup.setActive(active);
	}
	
	@Override
	public final void update(InputInformation inputInformation) {
		switchSelectionButtonGroup.update(inputInformation);
	}

	@Override
	public final void draw() {
		switchSelectionButtonGroup.draw();
	}

	public void setSwitch(RoomEvent switchType) {
		selectedSwitch = switchType;
		sendSelectionInformationToEditor();
	}
	
	public void setSwitchObject(int switchObjectID) {
		this.switchObjectID = switchObjectID;
		sendSelectionInformationToEditor();
	}

	private void sendSelectionInformationToEditor() {
		editorHandle.setMapChange(MapChangeType.SWITCH_PLACEMENT, selectedSwitch.getStringRepresentation(), "" + switchObjectID);
	}

}
