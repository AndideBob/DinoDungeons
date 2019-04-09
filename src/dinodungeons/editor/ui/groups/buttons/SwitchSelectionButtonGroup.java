package dinodungeons.editor.ui.groups.buttons;

import java.util.ArrayList;
import java.util.Collection;

import dinodungeons.editor.Editor;
import dinodungeons.editor.ui.buttons.BaseButton;
import dinodungeons.editor.ui.buttons.mapchange.SwitchChangeButton;
import dinodungeons.editor.ui.groups.general.SwitchPlacementUIGroup;
import dinodungeons.game.data.gameplay.RoomEvent;

public class SwitchSelectionButtonGroup extends UIButtonGroup {
	
	private SwitchPlacementUIGroup belongingGroup;
	
	private RoomEvent selectedSwitch;
	
	public SwitchSelectionButtonGroup(final Editor editorHandle, final SwitchPlacementUIGroup belongingGroup){
		super(editorHandle);
		this.belongingGroup = belongingGroup;
		selectedSwitch = RoomEvent.SWITCH_A;
	}

	@Override
	protected Collection<? extends BaseButton> initializeButtons(final Editor editorHandle) {
		ArrayList<BaseButton> buttons = new ArrayList<>();
		buttons.add(new SwitchChangeButton(256, 90, this, RoomEvent.NONE));
		buttons.add(new SwitchChangeButton(272, 90, this, RoomEvent.SWITCH_A));
		buttons.add(new SwitchChangeButton(288, 90, this, RoomEvent.SWITCH_B));
		buttons.add(new SwitchChangeButton(304, 90, this, RoomEvent.SWITCH_C));
		buttons.add(new SwitchChangeButton(256, 74, this, RoomEvent.SWITCH_D));
		buttons.add(new SwitchChangeButton(272, 74, this, RoomEvent.SWITCH_AB));
		buttons.add(new SwitchChangeButton(288, 74, this, RoomEvent.SWITCH_ABC));
		buttons.add(new SwitchChangeButton(304, 74, this, RoomEvent.SWITCH_ABCD));
		return buttons;
	}
	
	public void setSwitch(RoomEvent switchType) {
		selectedSwitch = switchType;
		belongingGroup.setSwitch(switchType);
	}
	
	@Override
	protected void onActivate() {
		belongingGroup.setSwitch(selectedSwitch);
	}

}
