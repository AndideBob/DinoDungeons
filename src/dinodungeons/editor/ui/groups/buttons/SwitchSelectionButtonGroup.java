package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.map.change.MapChangeType;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.SwitchChangeButton;
import dinodungeons.game.data.gameplay.RoomEvent;

public class SwitchSelectionButtonGroup extends UIButtonGroup {
	
	private Editor editorHandle;
	
	private RoomEvent selectedSwitch;
	
	private int switchObject;
	
	public SwitchSelectionButtonGroup(final Editor editorHandle){
		super(editorHandle);
		this.editorHandle = editorHandle;
		selectedSwitch = RoomEvent.SWITCH_A;
		switchObject = 0;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new SwitchChangeButton(256, 80, this, RoomEvent.SWITCH_A));
		buttons.add(new SwitchChangeButton(272, 80, this, RoomEvent.SWITCH_B));
		buttons.add(new SwitchChangeButton(288, 80, this, RoomEvent.SWITCH_C));
		buttons.add(new SwitchChangeButton(304, 80, this, RoomEvent.SWITCH_D));
		buttons.add(new SwitchChangeButton(256, 64, this, RoomEvent.SWITCH_AB));
		buttons.add(new SwitchChangeButton(272, 64, this, RoomEvent.SWITCH_ABC));
		buttons.add(new SwitchChangeButton(288, 64, this, RoomEvent.SWITCH_ABCD));
		return buttons;
	}
	
	public void setSwitch(RoomEvent switchType) {
		selectedSwitch = switchType;
		resendInformationToEditor();
	}
	
	public void setSwitchObject(int switchObject) {
		this.switchObject = switchObject;
	}
	
	private void resendInformationToEditor() {
		//TODO: Move sending of information to parenting class, so selection of pressed switches works
		editorHandle.setMapChange(MapChangeType.SWITCH_PLACEMENT, selectedSwitch.getStringRepresentation(), "" + switchObject);
	}
	
	@Override
	protected void onActivate() {
		resendInformationToEditor();
	}

}
